package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;

public class Mzl7ChangeGameDateTest extends TestUtils {

    private Response changeGameDate(String gameId, String jsonBody) {
        return RestAssured.given()
                .spec(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .body(jsonBody)
                .post(gameUri + "/changeGameDate/" + gameId);
    }

    private Response changeGameDateAccount(String gameId, String jsonBody, AccountDTO accountDTO) {
        return RestAssured.given()
                .spec(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(accountDTO))
                .log().all()
                .body(jsonBody)
                .post(gameUri + "/changeGameDate/" + gameId);
    }

    private Response changeGameDateGuest(String gameId, String jsonBody) {
        return RestAssured.given()
                .spec(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(false))
                .log().all()
                .body(jsonBody)
                .post(gameUri + "/changeGameDate/" + gameId);
    }

    private AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
    private AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);

    private String captain1Id = captain1.getRoles().get(0).getId().toString();
    private String captain2Id = captain2.getRoles().get(0).getId().toString();

    private String createTestGame() {
        String team1Id = createMzl6Team(captain1Id);
        String gameSquadAId = createMzl6GameSquad(team1Id);

        String team2Id = createMzl6Team(captain2Id);
        String gameSquadBId = createMzl6GameSquad(team2Id);

        String refereeId = createRefGetRoleId();
        String gameId = createMzl6Game(refereeId, gameSquadAId, gameSquadBId);

        return gameId;
    }

    private AccountDTO captain3 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
    private AccountDTO captain4 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);

    private String captain3Id = captain3.getRoles().get(0).getId().toString();
    private String captain4Id = captain4.getRoles().get(0).getId().toString();

    private String createEndedTestGame() {
        String team1Id = createMzl6Team(captain3Id);
        String gameSquadAId = createMzl6GameSquad(team1Id);

        String team2Id = createMzl6Team(captain4Id);
        String gameSquadBId = createMzl6GameSquad(team2Id);

        String refereeId = createRefGetRoleId();
        String gameId = createMzl6EndedGame(refereeId, gameSquadAId, gameSquadBId);

        return gameId;
    }

    @Test
    public void changeGameDatePositiveTest(){
        String gameId = createTestGame();
        String jsonBody = "{\"newDate\": \"2023-07-05 15:30:00\"}";
        Response response = changeGameDate(gameId, jsonBody);
        response.then().statusCode(200);
    }

    @Test
    public void changeGameDatePastDateTest(){
        String gameId = createTestGame();
        String jsonBody = "{\"newDate\": \"2022-05-05 15:30:00\"}";
        Response response = changeGameDate(gameId, jsonBody);
        response.then().statusCode(409);
    }

    @Test
    public void changeGameDateGameEndedTest(){
        String gameId = createEndedTestGame();
        String jsonBody = "{\"newDate\": \"2024-08-08 15:30:00\"}";
        Response response = changeGameDate(gameId, jsonBody);
        response.then().statusCode(409);
    }

    @Test
    public void changeGameDateAsNotAdminTest(){
        String gameId = createTestGame();
        String jsonBody = "{\"newDate\": \"2023-07-05 15:30:00\"}";
        Response response = changeGameDateAccount(gameId, jsonBody, captain1);
        response.then().statusCode(403);
    }
    @Test
    public void changeGameDateAsGuestTest(){
        String gameId = createTestGame();
        String jsonBody = "{\"newDate\": \"2023-07-05 15:30:00\"}";
        Response response = changeGameDateGuest(gameId, jsonBody);
        response.then().statusCode(403);
    }
}
