package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.CreateTeamDTO;

public class Mzl8CreateTeamTest extends TestUtils {

    private final CreateTeamDTO createTeamDTO = new CreateTeamDTO(generateRandomString(12), generateRandomString(12));

    private Response createTeam(CreateTeamDTO createTeamDTO, AccountDTO accountDTO) {
        return RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(accountDTO))
                .body(createTeamDTO)
                .log().all()
                .post(teamUri + "/createTeam");
    }

    private Response getMyTeams(AccountDTO accountDTO) {
        return RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(accountDTO))
                .log().all()
                .get(teamUri + "/getMyTeams");
    }

    private void validatePositiveResponse(Response response) {
        response.then()
                .statusCode(201);
                // .body("approved", equalTo(false))
                // .body("teamName", equalTo(createTeamDTO.getTeamName()))
                // .body("city", equalTo(createTeamDTO.getCity()));
    }

    @Test
    public void createTeamAsManagerPositiveTest() {
        AccountDTO manager = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        Response response = createTeam(createTeamDTO, manager);
        validatePositiveResponse(response);
    }

    @Test
    public void createTeamAsCaptainPositiveTest() {
        AccountDTO captain = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        Response response = createTeam(createTeamDTO, captain);
        validatePositiveResponse(response);
    }

    @Test
    public void createSecondTeamAsCaptainNegativeTest() {
        AccountDTO captain = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        Response response = createTeam(createTeamDTO, captain);
        validatePositiveResponse(response);
        createTeam(createTeamDTO, captain).then().statusCode(409);
    }

    @Test
    public void createSecondTeamAsManagerNegativeTest() {
        AccountDTO manager = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        Response response = createTeam(createTeamDTO, manager);
        validatePositiveResponse(response);
        createTeam(createTeamDTO, manager).then().statusCode(409);
    }

    @Test
    public void createSecondTeamAsCoachNegativeTest() {
        AccountDTO manager = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        Response response = createTeam(createTeamDTO, manager);
        validatePositiveResponse(response);
        createTeam(createTeamDTO, manager).then().statusCode(409);
    }

    @Test
    public void createTeamAsCoachPositiveTest() {
        AccountDTO coach = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        Response response = createTeam(createTeamDTO, coach);
        validatePositiveResponse(response);
    }

    @Test
    public void getMyTeamsAsCoachPositiveTest() {
        AccountDTO coach = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        createTeam(createTeamDTO, coach);
        getMyTeams(coach).then().statusCode(200);;
    }

    // @Test
    // public void getMyTeamsAsCaptainNegativeTest() {
    //     AccountDTO captain = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
    //     Response response = getMyTeams(captain);
    //     response.then().statusCode(409);
    // }

    @Test
    public void getMyTeamsAsManagerNegativeTest() {
        AccountDTO manager = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        Response response = getMyTeams(manager);
        response.then().statusCode(409);
    }

    @Test
    public void createTeamAsAdminPositiveTest() {
        AccountDTO admin = registerAccount(randomEmail(), randomLogin(), "ADMIN").as(AccountDTO.class);
        Response response = createTeam(createTeamDTO, admin);
        validatePositiveResponse(response);
    }

    @Test
    public void createTeamAsRefereeNegativeTest() {
        String uuid = createReferee();
        AccountDTO accountDTO = getAccount(uuid);
        Response response = createTeam(createTeamDTO, accountDTO);
        response.then().statusCode(403);
    }
}
