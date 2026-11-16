package com.schoolmanagement.security;
public record CurrentUser(Long userId, Long schoolId, String role, String email) {}
