package com.schoolmanagement.audit;
import com.schoolmanagement.common.BaseEntity; import com.schoolmanagement.school.School; import com.schoolmanagement.user.AppUser; import jakarta.persistence.*; import lombok.Getter; import lombok.Setter;
@Entity @Table(name="audit_events") @Getter @Setter
public class AuditEvent extends BaseEntity { @ManyToOne @JoinColumn(name="school_id") private School school; @ManyToOne @JoinColumn(name="actor_user_id") private AppUser actor; @Column(nullable=false) private String action; private String entityType; private String entityId; @Column(columnDefinition="text") private String details; }
