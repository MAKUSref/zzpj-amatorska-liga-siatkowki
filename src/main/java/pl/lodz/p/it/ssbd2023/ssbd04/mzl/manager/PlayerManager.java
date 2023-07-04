package pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager;

import com.nimbusds.jose.crypto.impl.PRFParams;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.SessionSynchronization;
import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.security.enterprise.SecurityContext;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractManager;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.GameException;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.MethodNotImplemented;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl.PlayerIsInGameSquadException;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl.PlayerNotInTeamException;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericManagerExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.CreatePlayerDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.RemovePlayerFromTeamDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.AccountFacadeMzl;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.PlayerFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.TeamFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.roles.Roles;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.tools.TeamTool;

import java.util.List;
import java.util.UUID;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({GenericManagerExceptionsInterceptor.class, TrackerInterceptor.class})
public class PlayerManager extends AbstractManager implements SessionSynchronization {

    @Inject
    private PlayerFacade playerFacade;

    @Inject
    private TeamFacade teamFacade;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private AccountFacadeMzl accountFacadeMzl;

    @Inject
    private TeamTool teamTool;

    @RolesAllowed("getPlayers")
    public Player getPlayerById(UUID id){
        return playerFacade.find(id);
    }

    @RolesAllowed("addPlayerToTeam")
    public void addPlayerToTeam(CreatePlayerDTO createPlayerDTO) {
        String accountUUID = securityContext.getCallerPrincipal().getName();
        Account account = accountFacadeMzl.find(UUID.fromString(accountUUID));
        Team team = teamFacade.find(createPlayerDTO.getTeamDTO().getTeamId());

        if (createPlayerDTO.getTeamDTO().getTeamVersion() < team.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }

        boolean isAdmin = account.getRoles().stream()
                .anyMatch(roles -> roles.getRoleType().equals(RoleType.ADMIN));

        if (teamTool.isTeamRepresentative(UUID.fromString(accountUUID), team.getId()) || isAdmin) {
            Player player = new Player(createPlayerDTO.getFirstName(), createPlayerDTO.getLastName(), createPlayerDTO.getAge(), createPlayerDTO.isPro(), team);
            playerFacade.create(player);
            team.getPlayers().add(player);
            teamFacade.edit(team);
        } else
            throw GameException.notATeamRepresentativeException();
    }

    @RolesAllowed("removePlayerFromTeam")
    public void removePlayerFromTeam(RemovePlayerFromTeamDTO removePlayerFromTeamDTO){
        Team t = teamFacade.find(removePlayerFromTeamDTO.getTeamId());

        String callerId = securityContext.getCallerPrincipal().getName();

        Account currentAccount = accountFacadeMzl.find(UUID.fromString(callerId));

        if (!teamTool.isTeamRepresentative(UUID.fromString(callerId), removePlayerFromTeamDTO.getTeamId()) &&
                currentAccount.getRoles().stream().noneMatch(role -> role.getRoleType().equals(RoleType.ADMIN))) {
            throw GameException.notATeamRepresentativeException();
        }

        if(t.getPlayers().stream().noneMatch(p -> p.getId().equals(removePlayerFromTeamDTO.getPlayerId()))){
            throw new PlayerNotInTeamException();
        }

        for( GameSquad gs : t.getGameSquads()){
            if(gs.getGame() == null || gs.getGame().getScore() == null){
                if(gs.getPlayers().stream().anyMatch(p -> p.getId().equals(removePlayerFromTeamDTO.getPlayerId()))){
                    throw new PlayerIsInGameSquadException();
                }
            }
        }

        Player playerToRemove =  t.getPlayers().stream().filter(p -> p.getId().equals(removePlayerFromTeamDTO.getPlayerId())).findFirst().orElse(null);

        if(playerToRemove != null){
            t.getPlayers().remove(playerToRemove);
        }
        teamFacade.edit(t);

        assert playerToRemove != null;
        playerToRemove.setTeam(null);

        playerFacade.edit(playerToRemove);
    }

}
