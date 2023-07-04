package pl.lodz.p.it.ssbd2023.ssbd04.mzl.algorithm.models;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;

@Getter
@Setter
public class TeamScore {
    private Team team;

    private int MMR;

    private int teamGames = 0;
}
