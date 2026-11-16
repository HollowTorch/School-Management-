package com.schoolmanagement.guardian;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface GuardianRepository extends JpaRepository<Guardian,Long> { List<Guardian> findAllBySchoolId(Long schoolId); Optional<Guardian> findByIdAndSchoolId(Long id, Long schoolId); }
