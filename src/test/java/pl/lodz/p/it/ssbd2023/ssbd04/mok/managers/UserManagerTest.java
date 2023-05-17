package pl.lodz.p.it.ssbd2023.ssbd04.mok.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.entities.Role;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.entities.User;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.facades.AccountFacade;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserManagerTest {

    @InjectMocks
    private UserManager userManager;

    @Mock
    private AccountFacade accountFacadeMock;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        userManager = new UserManager();
        Field accountFacadeField = UserManager.class.getDeclaredField("accountFacade");
        accountFacadeField.setAccessible(true);
        accountFacadeField.set(userManager, accountFacadeMock);
    }

    @Test
    void getUserByLogin() {
        User u = new User("JKowal", "P@ssw0rd",
                "Jan", "Kowalski", "jan@mail.com",
                false, false, new Date(), Locale.US);
        Mockito.when(accountFacadeMock.findByLogin("JKowal")).thenReturn(u);
        User userFromDb = userManager.getUserByLogin("JKowal");
        assertEquals("Jan", userFromDb.getName());
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getAllAdmins() {
    }

    @Test
    void getAllManagers() {
    }

    @Test
    void getAllCoaches() {
    }

    @Test
    void getAllReferees() {
    }

    @Test
    void getAllCaptains() {
    }

    @Test
    void sendResetPasswordEmail() {
    }

    @Test
    void resetPassword() {
    }
}