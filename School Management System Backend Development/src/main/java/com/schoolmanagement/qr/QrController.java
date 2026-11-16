package com.schoolmanagement.qr;
import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/v1/qr") public class QrController {private final QrService service;public QrController(QrService s){service=s;} @PostMapping("/students/{studentId}") public Map<String,Object> issue(@PathVariable Long studentId){return service.issue(studentId);} @DeleteMapping("/{credentialId}") public Map<String,String> revoke(@PathVariable Long credentialId){service.revoke(credentialId);return Map.of("message","QR credential revoked");}}
