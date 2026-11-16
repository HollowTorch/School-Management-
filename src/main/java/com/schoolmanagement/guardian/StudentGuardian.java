package com.schoolmanagement.guardian;
import com.schoolmanagement.common.BaseEntity; import com.schoolmanagement.student.Student; import jakarta.persistence.*; import lombok.Getter; import lombok.Setter;
@Entity @Table(name="student_guardians",uniqueConstraints=@UniqueConstraint(columnNames={"student_id","guardian_id"})) @Getter @Setter
public class StudentGuardian extends BaseEntity { @ManyToOne(optional=false) @JoinColumn(name="student_id") private Student student; @ManyToOne(optional=false) @JoinColumn(name="guardian_id") private Guardian guardian; private String relationship; private boolean receivesNotifications=true; }
