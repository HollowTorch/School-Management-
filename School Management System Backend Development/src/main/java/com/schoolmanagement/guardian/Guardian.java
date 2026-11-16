package com.schoolmanagement.guardian;
import com.schoolmanagement.common.BaseEntity; import com.schoolmanagement.school.School; import jakarta.persistence.*; import lombok.Getter; import lombok.Setter;
@Entity @Table(name="guardians") @Getter @Setter
public class Guardian extends BaseEntity { @ManyToOne(optional=false) @JoinColumn(name="school_id") private School school; @Column(nullable=false) private String fullName; @Column(nullable=false) private String phone; private String email; private boolean active=true; }
