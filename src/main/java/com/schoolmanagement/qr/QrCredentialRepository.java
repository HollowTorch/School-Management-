package com.schoolmanagement.qr;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface QrCredentialRepository extends JpaRepository<QrCredential,Long> { Optional<QrCredential> findByTokenHashAndActiveTrue(String tokenHash); }
