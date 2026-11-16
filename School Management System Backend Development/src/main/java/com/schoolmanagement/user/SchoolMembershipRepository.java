package com.schoolmanagement.user;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SchoolMembershipRepository extends JpaRepository<SchoolMembership,Long> { Optional<SchoolMembership> findByUserIdAndSchoolIdAndActiveTrue(Long userId, Long schoolId); List<SchoolMembership> findAllByUserId(Long userId); }
