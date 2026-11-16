package com.schoolmanagement.qr;
import com.schoolmanagement.common.BaseEntity; import com.schoolmanagement.school.School; import com.schoolmanagement.student.Student; import jakarta.persistence.*; import lombok.Getter; import lombok.Setter; import java.time.Instant;
@Entity @Table(name="qr_credentials") @Getter @Setter
public class QrCredential extends BaseEntity { @ManyToOne(optional=false) @JoinColumn(name="school_id") private School school; @ManyToOne(optional=false) @JoinColumn(name="student_id") private Student student; @Column(name="token_hash",nullable=false,unique=true,length=64) private String tokenHash; private boolean active=true; private Instant issuedAt=Instant.now(); private Instant revokedAt; }
