package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RoundWithScoreboardDTO {
    String id;
    int roundNumber;
    ScoreboardDTO scoreboards;
}
