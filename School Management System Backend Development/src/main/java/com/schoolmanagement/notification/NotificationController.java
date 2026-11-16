package com.schoolmanagement.notification;
import com.schoolmanagement.security.UserContext;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/v1/notifications") public class NotificationController {private final NotificationRepository repo;private final UserContext ctx;public NotificationController(NotificationRepository r,UserContext c){repo=r;ctx=c;}@GetMapping public List<Notification> list(){return repo.findAllBySchoolIdOrderByCreatedAtDesc(ctx.schoolId());}}
