package com.schoolmanagement.student;
import com.schoolmanagement.common.BaseEntity; import com.schoolmanagement.school.School; import com.schoolmanagement.classroom.Classroom; import jakarta.persistence.*; import lombok.Getter; import lombok.Setter; import java.time.LocalDate;
@Entity @Table(name="students") @Getter @Setter
public class Student extends BaseEntity { @ManyToOne(optional=false) @JoinColumn(name="school_id") private School school; @ManyToOne @JoinColumn(name="classroom_id") private Classroom classroom; @Column(nullable=false) private String admissionNumber; @Column(nullable=false) private String firstName; @Column(nullable=false) private String lastName; private LocalDate dateOfBirth; private boolean active=true; }
