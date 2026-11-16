package com.schoolmanagement.user;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface AppUserRepository extends JpaRepository<AppUser,Long> { Optional<AppUser> findByEmail(String email); }
