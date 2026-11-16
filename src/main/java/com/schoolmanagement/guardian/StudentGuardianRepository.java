package com.schoolmanagement.guardian;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface StudentGuardianRepository extends JpaRepository<StudentGuardian,Long> { List<StudentGuardian> findAllByStudentId(Long studentId); boolean existsByStudentIdAndGuardianId(Long studentId, Long guardianId); }
