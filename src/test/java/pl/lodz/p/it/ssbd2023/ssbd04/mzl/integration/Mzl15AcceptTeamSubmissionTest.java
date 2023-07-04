package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.SubmitTeamForLeagueDto;

import java.util.UUID;

public class Mzl15AcceptTeamSubmissionTest extends TestUtils {

    private String submitTeamForLeague() {
        String leagueId = createLeague();
        AccountDTO manager = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        String teamId = createUnapprovedTeamWithManager(manager.getRoles().get(0).getId().toString(), manager.getId().toString());
        SubmitTeamForLeagueDto submitTeamForLeagueDto = new SubmitTeamForLeagueDto(teamId, leagueId);

        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .body(submitTeamForLeagueDto)
                .log().all()
                .post(teamUri + "/submitTeamForLeague");

        return teamId;
    }

    private Response acceptTeamSubmission(String id, boolean isAdmin, boolean authorised) {
        if (authorised){
            return RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .cookie(authCookie(isAdmin))
                    .log().all()
                    .post(teamUri + "/acceptSubmission/" + id);
        }else {
            return RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .log().all()
                    .post(teamUri + "/acceptSubmission/" + id);
        }
    }

    @Test
    void acceptTeamPositiveTest() {
        String teamId = submitTeamForLeague();

        Response response = acceptTeamSubmission(teamId, true,true);
        Assertions.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    void acceptNotExistingTeamNegativeTest() {
        String teamId = UUID.randomUUID().toString();

        Response response = acceptTeamSubmission(teamId, true,true);
        Assertions.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    void acceptNotSubmittedTeamNegativeTest() {
        String teamId = createUnapprovedTeam();

        Response response = acceptTeamSubmission(teamId, true,true);
        Assertions.assertEquals(response.getStatusCode(), 406);
    }

    @Test
    void acceptTeamAlreadyApprovedNegativeTest() {
        String teamId = createApprovedTeam();

        Response response = acceptTeamSubmission(teamId, true,true);
        Assertions.assertEquals(response.getStatusCode(), 406);
    }

    @Test
    void acceptTeamForbiddenNegativeTest() {
        String teamId = createApprovedTeam();

        Response response = acceptTeamSubmission(teamId, false, true);
        Assertions.assertEquals(response.getStatusCode(), 403);
    }

    @Test
    void acceptTeamUnauthorisedNegativeTest() {
        String teamId = createApprovedTeam();

        Response response = acceptTeamSubmission(teamId, false, false);
        Assertions.assertEquals(response.getStatusCode(), 401);
    }
}
