package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.mappers.RoleDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.ShowTeamDTO;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShowTeamDTOMapper {
    public static ShowTeamDTO teamToShowTeamDTO(Team team) {
        ShowTeamDTO showTeamDTO = new ShowTeamDTO(team.getId(), team.getVersion(), team.getTeamName(), team.getCity());
        if (team.getLeague() != null) {
            showTeamDTO.setInLeague(true);
            showTeamDTO.setLeagueNumber(team.getLeague().getLeagueNumber());
            showTeamDTO.setApproved(team.isApproved());
        }
        return showTeamDTO;
    }

    public static List<ShowTeamDTO> teamsToShowTeamDTOList(Collection<Team> teams) {
        return null == teams ? null : teams.stream()
                .filter(Objects::nonNull)
                .map(ShowTeamDTOMapper::teamToShowTeamDTO)
                .collect(Collectors.toList());
    }
}
