package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Game;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GameDTOMapper {
    public static GameDTO gameToDTO (Game game, Account referee, Account postponingAccount) {
        String startTime;
        String endTime;
        String account;
        String postponeDate;
        String refNameLastname;
        GameSquadDTO gameSquadADTO;
        GameSquadDTO gameSquadBDTO;

        if(game.getStartTime() == null) {
            startTime = "";
        } else {
            startTime = game.getStartTime().toString();
        }

        if(game.getEndTime() == null) {
            endTime = "";
        } else {
            endTime = game.getEndTime().toString();
        }

        if(game.getPostponeDateRequest() == null) {
            postponeDate = "";
        } else {
            postponeDate = game.getPostponeDateRequest().toString();
        }

        if (postponingAccount == null){
            account = "";
        } else {
            account = postponingAccount.getName() + " " + postponingAccount.getLastname();
        }
        if(game.getTeamA() == null) {
            gameSquadADTO =  new GameSquadDTO();
        } else {
            gameSquadADTO = GameSquadDTOMapper.gameSquadToDTO(game.getTeamA());
        }
        if(game.getTeamB() == null) {
            gameSquadBDTO =  new GameSquadDTO();
        } else {
            gameSquadBDTO = GameSquadDTOMapper.gameSquadToDTO(game.getTeamB());
        }

        if (referee == null) {
            refNameLastname = "Referee not yet set";
        } else {
            refNameLastname = referee.getName() + " " + referee.getLastname();
        }

        return new GameDTO(game.getId(), game.getVersion(),
                new VenueDTO(game.getVenue().getAddress(), game.getVenue().getCourtNumber()),
                gameSquadADTO,
                gameSquadBDTO,
                refNameLastname,
                ScoreDTOMapper.ScoreToDTO(game.getScore()),
                startTime,
                endTime,
                game.getQueue(), game.isPostponed(), account, postponeDate);
    }

    public static List<GameDTO> mapList (List<Game> games, List<Account> referees) {
        List<GameDTO> gameDTOS = new ArrayList<>();

        for (int i=0; i < games.size(); i++) {
            gameDTOS.add(gameToDTO(games.get(i), referees.get(i), games.get(i).getRequestingAccount()));
        }
        return gameDTOS;
    }

    public  static List<RefereeGameDTO> mapRefereeGames(List<Game> games) {
        return games.stream().map(game -> {
            String startTime = game.getStartTime() == null ? null : game.getStartTime().format(DateTimeFormatter.ISO_DATE);
            String endTime = game.getEndTime() == null ? null : game.getEndTime().format(DateTimeFormatter.ISO_DATE);
            return new RefereeGameDTO(game.getTeamA(), game.getTeamB(), startTime, endTime, game.getQueue(), game.getScore() == null, game.getId(), game.getVersion());
        }).toList();
    }
}
