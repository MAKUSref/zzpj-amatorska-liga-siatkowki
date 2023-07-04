package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration.mok;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.RegisterAccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.RoleDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.integration.TestUtils;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class CreateAccountAsAdminTest extends TestUtils {

    public Response createAccountAsAdmin(String login, String email, String roleType, int expectedStatusCode, String password){
        AccountDTO account = new AccountDTO(UUID.randomUUID(),0,login, "jacek","placek", email,null,"pl");
        RoleDTO role = new RoleDTO(roleType);
        RegisterAccountDTO accountByAdminDTO = new RegisterAccountDTO(account,password, role);
        if (expectedStatusCode == 200) {
            return given(requestSpecification)
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .cookie(authCookie(true))
                    .body(accountByAdminDTO)
                    .log().all()
                    .post("/createAccountAdmin");
        } else if (expectedStatusCode == 403) {
            return given(requestSpecification)
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .cookie(authCookie(false))
                    .body(accountByAdminDTO)
                    .log().all()
                    .post("/createAccountAdmin");
        } else {
            return given(requestSpecification)
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .body(accountByAdminDTO)
                    .log().all()
                    .post("/createAccountAdmin");
        }
    }

    @Test
    void createManagerAsAdminPositiveTest(){
        int accountListSize1 = getAccountList().size();

        Response response = createAccountAsAdmin(randomLogin(), randomEmail(), "MANAGER", 200, "12345678");
        Assertions.assertEquals(response.getStatusCode(), 201);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1 +1);    }

    @Test
    void createRefereeAsAdminPositiveTest(){
        int accountListSize1 = getAccountList().size();

        Response response = createAccountAsAdmin(randomLogin(), randomEmail(), "REFEREE", 200, "12345678");
        Assertions.assertEquals(response.getStatusCode(), 201);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1 +1);
    }

    @Test
    void createAccountSameLoginNegativeTest() {
        int accountListSize1 = getAccountList().size();

        String login = randomLogin();
        Response response1 = createAccountAsAdmin(login, randomEmail(), "MANAGER", 200, "12345678");
        Assertions.assertEquals(response1.getStatusCode(), 201);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1 +1);

        Response response2 = createAccountAsAdmin(login, randomEmail(), "MANAGER", 200, "12345678");
        Assertions.assertEquals(response2.getStatusCode(), 409);
    }

    @Test
    void createAccountSameEmailNegativeTest() {
        int accountListSize1 = getAccountList().size();

        String email = randomEmail();
        Response response1 = createAccountAsAdmin(randomLogin(), email, "MANAGER", 200, "12345678");
        Assertions.assertEquals(response1.getStatusCode(), 201);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1 +1);

        Response response2 = createAccountAsAdmin(randomLogin(), email, "MANAGER", 200, "12345678");
        Assertions.assertEquals(response2.getStatusCode(), 409);

        int accountListSize3 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize3);
    }

    @Test
    void createManagerUnauthorizedNegativeTest(){
        int accountListSize1 = getAccountList().size();

        Response response = createAccountAsAdmin(randomLogin(), randomEmail(), "MANAGER", 401, "12345678");
        Assertions.assertEquals(response.getStatusCode(), 401);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1);
    }

    @Test
    void createManagerForbiddenNegativeTest(){
        int accountListSize1 = getAccountList().size();

        Response response = createAccountAsAdmin(randomLogin(), randomEmail(), "MANAGER", 403, "12345678");
        Assertions.assertEquals(response.getStatusCode(), 403);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1);
    }

    @Test
    void registerAccountInvalidPasswordNegativeTest() {
        int accountListSize1 = getAccountList().size();

        Response response = createAccountAsAdmin(randomLogin(), randomEmail(), "MANAGER", 200, "12345");
        Assertions.assertEquals(response.getStatusCode(), 400);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1);
    }

    @Test
    void registerAccountInvalidEmailNegativeTest() {
        int accountListSize1 = getAccountList().size();

        Response response = createAccountAsAdmin(randomLogin(), randomLogin(), "REFEREE", 200, "1234");
        Assertions.assertEquals(response.getStatusCode(), 400);

        int accountListSize2 = getAccountList().size();
        Assertions.assertEquals(accountListSize2, accountListSize1);
    }


}
