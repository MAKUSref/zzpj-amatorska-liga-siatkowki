package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.*;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

import java.util.Objects;
import java.util.UUID;

public class Mzl16ChangingRepresentativeOfTeam extends TestUtils{


    private Response addCaptainToTeamRequest(String teamId, String accountId){
        ChangeTeamAssignmentDto changeTeamAssignmentDto = new ChangeTeamAssignmentDto(UUID.fromString(teamId), 1, accountId);
        Etag etag = new Etag();
        String tag = etag.calculateSignature(changeTeamAssignmentDto);

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("If-Match", tag)
                .cookie(authCookie(true))
                .body(changeTeamAssignmentDto)
                .log().all()
                .post(teamUri+"/addCaptainToTeam");
    }

    private Response addCoachToTeamRequest(String teamId, String accountId){
        ChangeTeamAssignmentDto changeTeamAssignmentDto = new ChangeTeamAssignmentDto(UUID.fromString(teamId), 1, accountId);
        Etag etag = new Etag();
        String tag = etag.calculateSignature(changeTeamAssignmentDto);

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("If-Match", tag)
                .cookie(authCookie(true))
                .body(changeTeamAssignmentDto)
                .log().all()
                .post(teamUri+"/addCoachToTeam");
    }

    private Response addManagerToTeamRequest(String teamId, String accountId){
        ChangeTeamAssignmentDto changeTeamAssignmentDto = new ChangeTeamAssignmentDto(UUID.fromString(teamId), 1, accountId);
        Etag etag = new Etag();
        String tag = etag.calculateSignature(changeTeamAssignmentDto);

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("If-Match", tag)
                .cookie(authCookie(true))
                .body(changeTeamAssignmentDto)
                .log().all()
                .post(teamUri+"/addManagerToTeam");
    }


    private Response removeCaptainFromTeamRequest(String teamId){
        TeamIdDTO teamIdDTO = new TeamIdDTO(UUID.fromString(teamId), 1);
        Etag etag = new Etag();
        String tag = etag.calculateSignature(teamIdDTO);

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("If-Match", tag)
                .cookie(authCookie(true))
                .body(teamIdDTO)
                .log().all()
                .post(teamUri+"/removeCaptainFromTeam");
    }

    private Response removeCoachFromTeamRequest(String teamId){
        TeamIdDTO teamIdDTO = new TeamIdDTO(UUID.fromString(teamId), 1);
        Etag etag = new Etag();
        String tag = etag.calculateSignature(teamIdDTO);

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("If-Match", tag)
                .cookie(authCookie(true))
                .body(teamIdDTO)
                .log().all()
                .post(teamUri+"/removeCoachFromTeam");
    }

    private Response removeManagerFromTeamRequest(String teamId, String accountId){
        ChangeTeamAssignmentDto changeTeamAssignmentDto = new ChangeTeamAssignmentDto(UUID.fromString(teamId), 1, accountId);
        Etag etag = new Etag();
        String tag = etag.calculateSignature(changeTeamAssignmentDto);

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("If-Match", tag)
                .cookie(authCookie(true))
                .body(changeTeamAssignmentDto)
                .log().all()
                .post(teamUri+"/removeManagerFromTeam");

    }


    @Test
    public void changeTeamCaptain(){

        CreateLeagueWithRoundParams createLeagueWithRoundParams = createLeagueWithRound();

        String teamId = createTeamWithLeagueId(createLeagueWithRoundParams.leagueId);

        String accountId = createAccount();

        Response r = addCaptainToTeamRequest(teamId, accountId);

        Assertions.assertEquals(200, r.getStatusCode());

        TeamWithRepresentativesDTO team = getTeamWithRepresentatives(teamId);

        Assertions.assertNotNull(team);

        Assertions.assertEquals(accountId, team.getCaptain().getAccountId().toString());

        removeCaptainFromTeamRequest(teamId);

        TeamWithRepresentativesDTO teamAfter = getTeamWithRepresentatives(teamId);

        Assertions.assertNotNull(teamAfter);

        Assertions.assertNull(teamAfter.getCaptain());

    }

    @Test
    public void removeTeamCaptainWhenNotPresent(){

        CreateLeagueWithRoundParams createLeagueWithRoundParams = createLeagueWithRound();

        String teamId = createTeamWithLeagueId(createLeagueWithRoundParams.leagueId);


        Response r = removeCaptainFromTeamRequest(teamId);

        Assertions.assertEquals(400, r.getStatusCode());
        Assertions.assertEquals("exception.role_empty", r.getBody().print());
    }

    @Test
    public void addingTeamCaptainWhenAlreadyPresent(){

        CreateLeagueWithRoundParams createLeagueWithRoundParams = createLeagueWithRound();

        String teamId = createTeamWithLeagueId(createLeagueWithRoundParams.leagueId);

        String accountId = createAccount();
        String account2Id = createAccount();

        Response r = addCaptainToTeamRequest(teamId, accountId);

        Assertions.assertEquals(200, r.getStatusCode());

        TeamWithRepresentativesDTO team = getTeamWithRepresentatives(teamId);

        Assertions.assertNotNull(team);

        Assertions.assertEquals(accountId, team.getCaptain().getAccountId().toString());

        Response r2 = addCaptainToTeamRequest(teamId, account2Id);

        Assertions.assertEquals(400, r2.getStatusCode());
        Assertions.assertEquals("exception.role_occupied", r2.getBody().print());


    }


    @Test
    public void changeTeamCoach(){

        CreateLeagueWithRoundParams createLeagueWithRoundParams = createLeagueWithRound();

        String teamId = createTeamWithLeagueId(createLeagueWithRoundParams.leagueId);

        String accountId = createAccount();

        Response r = addCoachToTeamRequest(teamId, accountId);

        Assertions.assertEquals(200, r.getStatusCode());

        TeamWithRepresentativesDTO team = getTeamWithRepresentatives(teamId);

        Assertions.assertNotNull(team);

        Assertions.assertEquals(accountId, team.getCoach().getAccountId().toString());

        removeCoachFromTeamRequest(teamId);

        TeamWithRepresentativesDTO teamAfter = getTeamWithRepresentatives(teamId);

        Assertions.assertNotNull(teamAfter);

        Assertions.assertNull(teamAfter.getCoach());

    }

    @Test
    public void removeTeamCoachWhenNotPresent(){

        CreateLeagueWithRoundParams createLeagueWithRoundParams = createLeagueWithRound();

        String teamId = createTeamWithLeagueId(createLeagueWithRoundParams.leagueId);


        Response r = removeCoachFromTeamRequest(teamId);

        Assertions.assertEquals(400, r.getStatusCode());
        Assertions.assertEquals("exception.role_empty", r.getBody().print());
    }

    @Test
    public void addingTeamCoachWhenAlreadyPresent(){

        CreateLeagueWithRoundParams createLeagueWithRoundParams = createLeagueWithRound();

        String teamId = createTeamWithLeagueId(createLeagueWithRoundParams.leagueId);

        String accountId = createAccount();
        String account2Id = createAccount();

        Response r = addCoachToTeamRequest(teamId, accountId);

        Assertions.assertEquals(200, r.getStatusCode());

        TeamWithRepresentativesDTO team = getTeamWithRepresentatives(teamId);

        Assertions.assertNotNull(team);

        Assertions.assertEquals(accountId, team.getCoach().getAccountId().toString());

        Response r2 = addCoachToTeamRequest(teamId, account2Id);

        Assertions.assertEquals(400, r2.getStatusCode());
        Assertions.assertEquals("exception.role_occupied", r2.getBody().print());
    }






    @Test
    public void changeTeamManager(){

        CreateLeagueWithRoundParams createLeagueWithRoundParams = createLeagueWithRound();

        String teamId = createTeamWithLeagueId(createLeagueWithRoundParams.leagueId);

        String accountId = createAccount();
        String account2Id = createAccount();

        Response r = addManagerToTeamRequest(teamId, accountId);

        Assertions.assertEquals(200, r.getStatusCode());

        TeamWithRepresentativesDTO team = getTeamWithRepresentatives(teamId);

        Assertions.assertNotNull(team);

        Assertions.assertEquals(1, team.getManagers().size());

        Assertions.assertTrue(team.getManagers().stream().anyMatch(m -> m.getAccountId().equals(UUID.fromString(accountId))));



        Response r2 = addManagerToTeamRequest(teamId, account2Id);

        Assertions.assertEquals(200, r2.getStatusCode());

        team = getTeamWithRepresentatives(teamId);

        Assertions.assertNotNull(team);

        Assertions.assertEquals(2, team.getManagers().size());

        Assertions.assertTrue(team.getManagers().stream().anyMatch(m -> m.getAccountId().equals(UUID.fromString(accountId))));

        Assertions.assertTrue(team.getManagers().stream().anyMatch(m -> m.getAccountId().equals(UUID.fromString(account2Id))));



        removeManagerFromTeamRequest(teamId, accountId);

        team = getTeamWithRepresentatives(teamId);

        Assertions.assertNotNull(team);

        Assertions.assertEquals(1, team.getManagers().size());

        Assertions.assertTrue(team.getManagers().stream().anyMatch(m -> m.getAccountId().equals(UUID.fromString(account2Id))));


    }

    @Test
    public void removeTeamManagerWhenNotPresent(){

        CreateLeagueWithRoundParams createLeagueWithRoundParams = createLeagueWithRound();

        String teamId = createTeamWithLeagueId(createLeagueWithRoundParams.leagueId);

        String accountId = createAccount();

        Response r = removeManagerFromTeamRequest(teamId, accountId);

        Assertions.assertEquals(400, r.getStatusCode());
        Assertions.assertEquals("exception.role_empty", r.getBody().print());
    }

    @Test
    public void addingTeamManagerWhenAlreadyAdded(){

        CreateLeagueWithRoundParams createLeagueWithRoundParams = createLeagueWithRound();

        String teamId = createTeamWithLeagueId(createLeagueWithRoundParams.leagueId);

        String accountId = createAccount();

        Response r = addManagerToTeamRequest(teamId, accountId);

        Assertions.assertEquals(200, r.getStatusCode());

        TeamWithRepresentativesDTO team = getTeamWithRepresentatives(teamId);

        Assertions.assertNotNull(team);

        Assertions.assertEquals(accountId, Objects.requireNonNull(team.getManagers().stream().filter(m -> m.getAccountId().equals(UUID.fromString(accountId))).findFirst().orElse(null)).getAccountId().toString());

        Response r2 = addManagerToTeamRequest(teamId, accountId);

        Assertions.assertEquals(409, r2.getStatusCode());
        Assertions.assertEquals("exception.account_already_manager_in_this_team", r2.getBody().print());
    }




}
