package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;


public class Mok13GetAccountListTest extends TestUtils {
    private Response getAllAccounts() {

            return RestAssured.given(requestSpecification)
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .cookie(authCookie(true))
                    .log().all()
                    .get("");
    }

    @Test
    void getAccountListTest() {
        AccountDTO account1 = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);
        AccountDTO account2 = registerAccount(randomEmail(), randomLogin(),"COACH").as(AccountDTO.class);

        Response response = getAllAccounts();
        Assertions.assertEquals(response.getStatusCode(), 200);
    }
}
