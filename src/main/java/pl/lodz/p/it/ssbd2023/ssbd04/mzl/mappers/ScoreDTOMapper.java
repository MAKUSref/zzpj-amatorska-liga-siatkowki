package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Score;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.ScoreDTO;

public class ScoreDTOMapper {
    public static ScoreDTO ScoreToDTO(Score s){
        if (s == null) {
            return null;
        }
        return new ScoreDTO(s.getId(), s.getVersion(), SetDTOMapper.mapListToDTO(s.getSets()), s.getScoreboardPointsA(), s.getScoreboardPointsB(), s.getApprovalTeamA().value, s.getApprovalTeamB().value);
    }
}
