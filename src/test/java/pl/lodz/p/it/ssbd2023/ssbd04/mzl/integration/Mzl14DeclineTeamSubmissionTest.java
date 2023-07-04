package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.SubmitTeamForLeagueDto;

import java.util.UUID;

public class Mzl14DeclineTeamSubmissionTest extends TestUtils {

    private String submitTeamForLeague() {
        String leagueId = createLeague();
        AccountDTO manager = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        String teamId = createUnapprovedTeamWithManager(manager.getRoles().get(0).getId().toString(), manager.getId().toString());
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(teamId, leagueId);
        //TeamDTO teamDTO = getTeam(teamId);


             RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .cookie(authCookie(true))
                    .body(submitTeamForLeagueDto)
                    .log().all()
                    .post(teamUri + "/submitTeamForLeague");


         return teamId;
    }

    private Response declineTeamSubmission(String id, boolean isAdmin, boolean authorized) {
        if (authorized) {
            return RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .cookie(authCookie(isAdmin))
                    .log().all()
                    .post(teamUri + "/declineSubmission/" + id);
        } else {
            return RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .log().all()
                    .post(teamUri + "/declineSubmission/" + id);
        }
    }

    @Test
    void declineTeamPositiveTest() {
        String teamId = submitTeamForLeague();

        Response response = declineTeamSubmission(teamId, true, true);
        Assertions.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    void declineNotExistingTeamNegativeTest() {
        String teamId = UUID.randomUUID().toString();

        Response response = declineTeamSubmission(teamId, true, true);
        Assertions.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    void declineNotSubmittedTeamNegativeTest() {
        String teamId = createUnapprovedTeam();

        Response response = declineTeamSubmission(teamId, true, true);
        Assertions.assertEquals(response.getStatusCode(), 406);
    }

    @Test
    void declineTeamAlreadyApprovedNegativeTest() {
        String teamId = createApprovedTeam();

        Response response = declineTeamSubmission(teamId, true, true);
        Assertions.assertEquals(response.getStatusCode(), 406);
    }

    @Test
    void declineTeamForbiddenNegativeTest() {
        String teamId = createApprovedTeam();

        Response response = declineTeamSubmission(teamId, false, true);
        Assertions.assertEquals(response.getStatusCode(), 403);
    }

    @Test
    void declineTeamUnauthorisedNegativeTest() {
        String teamId = createApprovedTeam();

        Response response = declineTeamSubmission(teamId, false, false);
        Assertions.assertEquals(response.getStatusCode(), 401);
    }



}
