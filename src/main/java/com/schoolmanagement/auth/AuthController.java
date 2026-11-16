package com.schoolmanagement.auth;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/v1")
public class AuthController { private final AuthService service;
    public AuthController(AuthService s){service=s;}

    @PostMapping("/setup/initial-school")
    public Map<String,Object> setup(@Valid @RequestBody AuthDtos.SetupRequest r){return service.setup(r);}

    @PostMapping("/setup/register-school")
    public Map<String, Object> registerSchool(
            @Valid @RequestBody AuthDtos.SetupRequest request) {
        return service.registerSchool(request);
    }
    @PostMapping("/auth/login")
    public AuthDtos.TokenResponse login(@Valid @RequestBody AuthDtos.LoginRequest r){return service.login(r);}

    @PostMapping("/auth/attendance-staff")

    @PreAuthorize("hasRole('SCHOOL_ADMIN')")
    public AuthService.AttendanceStaffResponse createAttendanceStaff(
            @Valid @RequestBody AuthDtos.CreateAttendanceStaffRequest request) {

        return service.createAttendanceStaff(
                request.fullName(),
                request.email(),
                request.password());
    }
}
