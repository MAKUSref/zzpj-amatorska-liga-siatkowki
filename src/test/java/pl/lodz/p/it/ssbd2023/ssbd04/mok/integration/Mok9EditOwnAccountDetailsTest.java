package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.EditAccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

public class Mok9EditOwnAccountDetailsTest extends TestUtils{




    private Response editOwnAccount(AccountDTO account, String name, String lastname, boolean authorized, boolean withTag) {
        Etag etag = new Etag();
        EditAccountDTO editAccountDTO = new EditAccountDTO(account.getId(), account.getVersion(), name, lastname);
        String tag = etag.calculateSignature(editAccountDTO);

        if (authorized) {
            if(withTag) {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .header("If-Match", tag)
                        .cookie(authCookie(account))
                        .body(editAccountDTO)
                        .log().all()
                        .patch("/editAccount");
            } else {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .cookie(authCookie(account))
                        .body(editAccountDTO)
                        .log().all()
                        .patch("/editAccount");
            }
        } else {
            if(withTag) {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .header("If-Match", tag)
                        .body(editAccountDTO)
                        .log().all()
                        .patch("/editAccount");
            } else {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(editAccountDTO)
                        .log().all()
                        .patch("/editAccount");
            }
        }
    }

    @Test
    void changeAccountDetailsPositiveTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        //randomLogin() is used here to get random values to be assigned as the new name and lastname
        Response response = editOwnAccount(account, randomLogin(),randomLogin(), true, true);
        Assertions.assertEquals(response.getStatusCode(), 200);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertNotEquals(accountFromDb.getName(), account.getName());
    }

    @Test
    void changeAccountDetailsUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = editOwnAccount(account, randomLogin(),randomLogin(), false, true);
        Assertions.assertEquals(response.getStatusCode(), 401);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getName(), account.getName());
    }

    @Test
    void changeAccountDetailsNoTagNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = editOwnAccount(account, randomLogin(),randomLogin(), true, false);
        Assertions.assertEquals(response.getStatusCode(), 500);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getName(), account.getName());
    }

    @Test
    void changeAccountDetailsNoTagUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = editOwnAccount(account, randomLogin(),randomLogin(), false, false);
        Assertions.assertEquals(response.getStatusCode(), 401);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getName(), account.getName());
    }

    @Test
    void changeAccountDetailsInvalidDataNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = editOwnAccount(account, randomLogin()+ randomLogin() + randomLogin(), randomLogin(), true, true);
        Assertions.assertEquals(response.getStatusCode(), 400);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getName(), account.getName());
    }

    @Test
    void changeAccountDetailsOptimisticLockNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        String newName = "NOWEIMIE";

        editOwnAccount(account, randomLogin(),randomLogin(), true, true);
        Response response = editOwnAccount(account, newName, newName, true, true);

        Assertions.assertEquals(response.getStatusCode(), 409);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertNotEquals(accountFromDb.getName(), newName);
    }
}
