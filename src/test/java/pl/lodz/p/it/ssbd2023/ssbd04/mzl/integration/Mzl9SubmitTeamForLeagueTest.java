package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.CreateTeamDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.SubmitTeamForLeagueDto;

public class Mzl9SubmitTeamForLeagueTest extends TestUtils {

    private String leagueId;

    private final CreateTeamDTO createTeamDTO =
            new CreateTeamDTO(generateRandomString(12), generateRandomString(12));

    private Response submitTeamForLeagueAsAdmin(SubmitTeamForLeagueDto submitTeamForLeagueDto, AccountDTO accountDTO) {
        return RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(accountDTO))
                .body(submitTeamForLeagueDto)
                .log().all()
                .post(teamUri + "/submitTeamForLeague");
    }

    private Response submitOwnTeam(SubmitTeamForLeagueDto submitTeamForLeagueDto, AccountDTO accountDTO) {
        return RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(accountDTO))
                .body(submitTeamForLeagueDto)
                .log().all()
                .post(teamUri + "/submitOwnTeam");
    }

    private Response getCreateTeamResponse(CreateTeamDTO createTeamDTO, AccountDTO accountDTO) {
        return RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(accountDTO))
                .body(createTeamDTO)
                .log().all()
                .post(teamUri + "/createTeam");
    }

    private Response getSubmitTeamAsUserResponse(AccountDTO accountDTO) {
        Response response = getCreateTeamResponse(createTeamDTO, accountDTO);
        String teamId = getTeamIdFromCreateTeamResponse(response);
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(teamId, leagueId);
        return submitTeamForLeagueAsAdmin(submitTeamForLeagueDto, accountDTO);
    }

    private Response getSubmitOwnTeamResponse(AccountDTO accountDTO) {
        Response response = getCreateTeamResponse(createTeamDTO, accountDTO);
        String teamId = getTeamIdFromCreateTeamResponse(response);
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(teamId, leagueId);
        return submitOwnTeam(submitTeamForLeagueDto, accountDTO);
    }

    private String getTeamIdFromCreateTeamResponse(Response response) {
        JsonPath jsonPathEvaluator = response.jsonPath();
        return jsonPathEvaluator.get("id");
    }

    @BeforeEach
    void generate() {
        leagueId = createLeague();
    }

    @Test
    public void submitTeamForLeagueAsManagerThatIsNotRepresentativeNegativeTest() {
        AccountDTO manager1 = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        AccountDTO manager2 = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        Response response = getCreateTeamResponse(createTeamDTO, manager1);
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(getTeamIdFromCreateTeamResponse(response), leagueId);
        submitOwnTeam(submitTeamForLeagueDto, manager2).then().statusCode(409);
    }

    @Test
    public void submitTeamForLeagueAsCaptainThatIsNotRepresentativeNegativeTest() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        Response response = getCreateTeamResponse(createTeamDTO, captain1);
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(getTeamIdFromCreateTeamResponse(response), leagueId);
        submitOwnTeam(submitTeamForLeagueDto, captain2).then().statusCode(409);
    }

    @Test
    public void submitTeamForLeagueAsCoachThatIsNotRepresentativeNegativeTest() {
        AccountDTO coach1 = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        AccountDTO coach2 = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        Response response = getCreateTeamResponse(createTeamDTO, coach1);
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(getTeamIdFromCreateTeamResponse(response), leagueId);
        submitOwnTeam(submitTeamForLeagueDto, coach2).then().statusCode(409);
    }

    @Test
    public void submitTeamForLeagueAsManagerPositiveTest() {
        AccountDTO manager = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        getSubmitOwnTeamResponse(manager).then().statusCode(202);
    }

    @Test
    public void submitTeamForLeagueAsCaptainPositiveTest() {
        AccountDTO captain = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        getSubmitOwnTeamResponse(captain).then().statusCode(202);
    }

    @Test
    public void submitTeamForLeagueAsCoachPositiveTest() {
        AccountDTO coach = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        getSubmitOwnTeamResponse(coach).then().statusCode(202);
    }

    @Test
    public void submitTeamForLeagueAsAdminPositiveTest() {
        AccountDTO admin = registerAccount(randomEmail(), randomLogin(), "ADMIN").as(AccountDTO.class);
        getSubmitTeamAsUserResponse(admin).then().statusCode(202);
    }

    @Test
    public void submitTeamForLeagueThatDoesntExistTest() {
        String teamId = createApprovedTeam();
        String leagueId = "11111111-dd7d-4141-a3a3-a6259724c810";
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(teamId, leagueId);
        AccountDTO admin = registerAccount(randomEmail(), randomLogin(), "ADMIN").as(AccountDTO.class);

        submitTeamForLeagueAsAdmin(submitTeamForLeagueDto, admin).then().statusCode(404);
    }

    @Test
    public void submitTeamThatDoesntExistForLeagueTest() {
        // jakis nieistniejacy uuid
        String teamId = "70abe0d7-5126-4ecb-9c6f-4a8745638dd9";
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(teamId, leagueId);
        AccountDTO admin = registerAccount(randomEmail(), randomLogin(), "ADMIN").as(AccountDTO.class);

        submitTeamForLeagueAsAdmin(submitTeamForLeagueDto, admin).then().statusCode(404);
    }

    @Test
    public void submitTeamThatsAlreadyInLeagueTest() {
        String teamId = createApprovedTeam();
        String leagueId = createLeague();
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(teamId, leagueId);
        AccountDTO admin = registerAccount(randomEmail(), randomLogin(), "ADMIN").as(AccountDTO.class);

        submitTeamForLeagueAsAdmin(submitTeamForLeagueDto, admin).then().statusCode(202);
        submitTeamForLeagueAsAdmin(submitTeamForLeagueDto, admin).then().statusCode(406);
    }

    @Test
    public void submitTeamForLeagueAsRefereeNegativeTest() {
        String teamId = createApprovedTeam();
        String leagueId = createLeague();
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(teamId, leagueId);
        String uuid = createReferee();
        submitTeamForLeagueAsAdmin(submitTeamForLeagueDto, getAccount(uuid)).then().statusCode(403);
    }
}
