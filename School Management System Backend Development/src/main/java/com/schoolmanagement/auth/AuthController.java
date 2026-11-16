package com.schoolmanagement.auth;
import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/v1") public class AuthController { private final AuthService service; public AuthController(AuthService s){service=s;} @PostMapping("/setup/initial-school") public Map<String,Object> setup(@Valid @RequestBody AuthDtos.SetupRequest r){return service.setup(r);} @PostMapping("/auth/login") public AuthDtos.TokenResponse login(@Valid @RequestBody AuthDtos.LoginRequest r){return service.login(r);} }
