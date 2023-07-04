package pl.lodz.p.it.ssbd2023.ssbd04.mzl.algorithm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Venue;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Window {
    Team teamA;

    Team teamB;

    Date date;

    Venue venue;
}
