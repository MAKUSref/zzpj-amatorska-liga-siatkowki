package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.EditAccountLanguageDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

public class Mok12ChangeLanguageTest extends TestUtils {
    private Response changeLanguage(AccountDTO account, String name, String lastname, boolean authorized, boolean withTag) {
        Etag etag = new Etag();

        EditAccountLanguageDTO editAccountLanguageDTO = new EditAccountLanguageDTO(account.getId(), account.getVersion(), account.getLogin(), "en");
        String tag = etag.calculateSignature(editAccountLanguageDTO);
        if (authorized) {
            if(withTag) {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .header("If-Match", tag)
                        .cookie(authCookie(account))
                        .body(editAccountLanguageDTO)
                        .log().all()
                        .patch("/changeLanguage");
            } else {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .cookie(authCookie(account))
                        .body(editAccountLanguageDTO)
                        .log().all()
                        .patch("/changeLanguage");
            }
        } else {
            if(withTag) {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .header("If-Match", tag)
                        .body(editAccountLanguageDTO)
                        .log().all()
                        .patch("/changeLanguage");
            } else {
                return RestAssured.given(requestSpecification)
                        .relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body(editAccountLanguageDTO)
                        .log().all()
                        .patch("/changeLanguage");
            }
        }
    }

    @Test
    void changeLanguagePositiveTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changeLanguage(account, randomLogin(),randomLogin(), true, true);
        Assertions.assertEquals(response.getStatusCode(), 204);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertNotEquals(accountFromDb.getLocale(), account.getLocale());
    }

    @Test
    void changeLanguageUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changeLanguage(account, randomLogin(),randomLogin(), false, true);
        Assertions.assertEquals(response.getStatusCode(), 401);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getLocale(), account.getLocale());
    }

    @Test
    void changeLanguageNoTagNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changeLanguage(account, randomLogin(),randomLogin(), true, false);
        Assertions.assertEquals(response.getStatusCode(), 500);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getLocale(), account.getLocale());
    }

    @Test
    void changeLanguageNoTagUnauthorisedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        Response response = changeLanguage(account, randomLogin(),randomLogin(), false, false);
        Assertions.assertEquals(response.getStatusCode(), 401);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getLocale(), account.getLocale());
    }

    @Test
    void changeLanguageOptimisticLockNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(), randomLogin(),"MANAGER").as(AccountDTO.class);

        changeLanguage(account, randomLogin(),randomLogin(), true, true);
        Response response = changeLanguage(account, randomLogin(),randomLogin(), true, true);

        Assertions.assertEquals(response.getStatusCode(), 409);

        AccountDTO accountFromDb = getAccount(account.getId().toString());
        Assertions.assertEquals(accountFromDb.getVersion(), 1);
    }

}
