
package com.schoolmanagement.classroom;

import com.schoolmanagement.school.SchoolRepository;
import com.schoolmanagement.security.UserContext;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('SCHOOL_ADMIN')")
@RestController
@RequestMapping("/api/v1/classes")
public class ClassroomController {

    private final ClassroomRepository repo;
    private final SchoolRepository schools;
    private final UserContext ctx;

    public ClassroomController(ClassroomRepository r, SchoolRepository s, UserContext c) {
        repo = r;
        schools = s;
        ctx = c;
    }

    public record ClassroomRequest(@NotBlank String name, String academicYear) {
    }


    @GetMapping
    public List<Classroom> list() {
        return repo.findAllBySchoolId(ctx.schoolId());
    }

    @PostMapping
    public Classroom create(@Valid @RequestBody ClassroomRequest r) {
        {
            Classroom c = new Classroom();
            c.setSchool(schools.findById(ctx.schoolId()).orElseThrow());
            c.setName(r.name());
            c.setAcademicYear(r.academicYear());
            return repo.save(c);
        }

    }
}
