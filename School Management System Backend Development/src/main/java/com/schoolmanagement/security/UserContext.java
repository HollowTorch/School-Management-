package com.schoolmanagement.security;
import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component;
@Component public class UserContext { public CurrentUser get(){ Object p=SecurityContextHolder.getContext().getAuthentication().getPrincipal(); if(!(p instanceof CurrentUser u)) throw new IllegalStateException("Authenticated user context missing"); return u; } public Long schoolId(){return get().schoolId();} public Long userId(){return get().userId();} }
