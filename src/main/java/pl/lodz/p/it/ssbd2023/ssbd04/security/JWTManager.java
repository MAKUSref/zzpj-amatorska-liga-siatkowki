package pl.lodz.p.it.ssbd2023.ssbd04.security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.lodz.p.it.ssbd2023.ssbd04.security.dtos.CreateTokenDTO;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@ApplicationScoped
public class JWTManager {

    Config config = ConfigProvider.getConfig();
    Dotenv dotenv = Dotenv.load();
    Key hmacKey;
    public JWTManager() {
//        String secret = System.getenv("CLASSPATH");
        String secret = dotenv.get("JWT_SECRET");
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
                .setExpiration(Date.from(now.plus(config.getValue("jwt.duration", Long.class), ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
    }
}
