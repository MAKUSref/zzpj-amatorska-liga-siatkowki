package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Mzl2GetingScoreboard extends TestUtils {

    @Test
    public void generatingScoreboardTest(){



        CreateLeagueWithRoundParams createLeagueWithRoundParams = createLeagueWithRound();

        AccountDTO managerA = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        String teamAId = createApprovedTeamWithManager(managerA.getRoles().get(0).getId().toString(), managerA.getId().toString());
        AccountDTO managerB = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
        String teamBId = createApprovedTeamWithManager(managerB.getRoles().get(0).getId().toString(), managerB.getId().toString());
        String gamesquadAId = createGamesquad(teamAId);
        String gamesquadBId = createGamesquad(teamBId);

        String refereeId = createReferee();

        String scoreId = createUnacceptedScore(3, 1);

        String gameId = createGame(getAccount(refereeId).getRoles().get(0).getId().toString(), gamesquadAId, gamesquadBId, createLeagueWithRoundParams.roundId, scoreId);

        String set1Id = createSet(scoreId, 25, 3);
        String set2Id = createSet(scoreId, 12, 25);
        String set3Id = createSet(scoreId, 25, 19);
        String set4Id = createSet(scoreId, 25, 21);


        AcceptScoreDTO acceptScoreDTO = new AcceptScoreDTO(UUID.fromString(gameId), 1);

        String gameEtag = getGameETag(gameId);

        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("If-match", gameEtag)
                .cookie(authCookie(managerA))
                .body(acceptScoreDTO)
                .log().all()
                .patch(gameUri + "/acceptScore/" + teamAId);

        Assertions.assertEquals(200, response.getStatusCode());

        ScoreboardDTO scoreboard = getScoreboard(createLeagueWithRoundParams.scoreboardId);
        Assertions.assertEquals(2, scoreboard.getTeamScores().size());

        OverallTeamScoreDTO teamAScores = scoreboard.getTeamScores().stream().filter(s -> s.getTeam().equals(teamAId)).findFirst().orElse(null);
        OverallTeamScoreDTO teamBScores = scoreboard.getTeamScores().stream().filter(s -> s.getTeam().equals(teamBId)).findFirst().orElse(null);
        Assertions.assertNotNull(teamAScores);
        Assertions.assertNotNull(teamBScores);

        Assertions.assertEquals(3, teamAScores.getPoints());
        Assertions.assertEquals(1, teamAScores.getWonGames());
        Assertions.assertEquals(0, teamAScores.getLostGames());
        Assertions.assertEquals(3, teamAScores.getWonSets());
        Assertions.assertEquals(1, teamAScores.getLostSets());


        Assertions.assertEquals(1, teamBScores.getPoints());
        Assertions.assertEquals(0, teamBScores.getWonGames());
        Assertions.assertEquals(1, teamBScores.getLostGames());
        Assertions.assertEquals(1, teamBScores.getWonSets());
        Assertions.assertEquals(3, teamBScores.getLostSets());



    }

}
