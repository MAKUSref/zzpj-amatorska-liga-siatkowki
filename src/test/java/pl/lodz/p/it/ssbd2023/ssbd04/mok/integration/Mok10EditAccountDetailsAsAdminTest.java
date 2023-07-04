package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.EditAccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

public class Mok10EditAccountDetailsAsAdminTest extends TestUtils {
    private Response editAccountAsAdmin(AccountDTO account, String name, String lastname, boolean authorized, boolean withTag, boolean isAdmin) {
        Etag etag = new Etag();
        String tag = etag.calculateSignature(account);
        EditAccountDTO editAccountDTO = new EditAccountDTO(account.getId(), account.getVersion(), name, lastname);

        if (authorized) {
            if(withTag) {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .header("If-Match", tag)
                        .cookie(authCookie(isAdmin))
                        .body(editAccountDTO)
                        .log().all()
                        .patch("/editAccount/" + account.getLogin());
            } else {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .cookie(authCookie(isAdmin))
                        .body(editAccountDTO)
                        .log().all()
                        .patch("/editAccount/" + account.getLogin());
            }
        } else {
            if(withTag) {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .header("If-Match", tag)
                        .body(editAccountDTO)
                        .log().all()
                        .patch("/editAccount/" + account.getLogin());
            } else {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(editAccountDTO)
                        .log().all()
                        .patch("/editAccount/" + account.getLogin());
            }
        }
    }

    //TODO optimistic lock i invalid data

    @Test
    void changeAccountDetailsAsAdminPositiveTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        //randomLogin() is used here to get random values to be assigned as the new name and lastname
        Response response = editAccountAsAdmin(account, randomLogin(),randomLogin(), true, true, true);
        Assertions.assertEquals(response.getStatusCode(), 200);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertNotEquals(accountFromDb.getName(), account.getName());
    }

    @Test
    void changeAccountDetailsAsAdminNoTagNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = editAccountAsAdmin(account, randomLogin(),randomLogin(), true, false, true);
        Assertions.assertEquals(response.getStatusCode(), 500);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getName(), account.getName());
    }


    @Test
    void changeAccountDetailsAsAdminForbiddenNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = editAccountAsAdmin(account, randomLogin(),randomLogin(), true, true, false);
        Assertions.assertEquals(response.getStatusCode(), 403);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getName(), account.getName());
    }

    @Test
    void changeAccountDetailsAsAdminNoTagForbiddenNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = editAccountAsAdmin(account, randomLogin(),randomLogin(), true, false, false);
        Assertions.assertEquals(response.getStatusCode(), 403);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getName(), account.getName());
    }

    @Test
    void changeAccountDetailsAsAdminUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = editAccountAsAdmin(account, randomLogin(),randomLogin(), false, true, false);
        Assertions.assertEquals(response.getStatusCode(), 401);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getName(), account.getName());
    }

    @Test
    void changeAccountDetailsAsAdminNoTagUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = editAccountAsAdmin(account, randomLogin(),randomLogin(), false, false, false);
        Assertions.assertEquals(response.getStatusCode(), 401);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getName(), account.getName());
    }

    @Test
    void changeAccountDetailsAsAdminInvalidDataNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = editAccountAsAdmin(account, randomLogin() + randomLogin() + randomLogin(),randomLogin(), true, true, true);
        Assertions.assertEquals(response.getStatusCode(), 400);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getName(), account.getName());
    }

    @Test
    void changeAccountDetailsOptimisticLockNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        String newName = "NOWEIMIE";

        editAccountAsAdmin(account, randomLogin(),randomLogin(), true, true, true);
        Response response = editAccountAsAdmin(account, newName, newName, true, true, true);

        Assertions.assertEquals(response.getStatusCode(), 409);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertNotEquals(accountFromDb.getName(), newName);
    }
}
