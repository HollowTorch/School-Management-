package com.schoolmanagement.user;
import com.schoolmanagement.common.BaseEntity; import jakarta.persistence.*; import lombok.Getter; import lombok.Setter;
@Entity @Table(name="app_users") @Getter @Setter
public class AppUser extends BaseEntity { @Column(name="full_name",nullable=false) private String fullName; @Column(nullable=false,unique=true) private String email; @Column(name="password_hash",nullable=false) private String passwordHash; private boolean active=true; }
