
package com.schoolmanagement.auth;

import com.schoolmanagement.school.*;
import com.schoolmanagement.user.*;
import com.schoolmanagement.security.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AuthService {

 private final UserContext ctx;
 private final SchoolRepository schools;
 private final AppUserRepository users;
 private final SchoolMembershipRepository memberships;
 private final PasswordEncoder encoder;
 private final JwtService jwt;

 public AuthService(
         UserContext ctx,
         SchoolRepository s,
         AppUserRepository u,
         SchoolMembershipRepository m,
         PasswordEncoder e,
         JwtService j) {

  this.ctx = ctx;
  schools = s;
  users = u;
  memberships = m;
  encoder = e;
  jwt = j;
 }

 @Transactional
 public Map<String, Object> setup(AuthDtos.SetupRequest r) {

  if (schools.count() > 0)
   throw new IllegalArgumentException(
           "Initial setup has already been completed");

  if (users.findByEmail(r.email()).isPresent())
   throw new IllegalArgumentException("Email already exists");

  School s = new School();
  s.setName(r.schoolName());
  s.setCode(r.schoolCode().toUpperCase());
  schools.save(s);

  AppUser u = new AppUser();
  u.setFullName(r.adminName());
  u.setEmail(r.email().toLowerCase());
  u.setPasswordHash(encoder.encode(r.password()));
  users.save(u);

  SchoolMembership m = new SchoolMembership();
  m.setSchool(s);
  m.setUser(u);
  m.setRole(Role.SCHOOL_ADMIN);
  memberships.save(m);

  return Map.of(
          "message", "Initial school and administrator created",
          "schoolId", s.getId(),
          "adminEmail", u.getEmail());
 }

 @Transactional
 public Map<String, Object> registerSchool(AuthDtos.SetupRequest r) {

  String schoolCode = r.schoolCode().trim().toUpperCase();
  String email = r.email().trim().toLowerCase();

  if (schools.existsByCode(schoolCode))
   throw new IllegalArgumentException("School code already exists");

  if (users.findByEmail(email).isPresent())
   throw new IllegalArgumentException("Email already exists");

  School s = new School();
  s.setName(r.schoolName());
  s.setCode(schoolCode);
  schools.save(s);

  AppUser u = new AppUser();
  u.setFullName(r.adminName());
  u.setEmail(email);
  u.setPasswordHash(encoder.encode(r.password()));
  users.save(u);

  SchoolMembership m = new SchoolMembership();
  m.setSchool(s);
  m.setUser(u);
  m.setRole(Role.SCHOOL_ADMIN);
  memberships.save(m);

  return Map.of(
          "message", "School and administrator registered successfully",
          "schoolId", s.getId(),
          "adminEmail", u.getEmail()
  );
 }

 public AuthDtos.TokenResponse login(AuthDtos.LoginRequest r) {

  AppUser u = users.findByEmail(r.email().toLowerCase())
          .orElseThrow(() ->
                  new IllegalArgumentException("Invalid email or password"));

  if (!u.isActive()
          || !encoder.matches(r.password(), u.getPasswordHash())) {
   throw new IllegalArgumentException("Invalid email or password");
  }

  SchoolMembership m = memberships
          .findByUserIdAndSchoolIdAndActiveTrue(
                  u.getId(), r.schoolId())
          .orElseThrow(() ->
                  new IllegalArgumentException(
                          "No active membership for this school"));

  String token = jwt.generate(
          u.getId(),
          m.getSchool().getId(),
          m.getRole().name(),
          u.getEmail());

  return new AuthDtos.TokenResponse(
          token, "Bearer", 43200,
          m.getSchool().getId(), m.getRole().name());
 }

 public record AttendanceStaffResponse(
         Long userId,
         String fullName,
         String email,
         Role role) {
 }

 @Transactional
 public AttendanceStaffResponse createAttendanceStaff(
         String fullName, String email, String password) {

  if (fullName == null || fullName.isBlank()
          || email == null || email.isBlank()) {
   throw new IllegalArgumentException(
           "Full name and email are required");
  }

  if (password == null || password.length() < 8) {
   throw new IllegalArgumentException(
           "Password must be at least 8 characters");
  }

  String normalizedEmail = email.trim().toLowerCase();

  if (users.findByEmail(normalizedEmail).isPresent()) {
   throw new IllegalArgumentException(
           "Email is already registered");
  }

  School school = schools.findById(ctx.schoolId())
          .orElseThrow(() ->
                  new NoSuchElementException("School not found"));

  AppUser user = new AppUser();
  user.setFullName(fullName.trim());
  user.setEmail(normalizedEmail);
  user.setPasswordHash(encoder.encode(password));

  user = users.save(user);

  SchoolMembership membership = new SchoolMembership();
  membership.setSchool(school);
  membership.setUser(user);
  membership.setRole(Role.ATTENDANCE_STAFF);
  membership.setActive(true);

  memberships.save(membership);

  return new AttendanceStaffResponse(
          user.getId(),
          user.getFullName(),
          user.getEmail(),
          Role.ATTENDANCE_STAFF);
 }
}
