package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class LeagueWithScoreboardsDTO {

    String id;
    int leagueNumber;
    String season;

    List<RoundWithScoreboardDTO> rounds;

}
