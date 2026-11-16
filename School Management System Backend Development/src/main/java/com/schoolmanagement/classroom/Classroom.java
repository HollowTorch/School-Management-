package com.schoolmanagement.classroom;
import com.schoolmanagement.common.BaseEntity; import com.schoolmanagement.school.School; import jakarta.persistence.*; import lombok.Getter; import lombok.Setter;
@Entity @Table(name="classrooms") @Getter @Setter
public class Classroom extends BaseEntity { @ManyToOne(optional=false) @JoinColumn(name="school_id") private School school; @Column(nullable=false) private String name; private String academicYear; }
