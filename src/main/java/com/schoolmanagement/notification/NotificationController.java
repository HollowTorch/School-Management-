package com.schoolmanagement.notification;

import com.schoolmanagement.security.UserContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('SCHOOL_ADMIN')")
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationRepository repo;
    private final UserContext ctx;

    public NotificationController(NotificationRepository repo, UserContext ctx) {
        this.repo = repo;
        this.ctx = ctx;
    }

    @GetMapping
    public List<NotificationResponse> list() {
        return repo.findAllBySchoolIdOrderByCreatedAtDesc(ctx.schoolId())
                .stream()
                .map(NotificationResponse::from)
                .toList();
    }
}