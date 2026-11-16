
package com.schoolmanagement.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AttendanceEventRepository extends JpaRepository<AttendanceEvent, Long> {

    List<AttendanceEvent> findAllBySchoolIdOrderByEventTimeDesc(Long schoolId);

    Optional<AttendanceEvent> findTopBySchoolIdAndStudentIdOrderByEventTimeDesc(
            Long schoolId, Long studentId);

    Optional<AttendanceEvent> findBySchoolIdAndIdempotencyKey(
            Long schoolId, String idempotencyKey);
}
