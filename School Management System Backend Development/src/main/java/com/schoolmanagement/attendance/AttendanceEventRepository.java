package com.schoolmanagement.attendance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceEventRepository extends JpaRepository<AttendanceEvent, Long> {
    Page<AttendanceEvent> findAllBySchoolIdOrderByEventTimeDesc(Long schoolId, Pageable pageable);
    Optional<AttendanceEvent> findTopBySchoolIdAndStudentIdOrderByEventTimeDesc(Long schoolId, Long studentId);
    boolean existsBySchoolIdAndIdempotencyKey(Long schoolId, String idempotencyKey);
}
