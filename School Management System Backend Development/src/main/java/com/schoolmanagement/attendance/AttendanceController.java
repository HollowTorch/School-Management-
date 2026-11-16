package com.schoolmanagement.attendance;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {
    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    public record ScanBody(@NotBlank String qrToken, @NotNull EventType eventType, String idempotencyKey) {}

    public record AttendanceResponse(Long id, Long studentId, String studentName, EventType eventType,
                                    Instant eventTime, String staffName) {
        static AttendanceResponse from(AttendanceEvent e) {
            return new AttendanceResponse(
                    e.getId(),
                    e.getStudent().getId(),
                    e.getStudent().getFirstName() + " " + e.getStudent().getLastName(),
                    e.getEventType(),
                    e.getEventTime(),
                    e.getActor().getFullName()
            );
        }
    }

    @PostMapping("/scan")
    public AttendanceResponse scan(@Valid @RequestBody ScanBody body) {
        return AttendanceResponse.from(service.scan(new AttendanceService.ScanRequest(
                body.qrToken(), body.eventType(), body.idempotencyKey()
        )));
    }

    @GetMapping
    public Page<AttendanceResponse> history(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return service.history(page, size).map(AttendanceResponse::from);
    }
}
