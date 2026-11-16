package com.schoolmanagement.attendance;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface AttendanceEventRepository extends JpaRepository<AttendanceEvent,Long> { List<AttendanceEvent> findAllBySchoolIdOrderByEventTimeDesc(Long schoolId); Optional<AttendanceEvent> findTopBySchoolIdAndStudentIdOrderByEventTimeDesc(Long schoolId, Long studentId); }
