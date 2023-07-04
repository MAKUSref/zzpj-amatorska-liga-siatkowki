package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Player;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.*;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

import java.util.UUID;

import static pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration.TestUtils.*;

public class MZL10AddPlayerToTeamTest extends TestUtils {

    private Response addPlayerToTeam(CreatePlayerDTO createPlayerDTO, AccountDTO accountDTO, boolean isRepresentative, boolean isAdmin, String etagString) {
        if(etagString == null)
        {
            Etag etag = new Etag();
            etagString = etag.calculateSignature(createPlayerDTO.getTeamDTO());
        }
        if (isRepresentative) {
            if (isAdmin) {

                return RestAssured.given()
                        .spec(requestSpecification)
                        .relaxedHTTPSValidation()
                        .cookie(authCookie(isAdmin))
                        .header("Content-Type", "application/json")
                        .header("If-Match", etagString)
                        .log().all()
                        .body(createPlayerDTO)
                        .post(playerUri);
            } else {
                return RestAssured.given()
                        .spec(requestSpecification)
                        .relaxedHTTPSValidation()
                        .cookie(authCookie(accountDTO))
                        .header("Content-Type", "application/json")
                        .header("If-Match", etagString)
                        .log().all()
                        .body(createPlayerDTO)
                        .post(playerUri);
            }
        } else {
            return RestAssured.given()
                    .spec(requestSpecification)
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .header("If-Match", etagString)
                    .log().all()
                    .body(createPlayerDTO)
                    .post(playerUri);
        }
    }

    private Response removePlayerFromTeam(RemovePlayerFromTeamDTO removePlayerFromTeamDTO, AccountDTO accountDTO, boolean isRepresentative, boolean isAdmin) {
        if (isRepresentative) {
            if (isAdmin) {

                return RestAssured.given()
                        .spec(requestSpecification)
                        .relaxedHTTPSValidation()
                        .cookie(authCookie(isAdmin))
                        .header("Content-Type", "application/json")
                        .log().all()
                        .body(removePlayerFromTeamDTO)
                        .patch(playerUri + "/removePlayer");
            } else {
                return RestAssured.given()
                        .spec(requestSpecification)
                        .relaxedHTTPSValidation()
                        .cookie(authCookie(accountDTO))
                        .header("Content-Type", "application/json")
                        .log().all()
                        .body(removePlayerFromTeamDTO)
                        .patch(playerUri + "/removePlayer");
            }
        } else {
            return RestAssured.given()
                    .spec(requestSpecification)
                    .relaxedHTTPSValidation()
                    .cookie(authCookie(true))
                    .header("Content-Type", "application/json")
                    .log().all()
                    .body(removePlayerFromTeamDTO)
                    .patch(playerUri + "/removePlayer");
        }
    }
    @Test
    public void addPlayerToTeamPositiveTestAsRepresentative () {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        CreatePlayerDTO createPlayerDTO = new CreatePlayerDTO("Mateusz", "Kaczmar", 40, true, teamDTO);
        Response response = addPlayerToTeam(createPlayerDTO, captain1, true, false, null);
        response.then().statusCode(200);
    }

    @Test
    public void addPlayerToTeamPositiveTestAsAdmin() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        CreatePlayerDTO createPlayerDTO = new CreatePlayerDTO("Mateusz", "Kaczmar", 40, true, teamDTO);
        Response response = addPlayerToTeam(createPlayerDTO, captain1, true, true, null);
        response.then().statusCode(200);
    }

    @Test
    public void addPlayerToTeamAsNotRepresentative() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        CreatePlayerDTO createPlayerDTO = new CreatePlayerDTO("Mateusz", "Kaczmar", 40, true, teamDTO);
        Response response = addPlayerToTeam(createPlayerDTO, captain2, true, false, null);
        response.then().statusCode(409);
    }

    @Test
    public void addPlayerToTeamAsGuest() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        CreatePlayerDTO createPlayerDTO = new CreatePlayerDTO("Mateusz", "Kaczmar", 40, true, teamDTO);
        Response response = addPlayerToTeam(createPlayerDTO, captain2, false, false, null);
        response.then().statusCode(401);
    }

    @Test
    public void addPlayerToTeamOptimisticLock() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        TeamDTO teamDTOOptimisticLock = new TeamDTO(teamDTO.getTeamId(), teamDTO.getTeamVersion()-1);
        CreatePlayerDTO createPlayerDTO = new CreatePlayerDTO("Mateusz", "Kaczmar", 40, true, teamDTOOptimisticLock);
        Response response = addPlayerToTeam(createPlayerDTO, captain1, true, true, null);
        response.then().statusCode(409);
    }
    @Test
    public void addPlayerToTeamETagException() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        String etag = getTeamETag(teamDTO.getTeamId().toString());
        TeamDTO teamDTOEtag = new TeamDTO(teamDTO.getTeamId(), teamDTO.getTeamVersion()+10);
        CreatePlayerDTO createPlayerDTO = new CreatePlayerDTO("Mateusz", "Kaczmar", 40, true, teamDTOEtag);
        Response response = addPlayerToTeam(createPlayerDTO, captain1, true, true, etag);
        response.then().statusCode(412);
    }

    @Test
    public void removePlayerFromTeamPositiveTestAsRepresentative () {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        CreatePlayerDTO createPlayerDTO = new CreatePlayerDTO("Mateusz", "Kaczmar", 40, true, teamDTO);
        Response response = addPlayerToTeam(createPlayerDTO, captain1, true, false, null);
        response.then().statusCode(200);

        TeamDTO teamFromDB = getTeamWithPlayers(teamDTO.getTeamId().toString());

        assert teamFromDB != null;
        Assertions.assertEquals(1, teamFromDB.getPlayers().size());

        PlayerDTO ourPlayer = teamFromDB.getPlayers().get(0);

        RemovePlayerFromTeamDTO removePlayerFromTeamDTO = new RemovePlayerFromTeamDTO(teamDTO.getTeamId(), ourPlayer.getId());

        response = removePlayerFromTeam(removePlayerFromTeamDTO, captain1, true, false);

        Assertions.assertEquals(204, response.getStatusCode());

        teamFromDB = getTeamWithPlayers(teamDTO.getTeamId().toString());

        assert teamFromDB != null;
        Assertions.assertEquals(0, teamFromDB.getPlayers().size());


        removePlayerFromTeamDTO = new RemovePlayerFromTeamDTO(teamDTO.getTeamId(), ourPlayer.getId());

        response = removePlayerFromTeam(removePlayerFromTeamDTO, captain1, true, false);

        Assertions.assertEquals("exception.player_not_in_team", response.getBody().print());

    }

    @Test
    public void removePlayerFromTeamPositiveTestAsAdmin() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        CreatePlayerDTO createPlayerDTO = new CreatePlayerDTO("Mateusz", "Kaczmar", 40, true, teamDTO);
        Response response = addPlayerToTeam(createPlayerDTO, captain1, true, true, null);
        response.then().statusCode(200);

        TeamDTO teamFromDB = getTeamWithPlayers(teamDTO.getTeamId().toString());

        assert teamFromDB != null;
        Assertions.assertEquals(1, teamFromDB.getPlayers().size());

        PlayerDTO ourPlayer = teamFromDB.getPlayers().get(0);

        RemovePlayerFromTeamDTO removePlayerFromTeamDTO = new RemovePlayerFromTeamDTO(teamDTO.getTeamId(), ourPlayer.getId());

        response = removePlayerFromTeam(removePlayerFromTeamDTO, captain1, false, true);

        Assertions.assertEquals(204, response.getStatusCode());

        teamFromDB = getTeamWithPlayers(teamDTO.getTeamId().toString());

        assert teamFromDB != null;
        Assertions.assertEquals(0, teamFromDB.getPlayers().size());


        removePlayerFromTeamDTO = new RemovePlayerFromTeamDTO(teamDTO.getTeamId(), ourPlayer.getId());

        response = removePlayerFromTeam(removePlayerFromTeamDTO, captain1, false, true);

        Assertions.assertEquals("exception.player_not_in_team", response.getBody().print());

    }

}
