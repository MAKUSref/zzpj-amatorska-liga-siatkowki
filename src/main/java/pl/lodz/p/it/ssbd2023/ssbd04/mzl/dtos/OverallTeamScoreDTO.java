package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class OverallTeamScoreDTO {
    String team;
    int points;
    int wonGames;
    int lostGames;
    int wonSets;
    int lostSets;
}
