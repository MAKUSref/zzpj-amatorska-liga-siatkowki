package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AddRoleDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.RemoveRoleDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.RoleDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class Mok6RemoveRoleTest extends TestUtils{
    public Response removeRole(AccountDTO account, String roleType, boolean isAdmin, boolean withEtag, boolean isAuthorised){
        RoleDTO role = new RoleDTO(roleType);
        Etag etag = new Etag();
        String tag = etag.calculateSignature(account);
        RemoveRoleDTO removeRoleDTO = new RemoveRoleDTO(account.getId(), account.getVersion(),account.getLogin(), role);

        if (withEtag) {
            if (isAuthorised) {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(removeRoleDTO)
                        .header("If-Match", tag)
                        .cookie(authCookie(isAdmin))
                        .log().all()
                        .patch("/removeRole");
            } else {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(removeRoleDTO)
                        .header("If-Match", tag)
                        .log().all()
                        .patch("/removeRole");
            }
        } else {
            if (isAuthorised) {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(removeRoleDTO)
                        .cookie(authCookie(isAdmin))
                        .log().all()
                        .patch("/removeRole");
            } else {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(removeRoleDTO)
                        .log().all()
                        .patch("/removeRole");
            }
        }
    }

    public Response addRole(AccountDTO account, String roleType, boolean isAdmin) {
        RoleDTO role = new RoleDTO(roleType);
        Etag etag = new Etag();
        String tag = etag.calculateSignature(account);
        AddRoleDTO addRoleDTO = new AddRoleDTO(account.getId(), account.getVersion(), account.getLogin(), role);

        return given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(addRoleDTO)
                .header("If-Match", tag)
                .cookie(authCookie(isAdmin))
                .log().all()
                .patch("/addRole");
    }

    @Test
    void removeRolePositiveTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        activateAccount(account.getLogin());
        Response response1 = addRole(account, "COACH", true);
        Assertions.assertEquals(response1.getStatusCode(), 200);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 2);

        account = getAccount(account.getId().toString());

        Response response2 = removeRole(account, "COACH", true, true, true);
        Assertions.assertEquals(response2.getStatusCode(), 200);

        accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 1);
    }

    @Test
    void removeRoleForbiddenNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        activateAccount(account.getLogin());

        Response response1 = addRole(account, "COACH", true);
        Assertions.assertEquals(response1.getStatusCode(), 200);
        account = getAccount(account.getId().toString());

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 2);

        Response response2 = removeRole(account, "COACH", false, true, true);
        Assertions.assertEquals(response2.getStatusCode(), 403);

        accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 2);
    }

    @Test
    void removeRoleUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        activateAccount(account.getLogin());

        Response response1 = addRole(account, "COACH", true);
        Assertions.assertEquals(response1.getStatusCode(), 200);
        account = getAccount(account.getId().toString());

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 2);

        Response response2 = removeRole(account, "COACH", false, true, false);
        Assertions.assertEquals(response2.getStatusCode(), 401);

        accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 2);
    }

    @Test
    void removeRoleNoTagNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        activateAccount(account.getLogin());

        Response response1 = addRole(account, "COACH", true);
        Assertions.assertEquals(response1.getStatusCode(), 200);
        account = getAccount(account.getId().toString());

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 2);

        Response response2 = removeRole(account, "COACH", false, false, true);
        Assertions.assertEquals(response2.getStatusCode(), 403);

        accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getRoles().size(), 2);

    }

}
