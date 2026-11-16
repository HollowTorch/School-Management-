package com.schoolmanagement.attendance;

import com.schoolmanagement.notification.*;
import com.schoolmanagement.qr.*;
import com.schoolmanagement.school.*;
import com.schoolmanagement.security.*;
import com.schoolmanagement.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceService {
    private final QrService qr;
    private final AttendanceEventRepository events;
    private final SchoolRepository schools;
    private final AppUserRepository users;
    private final UserContext ctx;
    private final NotificationService notifications;

    public AttendanceService(QrService qr, AttendanceEventRepository events, SchoolRepository schools,
                            AppUserRepository users, UserContext ctx, NotificationService notifications) {
        this.qr = qr;
        this.events = events;
        this.schools = schools;
        this.users = users;
        this.ctx = ctx;
        this.notifications = notifications;
    }

    public record ScanRequest(String qrToken, EventType eventType, String idempotencyKey) {}

    @Transactional
    public AttendanceEvent scan(ScanRequest r) {
        if (r.qrToken() == null || r.qrToken().isBlank() || r.eventType() == null) {
            throw new IllegalArgumentException("QR token and event type are required");
        }

        var credential = qr.validate(r.qrToken());
        if (!credential.getSchool().getId().equals(ctx.schoolId())) {
            throw new IllegalArgumentException("QR credential does not belong to your school");
        }

        var student = credential.getStudent();
        var last = events.findTopBySchoolIdAndStudentIdOrderByEventTimeDesc(ctx.schoolId(), student.getId());

        if (last.isPresent() && last.get().getEventType().equals(r.eventType())) {
            throw new IllegalArgumentException("Duplicate attendance action: student is already in this state");
        }

        if (r.eventType() == EventType.DEPARTURE && (last.isEmpty() || !last.get().getEventType().equals(EventType.ARRIVAL))) {
            throw new IllegalArgumentException("Student must have a recorded arrival before departure");
        }

        AttendanceEvent e = new AttendanceEvent();
        e.setSchool(schools.findById(ctx.schoolId()).orElseThrow());
        e.setStudent(student);
        e.setActor(users.findById(ctx.userId()).orElseThrow());
        e.setEventType(r.eventType());
        e.setIdempotencyKey(r.idempotencyKey());
        events.save(e);
        notifications.createFor(e);
        return e;
    }

    public Page<AttendanceEvent> history(int page, int size) {
        return events.findAllBySchoolIdOrderByEventTimeDesc(ctx.schoolId(), PageRequest.of(page, size));
    }
}
