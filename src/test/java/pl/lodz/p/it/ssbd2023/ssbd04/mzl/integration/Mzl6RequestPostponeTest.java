package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;

import java.util.UUID;

public class Mzl6RequestPostponeTest extends TestUtils {

    private Response requestPostpone(String gameId, AccountDTO accountDTO) {
        return RestAssured.given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(accountDTO))
                .log().all()
                .patch(gameUri + "/requestPostpone/" + gameId);
    }

    private Response requestPostponeGuest(String gameId) {
        return RestAssured.given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .log().all()
                .patch(gameUri + "/requestPostpone/" + gameId);
    }

    private AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
    private AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
    private AccountDTO captain3 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);

    private AccountDTO coach1 = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
    private AccountDTO coach2 = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
    private AccountDTO coach3 = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);

    private String captain1Id = captain1.getRoles().get(0).getId().toString();
    private String captain2Id = captain2.getRoles().get(0).getId().toString();
    private String captain3Id = captain3.getRoles().get(0).getId().toString();

    private String coach1Id = coach1.getRoles().get(0).getId().toString();
    private String coach2Id = coach2.getRoles().get(0).getId().toString();
    private String coach3Id = coach3.getRoles().get(0).getId().toString();

    private String createTestGame() {
        String team1Id = createMzl6Team(captain1Id);
        String gameSquadAId = createMzl6GameSquad(team1Id);

        String team2Id = createMzl6Team(captain2Id);
        String gameSquadBId = createMzl6GameSquad(team2Id);

        String refereeId = createRefGetRoleId();
        String gameId = createMzl6Game(refereeId, gameSquadAId, gameSquadBId);

        return gameId;
    }

    private String createTestGameWithCoach() {
        String team1Id = createMzl6TeamWithCoach(coach1Id);
        String gameSquadAId = createMzl6GameSquad(team1Id);

        String team2Id = createMzl6TeamWithCoach(coach2Id);
        String gameSquadBId = createMzl6GameSquad(team2Id);

        String refereeId = createRefGetRoleId();
        String gameId = createMzl6Game(refereeId, gameSquadAId, gameSquadBId);

        return gameId;
    }

    @Test
    public void requestPostponeGameAsTeamARepresentativeTest() {
        String gameId = createTestGame();
        Response response = requestPostpone(gameId, captain1);
        response.then().statusCode(204);
    }

    @Test
    public void requestPostponeGameAsTeamBRepresentativeTest() {
        String gameId = createTestGame();
        Response response = requestPostpone(gameId, captain2);
        response.then().statusCode(204);
    }

    @Test
    public void requestPostponeGameAsTeamARepresentativeCoachTest() {
        String gameId = createTestGameWithCoach();
        Response response = requestPostpone(gameId, coach1);
        response.then().statusCode(204);
    }

    @Test
    public void requestPostponeGameAsTeamBRepresentativeCoachTest() {
        String gameId = createTestGameWithCoach();
        Response response = requestPostpone(gameId, coach2);
        response.then().statusCode(204);
    }

    @Test
    public void requestPostponeGameAsNotRepresentativeTest() {
        String gameId = createTestGame();
        Response response = requestPostpone(gameId, captain3);
        response.then().statusCode(409);
    }

    @Test
    public void requestPostponeGameAsNotRepresentativeCoachTest() {
        String gameId = createTestGameWithCoach();
        Response response = requestPostpone(gameId, coach3);
        response.then().statusCode(409);
    }

    @Test
    public void requestPostponeGameAsAdminTest() {
        String gameId = createTestGame();
        AccountDTO admin = registerAccount(randomEmail(), randomLogin(), "ADMIN").as(AccountDTO.class);
        Response response = requestPostpone(gameId, admin);
        response.then().statusCode(204);
    }

    @Test
    public void requestPostponeGameAsGuestTest() {
        String gameId = createTestGame();
        Response response = requestPostponeGuest(gameId);
        response.then().statusCode(401);
    }
}
