package com.schoolmanagement.classroom;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface ClassroomRepository extends JpaRepository<Classroom,Long> { List<Classroom> findAllBySchoolId(Long schoolId); Optional<Classroom> findByIdAndSchoolId(Long id, Long schoolId); }
