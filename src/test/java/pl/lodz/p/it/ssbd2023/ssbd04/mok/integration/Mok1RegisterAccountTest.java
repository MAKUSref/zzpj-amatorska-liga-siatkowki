package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Mok1RegisterAccountTest extends TestUtils {

    //invalid data format - login/email

    @Test
    public void registerManagerPositiveTest() {

        int accountListSize1 = getAccountList().size();
        Response response = registerAccount(randomEmail(), randomLogin(), "MANAGER");
        Assertions.assertEquals(response.getStatusCode(), 201);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1 +1);

    }

    @Test
    void registerCoachPositiveTest() {
        int accountListSize1 = getAccountList().size();
        Response response = registerAccount(randomEmail(), randomLogin(), "COACH");
        Assertions.assertEquals(response.getStatusCode(), 201);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1 +1);
    }

    @Test
    void registerAccountAsCaptainPositiveTest() {
        int accountListSize1 = getAccountList().size();
        Response response = registerAccount(randomEmail(), randomLogin(), "CAPTAIN");
        Assertions.assertEquals(response.getStatusCode(), 201);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1 +1);
    }

    @Test
    void registerAccountSameLoginNegativeTest() {
        int accountListSize1 = getAccountList().size();

        String login = randomLogin();
        Response response1 = registerAccount(randomEmail(), login, "MANAGER");
        Assertions.assertEquals(response1.getStatusCode(), 201);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1 +1);

        Response response2 = registerAccount(randomEmail(), login, "MANAGER");
        Assertions.assertEquals(response2.getStatusCode(), 409);

        int accountListSize3 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize3);
    }

    @Test
    void registerAccountSameEmailNegativeTest() {
        int accountListSize1 = getAccountList().size();

        String email = randomEmail();
        Response response1 = registerAccount(email, randomLogin(), "MANAGER");
        Assertions.assertEquals(response1.getStatusCode(), 201);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1 +1);

        Response response2 = registerAccount(email, randomLogin(), "MANAGER");
        Assertions.assertEquals(response2.getStatusCode(), 409);

        int accountListSize3 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize3);
    }

    @Test
    void registerRefereeNegativeTest() {
        int accountListSize1 = getAccountList().size();

        Response response = registerAccount(randomEmail(), randomLogin(), "REFEREE");
        Assertions.assertEquals(response.getStatusCode(), 400);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1);
    }

    @Test
    void registerAccountInvalidPasswordNegativeTest() {
        int accountListSize1 = getAccountList().size();

        Response response = registerAccount(randomEmail(), randomLogin(), "MANAGER", "1234");
        Assertions.assertEquals(response.getStatusCode(), 400);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1);
    }
}

