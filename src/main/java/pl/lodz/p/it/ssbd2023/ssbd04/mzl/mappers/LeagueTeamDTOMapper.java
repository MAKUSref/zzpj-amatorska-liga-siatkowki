package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.LeagueTeamDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LeagueTeamDTOMapper {

    public static LeagueTeamDTO teamToDTO(Team team) {
        return new LeagueTeamDTO(team.getId(), team.getVersion(), team.getTeamName(), team.getCity());

    }
    public static List<LeagueTeamDTO> teamsToLeagueTeamDTOList(Collection<Team> teams) {
        return null == teams ? null : teams.stream()
                .filter(Objects::nonNull)
                .map(LeagueTeamDTOMapper::teamToDTO)
                .collect(Collectors.toList());

    }
}
