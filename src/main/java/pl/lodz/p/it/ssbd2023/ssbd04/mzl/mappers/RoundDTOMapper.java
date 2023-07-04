package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Round;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.RoundDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.RoundWithScoreboardDTO;

import java.util.*;
import java.util.stream.Collectors;

public class RoundDTOMapper {

    public static List<UUID> roundListToDTO(Collection<Round> roundList) {
        return null == roundList ? Collections.emptyList() : roundList.stream()
                .filter(Objects::nonNull)
                .map(Round::getId)
                .collect(Collectors.toList());
    }

    public static RoundDTO roundToDTO(Round round) {
        List<UUID> gameList = new ArrayList<>();
        List<UUID> leagueList = new ArrayList<>();

        round.getLeagues().forEach(league -> leagueList.add(league.getId()));
        round.getGames().forEach(game -> gameList.add(game.getId()));

        return new RoundDTO(round.getId(),
                round.getVersion(),
                round.getRoundNumber(),
                leagueList,
                gameList);
    }

    public static RoundWithScoreboardDTO roundToRoundWithScoreboardDTO(Round round){
        return new RoundWithScoreboardDTO(round.getId().toString(), round.getRoundNumber(), ScoreboardMapper.ScoreboardToDTO(round.getScoreboard()));
    }
}