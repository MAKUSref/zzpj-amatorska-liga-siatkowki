package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Player;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.AddGamesquadDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.EditGameSquadDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.GameDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.PlayerDTO;

import java.util.List;
import java.util.UUID;

public class Mzl17ChangingGameSquad extends TestUtils {

    private Response addGamesquad(AddGamesquadDTO addGamesquadDTO, AccountDTO accountDTO, boolean isAdmin, boolean isInTeam){

        if (isInTeam){
            if (isAdmin) {
                //isAdmin = true & isInTeam = true return a request without authorisation
                return RestAssured
                        .given()
                        .relaxedHTTPSValidation()
                        .header("Content-Type","application/json")
                        .body(addGamesquadDTO)
                        .log().all()
                        .post(gameUri+"/addGamesquad");
            }
            return RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type","application/json")
                    .cookie(authCookie(accountDTO))
                    .body(addGamesquadDTO)
                    .log().all()
                    .post(gameUri+"/addGamesquad");
        } else {
            return RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type","application/json")
                    .cookie(authCookie(isAdmin))
                    .body(addGamesquadDTO)
                    .log().all()
                    .post(gameUri+"/addGamesquad");
        }
    }

    private Response changeGamesquad(EditGameSquadDTO editGameSquadDTO, AccountDTO accountDTO, boolean isAdmin, boolean isInTeam){

        if (isInTeam){
            if (isAdmin) {
                //isAdmin = true & isInTeam = true return a request without authorisation
                return RestAssured
                        .given()
                        .relaxedHTTPSValidation()
                        .header("Content-Type","application/json")
                        .body(editGameSquadDTO)
                        .log().all()
                        .patch(gameUri+"/squad");
            }
            return RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type","application/json")
                    .cookie(authCookie(accountDTO))
                    .body(editGameSquadDTO)
                    .log().all()
                    .patch(gameUri+"/squad");
        } else {
            return RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type","application/json")
                    .cookie(authCookie(isAdmin))
                    .body(editGameSquadDTO)
                    .log().all()
                    .patch(gameUri+"/squad");
        }
    }

    @Test
    public void changingGameSquadTest(){
        AccountDTO account = registerAccount(randomEmail(),randomLogin(),"MANAGER").as(AccountDTO.class);
        String refId = createRefGetRoleId();
        String teamAid = createTeam();
        String teamBid = createTeam();
        String gameId = createGame(refId, createGamesquad(teamAid), createGamesquad(teamBid));
        List<String> playersA = createPlayer(5, teamAid);
        List<String> playersB = createPlayer(5, teamBid);

        AddGamesquadDTO addGamesquadDTO = new AddGamesquadDTO(UUID.fromString(gameId), 0, playersA ,UUID.fromString(gameId));

        Response response = addGamesquad(addGamesquadDTO,account, true, false);

        GameDTO gameFromDb = getGame(gameId);

        Assertions.assertNotNull(gameFromDb);
        Assertions.assertEquals(5, gameFromDb.getTeamA().getPlayers().size());
        for(PlayerDTO p : gameFromDb.getTeamA().getPlayers()){
            Assertions.assertTrue(playersA.stream().anyMatch(playerId -> UUID.fromString(playerId).equals(p.getId())));
        }
        List<String> playersA2 = createPlayer(3, teamAid);

        //EditGameSquadDTO editGameSquadDTO = new EditGameSquadDTO(gameFromDb.getTeamA().getId().toString(), playersA2);
        EditGameSquadDTO editGameSquadDTO = new EditGameSquadDTO();

        response = changeGamesquad(editGameSquadDTO, account, true, false);

        Assertions.assertEquals(200, response.getStatusCode());

        gameFromDb = getGame(gameId);

        Assertions.assertNotNull(gameFromDb);
        Assertions.assertEquals(3, gameFromDb.getTeamA().getPlayers().size());
        for(PlayerDTO p : gameFromDb.getTeamA().getPlayers()){
            Assertions.assertTrue(playersA2.stream().anyMatch(playerId -> UUID.fromString(playerId).equals(p.getId())));
        }

        editGameSquadDTO.setPlayerIds(playersA);
        response = changeGamesquad(editGameSquadDTO, account, true, false);

        Assertions.assertEquals(200, response.getStatusCode());
        gameFromDb = getGame(gameId);

        Assertions.assertNotNull(gameFromDb);
        Assertions.assertEquals(5, gameFromDb.getTeamA().getPlayers().size());
        for(PlayerDTO p : gameFromDb.getTeamA().getPlayers()){
            Assertions.assertTrue(playersA.stream().anyMatch(playerId -> UUID.fromString(playerId).equals(p.getId())));
        }

    }

}
