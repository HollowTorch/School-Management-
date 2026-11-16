package com.schoolmanagement.school;
import com.schoolmanagement.common.BaseEntity; import jakarta.persistence.*; import lombok.Getter; import lombok.Setter;
@Entity @Table(name="schools") @Getter @Setter
public class School extends BaseEntity { @Column(nullable=false,length=180) private String name; @Column(nullable=false,unique=true,length=40) private String code; private String email; private String phone; @Column(columnDefinition="text") private String address; private boolean active=true; }
