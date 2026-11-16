package com.school.school_management_system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class HellloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Backend Developer!";
    }
}
