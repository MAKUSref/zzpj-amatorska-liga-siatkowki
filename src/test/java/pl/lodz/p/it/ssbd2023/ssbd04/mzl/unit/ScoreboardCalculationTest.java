package pl.lodz.p.it.ssbd2023.ssbd04.mzl.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager.ScoreboardManager;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScoreboardCalculationTest {

    ScoreboardManager scoreboardManager = new ScoreboardManager();

    Team teamA;
    GameSquad gameSquadA;
    Team teamB;
    GameSquad gameSquadB;

    Scoreboard scoreboard;

    Set s1_1;
    Set s1_2;
    Set s1_3;
    Set s1_4;
    Score score1;
    Game g1;

    Set s2_1;
    Set s2_2;
    Set s2_3;
    Set s2_4;
    Score score2;
    Game g2;

    List<Game> games;

    @BeforeEach
    void init(){

        teamA = new Team();
        teamA.setId(UUID.randomUUID());

        gameSquadA = new GameSquad();
        gameSquadA.setTeam(teamA);


        teamB = new Team();
        teamB.setId(UUID.randomUUID());

        gameSquadB = new GameSquad();
        gameSquadB.setTeam(teamB);


        scoreboard = new Scoreboard();
        scoreboard.setId(UUID.randomUUID());

        s1_1 = new Set();
        s1_1.setTeamAPoints(25);
        s1_1.setTeamBPoints(19);

        s1_2 = new Set();
        s1_2.setTeamAPoints(22);
        s1_2.setTeamBPoints(25);

        s1_3 = new Set();
        s1_3.setTeamAPoints(25);
        s1_3.setTeamBPoints(12);

        s1_4 = new Set();
        s1_4.setTeamAPoints(25);
        s1_4.setTeamBPoints(21);

        score1 = new Score(UUID.randomUUID());
        score1.getSets().add(s1_1);
        score1.getSets().add(s1_2);
        score1.getSets().add(s1_3);
        score1.getSets().add(s1_4);
        score1.setScoreboardPointsA(3);
        score1.setScoreboardPointsB(1);
        score1.setApprovalTeamA(ScoreDecision.APPROVED);
        score1.setApprovalTeamB(ScoreDecision.APPROVED);


        g1 = new Game();
        g1.setTeamA(gameSquadA);
        g1.setTeamB(gameSquadB);
        g1.setScore(score1);


        s2_1 = new Set();
        s2_1.setTeamAPoints(25);
        s2_1.setTeamBPoints(19);

        s2_2 = new Set();
        s2_2.setTeamAPoints(22);
        s2_2.setTeamBPoints(25);

        s2_3 = new Set();
        s2_3.setTeamAPoints(12);
        s2_3.setTeamBPoints(25);

        s2_4 = new Set();
        s2_4.setTeamAPoints(25);
        s2_4.setTeamBPoints(21);

        score2 = new Score(UUID.randomUUID());
        score2.getSets().add(s2_1);
        score2.getSets().add(s2_2);
        score2.getSets().add(s2_3);
        score2.getSets().add(s2_4);
        score2.setScoreboardPointsA(2);
        score2.setScoreboardPointsB(2);
        score1.setApprovalTeamA(ScoreDecision.APPROVED);
        score1.setApprovalTeamB(ScoreDecision.APPROVED);


        g2 = new Game();
        g2.setTeamA(gameSquadB);
        g2.setTeamB(gameSquadA);
        g2.setScore(score2);

        games = new ArrayList<>();
        games.add(g1);
        games.add(g2);
    }

    @Test
    void calculatePositiveTeamScoreTest(){
        OverallTeamScore overallTeamScore = scoreboardManager.calculateTeamScore(teamA, scoreboard, games, new OverallTeamScore(), null);
        Assertions.assertEquals(1, overallTeamScore.getWonGames());
        Assertions.assertEquals(0 ,overallTeamScore.getLostGames());
        Assertions.assertEquals(5, overallTeamScore.getWonSets());
        Assertions.assertEquals(3, overallTeamScore.getLostSets());
        Assertions.assertEquals(5, overallTeamScore.getPoints());
    }


    @Test
    void calculateTeamScoreNotApprovedTest(){
        score1.setApprovalTeamA(ScoreDecision.DECLINE);
        OverallTeamScore overallTeamScore = scoreboardManager.calculateTeamScore(teamA, scoreboard, games, new OverallTeamScore(), null);
        Assertions.assertEquals(0, overallTeamScore.getWonGames());
        Assertions.assertEquals(0 ,overallTeamScore.getLostGames());
        Assertions.assertEquals(2, overallTeamScore.getWonSets());
        Assertions.assertEquals(2, overallTeamScore.getLostSets());
        Assertions.assertEquals(2, overallTeamScore.getPoints());
    }


    @Test
    void calculateTeamBScoreTest(){
        OverallTeamScore overallTeamScore = scoreboardManager.calculateTeamScore(teamB, scoreboard, games, new OverallTeamScore(), null);
        Assertions.assertEquals(0, overallTeamScore.getWonGames());
        Assertions.assertEquals(1 ,overallTeamScore.getLostGames());
        Assertions.assertEquals(3, overallTeamScore.getWonSets());
        Assertions.assertEquals(5, overallTeamScore.getLostSets());
        Assertions.assertEquals(3, overallTeamScore.getPoints());
    }

}
