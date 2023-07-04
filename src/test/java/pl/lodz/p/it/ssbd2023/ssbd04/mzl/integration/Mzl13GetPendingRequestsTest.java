package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.SubmitTeamForLeagueDto;

public class Mzl13GetPendingRequestsTest extends TestUtils {
    private Response getAllPendingRequests(String leagueId) {

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get(leagueUri + "/teamsRequests/" + leagueId);
    }

    private Response getAllPendingRequestsAsNotAdmin(String leagueId) {

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .log().all()
                .get(leagueUri + "/teamsRequests/" + leagueId);
    }

    private Response getAllPendingRequests(String leagueId, AccountDTO accountDTO) {

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .cookie(authCookie(accountDTO))
                .header("Content-Type", "application/json")
                .log().all()
                .get(leagueUri + "/teamsRequests/" + leagueId);
    }

    private Response getAllTeamsInLeague(String leagueId) {

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get(leagueUri + "/teamsInLeague/" + leagueId);
    }

    private Response submitTeamForLeague(SubmitTeamForLeagueDto submitTeamForLeagueDto, AccountDTO accountDTO) {
        return RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(accountDTO))
                .body(submitTeamForLeagueDto)
                .log().all()
                .post(teamUri + "/submitTeamForLeague");
    }

    @Test
    public void getAllPendingRequestsInLeaguePositiveTest() {
        String leagueId = createLeague();
        String teamId = createTeam();
        AccountDTO admin = registerAccount(randomEmail(), randomLogin(), "ADMIN").as(AccountDTO.class);
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(teamId, leagueId);
        submitTeamForLeague(submitTeamForLeagueDto, admin);

        Response response = getAllPendingRequests(leagueId);
        response.then().statusCode(200);
    }

    @Test
    public void getAllPendingRequestsInLeagueNegativeTest() {
        String leagueId = createLeague();
        Response response = getAllPendingRequests(leagueId);
        response.then().statusCode(200).body("", Matchers.hasSize(0));
    }

    @Test
    public void getAllPendingRequestsInLeagueUnauthorizedTest() {
        String leagueId = createLeague();
        Response response = getAllPendingRequestsAsNotAdmin(leagueId);
        response.then().statusCode(401);
    }

    @Test
    public void getAllPendingRequestsInLeagueAsNotAdminTest() {
        String leagueId = createLeague();
        AccountDTO captain = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        Response response = getAllPendingRequests(leagueId, captain);
        response.then().statusCode(403);
    }

    @Test
    public void getAllTeamsInLeaguePositiveTest() {
        String leagueId = createLeague();
        String teamId = createApprovedTeam();
        AccountDTO admin = registerAccount(randomEmail(), randomLogin(), "ADMIN").as(AccountDTO.class);
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(teamId, leagueId);
        submitTeamForLeague(submitTeamForLeagueDto, admin);

        Response response = getAllTeamsInLeague(leagueId);
        response.then().statusCode(200);
    }

    @Test
    public void getAllTeamsInLeagueNegativeTest() {
        String leagueId = createLeague();
        Response response = getAllTeamsInLeague(leagueId);
        response.then().statusCode(200).body("", Matchers.hasSize(0));
    }
}
