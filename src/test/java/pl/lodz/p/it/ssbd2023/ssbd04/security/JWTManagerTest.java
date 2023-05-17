package pl.lodz.p.it.ssbd2023.ssbd04.security;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.entities.RoleType;
import pl.lodz.p.it.ssbd2023.ssbd04.security.dtos.CreateTokenDTO;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class JWTManagerTest {

    private static JWTManager jwtManager;
    private static Key hmacKey;
    private static List<RoleType> roles;
    private static final UUID clientId = UUID.randomUUID();
    private static CreateTokenDTO testToken;

    @BeforeAll
    static void setup() {
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        hmacKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        jwtManager = new JWTManager();
        testToken = new CreateTokenDTO();
        testToken.setClientId(clientId);
        roles = new ArrayList<RoleType>();
        roles.add(RoleType.ADMIN);
        testToken.setRoles(roles);
    }

    @Test
    void testDecodeJWTToken() {
        String token = jwtManager.createJWTToken(testToken);
        Jws<Claims> decoded = jwtManager.decodeJWTToken(token);
        assertEquals(clientId.toString(), decoded.getBody().getId().toString());
        assertEquals(roles.toString(), decoded.getBody().get("role").toString());
    }

    @Test
    void testInvalidTokenThrowsException() {
        assertThrows(MalformedJwtException.class, () -> jwtManager.decodeJWTToken("invalid-token"));
    }

    @Test
    void testCreateJWTToken() {
        String token = jwtManager.createJWTToken(testToken);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testCreateJWTTokenWithExpiration() {
        Instant now = Instant.now();
        String token = null;
        token = jwtManager.createJWTToken(testToken);

        // Verify that the expiration time is later than the current time
        Jws<Claims> decoded = jwtManager.decodeJWTToken(token);
        Instant expiration = decoded.getBody().getExpiration().toInstant();
        assertTrue(expiration.isAfter(now));
    }
}
