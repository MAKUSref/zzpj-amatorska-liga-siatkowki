package pl.lodz.p.it.ssbd2023.ssbd04;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericManagerExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Role;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.RoleType;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2023.ssbd04.security.JWTManager;
import pl.lodz.p.it.ssbd2023.ssbd04.security.managers.SecurityManager;

import java.util.*;

@ApplicationScoped
@Interceptors({GenericManagerExceptionsInterceptor.class})
public class JWTAuthMechanism implements HttpAuthenticationMechanism {
    @Inject
    private JWTManager jwtManager;

    @Inject
    private SecurityManager securityManager;

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

            Account account = securityManager.getAccountById(UUID.fromString(jws.getBody().getId()));

            if(account == null ){
                return httpMessageContext.doNothing();
            }

            Set<String> groups = new HashSet<>(account.getRoles().stream().map(Role::getRoleType).map(RoleType::toString).toList());

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


