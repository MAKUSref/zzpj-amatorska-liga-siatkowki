package pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager;

import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericManagerExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.GameFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.OverallTeamScoreFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.ScoreboardFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.TeamFacade;

import java.util.List;
import java.util.UUID;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({GenericManagerExceptionsInterceptor.class, TrackerInterceptor.class})
public class ScoreboardManager {

    @Inject
    ScoreboardFacade scoreboardFacade;

    @Inject
    GameFacade gameFacade;

    @Inject
    TeamFacade teamFacade;

    @Inject
    OverallTeamScoreFacade overallTeamScoreFacade;
    @PermitAll
    public List<Scoreboard> getAllScoreboards(){
        return scoreboardFacade.findAll();
    }

    @PermitAll
    public Scoreboard getScoreboard(UUID id){
        return scoreboardFacade.find(id);
    }


    public OverallTeamScore updateScoreboard(UUID teamId, UUID roundId, UUID lastApprovedGameId) {
        List<Game> games = gameFacade.findByTeamAndRound(teamId, roundId);
        Team team = teamFacade.find(teamId);
        Scoreboard scoreboard = scoreboardFacade.findByRound(roundId);

        OverallTeamScore overallTeamScore;


        List<OverallTeamScore> databaseScores = overallTeamScoreFacade.findByTeamAndScoreboard(teamId, scoreboard.getId());
        if(databaseScores.size() > 0){
            overallTeamScore = databaseScores.get(0);
        }else{
            overallTeamScore = new OverallTeamScore();
        }


        overallTeamScore = calculateTeamScore(team, scoreboard, games, overallTeamScore, lastApprovedGameId);
        overallTeamScoreFacade.edit(overallTeamScore);
        return overallTeamScore;
    }

    public OverallTeamScore calculateTeamScore(Team team, Scoreboard scoreboard ,List<Game> games, OverallTeamScore overallTeamScore, UUID lastApprovedGameId){

        overallTeamScore.setScoreboard(scoreboard);
        overallTeamScore.setTeam(team);
        overallTeamScore.setPoints(0);
        overallTeamScore.setWonSets(0);
        overallTeamScore.setLostSets(0);
        overallTeamScore.setWonGames(0);
        overallTeamScore.setLostGames(0);


        for(Game g : games){
            if(g.getScore() != null && (g.getScore().isApproved() || g.getScore().getId().equals(lastApprovedGameId))){
                if(g.getTeamA().getTeam().getId().equals(team.getId())){
                    overallTeamScore.setPoints(overallTeamScore.getPoints()+g.getScore().getScoreboardPointsA());
                    for(Set s : g.getScore().getSets()){
                        if(s.getTeamAPoints() > s.getTeamBPoints()){
                            overallTeamScore.setWonSets(overallTeamScore.getWonSets()+1);
                        }else{
                            overallTeamScore.setLostSets(overallTeamScore.getLostSets()+1);
                        }
                    }
                    if(g.getScore().getScoreboardPointsA() > g.getScore().getScoreboardPointsB()){
                        overallTeamScore.setWonGames(overallTeamScore.getWonGames()+1);
                    }else if(g.getScore().getScoreboardPointsA() < g.getScore().getScoreboardPointsB()){
                        overallTeamScore.setLostGames(overallTeamScore.getLostGames()+1);
                    }

                }else if(g.getTeamB().getTeam().getId().equals(team.getId())){
                    overallTeamScore.setPoints(overallTeamScore.getPoints()+g.getScore().getScoreboardPointsB());
                    for(Set s : g.getScore().getSets()){
                        if(s.getTeamBPoints() > s.getTeamAPoints()){
                            overallTeamScore.setWonSets(overallTeamScore.getWonSets()+1);
                        }else{
                            overallTeamScore.setLostSets(overallTeamScore.getLostSets()+1);
                        }
                    }
                    if(g.getScore().getScoreboardPointsB() > g.getScore().getScoreboardPointsA() ){
                        overallTeamScore.setWonGames(overallTeamScore.getWonGames()+1);
                    }else if(g.getScore().getScoreboardPointsB() < g.getScore().getScoreboardPointsA()){
                        overallTeamScore.setLostGames(overallTeamScore.getLostGames()+1);
                    }
                }
            }
        }
        return overallTeamScore;
    }
}
