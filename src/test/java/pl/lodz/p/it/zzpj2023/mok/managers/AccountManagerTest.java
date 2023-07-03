package pl.lodz.p.it.zzpj2023.mok.managers;

import com.github.javafaker.Faker;
import jakarta.security.enterprise.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.lodz.p.it.zzpj2023.mok.entities.Account;
import pl.lodz.p.it.zzpj2023.mok.entities.AccountHistory;
import pl.lodz.p.it.zzpj2023.mok.entities.Manager;
import pl.lodz.p.it.zzpj2023.mok.facades.AccountFacade;
import pl.lodz.p.it.zzpj2023.mok.facades.AccountHistoryFacade;
import pl.lodz.p.it.zzpj2023.security.EmailService;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class AccountManagerTest {

    @Spy
    @InjectMocks
    private AccountManager accountManager;

    @Mock
    private AccountFacade accountFacadeMock;

    @Mock
    private TokenManager tokenManagerMock;

    @Mock
    private AccountHistoryFacade accountHistoryFacadeMock;

    @Mock
    private RolesFacade rolesFacedMock;
    @Mock
    private SecurityContext securityContextMock;
    @Mock
    private EmailService emailServiceMock;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    @Captor
    private ArgumentCaptor<AccountHistory> accountHistoryCaptor;

    @Mock
    private Account accountMock;
    @Mock
    private Principal principalMock;
    @Mock
    private Coach roleMock;
    @Mock
    private Collection<Role> roleListMock;
    @Mock
    private Manager managerMock;
    @Mock
    private EditAccountLanguageDTO editAccountLanguageDTOMock;

    private Faker faker;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
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
    public void shouldReturnExceptionWhenAdminLoginIsTheSameLogin() {
        // Test case setup
        String login = getRandomLogin();
        String admin = login;
        long version = 1;
        // Mock the SecurityContext behavior
        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principalMock);
        Mockito.when(principalMock.getName()).thenReturn(admin);

        // Call the method under test
        assertThrows(RuntimeException.class, () -> {
            accountManager.addAccessLevel(login, roleMock, version);
        });

        // Verify that the AccountFacade and Account methods were called with the expected arguments
        verify(principalMock).getName();
    }

    @Test
    public void shouldReturnExceptionWhenAccountWithLoginNotFound() {
        // Test case setup
        String login = getRandomLogin();
        String admin = getRandomLogin();
        long version = 1;
        // Mock the SecurityContext behavior
        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principalMock);
        Mockito.when(principalMock.getName()).thenReturn(admin);
        Mockito.when(accountFacadeMock.findByLogin(login)).thenReturn(accountMock);
        // Call the method under test
        assertThrows(RuntimeException.class, () -> {
            accountManager.addAccessLevel(login, roleMock, version);
        });
        // assertThrows(NoResultException.class, () -> accountFacadeMock.findByEmail(login));

        // Verify that the AccountFacade and Account methods were called with the expected arguments
        verify(principalMock, atLeast(1)).getName();
        verify(accountFacadeMock).findByLogin(login);

    }

    @Test
    public void shouldReturnExceptionWhenAccountIsNoActive() {
        // Test case setup
        String login = getRandomLogin();
        String admin = getRandomLogin();
        long version = 1;
        // Mock the SecurityContext behavior
        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principalMock);
        Mockito.when(principalMock.getName()).thenReturn(admin);
        Mockito.when(accountFacadeMock.findByLogin(login)).thenReturn(accountMock);
        Mockito.when(accountMock.isActive()).thenReturn(false);
        // Call the method under test
        assertThrows(RuntimeException.class, () -> {
            accountManager.addAccessLevel(login, roleMock, version);
        });
        // assertThrows(NoResultException.class, () -> accountFacadeMock.findByEmail(login));

        // Verify that the AccountFacade and Account methods were called with the expected arguments
        verify(principalMock, atLeast(1)).getName();
        verify(accountFacadeMock).findByLogin(login);
        // verify(accountMock).isActive();
    }

    @Test
    public void shouldReturnExceptionWhenAccountContainsTheRole() {
        // Test case setup
        String login = getRandomLogin();
        String admin = getRandomLogin();
        long version = 1;

        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principalMock);
        Mockito.when(principalMock.getName()).thenReturn(admin);
        Mockito.when(accountFacadeMock.findByLogin(login)).thenReturn(accountMock);
        Mockito.when(accountMock.isActive()).thenReturn(true);
        List<Role> roles = new ArrayList<>();
        roles.add(managerMock);
        Mockito.when(accountMock.getRoles()).thenReturn(roles);


        assertThrows(RuntimeException.class, () -> {
            accountManager.addAccessLevel(login, roleMock, version);
        });

        verify(principalMock, atLeast(1)).getName();
        verify(accountFacadeMock).findByLogin(login);
        // verify(accountMock).isActive();
        verify(managerMock, Mockito.never()).setAccount(accountMock);

        assertEquals(1, accountMock.getRoles().size()); // Role count remains the same
        assertTrue(accountMock.getRoles().contains(managerMock));
    }

    @Test
    public void addRoleSuccessTest() {
        // Test case setup
        String login = "d93b7ab9-da9b-490c-a096-5c48437cd3a9";
        String admin = "aaaaaaa9-da9b-490c-a096-5c48437cd3a9";
        long version = 1;

        // Mock the SecurityContext behavior
        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principalMock);
        Mockito.when(principalMock.getName()).thenReturn(admin);
        Mockito.when(accountFacadeMock.findByLogin(login)).thenReturn(accountMock);
        Mockito.when(accountMock.isActive()).thenReturn(true);
        List<Role> roles = new ArrayList<>();
        Manager manager = new Manager();
        Admin admin1 = new Admin();
        roles.add(manager);
        assertEquals(1, roles.size());
        Mockito.when(accountMock.getRoles()).thenReturn(roles);
        Mockito.when(accountFacadeMock.find(login)).thenReturn(accountMock);
        Mockito.when(accountMock.getLocale()).thenReturn(new Locale("PL"));
        Mockito.when(accountMock.getId()).thenReturn(UUID.randomUUID());
        // Call the method under test
        accountManager.addAccessLevel(login, admin1, version);

        verify(principalMock).getName();
        verify(accountFacadeMock).findByLogin(login);
        verify(accountMock).isActive();
        assertNotNull(admin1.getAccount());
        assertTrue(roles.contains(admin1));
        // Assertions
        assertEquals(2, roles.size());
    }

    @Test
    public void shouldReturnExceptionWhenAdminLoginIsTheSameLoginRemoveAccessTest() {
        // Test case setup
        String login = getRandomLogin();
        String admin = login;
        // Mock the SecurityContext behavior
        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principalMock);
        Mockito.when(principalMock.getName()).thenReturn(admin);

        // Call the method under test
        assertThrows(RuntimeException.class, () -> {
            accountManager.removeAccessLevel(login, roleMock);
        });
        // Verify that the AccountFacade and Account methods were called with the expected arguments
        verify(principalMock).getName();
    }

    @Test
    public void shouldReturnExceptionWhenAccountWithLoginNotFoundRemoveAccessTest() {
        // Test case setup
        String login = getRandomLogin();
        String admin = getRandomLogin();
        // Mock the SecurityContext behavior
        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principalMock);
        Mockito.when(principalMock.getName()).thenReturn(admin);
        Mockito.when(accountFacadeMock.findByLogin(login)).thenReturn(accountMock);
        // Call the method under test
        assertThrows(RuntimeException.class, () -> {
            accountManager.removeAccessLevel(login, roleMock);
        });
        // assertThrows(NoResultException.class, () -> accountFacadeMock.findByEmail(login));

        // Verify that the AccountFacade and Account methods were called with the expected arguments
        verify(principalMock).getName();
        verify(accountFacadeMock).findByLogin(login);

    }

    @Test
    public void shouldReturnExceptionWhenAccountIsNoActiveRemoveAccessTest() {
        // Test case setup
        String login = getRandomLogin();
        String admin = getRandomLogin();
        // Mock the SecurityContext behavior
        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principalMock);
        Mockito.when(principalMock.getName()).thenReturn(admin);
        Mockito.when(accountFacadeMock.findByLogin(login)).thenReturn(accountMock);
        Mockito.when(accountMock.isActive()).thenReturn(false);
        // Call the method under test
        assertThrows(RuntimeException.class, () -> {
            accountManager.removeAccessLevel(login, roleMock);
        });
        // assertThrows(NoResultException.class, () -> accountFacadeMock.findByEmail(login));

        // Verify that the AccountFacade and Account methods were called with the expected arguments
        verify(principalMock).getName();
        verify(accountFacadeMock).findByLogin(login);
        // verify(accountMock).isActive();
    }

    @Test
    public void shouldReturnExceptionWhenAccountHasOnlyOneRoleRemoveAccessTest() {
        // Test case setup
        String login = getRandomLogin();
        String admin = getRandomLogin();

        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principalMock);
        Mockito.when(principalMock.getName()).thenReturn(admin);
        Mockito.when(accountFacadeMock.findByLogin(login)).thenReturn(accountMock);
        Mockito.when(accountMock.isActive()).thenReturn(true);
        List<Role> roles = new ArrayList<>();
        roles.add(managerMock);
        Mockito.when(accountMock.getRoles()).thenReturn(roles);

        assertThrows(RuntimeException.class, () -> {
            accountManager.removeAccessLevel(login, roleMock);
        });

        verify(principalMock).getName();
        verify(accountFacadeMock).findByLogin(login);
        // verify(accountMock).isActive();
        verify(managerMock, Mockito.never()).setAccount(accountMock);

        assertEquals(1, roles.size()); // Role count remains the same
    }

    @Test
    public void RemoveRoleSuccessTest() {
        // Test case setup
        String login = "d93b7ab9-da9b-490c-a096-5c48437cd3a9";
        String admin = "aaaaaaa9-da9b-490c-a096-5c48437cd3a9";

        // Mock the SecurityContext behavior
        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principalMock);
        Mockito.when(principalMock.getName()).thenReturn(admin);
        Mockito.when(accountFacadeMock.findByLogin(login)).thenReturn(accountMock);
        Mockito.when(accountMock.isActive()).thenReturn(true);
        List<Role> roles = new ArrayList<>();
        Manager manager = new Manager();
        Coach coach = new Coach();
        roles.add(manager);
        roles.add(coach);
        assertEquals(2, roles.size());

        Mockito.when(accountMock.getRoles()).thenReturn(roles);
        Mockito.when(accountFacadeMock.find(login)).thenReturn(accountMock);
        Mockito.when(accountMock.getLocale()).thenReturn(new Locale("PL"));
        Mockito.when(accountMock.getId()).thenReturn(UUID.fromString("d93b7ab9-da9b-490c-a096-5c48437cd3a9"));
        // Call the method under test
        accountManager.removeAccessLevel(login, coach);

        verify(principalMock).getName();
        verify(accountFacadeMock).findByLogin(login);
        verify(accountMock).isActive();
        verify(rolesFacedMock).remove(coach);
        // Assertions
        assertEquals(1, roles.size());
    }
    @Test
    public void testCreateAccount() {
        // Mock objects
        Account account = new Account();
        Role role = new Role();

        // Set up the mock behavior
        doNothing().when(accountFacadeMock).create(account);

        // Call the method
        Account result = accountManager.createAccount(account, role);

        // Verify the interactions
        verify(accountFacadeMock, times(1)).create(account);

        // Assert the result
        assertNotNull(result);
        assertEquals(account, result);
    }
    @Test
    public void testCreateAccountAsAdmin() {
        // Mock objects
        Account account = new Account();
        Role role = new Role();

        // Set up the mock behavior
        doNothing().when(accountFacadeMock).create(account);
        Mockito.when(accountFacadeMock.find(account.getId())).thenReturn(account);

        Principal principal = Mockito.mock(Principal.class);

        Mockito.when(principal.getName()).thenReturn("d93b7ab9-da9b-490c-a096-5c48437cd3a9"); // Ustawienie wartości zwrotnej dla getName()
        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principal);
        // Call the method
        Account result = accountManager.createAccountAsAdmin(account, role);

        // Verify the interactions
        verify(accountFacadeMock, times(1)).create(account);

        // Assert the result
        assertNotNull(result);
        assertEquals(account, result);
    }
    @Test
    public void testGetAccountByUUID_existingAccount() {
        // Mock objects
        UUID id = UUID.randomUUID();
        Account account = new Account();
        when(accountFacadeMock.find(id)).thenReturn(account);

        // Call the method
        Account result = accountManager.getAccountByUUID(id);

        // Verify the interactions
        verify(accountFacadeMock, times(1)).find(id);

        // Assert the result
        assertNotNull(result);
        assertEquals(account, result);
    }

    @Test
    public void testCheckIsEmailPast24Hours_Positive() {
        // Current date and time
        Date currentDate = new Date();

        // Set date 23 hours before
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR_OF_DAY, -23);
        Date twentyThreeHoursAgo = calendar.getTime();

        // Check if message was sent before 24h ago
        boolean result = accountManager.checkIsEmailPast24Hours(twentyThreeHoursAgo);
        assertEquals(false, result);
    }

    @Test
    public void testCheckIsEmailPast24Hours_Negative() {
        // Current date and time
        Date currentDate = new Date();

        // Set date 25 hours before
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR_OF_DAY, -25);
        Date twentyFiveHoursAgo = calendar.getTime();

        // Check if message was sent before 24h ago
        boolean result = accountManager.checkIsEmailPast24Hours(twentyFiveHoursAgo);

        assertTrue(result);
    }

    @Test
    public void testAddAccountCreatedToHistory() {
        MockitoAnnotations.initMocks(this);

        // Przygotowanie danych testowych
        Account account = new Account();
        Role role = new Role();
        String changeType = "Create";
        String property = "Created";

        // Wywołanie metody, którą chcemy przetestować
        accountManager.createAccount(account, role);

        // Weryfikacja, czy metody zostały wywołane z odpowiednimi argumentami
        verify(accountFacadeMock).create(accountCaptor.capture());
        verify(accountHistoryFacadeMock).create(accountHistoryCaptor.capture());

        // Sprawdzenie argumentów przekazanych do metody updateAccountHistory
        Account changedBy = accountHistoryCaptor.getValue().getChangedBy();
        Account changedAccount = accountHistoryCaptor.getValue().getChangedAccount();
        Date date = accountHistoryCaptor.getValue().getChangedAt();
        String capturedChangeType = accountHistoryCaptor.getValue().getChangeType();
        String capturedProperty = accountHistoryCaptor.getValue().getProperty();

        assertEquals(account, changedBy);
        assertEquals(account, changedAccount);
        assertNotNull(date);
        assertEquals(changeType, capturedChangeType);
        assertEquals(property, capturedProperty);
    }

    @Test
    public void testEditSelfAccount() {
        // Arrange
        EditAccountDTO editAccountDTO = new EditAccountDTO();
        String currentAccountUUID = "123e4567-e89b-12d3-a456-556642440000";
        Account currentAccount = new Account();
        Account accountToEdit = new Account();

        Principal principal = Mockito.mock(Principal.class);

        Mockito.when(principal.getName()).thenReturn("account123"); // Ustawienie wartości zwrotnej dla getName()
        Mockito.when(securityContextMock.getCallerPrincipal()).thenReturn(principal);
        Mockito.when(accountFacadeMock.find(currentAccountUUID)).thenReturn(currentAccount);
        Mockito.when(securityContextMock.getCallerPrincipal().getName()).thenReturn(currentAccountUUID);
        Mockito.when(accountFacadeMock.find(UUID.fromString(currentAccountUUID))).thenReturn(accountToEdit);

        ArgumentCaptor<Account> changedByCaptor = ArgumentCaptor.forClass(Account.class);
        ArgumentCaptor<Account> changedAccountCaptor = ArgumentCaptor.forClass(Account.class);
        ArgumentCaptor<String> changeTypeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> propertyCaptor = ArgumentCaptor.forClass(String.class);

        // Act
        accountManager.editSelfAccount(editAccountDTO);

        // Assert
        Mockito.verify(accountManager).updateAccountHistory(changedByCaptor.capture(), changedAccountCaptor.capture(), changeTypeCaptor.capture(), propertyCaptor.capture());

        // Verify the captured values
        Account capturedChangedBy = changedByCaptor.getValue();
        Account capturedChangedAccount = changedAccountCaptor.getValue();
        String capturedChangeType = changeTypeCaptor.getValue();
        String capturedProperty = propertyCaptor.getValue();

        // Add your assertions here based on the captured values
        // For example:
        assertEquals(currentAccount, capturedChangedBy);
        assertEquals(accountToEdit, capturedChangedAccount);
        assertEquals("Update", capturedChangeType);
        assertEquals("Details", capturedProperty);
    }
}