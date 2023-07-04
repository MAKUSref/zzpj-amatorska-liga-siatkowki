package pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.SessionSynchronization;
import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.Valid;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractManager;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Set;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;
import jakarta.security.enterprise.SecurityContext;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractManager;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.GameException;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.MethodNotImplemented;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl.TeamNotFoundException;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl.TeamNotPlayInGame;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericManagerExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers.GameDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers.SetDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.tools.TeamTool;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({GenericManagerExceptionsInterceptor.class, TrackerInterceptor.class})
public class GameManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private GameFacade gameFacade;

    @Inject
    private PlayerFacade playerFacade;

    @Inject
    private TeamFacade teamFacade;

    @Inject
    private AccountFacadeMzl accountFacadeMzl;

    @Inject
    private SetFacade setFacade;

    @Inject
    private GameSquadFacade gameSquadFacade;

    @Inject
    private ScoreFacade scoreFacade;
    @Inject
    private PlayerManager playerManager;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private TeamTool teamTool;

    @Inject
    private GameSquadManager gameSquadManager;

    @Inject
    private ScoreboardManager scoreboardManager;

    @RolesAllowed("editGamesquad")
    public void editGamesquad(EditGameSquadDTO editGameSquadDTO) {
        GameSquad gameSquad = gameSquadFacade.find(UUID.fromString(editGameSquadDTO.getGamesquadId()));
        String callerId = securityContext.getCallerPrincipal().getName();
        Account currentAccount = accountFacadeMzl.find(UUID.fromString(callerId));
        if (gameSquad == null || currentAccount == null) {
            throw BaseApplicationException.createNoEntityException();
        }
        Team squadTeam = gameSquad.getTeam();

        if (!teamTool.isTeamRepresentative(UUID.fromString(callerId), squadTeam.getId()) &&
                currentAccount.getRoles().stream().noneMatch(role -> role.getRoleType().equals(RoleType.ADMIN))) {
            throw GameException.notATeamRepresentativeException();
        }

        gameSquad.clearSquad();

        for (String playerId : editGameSquadDTO.getPlayerIds()) {
            Player player = playerManager.getPlayerById(UUID.fromString(playerId));
            if (player == null) {
                throw BaseApplicationException.createNoEntityException();
            }
            player.getGameSquads().remove(gameSquad);

            if (squadTeam.getPlayers().stream().filter(p -> p.getId().equals(player.getId())).toList().size() > 0) {
                gameSquad.addPlayerToSquad(player);
            } else {
                //todo: dodać wjątek gdy ktoś chce dodać playera z nie swojej drużyny
            }

        }
        gameSquadFacade.edit(gameSquad);
    }

    @RolesAllowed("requestPostpone")
    public void postponeGameRequest(String uuid) {
        String accountUUID = securityContext.getCallerPrincipal().getName();
        Game gameToPostpone = gameFacade.find(UUID.fromString(uuid));

        Optional<Role> adminUserRole = accountFacadeMzl.find(UUID.fromString(accountUUID)).getRoles().stream()
                .filter(role -> role.getRoleType() == RoleType.ADMIN)
                .findFirst();

        if (!teamTool.isTeamRepresentative(UUID.fromString(accountUUID), gameToPostpone.getTeamA().getTeam().getId()) &&
            !teamTool.isTeamRepresentative(UUID.fromString(accountUUID), gameToPostpone.getTeamB().getTeam().getId()) &&
            adminUserRole.isEmpty())
        {
               throw GameException.notATeamRepresentativeException();
        }

        gameToPostpone.setPostponeRequest(true);
        gameToPostpone.setPostponed(true);
        var account = new Account(accountUUID);
        gameToPostpone.setRequestingAccount(account);
        gameFacade.edit(gameToPostpone);
    }

    @RolesAllowed("changeGameDate")
    public void changeGameDate(UUID uuid, NewGameDateDTO newGameDateDTO) {
        Game gameToChangeDate = gameFacade.find(uuid);
        if(newGameDateDTO.getGameVersion() < gameToChangeDate.getVersion()){
            throw new AppOptimisticLockException();
        }
        if (LocalDateTime.now().isAfter(newGameDateDTO.getNewDate())){
            throw GameException.pastPostponeDateException();
        }
        if ((gameToChangeDate.getEndTime() != null) && gameToChangeDate.getEndTime().isBefore(newGameDateDTO.getNewDate())){
            throw GameException.gameAlreadyFinishedException();
        }
        gameToChangeDate.setPostponeDateRequest(newGameDateDTO.getNewDate());
        gameFacade.edit(gameToChangeDate);
    }

    @RolesAllowed("acceptScore")
    public void acceptScore(UUID teamId, AcceptScoreDTO acceptScoreDTO) {
        String accountUUID = securityContext.getCallerPrincipal().getName();
        Account account = accountFacadeMzl.find(UUID.fromString(accountUUID));

        boolean isAdmin = account.getRoles().stream()
                .anyMatch(roles -> roles.getRoleType().equals(RoleType.ADMIN));

        if (teamTool.isTeamRepresentative(UUID.fromString(accountUUID), teamId) || isAdmin) {
            Game game = gameFacade.find(acceptScoreDTO.getGameId());

            if (acceptScoreDTO.getGameVersion() < game.getVersion()) {
                throw new AppOptimisticLockException();
            }

            if (game.getTeamA().getTeam().getId().equals(teamId)) {
                game.getScore().setApprovalTeamA(ScoreDecision.APPROVED);
                scoreFacade.edit(game.getScore());
            } else if (game.getTeamB().getTeam().getId().equals(teamId)) {
                game.getScore().setApprovalTeamB(ScoreDecision.APPROVED);
                scoreFacade.edit(game.getScore());
            } else {
                throw new TeamNotPlayInGame();
            }
            if(game.getScore().isApproved()){
                scoreboardManager.updateScoreboard(game.getTeamA().getTeam().getId(), game.getRound().getId(), game.getScore().getId());
                scoreboardManager.updateScoreboard(game.getTeamB().getTeam().getId(), game.getRound().getId(), game.getScore().getId());
            }


        } else {
            throw GameException.notATeamRepresentativeException();
        }


    }

    @RolesAllowed("declineScore")
    public void declineScore(UUID teamId, DeclineScoreDTO declineScoreDTO) {
        String accountUUID = securityContext.getCallerPrincipal().getName();
        Account account = accountFacadeMzl.find(UUID.fromString(accountUUID));

        boolean isAdmin = account.getRoles().stream()
                .anyMatch(roles -> roles.getRoleType().equals(RoleType.ADMIN));

        if (teamTool.isTeamRepresentative(UUID.fromString(accountUUID), teamId) || isAdmin) {
            Game game = gameFacade.find(declineScoreDTO.getGameId());

            if (declineScoreDTO.getGameVersion() < game.getVersion()) {
                throw new AppOptimisticLockException();
            }

            if (game.getTeamA().getTeam().getId().equals(teamId)) {
                game.getScore().setApprovalTeamA(ScoreDecision.DECLINE);
                scoreFacade.edit(game.getScore());
            } else if (game.getTeamB().getTeam().getId().equals(teamId)) {
                game.getScore().setApprovalTeamB(ScoreDecision.DECLINE);
                scoreFacade.edit(game.getScore());
            } else
                throw new TeamNotPlayInGame();
        } else
            throw GameException.notATeamRepresentativeException();
    }

    @RolesAllowed("addScoreToGame")
    public void addScoreToGame(@Valid AddGameScoreDTO addGameScoreDTO) {
        Game game = gameFacade.find(UUID.fromString(addGameScoreDTO.getGameId()));
        Score score = game.getScore() != null ? game.getScore() : new Score();
        List<Set> sets = SetDTOMapper.maplist(addGameScoreDTO.getSets());
        score.setSets(sets);
        sets.forEach(set -> set.setScore(score));
        score.setScoreboardPointsA(addGameScoreDTO.getScoreboardPointsA());
        score.setScoreboardPointsB(addGameScoreDTO.getScoreboardPointsB());
        score.setApprovalTeamA(ScoreDecision.NONE);
        score.setApprovalTeamB(ScoreDecision.NONE);
        score.getGames().add(game);
        game.setScore(score);
    }

    @RolesAllowed("generateTimetable")
    public void createGames(List<Game> games) {
        games.forEach(game -> {
            gameSquadManager.createGameSqad(game.getTeamA());
            gameSquadManager.createGameSqad(game.getTeamB());
            gameFacade.create(game);
        });
    }

    @PermitAll
    public List<GameDTO> getAllByTeam(String teamId) {
        List<Game> games = gameFacade.findByTeamId(UUID.fromString(teamId));
        List<Account> referees = new ArrayList<>();
        for (Game game : games) {
            if (game.getReferee() == null) {
                referees.add(null);
            } else {
                referees.add(accountFacadeMzl.findByRoleId(game.getReferee().getId()));
            }
        }

        return GameDTOMapper.mapList(games, referees);
    }

    @PermitAll
    public List<GameDTO> getAllByIdList(List<String> idList) {
        List<Game> games = gameFacade.findByIdList(idList);
        List<Account> referees = new ArrayList<>();

        for (Game game : games) {
            if (game.getReferee() == null) {
                referees.add(null);
            } else {
                referees.add(accountFacadeMzl.findByRoleId(game.getReferee().getId()));
            }
        }

        return GameDTOMapper.mapList(games, referees);
    }

    @PermitAll
    public GameDTO getById(String id) {
        Game game = gameFacade.find(UUID.fromString(id));
        Referee referee = game.getReferee();
        if (referee == null) {
            return GameDTOMapper.gameToDTO(game, null, game.getRequestingAccount());
        }
        return GameDTOMapper.gameToDTO(game, game.getReferee().getAccount(), game.getRequestingAccount());
    }

    @RolesAllowed("getRefereeGames")
    public List<Game> getRefereeGames(String refereeId) {
        return gameFacade.findRefereeGames(refereeId);
    }

    @PermitAll
    public void addReferee(UUID gameId, UUID refereeId) {
        Account referee = accountFacadeMzl.find(refereeId);
        Game game = gameFacade.find(gameId);

        if (referee == null || game == null) {
            throw BaseApplicationException.createNoEntityException();
        }
        Optional<Role> refRole = referee.getRoles().stream()
                .filter(role -> role.getRoleType().equals(RoleType.REFEREE))
                .findFirst();

        if (refRole.isPresent()) {
            game.setReferee((Referee) refRole.get());
            ((Referee) refRole.get()).getGames().add(game);
            gameFacade.edit(game);
            accountFacadeMzl.edit(referee);
        } else {
            throw BaseApplicationException.incorrectRole();
        }

    }
}
