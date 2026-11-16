
package com.schoolmanagement.attendance;

import com.schoolmanagement.qr.QrService;
import com.schoolmanagement.security.UserContext;
import com.schoolmanagement.school.SchoolRepository;
import com.schoolmanagement.user.AppUserRepository;
import com.schoolmanagement.notification.NotificationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendanceService {

 private final QrService qr;
 private final AttendanceEventRepository events;
 private final SchoolRepository schools;
 private final AppUserRepository users;
 private final UserContext ctx;
 private final NotificationService notifications;

 public AttendanceService(
         QrService q,
         AttendanceEventRepository e,
         SchoolRepository s,
         AppUserRepository u,
         UserContext c,
         NotificationService n) {
  qr = q;
  events = e;
  schools = s;
  users = u;
  ctx = c;
  notifications = n;
 }

 public record ScanRequest(
         String qrToken,
         EventType eventType,
         String idempotencyKey) {}

 @Transactional
 public AttendanceEvent scan(ScanRequest r) {

  if (r.qrToken() == null || r.qrToken().isBlank()
          || r.eventType() == null) {
   throw new IllegalArgumentException(
           "QR token and event type are required");
  }

  if (r.idempotencyKey() != null && !r.idempotencyKey().isBlank()
          && events.findBySchoolIdAndIdempotencyKey(
          ctx.schoolId(), r.idempotencyKey()).isPresent()) {
   throw new IllegalArgumentException(
           "This attendance request has already been processed");
  }

  var credential = qr.validate(r.qrToken());

  if (!credential.getSchool().getId().equals(ctx.schoolId())) {
   throw new IllegalArgumentException(
           "QR credential does not belong to your school");
  }

  var student = credential.getStudent();

  var last = events.findTopBySchoolIdAndStudentIdOrderByEventTimeDesc(
          ctx.schoolId(), student.getId());

  if (last.isPresent() && last.get().getEventType() == r.eventType()) {
   throw new IllegalArgumentException(
           "Duplicate attendance action: student is already in this state");
  }

  if (r.eventType() == EventType.DEPARTURE
          && (last.isEmpty()
          || last.get().getEventType() != EventType.ARRIVAL)) {
   throw new IllegalArgumentException(
           "Student must have a recorded arrival before departure");
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

 public List<AttendanceEvent> history() {
  return events.findAllBySchoolIdOrderByEventTimeDesc(ctx.schoolId());
 }
}
