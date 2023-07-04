package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.ChangeOwnPasswordDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.ChangePasswordDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

public class Mok7ChangeOwnPasswordTest extends TestUtils {




    private Response changeOwnPassword(String password, AccountDTO account, boolean authorized, boolean withTag) {
        Etag etag = new Etag();
        String tag = etag.calculateSignature(account);
        ChangeOwnPasswordDTO passwordResetDTO = new ChangeOwnPasswordDTO(account.getId(), account.getVersion(), password, "12345678");
        if (authorized) {
            if (withTag){
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .header("If-Match", tag)
                        .cookie(authCookie(account))
                        .body(passwordResetDTO)
                        .log().all()
                        .patch("/changePassword");
            } else {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .cookie(authCookie(account))
                        .body(passwordResetDTO)
                        .log().all()
                        .patch("/changePassword");
            }
        } else {
            if (withTag) {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .header("If-Match", tag)
                        .body(passwordResetDTO)
                        .log().all()
                        .patch("/changePassword");
            } else {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(passwordResetDTO)
                        .log().all()
                        .patch("/changePassword");
            }
        }
    }

    @Test
    void changeOwnPasswordPositiveTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changeOwnPassword("zmiana12345", account, true, true);
        Assertions.assertEquals(response.getStatusCode(), 200);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 1);
    }

    @Test
    void changeOwnPasswordIncorrectOldPasswordNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changeOwnPassword("zmiana12345", account, true, true);
        account = getAccount(account.getId().toString());
        Response response2 = changeOwnPassword("zmiana123sds45", account, true, true);

        Assertions.assertEquals(response2.getStatusCode(), 400);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 1);
    }

    @Test
    void changeOwnPasswordUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changeOwnPassword("zmiana123", account, false, true);
        Assertions.assertEquals(response.getStatusCode(), 401);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 0);
    }

    @Test
    void changeOwnPasswordInvalidNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changeOwnPassword("123", account, true, true);
        Assertions.assertEquals(response.getStatusCode(), 400);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 0);
    }

    @Test
    void changeOwnPasswordNoTagNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changeOwnPassword("zmiana12345", account, true, false);
        Assertions.assertEquals(response.getStatusCode(), 500);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 0);
    }

    @Test
    void changeOwnPasswordOptimisticLockNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changeOwnPassword("zmiana12345", account, true, true);
        Response response2 = changeOwnPassword("zmiana123sds45", account, true, true);

        Assertions.assertEquals(response2.getStatusCode(), 409);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 1);
    }

}

