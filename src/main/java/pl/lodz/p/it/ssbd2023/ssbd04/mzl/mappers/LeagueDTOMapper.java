package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.League;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Round;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.LeagueDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.LeagueWithScoreboardsDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.RoundWithScoreboardDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LeagueDTOMapper {
    public static LeagueDTO LeagueToDTO(League league){
        return new LeagueDTO(
                league.getId(), league.getVersion(),
                LeagueTeamDTOMapper.teamsToLeagueTeamDTOList(league.getTeams()), league.getLeagueNumber(), league.getSeason()
        );
    }
    
    public static LeagueDTO LeagueToDTONoTeams(League league) {
        return new LeagueDTO(league.getId(), league.getVersion(), league.getLeagueNumber(), league.getSeason());
    } 

    public static List<LeagueDTO> leaguesToLeagueDTOList(List<League> leagues) {
        return leagues == null ? null : leagues.stream()
                .filter(Objects::nonNull)
                .map(LeagueDTOMapper::LeagueToDTONoTeams)
                .collect(Collectors.toList());

    }

    public static League leagueDTOToLeague(LeagueDTO leagueDTO) {
        League league = new League();
        league.setLeagueNumber(leagueDTO.getLeagueNumber());
        league.setSeason(leagueDTO.getSeason());
        return league;
    }

    public static List<LeagueWithScoreboardsDTO> leaguesToLeagueWithScoreboardsDTO(List<League> leagues){
        return leagues == null ? null : leagues.stream()
                .filter(Objects::nonNull)
                .map(LeagueDTOMapper::leagueToLeagueWithScoreboardsDTO)
                .toList();
    }

    public static LeagueWithScoreboardsDTO leagueToLeagueWithScoreboardsDTO(League league){
        List<Round> allRounds = league.getRounds();
        List<RoundWithScoreboardDTO> roundsWithScoreboardDTO = allRounds.stream().map(RoundDTOMapper::roundToRoundWithScoreboardDTO).toList();

        return new LeagueWithScoreboardsDTO(league.getId().toString(), league.getLeagueNumber(), league.getSeason(), roundsWithScoreboardDTO);
    }
}
