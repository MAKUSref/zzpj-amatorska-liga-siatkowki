package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ScoreboardDTO {
    String id;
    String roundId;
    List<OverallTeamScoreDTO> teamScores;
}
