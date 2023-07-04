package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.AddGamesquadDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.EditGameSquadDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.GameDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Mzl12EditGamesquadTest extends TestUtils{


    private Response addGamesquad(EditGameSquadDTO editGamesquadDTO,AccountDTO accountDTO, boolean isAdmin, boolean isInTeam){

        if (isInTeam){
            if (isAdmin) {
                //isAdmin = true & isInTeam = true return a request without authorisation
                return RestAssured
                        .given()
                        .relaxedHTTPSValidation()
                        .header("Content-Type","application/json")
                        .body(editGamesquadDTO)
                        .log().all()
                        .post(gameUri+"/editGamesquad");
            }
            return RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type","application/json")
                    .cookie(authCookie(accountDTO))
                    .body(editGamesquadDTO)
                    .log().all()
                    .post(gameUri+"/editGamesquad");
        } else {
            return RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type","application/json")
                    .cookie(authCookie(isAdmin))
                    .body(editGamesquadDTO)
                    .log().all()
                    .post(gameUri+"/editGamesquad");
        }
    }

    @Test
    void AddGamesquadAsAdminPositiveTest() {
        AccountDTO account = registerAccount(randomEmail(),randomLogin(),"MANAGER").as(AccountDTO.class);
        String refId = createRefGetRoleId();
        String teamAid = createTeam();
        String teamBid = createTeam();
        String gamesquadAId = createGamesquad(teamAid);
        String gamesquadBId = createGamesquad(teamBid);
        String gameId = createGame(refId, gamesquadAId, gamesquadBId);
        List<String> playersA = createPlayer(5, teamAid);
        List<String> playersB = createPlayer(5, teamBid);

        EditGameSquadDTO editGamesquadDTO = new EditGameSquadDTO(playersA, gamesquadAId);
        Response response = addGamesquad(editGamesquadDTO,account, true, false);

        GameDTO gameFromDb = getGame(gameId);
        Assertions.assertEquals(response.getStatusCode(),200);
        Assertions.assertEquals(gameFromDb.getTeamA().getPlayers().size(),5);
        Assertions.assertEquals(gameFromDb.getTeamB().getPlayers().size(),0);

        editGamesquadDTO.setPlayerIds(playersB);
        editGamesquadDTO.setGamesquadId(gamesquadBId);
        response = addGamesquad(editGamesquadDTO,account, true, false);
        gameFromDb = getGame(gameId);

        Assertions.assertEquals(response.getStatusCode(),200);
        Assertions.assertEquals(gameFromDb.getTeamB().getPlayers().size(),5);
    }

    @Test
    void AddGamesquadAsTeamRepresentativePositiveTest() {
        AccountDTO account = registerAccount(randomEmail(),randomLogin(),"MANAGER").as(AccountDTO.class);
        String refId = createRefGetRoleId();
        String teamAid = createTeam();
        String teamBid = createTeam();
        String gamesquadAId = createGamesquad(teamAid);
        String gamesquadBId = createGamesquad(teamBid);
        String gameId = createGame(refId, gamesquadAId, gamesquadBId);
        List<String> playersA = createPlayer(5, teamAid);
        List<String> playersB = createPlayer(5, teamBid);
        addManagerToTeam(account.getRoles().get(0).getId().toString(), teamAid);

        EditGameSquadDTO editGamesquadDTO = new EditGameSquadDTO(playersA, gamesquadAId);
        Response response = addGamesquad(editGamesquadDTO,account, false, true);

        GameDTO gameFromDb = getGame(gameId);

        Assertions.assertEquals(response.getStatusCode(),200);
        Assertions.assertEquals(gameFromDb.getTeamA().getPlayers().size(),5);
    }

    @Test
    void AddGamesquadAsNotTeamRepresentativeNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(),randomLogin(),"MANAGER").as(AccountDTO.class);
        String refId = createRefGetRoleId();
        String teamAid = createTeam();
        String teamBid = createTeam();
        String gamesquadAId = createGamesquad(teamAid);
        String gamesquadBId = createGamesquad(teamBid);
        String gameId = createGame(refId, gamesquadAId, gamesquadBId);
        List<String> playersA = createPlayer(5, teamAid);
        List<String> playersB = createPlayer(5, teamBid);
        addManagerToTeam(account.getRoles().get(0).getId().toString(), teamBid);

        EditGameSquadDTO editGamesquadDTO = new EditGameSquadDTO(playersA, gamesquadAId);
        Response response = addGamesquad(editGamesquadDTO,account, false, true);

        GameDTO gameFromDb = getGame(gameId);
        Assertions.assertEquals(response.getStatusCode(),409);
        Assertions.assertEquals(gameFromDb.getTeamA().getPlayers().size(),0);
    }

    @Test
    void AddGamesquadUnauthorizedNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(),randomLogin(),"MANAGER").as(AccountDTO.class);
        String refId = createRefGetRoleId();
        String teamAid = createTeam();
        String teamBid = createTeam();
        String gamesquadAId = createGamesquad(teamAid);
        String gamesquadBId = createGamesquad(teamBid);
        String gameId = createGame(refId, gamesquadAId, gamesquadBId);
        List<String> playersA = createPlayer(5, teamAid);

        EditGameSquadDTO editGamesquadDTO = new EditGameSquadDTO(playersA, gamesquadAId);
        Response response = addGamesquad(editGamesquadDTO,account, true, true);

        GameDTO gameFromDb = getGame(gameId);
        Assertions.assertEquals(response.getStatusCode(),401);
        Assertions.assertEquals(gameFromDb.getTeamA().getPlayers().size(),0);
        Assertions.assertEquals(gameFromDb.getTeamB().getPlayers().size(),0);
    }

    @Test
    void AddGamesquadForbiddenNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(),randomLogin(),"MANAGER").as(AccountDTO.class);
        String refId = createRefGetRoleId();
        String teamAid = createTeam();
        String teamBid = createTeam();
        String gamesquadAId = createGamesquad(teamAid);
        String gamesquadBId = createGamesquad(teamBid);
        String gameId = createGame(refId, gamesquadAId, gamesquadBId);
        List<String> playersA = createPlayer(5, teamAid);

        EditGameSquadDTO editGamesquadDTO = new EditGameSquadDTO(playersA, gamesquadAId);
        Response response = addGamesquad(editGamesquadDTO,account, false, false);

        GameDTO gameFromDb = getGame(gameId);
        Assertions.assertEquals(response.getStatusCode(),409);
        Assertions.assertEquals(gameFromDb.getTeamA().getPlayers().size(),0);
        Assertions.assertEquals(gameFromDb.getTeamB().getPlayers().size(),0);
    }

    @Test
    void AddGamesquadIncorrectPlayersNegativeTest() {
        AccountDTO account = registerAccount(randomEmail(),randomLogin(),"MANAGER").as(AccountDTO.class);
        String refId = createRefGetRoleId();
        String teamAid = createTeam();
        String teamBid = createTeam();
        String gamesquadAId = createGamesquad(teamAid);
        String gamesquadBId = createGamesquad(teamBid);
        String gameId = createGame(refId, gamesquadAId, gamesquadBId);
        List<String> incorrectPlayers = new ArrayList<>();
        incorrectPlayers.add(UUID.randomUUID().toString());

        EditGameSquadDTO editGamesquadDTO = new EditGameSquadDTO(incorrectPlayers, gamesquadAId);
        Response response = addGamesquad(editGamesquadDTO,account, true, false);

        GameDTO gameFromDb = getGame(gameId);
        Assertions.assertEquals(response.getStatusCode(),404);
        Assertions.assertEquals(gameFromDb.getTeamA().getPlayers().size(),0);
        Assertions.assertEquals(gameFromDb.getTeamB().getPlayers().size(),0);
    }
}
