package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.DeclineScoreDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.GameDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TeamDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

import java.util.UUID;

public class Mzl5DeclineScore extends TestUtils {
    private Response declineScore(DeclineScoreDTO declineScoreDTO, AccountDTO accountDTO, boolean isRepresentative, boolean isAdmin, String etagString, UUID teamId) {
        if(etagString == null)
        {
            Etag etag = new Etag();
            etagString = etag.calculateSignature(declineScoreDTO);
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
                        .body(declineScoreDTO)
                        .patch(gameUri + "/declineScore/" + teamId.toString());
            } else {
                return RestAssured.given()
                        .spec(requestSpecification)
                        .relaxedHTTPSValidation()
                        .cookie(authCookie(accountDTO))
                        .header("Content-Type", "application/json")
                        .header("If-Match", etagString)
                        .log().all()
                        .body(declineScoreDTO)
                        .patch(gameUri + "/declineScore/" + teamId.toString());
            }
        } else {
            return RestAssured.given()
                    .spec(requestSpecification)
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .header("If-Match", etagString)
                    .log().all()
                    .body(declineScoreDTO)
                    .patch(gameUri + "/declineScore/" + teamId.toString());
        }
    }
    @Test
    public void declineScorePositiveTestAsAdmin () {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        String captain2Id = captain2.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        TeamDTO teamDTO2 = createMzl10TeamWithCaptain(captain2Id);
        String gameSquadAId = createMzl6GameSquad(teamDTO.getTeamId().toString());
        String gameSquadBId = createMzl6GameSquad(teamDTO2.getTeamId().toString());
        String refereeId = createRefGetRoleId();
        GameDTO gameDTO = createMzl10Game(refereeId, gameSquadAId, gameSquadBId);
        DeclineScoreDTO declineScoreDTO = new DeclineScoreDTO(gameDTO.getId(),gameDTO.getVersion());
        Response response = declineScore(declineScoreDTO, captain1, true, true, null, teamDTO.getTeamId());
        response.then().statusCode(200);
    }
    @Test
    public void declineScorePositiveTestAsRepresentative () {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        String captain2Id = captain2.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        TeamDTO teamDTO2 = createMzl10TeamWithCaptain(captain2Id);
        String gameSquadAId = createMzl6GameSquad(teamDTO.getTeamId().toString());
        String gameSquadBId = createMzl6GameSquad(teamDTO2.getTeamId().toString());
        String refereeId = createRefGetRoleId();
        GameDTO gameDTO = createMzl10Game(refereeId, gameSquadAId, gameSquadBId);
        DeclineScoreDTO declineScoreDTO = new DeclineScoreDTO(gameDTO.getId(),gameDTO.getVersion());
        Response response = declineScore(declineScoreDTO, captain1, true, false, null, teamDTO.getTeamId());
        response.then().statusCode(200);
    }
    @Test
    public void declineScoreNegativeTestAsGuest() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        String captain2Id = captain2.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        TeamDTO teamDTO2 = createMzl10TeamWithCaptain(captain2Id);
        String gameSquadAId = createMzl6GameSquad(teamDTO.getTeamId().toString());
        String gameSquadBId = createMzl6GameSquad(teamDTO2.getTeamId().toString());
        String refereeId = createRefGetRoleId();
        GameDTO gameDTO = createMzl10Game(refereeId, gameSquadAId, gameSquadBId);
        DeclineScoreDTO declineScoreDTO = new DeclineScoreDTO(gameDTO.getId(),gameDTO.getVersion());
        Response response = declineScore(declineScoreDTO, captain1, false, false, null, teamDTO.getTeamId());
        response.then().statusCode(401);
    }

    @Test
    public void declineScoreNegativeTestAsNotRepresentative() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain3 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        String captain2Id = captain2.getRoles().get(0).getId().toString();
        String captain3Id = captain3.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        TeamDTO teamDTO2 = createMzl10TeamWithCaptain(captain2Id);
        String gameSquadAId = createMzl6GameSquad(teamDTO.getTeamId().toString());
        String gameSquadBId = createMzl6GameSquad(teamDTO2.getTeamId().toString());
        String refereeId = createRefGetRoleId();
        GameDTO gameDTO = createMzl10Game(refereeId, gameSquadAId, gameSquadBId);
        DeclineScoreDTO declineScoreDTO = new DeclineScoreDTO(gameDTO.getId(),gameDTO.getVersion());
        Response response = declineScore(declineScoreDTO, captain3, true, false, null, teamDTO.getTeamId());
        response.then().statusCode(409);
    }
    @Test
    public void declineScoreNegativeTestOptimisticLock() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        String captain2Id = captain2.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        TeamDTO teamDTO2 = createMzl10TeamWithCaptain(captain2Id);

        String gameSquadAId = createMzl6GameSquad(teamDTO.getTeamId().toString());
        String gameSquadBId = createMzl6GameSquad(teamDTO2.getTeamId().toString());
        String refereeId = createRefGetRoleId();
        GameDTO gameDTO = createMzl10Game(refereeId, gameSquadAId, gameSquadBId);
        DeclineScoreDTO declineScoreDTO = new DeclineScoreDTO(gameDTO.getId(),gameDTO.getVersion()-1);
        Response response = declineScore(declineScoreDTO, captain1, true, true, null, teamDTO.getTeamId());
        response.then().statusCode(409);
    }
    @Test
    public void declineScoreNegativeTestEtagException() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        String captain2Id = captain2.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        TeamDTO teamDTO2 = createMzl10TeamWithCaptain(captain2Id);

        String gameSquadAId = createMzl6GameSquad(teamDTO.getTeamId().toString());
        String gameSquadBId = createMzl6GameSquad(teamDTO2.getTeamId().toString());
        String refereeId = createRefGetRoleId();
        GameDTO gameDTO = createMzl10Game(refereeId, gameSquadAId, gameSquadBId);
        String etag = getGameETag(gameDTO.getId().toString());

        DeclineScoreDTO declineScoreDTO = new DeclineScoreDTO(gameDTO.getId(),gameDTO.getVersion()+1);
        Response response = declineScore(declineScoreDTO, captain1, true, true, etag, teamDTO.getTeamId());
        response.then().statusCode(412);
    }

    @Test
    public void declineScoreNegativeTestTeamNotInGameException() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain3 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        String captain2Id = captain2.getRoles().get(0).getId().toString();
        String captain3Id = captain2.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        TeamDTO teamDTO2 = createMzl10TeamWithCaptain(captain2Id);
        TeamDTO teamDTO3 = createMzl10TeamWithCaptain(captain3Id);
        String gameSquadAId = createMzl6GameSquad(teamDTO.getTeamId().toString());
        String gameSquadBId = createMzl6GameSquad(teamDTO2.getTeamId().toString());
        String refereeId = createRefGetRoleId();
        GameDTO gameDTO = createMzl10Game(refereeId, gameSquadAId, gameSquadBId);
        DeclineScoreDTO declineScoreDTO = new DeclineScoreDTO(gameDTO.getId(),gameDTO.getVersion());
        Response response = declineScore(declineScoreDTO, captain1, true, true, null, teamDTO3.getTeamId());
        response.then().statusCode(404);
    }
}
