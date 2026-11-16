package com.schoolmanagement.auth;
import jakarta.validation.constraints.*;
public class AuthDtos { public record SetupRequest(@NotBlank String schoolName,@NotBlank String schoolCode,@NotBlank String adminName,@Email @NotBlank String email,@NotBlank @Size(min=8) String password){} public record LoginRequest(@Email @NotBlank String email,@NotBlank String password,@NotNull Long schoolId){} public record TokenResponse(String accessToken,String tokenType,long expiresInSeconds,Long schoolId,String role){} }
