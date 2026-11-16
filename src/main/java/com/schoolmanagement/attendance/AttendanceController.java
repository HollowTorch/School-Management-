package com.schoolmanagement.attendance;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {
    private final AttendanceService service;
    public AttendanceController(AttendanceService service) { this.service = service; }

    public record ScanBody(@NotBlank String qrToken, @NotNull EventType eventType, String idempotencyKey) {}
    public record AttendanceResponse(Long id, Long studentId, String studentName, EventType eventType,
                                      Instant eventTime, String staffName) {
        static AttendanceResponse from(AttendanceEvent e) {
            return new AttendanceResponse(e.getId(), e.getStudent().getId(),
                    e.getStudent().getFirstName() + " " + e.getStudent().getLastName(),
                    e.getEventType(), e.getEventTime(), e.getActor().getFullName());
        }
    }

    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN', 'ATTENDANCE_STAFF')")
    @PostMapping("/scan")
    public AttendanceResponse scan(@Valid @RequestBody ScanBody body) {
        return AttendanceResponse.from(service.scan(new AttendanceService.ScanRequest(
                body.qrToken(), body.eventType(), body.idempotencyKey())));
    }

    @PreAuthorize("hasAnyRole('SCHOOL_ADMIN', 'ATTENDANCE_STAFF')")
    @GetMapping
    public List<AttendanceResponse> history() {
        return service.history().stream().map(AttendanceResponse::from).toList();
    }
}
