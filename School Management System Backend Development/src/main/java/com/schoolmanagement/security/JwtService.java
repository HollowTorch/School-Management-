package com.schoolmanagement.security;
import io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.time.Instant; import java.util.Date;
@Service public class JwtService {
 private final SecretKey key; private final long expiry;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-minutes}") long expiry){ if(secret.getBytes(StandardCharsets.UTF_8).length<32) throw new IllegalArgumentException("JWT_SECRET must be at least 32 bytes"); this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.expiry=expiry; }
 public String generate(Long userId,Long schoolId,String role,String email){ Instant now=Instant.now(); return Jwts.builder().subject(userId.toString()).claim("schoolId",schoolId).claim("role",role).claim("email",email).issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(expiry*60))).signWith(key).compact(); }
 public CurrentUser parse(String token){ var c=Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload(); Number schoolId=(Number)c.get("schoolId"); return new CurrentUser(Long.valueOf(c.getSubject()),schoolId.longValue(),c.get("role",String.class),c.get("email",String.class)); }
}
