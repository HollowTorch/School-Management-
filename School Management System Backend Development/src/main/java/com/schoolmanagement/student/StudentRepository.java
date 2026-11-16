package com.schoolmanagement.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAllBySchoolId(Long schoolId, Pageable pageable);
    Optional<Student> findByIdAndSchoolId(Long id, Long schoolId);
    boolean existsBySchoolIdAndAdmissionNumber(Long schoolId, String admissionNumber);
}
