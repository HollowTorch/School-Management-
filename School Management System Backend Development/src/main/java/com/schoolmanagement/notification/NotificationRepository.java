package com.schoolmanagement.notification;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface NotificationRepository extends JpaRepository<Notification,Long> { List<Notification> findAllBySchoolIdOrderByCreatedAtDesc(Long schoolId); }
