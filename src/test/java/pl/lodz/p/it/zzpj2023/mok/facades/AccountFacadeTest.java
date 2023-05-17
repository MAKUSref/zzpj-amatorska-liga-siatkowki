package pl.lodz.p.it.zzpj2023.mok.facades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.security.enterprise.AuthenticationStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.lodz.p.it.zzpj2023.mok.entities.User;
import pl.lodz.p.it.ssbd2023.ssbd04.security.JWTAuthMechanism;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;


class AccountFacadeTest {

    @Mock
    EntityManager emMock;

    @Mock
    TypedQuery<User> typedQueryMock;

    AccountFacade accountFacade;

    User u1;
    User u2;
    User u3;
    List<User> userList;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        accountFacade = new AccountFacade();
        Field emField = AccountFacade.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(accountFacade, emMock);

        u1 = new User("User1", "Password1", "User1",
                "User1", "user1@mail.com", false,
                false, new Date(), Locale.CHINA);

        u2 = new User("User2", "Password2", "User2",
                "User2", "user2@mail.com", false,
                false, new Date(), Locale.CHINA);

        u3 = new User("User3", "Password3", "User3",
                "User3", "user3@mail.com", false,
                false, new Date(), Locale.CHINA);

        userList = Arrays.asList(u1, u2, u3);
    }

    @Test
    void findByLogin() {
        // Tworzenie instancji klasy, którą testujemy
        // Konfiguracja zachowania mocka dla createNamedQuery
        when(emMock.createNamedQuery(Mockito.eq("User.findByLogin"), Mockito.eq(User.class))).thenReturn(typedQueryMock);

        // Konfiguracja zachowania mocka dla setParameter
        when(typedQueryMock.setParameter(Mockito.eq("login"), anyString())).thenReturn(typedQueryMock);

        // Konfiguracja zachowania mocka dla getSingleResult
        User expectedUser = new User(); // Utwórz oczekiwanego użytkownika
        when(typedQueryMock.getSingleResult()).thenReturn(expectedUser);

        // Wywołanie metody, którą testujemy
        User result = accountFacade.findByLogin("testLogin");

        // Sprawdzenie, czy otrzymany wynik jest taki sam jak oczekiwany
        assertEquals(expectedUser, result);
    }

    @Test
    void findByEmail() {
        // Konfiguracja zachowania mocka dla createNamedQuery
        when(emMock.createNamedQuery(Mockito.eq("User.findByEmail"), Mockito.eq(User.class))).thenReturn(typedQueryMock);

        // Konfiguracja zachowania mocka dla setParameter
        when(typedQueryMock.setParameter(Mockito.eq("email"), anyString())).thenReturn(typedQueryMock);

        // Tworzenie oczekiwanego użytkownika
        User expectedUser = new User();

        // Konfiguracja zachowania mocka dla getSingleResult
        when(typedQueryMock.getSingleResult()).thenReturn(expectedUser);

        // Wywołanie metody, którą testujemy
        User result = accountFacade.findByEmail("test@example.com");

        // Sprawdzenie, czy otrzymany wynik jest taki sam jak oczekiwany
        assertEquals(expectedUser, result);
    }
}