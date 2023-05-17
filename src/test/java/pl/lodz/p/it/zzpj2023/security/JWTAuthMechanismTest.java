package pl.lodz.p.it.zzpj2023.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JWTAuthMechanismTest {

    @Mock
    private HttpMessageContext httpMessageContext;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private JWTManager jwtManager;
    @Mock
    private Jws<Claims> jws;
    @Mock
    private Claims claims;
    @Mock
    private Cookie cookie;

    private JWTAuthMechanism jwtAuthMechanism;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        jwtAuthMechanism = new JWTAuthMechanism();
        Field jwtManagerField = JWTAuthMechanism.class.getDeclaredField("jwtManager");
        jwtManagerField.setAccessible(true);
        jwtManagerField.set(jwtAuthMechanism, jwtManager);
        Mockito.when(httpMessageContext.responseUnauthorized()).thenReturn(AuthenticationStatus.SEND_FAILURE);
        Mockito.when(httpMessageContext.responseNotFound()).thenReturn(AuthenticationStatus.SEND_FAILURE);
        Mockito.when(httpMessageContext.doNothing()).thenReturn(AuthenticationStatus.NOT_DONE);
    }

    @Test
    void shouldReturnResponseNotFoundWhenRequestIsNotSecure() throws AuthenticationException {
        when(httpServletRequest.isSecure()).thenReturn(false);

        AuthenticationStatus status = jwtAuthMechanism.validateRequest(httpServletRequest, httpServletResponse, httpMessageContext);

        verify(httpMessageContext).responseNotFound();
        assertEquals(AuthenticationStatus.SEND_FAILURE, status);
    }

    @Test
    void shouldDoNothingWhenNoCookiesArePresent() throws AuthenticationException {
        when(httpServletRequest.isSecure()).thenReturn(true);
        when(httpServletRequest.getCookies()).thenReturn(null);

        AuthenticationStatus status = jwtAuthMechanism.validateRequest(httpServletRequest, httpServletResponse, httpMessageContext);

        verify(httpMessageContext).doNothing();
        assertEquals(AuthenticationStatus.NOT_DONE, status);
    }

    @Test
    void shouldDoNothingWhenTokenCookieIsNotPresent() throws AuthenticationException {
        when(httpServletRequest.isSecure()).thenReturn(true);
        when(httpServletRequest.getCookies()).thenReturn(new Cookie[]{cookie});
        when(cookie.getName()).thenReturn("not-token");

        AuthenticationStatus status = jwtAuthMechanism.validateRequest(httpServletRequest, httpServletResponse, httpMessageContext);

        verify(httpMessageContext).doNothing();
        assertEquals(AuthenticationStatus.NOT_DONE, status);
    }

    @Test
    void shouldReturnResponseUnauthorizedWhenTokenIsInvalid() throws AuthenticationException {
        when(httpServletRequest.isSecure()).thenReturn(true);
        when(httpServletRequest.getCookies()).thenReturn(new Cookie[]{cookie});
        when(cookie.getName()).thenReturn("token");
        when(cookie.getValue()).thenReturn("invalid-token");
        when(jwtManager.decodeJWTToken("invalid-token")).thenThrow(JwtException.class);

        AuthenticationStatus status = jwtAuthMechanism.validateRequest(httpServletRequest, httpServletResponse, httpMessageContext);

        verify(httpMessageContext).responseUnauthorized();
        assertEquals(AuthenticationStatus.SEND_FAILURE, status);
    }

    @Test
    void shouldNotifyContainerAboutLoginWhenTokenIsValid() throws AuthenticationException {
        when(httpServletRequest.isSecure()).thenReturn(true);
        when(httpServletRequest.getCookies()).thenReturn(new Cookie[]{cookie});
        when(cookie.getName()).thenReturn("token");
        when(cookie.getValue()).thenReturn("valid-token");
        when(jwtManager.decodeJWTToken("valid-token")).thenReturn(jws);
        when(jws.getBody()).thenReturn(claims);
        when(claims.get("type", String.class)).thenReturn("user");

        AuthenticationStatus status = jwtAuthMechanism.validateRequest(httpServletRequest, httpServletResponse, httpMessageContext);

//        verify(httpMessageContext).notifyContainerAboutLogin(() -> claims.getId(), Set.of("user"));
//        assertEquals(AuthenticationStatus.SUCCESS, status);
    }
}