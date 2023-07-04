package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AddRoleDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.RoleDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

import static io.restassured.RestAssured.given;

public class Mok5AddRoleTest extends TestUtils {

    public Response addRole(AccountDTO account, String roleType, boolean isAdmin, boolean withEtag, boolean isAuthorised) {
        RoleDTO role = new RoleDTO(roleType);
        Etag etag = new Etag();
        String tag = etag.calculateSignature(account);
        AddRoleDTO addRoleDTO = new AddRoleDTO(account.getId(), account.getVersion(), account.getLogin(), role);
        if (withEtag) {
            if (isAuthorised) {
                return given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(addRoleDTO)
                        .header("If-Match", tag)
                        .cookie(authCookie(isAdmin))
                        .log().all()
                        .patch("/addRole");
            } else {
                return given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(addRoleDTO)
                        .header("If-Match", tag)
                        .log().all()
                        .patch("/addRole");
            }
        } else {
            if (isAuthorised) {
                return given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(addRoleDTO)
                        .cookie(authCookie(isAdmin))
                        .log().all()
                        .patch("/addRole");
            } else {
                return given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(addRoleDTO)
                        .log().all()
                        .patch("/addRole");
            }
        }
    }

    //optimistic lock

    @Test
    void addRolePositiveTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        activateAccount(account.getLogin());

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 1);

        Response response1 = addRole(account, "ADMIN", true, true, true);
        Assertions.assertEquals(response1.getStatusCode(), 200);

        accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 2);
    }

    @Test
    void addRoleNoTagNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        activateAccount(account.getLogin());

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 1);

        Response response1 = addRole(account, "COACH", true, false, true);
        Assertions.assertEquals(response1.getStatusCode(), 500);

        accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 1);
    }

    @Test
    void addRoleAccountInactiveNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 1);

        Response response1 = addRole(account, "COACH", true, true, true);
        Assertions.assertEquals(response1.getStatusCode(), 403);//hmmm

        accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 1);

    }

    @Test
    void addRoleForbiddenNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 1);

        Response response1 = addRole(account, "COACH", false, true, true);
        Assertions.assertEquals(response1.getStatusCode(), 403);

        accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 1);
    }

    @Test
    void addRoleUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 1);

        Response response1 = addRole(account, "COACH", false, true, false);
        Assertions.assertEquals(response1.getStatusCode(), 401);

        accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 1);

    }

}
