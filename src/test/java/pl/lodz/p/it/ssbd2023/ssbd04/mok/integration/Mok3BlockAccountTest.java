package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;

public class Mok3BlockAccountTest extends TestUtils {

    private Response blockAccount(String id, boolean isAuthorised, boolean isAdmin) {
        if (isAuthorised) {
            return RestAssured.given(requestSpecification)
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .cookie(authCookie(isAdmin))
                    .log().all()
                    .patch("/block/"+id);
        } else {
            return RestAssured.given(requestSpecification)
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .log().all()
                    .patch("/block/"+id);
        }
    }

    //TODO test if blocking self

    @Test
    void blockAccountPositiveTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        Response response = blockAccount(account.getId().toString(), true, true);
        AccountDTO accountFromDb = getAccount(account.getId().toString());

        Assertions.assertEquals(response.getStatusCode(), 204);
        Assertions.assertTrue(accountFromDb.isBlocked());
    }

    //TODO fix
    @Test
    void blockSelfNegativeTest() {
        AccountDTO admin = getAccount(adminUUID);

        Response response = RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(admin))
                .log().all()
                .patch("/block/"+ adminUUID);

        Assertions.assertEquals(response.getStatusCode(), 405);
    }

    @Test
    void blockAccountAlreadyBlockedTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        Response response = blockAccount(account.getId().toString(), true, true);
        AccountDTO accountFromDb = getAccount(account.getId().toString());

        Assertions.assertEquals(response.getStatusCode(), 204);
        Assertions.assertTrue(accountFromDb.isBlocked());

        Response response2 = blockAccount(account.getId().toString(), true, true);
        Assertions.assertEquals(response2.getStatusCode(), 400);
    }


    @Test
    void blockAccountForbiddenNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        Response response = blockAccount(account.getId().toString(), true,false);
        AccountDTO accountFromDb = getAccount(account.getId().toString());

        Assertions.assertEquals(response.getStatusCode(), 403);
        Assertions.assertFalse(accountFromDb.isBlocked());
    }

    @Test
    void blockAccountUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        Response response = blockAccount(account.getId().toString(), false, false);
        AccountDTO accountFromDb = getAccount(account.getId().toString());

        Assertions.assertEquals(response.getStatusCode(), 401);
        Assertions.assertFalse(accountFromDb.isBlocked());
    }


}
