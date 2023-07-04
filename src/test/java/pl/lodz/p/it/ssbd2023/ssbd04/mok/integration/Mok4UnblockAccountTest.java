package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;

public class Mok4UnblockAccountTest extends TestUtils {

    private Response blockAccount(String id) {

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .patch("/block/"+id);
    }

    private Response unblockAccount(String id, boolean isAdmin) {

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(isAdmin))
                .log().all()
                .patch("/unblock/"+id);
    }

    @Test
    void unblockAccountPositiveTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);

        Response response1 = blockAccount(account.getId().toString());
        Assertions.assertEquals(response1.getStatusCode(), 204);

        Response response2 = unblockAccount(account.getId().toString(), true);
        Assertions.assertEquals(response2.getStatusCode(), 204);
    }

    @Test
    void unblockAccountUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);

        Response response1 = blockAccount(account.getId().toString());
        Assertions.assertEquals(response1.getStatusCode(), 204);

        Response response = unblockAccount(account.getId().toString(), false);
        Assertions.assertEquals(response.getStatusCode(), 403);
    }
}
