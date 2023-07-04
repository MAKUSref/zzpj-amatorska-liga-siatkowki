package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;



import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TeamDTO;

import java.util.ArrayList;
import java.util.List;

public class TeamDTOMapper {
    public static TeamDTO teamToDto(Team team) {
        TeamDTO teamDTO = new TeamDTO(team.getId(), team.getVersion(),team.getTeamName(), team.getCity());
        if (team.getLeague() != null) {
            teamDTO.setInLeague(true);
            teamDTO.setLeagueNumber(team.getLeague().getLeagueNumber());
            teamDTO.setApproved(team.isApproved());
        }
        return teamDTO;
    }

    public static TeamDTO teamToDtoWithPlayers(Team team) {
        return new TeamDTO(team.getId(), team.getVersion(),team.getTeamName(),team.getCity(), PlayerDTOMapper.mapList(team.getPlayers()));
    }

    public static List<TeamDTO> mapList (List<Team> teams) {
        List<TeamDTO> teamDTOS = new ArrayList<>();

        for (Team team: teams) {
            teamDTOS.add(teamToDto(team));
        }
        return teamDTOS;
    }
}
