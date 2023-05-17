package pl.lodz.p.it.zzpj2023.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;
import java.util.logging.Logger;

@ApplicationScoped
public class JWTAuthMechanism implements HttpAuthenticationMechanism {

    private final Logger logger = Logger.getLogger(JWTAuthMechanism.class.toString());
    @Inject
    private JWTManager jwtManager;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {
        if (!httpServletRequest.isSecure()) {
            return httpMessageContext.responseNotFound();
        }
        if (httpServletRequest.getCookies() == null || httpServletRequest.getCookies().length == 0) {
            return httpMessageContext.doNothing();
        }
        Optional<Cookie> optToken = Arrays.stream(httpServletRequest.getCookies()).filter((cookie) -> Objects.equals(cookie.getName(), "token")).findFirst();
        if (optToken.isEmpty()) {
            return httpMessageContext.doNothing();
        }

        String token = optToken.get().getValue();
        try {
            Jws<Claims> jws = jwtManager.decodeJWTToken(token);
            Set<String> groups = new HashSet<>();
            groups.add(jws.getBody().get("type", String.class));
            return httpMessageContext.notifyContainerAboutLogin(
                    () -> jws.getBody().getId(),
                    groups
            );
        } catch (JwtException exception) {
            exception.printStackTrace();
            return httpMessageContext.responseUnauthorized();
        }
    }
}


