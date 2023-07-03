package pl.lodz.p.it.zzpj2023.mok.facades;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.lodz.p.it.zzpj2023.mok.entities.Account;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;


class AccountFacadeTest {

    @Mock
    EntityManager emMock;

    @Mock
    TypedQuery<Account> typedQueryMock;

    AccountFacade accountFacade;

    private Faker faker;

    Account u1;
    Account u2;
    Account u3;
    List<Account> accountList;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
        u1 = createRandomAccount();
        u2 = createRandomAccount();
        u3 = createRandomAccount();

        accountList = Arrays.asList(u1, u2, u3);
    }
    private Account createRandomAccount() {
        return new Account(
                getRandomLogin(),
                getRandomPassword(),
                getRandomFirstName(),
                getRandomLastName(),
                getRandomEmail(),
                false,
                false,
                new Date(),
                Locale.ENGLISH
        );
    }
    public String getRandomLogin() {
        return faker.name().username();
    }
    public String getRandomFirstName() {
        return faker.name().firstName();
    }
    public String getRandomLastName() {
        return faker.name().lastName();
    }
    public String getRandomPassword() {
        return faker.internet().password();
    }
    public String getRandomEmail() {
        return faker.internet().emailAddress();
    }
    @Test
    void findByLogin_ValidLogin_ReturnAccount() {
        // Tworzenie instancji klasy, którą testujemy
        // Konfiguracja zachowania mocka dla createNamedQuery
        when(emMock.createNamedQuery(Mockito.eq("Account.findByLogin"), Mockito.eq(Account.class))).thenReturn(typedQueryMock);

        // Konfiguracja zachowania mocka dla setParameter
        when(typedQueryMock.setParameter(Mockito.eq("login"), anyString())).thenReturn(typedQueryMock);

        // Konfiguracja zachowania mocka dla getSingleResult
        Account expectedAccount = new Account(); // Utwórz oczekiwanego użytkownika
        when(typedQueryMock.getSingleResult()).thenReturn(expectedAccount);

        // Wywołanie metody, którą testujemy
        Account result = accountFacade.findByLogin("testLogin");

        // Sprawdzenie, czy otrzymany wynik jest taki sam jak oczekiwany
        assertEquals(expectedAccount, result);
    }
    @Test
    void findByLogin_InvalidLogin_ThrowsException() {
        String login = getRandomLogin();

        when(emMock.createNamedQuery("Account.findByLogin", Account.class)).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter("login", login)).thenReturn(typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> accountFacade.findByLogin(login));


        verify(emMock).createNamedQuery("Account.findByLogin", Account.class);
        verify(typedQueryMock).setParameter("login", login);
        verify(typedQueryMock).getSingleResult();
    }


    @Test
    public void testFindByEmail_ValidEmail_ReturnsAccount() {
        // Arrange
        String validEmail = getRandomEmail();
        Account expectedAccount = new Account();
        when(emMock.createNamedQuery("Account.findByEmail", Account.class)).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter("email", validEmail)).thenReturn(typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenReturn(expectedAccount);

        // Act
        Account result = accountFacade.findByEmail(validEmail);

        // Assert
        verify(emMock).createNamedQuery("Account.findByEmail", Account.class);
        verify(typedQueryMock).setParameter("email", validEmail);
        verify(typedQueryMock).getSingleResult();
        assertEquals(expectedAccount, result);
    }

    @Test
    public void testFindByEmail_InvalidEmail_ThrowsException() {
        // Arrange
        String invalidEmail = getRandomEmail();

        when(emMock.createNamedQuery("Account.findByEmail", Account.class)).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter("email", invalidEmail)).thenReturn(typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenThrow(NoResultException.class);

        // Act & Assert
        assertThrows(NoResultException.class, () -> accountFacade.findByEmail(invalidEmail));

        verify(emMock).createNamedQuery("Account.findByEmail", Account.class);
        verify(typedQueryMock).setParameter("email", invalidEmail);
        verify(typedQueryMock).getSingleResult();
    }

    @Test
    public void testFindAllAdmins_NoAdminsFound() {
        // Test case setup
        List<Account> emptyList = Arrays.asList(); // Empty list to simulate no admins found

        // Mock the EntityManager and TypedQuery behavior
        Mockito.when(emMock.createNamedQuery("Admin.findAllAccountsThatAreAdmin", Account.class)).thenReturn(typedQueryMock);
        Mockito.when(typedQueryMock.getResultList()).thenReturn(emptyList);

        // Call the method under test
        List<Account> admins = accountFacade.findAllAdmins();

        // Assertions
        assertEquals(emptyList, admins);

        // Verify that the EntityManager and TypedQuery methods were called with the expected arguments
        Mockito.verify(emMock).createNamedQuery("Admin.findAllAccountsThatAreAdmin", Account.class);
        Mockito.verify(typedQueryMock).getResultList();
    }

    @Test
    public void testFindAllAdmins_AdminFound() {
        // Test case setup
        Account admin = new Account(); // Create a sample admin account
        List<Account> adminList = Arrays.asList(admin);

        // Mock the EntityManager and TypedQuery behavior
        Mockito.when(emMock.createNamedQuery("Admin.findAllAccountsThatAreAdmin", Account.class)).thenReturn(typedQueryMock);
        Mockito.when(typedQueryMock.getResultList()).thenReturn(adminList);

        // Call the method under test
        List<Account> admins = accountFacade.findAllAdmins();

        // Assertions
        assertEquals(adminList, admins);

        // Verify that the EntityManager and TypedQuery methods were called with the expected arguments
        Mockito.verify(emMock).createNamedQuery("Admin.findAllAccountsThatAreAdmin", Account.class);
        Mockito.verify(typedQueryMock).getResultList();
    }


    @Test
    public void testFindAllManagers_NoManagersFound() {
        // Test case setup
        List<Account> emptyList = Arrays.asList(); // Empty list to simulate no admins found

        // Mock the EntityManager and TypedQuery behavior
        Mockito.when(emMock.createNamedQuery("Manager.findAllAccountsThatAreManager", Account.class)).thenReturn(typedQueryMock);
        Mockito.when(typedQueryMock.getResultList()).thenReturn(emptyList);

        // Call the method under test
        List<Account> managers = accountFacade.findAllManagers();

        // Assertions
        assertEquals(emptyList, managers);

        // Verify that the EntityManager and TypedQuery methods were called with the expected arguments
        Mockito.verify(emMock).createNamedQuery("Manager.findAllAccountsThatAreManager", Account.class);
        Mockito.verify(typedQueryMock).getResultList();
    }

    @Test
    public void testFindAllManagers_ManagersFound() {
        // Test case setup
        Account manager = new Account(); // Create a sample admin account
        List<Account> managerList = Arrays.asList(manager);

        // Mock the EntityManager and TypedQuery behavior
        Mockito.when(emMock.createNamedQuery("Manager.findAllAccountsThatAreManager", Account.class)).thenReturn(typedQueryMock);
        Mockito.when(typedQueryMock.getResultList()).thenReturn(managerList);

        // Call the method under test
        List<Account> managers = accountFacade.findAllManagers();

        // Assertions
        assertEquals(managerList, managers);

        // Verify that the EntityManager and TypedQuery methods were called with the expected arguments
        Mockito.verify(emMock).createNamedQuery("Manager.findAllAccountsThatAreManager", Account.class);
        Mockito.verify(typedQueryMock).getResultList();
    }
}