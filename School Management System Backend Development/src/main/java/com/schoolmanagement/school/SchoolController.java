package com.schoolmanagement.school;
import com.schoolmanagement.security.UserContext; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/v1/schools") public class SchoolController { private final SchoolRepository repo; private final UserContext ctx; public SchoolController(SchoolRepository r,UserContext c){repo=r;ctx=c;} @GetMapping("/me") public School me(){return repo.findById(ctx.schoolId()).orElseThrow(()->new NoSuchElementException("School not found"));} }
