package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.OverallTeamScoreDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.ScoreboardDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.OverallTeamScore;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardMapper {

    public static ScoreboardDTO ScoreboardToDTO(Scoreboard s){

        List<OverallTeamScoreDTO> teamScoreDTOS = new ArrayList<>();
        for(OverallTeamScore o : s.getTeamsScores()){
            teamScoreDTOS.add(OverallTeamScoreToDTO(o));
        }
        return new ScoreboardDTO(s.getId().toString(), s.getRound().getId().toString(), teamScoreDTOS);
    }

    public static OverallTeamScoreDTO OverallTeamScoreToDTO(OverallTeamScore o){
        return new OverallTeamScoreDTO(o.getTeam().getId().toString(), o.getPoints(), o.getWonGames(), o.getLostGames(), o.getWonSets(), o.getLostSets());
    }

}
