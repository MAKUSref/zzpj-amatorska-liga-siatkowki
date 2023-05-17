package pl.lodz.p.it.zzpj2023.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.it.zzpj2023.security.dtos.CreateTokenDTO;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@ApplicationScoped
public class JWTManager {
    Key hmacKey;
    public JWTManager() {
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4"; // TODO make it dynamic
        hmacKey  = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
    }

    public Jws<Claims> decodeJWTToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(token);
    }

    public String createJWTToken(CreateTokenDTO client) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim("role", client.getRoles().stream().map(role -> role.toString()).toList())
                .setId(client.getClientId().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1l, ChronoUnit.HOURS)))
                .signWith(hmacKey)
                .compact();
    }
}
