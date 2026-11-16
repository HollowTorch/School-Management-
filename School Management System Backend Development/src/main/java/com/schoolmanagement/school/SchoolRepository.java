package com.schoolmanagement.school;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SchoolRepository extends JpaRepository<School,Long> { Optional<School> findByCode(String code); boolean existsByCode(String code); }
