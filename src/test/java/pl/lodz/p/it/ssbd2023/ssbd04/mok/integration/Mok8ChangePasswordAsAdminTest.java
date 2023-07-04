package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.ChangePasswordDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

public class Mok8ChangePasswordAsAdminTest extends TestUtils {

    private Response changePassword(AccountDTO account, String password,  int expectedStatusCode, boolean withTag) {
        Etag etag = new Etag();
        String tag = etag.calculateSignature(account);
        ChangePasswordDTO passwordResetDTO = new ChangePasswordDTO(account.getId(), account.getVersion(), password);
        if (expectedStatusCode == 200) {
            if (withTag) {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .header("If-Match", tag)
                        .cookie(authCookie(true))
                        .body(passwordResetDTO)
                        .log().all()
                        .post("/changeAccountPassword/"+account.getId());
            } else {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .cookie(authCookie(true))
                        .body(passwordResetDTO)
                        .log().all()
                        .post("/changeAccountPassword/"+account.getId());
            }
        } else if (expectedStatusCode == 403) {
            return RestAssured.given(requestSpecification)
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .cookie(authCookie(false))
                    .body(passwordResetDTO)
                    .log().all()
                    .post("/changeAccountPassword/"+account.getId());
        } else {
            return RestAssured.given(requestSpecification)
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .body(passwordResetDTO)
                    .log().all()
                    .post("/changeAccountPassword/"+account.getId());
        }
    }

    @Test
    void changePasswordAsAdminPositiveTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changePassword(account, "12345677", 200, true);
        Assertions.assertEquals(response.getStatusCode(), 200);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 1);
    }

    @Test
    void changePasswordAsAdminUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changePassword(account, "12345677", 401, true);
        Assertions.assertEquals(response.getStatusCode(), 401);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 0);
    }

    @Test
    void changePasswordAsAdminForbiddenNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changePassword(account, "12345677", 403, true);
        Assertions.assertEquals(response.getStatusCode(), 403);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 0);
    }

    @Test
    void changePasswordAsAdminNoTagNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changePassword(account, "12345677", 200, false);
        Assertions.assertEquals(response.getStatusCode(), 500);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 0);
    }

    @Test
    void changePasswordAsAdminOptimisticLockNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        changePassword(account, "12345677", 200, true);
        Response response = changePassword(account, "87654321", 200, true);

        Assertions.assertEquals(response.getStatusCode(), 409);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 1);
    }

    //TODO implement after constraints are added
    @Test
    void changePasswordInvalidAsAdminNegativeTest() {
//        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);
//
//        Response response = changePassword(account.getId().toString(), "1234", 200);
//        Assertions.assertEquals(response.getStatusCode(), 400);

    }



}
