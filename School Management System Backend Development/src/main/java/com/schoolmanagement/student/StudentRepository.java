package com.schoolmanagement.student;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface StudentRepository extends JpaRepository<Student,Long> { List<Student> findAllBySchoolId(Long schoolId); Optional<Student> findByIdAndSchoolId(Long id, Long schoolId); boolean existsBySchoolIdAndAdmissionNumber(Long schoolId, String admissionNumber); }
