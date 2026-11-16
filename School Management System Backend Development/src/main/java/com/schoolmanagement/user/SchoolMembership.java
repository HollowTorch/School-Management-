package com.schoolmanagement.user;
import com.schoolmanagement.common.BaseEntity; import com.schoolmanagement.school.School; import jakarta.persistence.*; import lombok.Getter; import lombok.Setter;
@Entity @Table(name="school_memberships",uniqueConstraints=@UniqueConstraint(columnNames={"school_id","user_id"})) @Getter @Setter
public class SchoolMembership extends BaseEntity { @ManyToOne(optional=false) @JoinColumn(name="school_id") private School school; @ManyToOne(optional=false) @JoinColumn(name="user_id") private AppUser user; @Enumerated(EnumType.STRING) @Column(nullable=false,length=40) private Role role; private boolean active=true; }
