package pl.lodz.p.it.ssbd2023.ssbd04.utils.tools;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.GameException;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Role;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.RoleType;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.AccountFacadeMzl;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.TeamFacade;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TeamTool {
    @Inject
    private AccountFacadeMzl accountFacadeMzl;

    @Inject
    private TeamFacade teamFacade;

    public boolean isTeamRepresentative(UUID accountUUID, UUID teamUUID) {
        boolean flag = false;
        Account currentAccount = accountFacadeMzl.find(accountUUID);
        Optional<Role> currentUserRole = currentAccount.getRoles().stream()
                .filter(role -> role.getRoleType() == RoleType.MANAGER ||
                        role.getRoleType() == RoleType.CAPTAIN ||
                        role.getRoleType() == RoleType.COACH)
                .findFirst();
        if (!currentUserRole.isPresent()) {
            return flag;
        }
        RoleType roleType = currentUserRole.get().getRoleType();
        UUID roleUUID = currentUserRole.get().getId();
        Team team = teamFacade.find(teamUUID);
        switch (roleType) {
            case MANAGER:
                if (!team.getManager().isEmpty()) {
                    flag = team.getManager().stream().anyMatch(manager -> manager.getId().equals(roleUUID));
                }
                break;
            case CAPTAIN:
                if (team.getCaptain() != null) {
                    flag = team.getCaptain().getId().equals(roleUUID);
                }
                break;
            case COACH:
                if (team.getCoach() != null) {
                    flag = team.getCoach().getId().equals(roleUUID);
                }
                break;
            default: {
                throw GameException.invalidRoleException();
            }
        }
        return flag;
    }
}
