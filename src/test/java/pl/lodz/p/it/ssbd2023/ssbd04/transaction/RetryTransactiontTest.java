package pl.lodz.p.it.ssbd2023.ssbd04.transaction;

import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.endpoints.AccountEndpoint;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RetryTransactiontTest {
    @Mock
    private AccountManager accountManager;

    @Mock
    private Etag etag;

    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private AccountEndpoint accountEndpoint;
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
    }


//    @Test
//    public void shouldReturnExceptionWhenRetryWillFailAfter3Attemps() {
//        AddRoleDTO addRoleDTO = new AddRoleDTO();
//        addRoleDTO.setLogin("testAccount");
//        RoleMzlDTO roleDTO = new RoleMzlDTO();
//        roleDTO.setRole("ADMIN");
//        addRoleDTO.setRole(roleDTO);
//
//        Mockito.doNothing().when(accountManager).addAccessLevel(Mockito.anyString(), Mockito.any(Role.class));
//        Mockito.when(accountManager.isLastTXRollback()).thenReturn(true);
//        assertThrows(BaseApplicationException.class, () -> {
//            accountEndpoint.addRole(etag.toString(),addRoleDTO,securityContext);
//        });
//        verify(accountManager, Mockito.times(3)).isLastTXRollback();
//    }
//
//    @Test
//    public void shouldReturnExceptionWhenOptimisticLockAppear() {
//        AddRoleDTO addRoleDTO = new AddRoleDTO();
//        addRoleDTO.setLogin("testAccount");
//        RoleMzlDTO roleDTO = new RoleMzlDTO();
//        roleDTO.setRole("ADMIN");
//        addRoleDTO.setRole(roleDTO);
//
//        Mockito.when(accountManager.isLastTXRollback()).thenReturn(true);
//        Mockito.doThrow(new AppOptimisticLockException()).when(accountManager).addAccessLevel(Mockito.anyString(), Mockito.any(Role.class));
//        assertThrows(AppOptimisticLockException.class, () -> {
//            accountEndpoint.addRole(etag.toString(),addRoleDTO,securityContext);
//        });
//        verify(accountManager, Mockito.times(2)).addAccessLevel(Mockito.anyString(), Mockito.any(Role.class));
//    }
//
//    @Test
//    public void shouldReturnResponseStatusOkWhenRollbackNotAppear() {
//        AddRoleDTO addRoleDTO = new AddRoleDTO();
//        addRoleDTO.setLogin("testAccount");
//        RoleMzlDTO roleDTO = new RoleMzlDTO();
//        roleDTO.setRole("ADMIN");
//        addRoleDTO.setRole(roleDTO);
//
//        Mockito.doNothing().when(accountManager).addAccessLevel(Mockito.anyString(), Mockito.any(Role.class));
//        Mockito.when(accountManager.isLastTXRollback()).thenReturn(false);
//        Response response = accountEndpoint.addRole(etag.toString(),addRoleDTO,securityContext);
//
//        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        verify(accountManager, Mockito.times(1)).addAccessLevel(Mockito.anyString(), Mockito.any(Role.class));
//    }
//
//
//    @Test
//    public void shouldReturnResponseStatusPreconditionRequiredWhenVerifyEtagFail() {
//        AddRoleDTO addRoleDTO = new AddRoleDTO();
//        addRoleDTO.setLogin("testAccount");
//        RoleMzlDTO roleDTO = new RoleMzlDTO();
//        roleDTO.setRole("ADMIN");
//        addRoleDTO.setRole(roleDTO);
//        Mockito.when(etag.verifyTag(Mockito.any(SignableEnt.class), Mockito.anyString())).thenReturn(false);
//        Mockito.doNothing().when(accountManager).addAccessLevel(Mockito.anyString(), Mockito.any(Role.class));
//        Mockito.when(accountManager.isLastTXRollback()).thenReturn(false);
//        Response response = accountEndpoint.addRole(etag.toString(),addRoleDTO,securityContext);
//
//        Assert.assertEquals(Response.Status.PRECONDITION_FAILED.getStatusCode(), response.getStatus());
//        verify(accountManager, Mockito.never()).addAccessLevel(Mockito.anyString(), Mockito.any(Role.class));
//    }

}
