package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Captain;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Coach;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Manager;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TeamCaptainDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TeamCoachDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TeamManagerDTO;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TeamRepresentativeMapper {
    public static TeamCaptainDTO captainToTeamCaptainDTO(Captain captain) {
        return new TeamCaptainDTO(captain.getId(), captain.getVersion(),
                captain.getAccount().getName(), captain.getAccount().getLastname()
        );
    }

    public static TeamCoachDTO coachToTeamCoachDTO(Coach coach) {
        return new TeamCoachDTO(coach.getId(), coach.getVersion(),
                coach.getAccount().getName(), coach.getAccount().getLastname()
        );
    }

    public static TeamManagerDTO managerToTeamManagerDTO(Manager manager) {
        return new TeamManagerDTO(manager.getId(), manager.getVersion(),
                manager.getAccount().getName(), manager.getAccount().getLastname()
        );
    }

    public static List<TeamManagerDTO> managerListToTeamManagerDTOList(Collection<Manager> managers) {
        return null == managers ? null : managers.stream()
                .filter(Objects::nonNull)
                .map(TeamRepresentativeMapper::managerToTeamManagerDTO)
                .collect(Collectors.toList());
    }
}
