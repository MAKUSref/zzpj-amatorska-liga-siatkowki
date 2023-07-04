package pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager;

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
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl.*;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericManagerExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.*;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.tools.TeamTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({GenericManagerExceptionsInterceptor.class, TrackerInterceptor.class})
public class TeamManager extends AbstractManager implements SessionSynchronization {

    @Inject
    private TeamFacade teamFacade;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private LeagueFacade leagueFacade;

    @Inject
    private AccountFacadeMzl accountFacadeMzl;

    @Inject
    private RoleFacadeMzl roleFacadeMzl;

    @Inject
    private ManagerFacadeMzl managerFacadeMzl;

    @Inject
    private CoachFacadeMzl coachFacadeMzl;

    @Inject
    private CaptainFacadeMzl captainFacadeMzl;

    @Inject
    private TeamTool teamTool;

    public Team getTeamByUUID(UUID uuid) {
        return teamFacade.find(uuid);
    }

    @RolesAllowed("declineTeamSubmission")
    public void declineSubmission(String id){
        Team team = teamFacade.find(UUID.fromString(id));
        if (team == null) {
            throw new TeamNotFoundException();
        }
        if (team.isApproved()) {
            throw new TeamIsAlreadyInLeagueException();
        }
        if (team.getLeague() == null) {
            throw new TeamNotSubmittedException();
        }

        League league = leagueFacade.find(team.getLeague().getId());



        league.getTeams().remove(team);
        team.setApproved(false);
        team.setLeague(null);
        teamFacade.edit(team);
        leagueFacade.edit(league);
    }

    @RolesAllowed("acceptTeamSubmission")
    public void acceptSubmission(String id){
        Team team = teamFacade.find(UUID.fromString(id));
        if (team == null) {
            throw new TeamNotFoundException();
        }
        if (team.isApproved()) {
            throw new TeamIsAlreadyInLeagueException();
        }
        if (team.getLeague() == null) {
            throw new TeamNotSubmittedException();
        }
        team.setApproved(true);
        teamFacade.edit(team);
    }


    @PermitAll
    public Team getTeamById(String id){
        return teamFacade.find(UUID.fromString(id));
    }

    @RolesAllowed({"getTeamByName"})
    public Team getTeamByName(String teamName){
        return teamFacade.findByTeamName(teamName);
    }


    @RolesAllowed("changeTeamAssignment")
    public void removeManagerFromTeam(ChangeTeamAssignmentDto changeTeamAssignmentDto){
        Team team = teamFacade.find(changeTeamAssignmentDto.getId());

        if (changeTeamAssignmentDto.getVersion() < team.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }

        Optional<Manager> roleToDelete =  team.getManager().stream().filter(m -> m.getAccount().getId().equals(UUID.fromString(changeTeamAssignmentDto.getAccountId()))).findFirst();
        if(roleToDelete.isEmpty()){
            throw new RoleEmptyException();
        }
        team.getManager().remove(roleToDelete.get());
        Account managerAccount = roleToDelete.get().getAccount();
        managerAccount.getRoles().remove(roleToDelete.get());

//        accountFacadeMzl.edit(managerAccount);
//        teamFacade.edit(team);
        roleFacadeMzl.remove(roleToDelete.get());
    }

    @RolesAllowed("changeTeamAssignment")
    public void removeCaptainFromTeam(TeamIdDTO teamIdDTO){
        Team team = teamFacade.find(teamIdDTO.getId());

        if (teamIdDTO.getVersion() < team.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }

        Captain roleToDelete = team.getCaptain();
        if(roleToDelete == null){
            throw new RoleEmptyException();
        }
        team.setCaptain(null);
        Account captainAccount = roleToDelete.getAccount();
        while (captainAccount.getRoles().contains(roleToDelete)) {
            captainAccount.getRoles().remove(roleToDelete);
        }
//        accountFacadeMzl.edit(captainAccount);
        teamFacade.edit(team);
//        roleFacadeMzl.remove(roleToDelete);
    }

    @RolesAllowed("changeTeamAssignment")
    public void removeCoachFromTeam(TeamIdDTO teamIdDTO){
        Team team = teamFacade.find(teamIdDTO.getId());

        if (teamIdDTO.getVersion() < team.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }

        Coach roleToDelete = team.getCoach();
        if(roleToDelete == null){
            throw new RoleEmptyException();
        }
        team.setCoach(null);
        Account coachAccount = roleToDelete.getAccount();
        coachAccount.getRoles().remove(roleToDelete);
        accountFacadeMzl.edit(coachAccount);
        teamFacade.edit(team);
//        roleFacadeMzl.remove(roleToDelete);
    }

    @RolesAllowed("changeTeamAssignment")
    public Team addManagerToTeam(ChangeTeamAssignmentDto changeTeamAssignmentDto){

        Team team = teamFacade.find(changeTeamAssignmentDto.getId());

        if (changeTeamAssignmentDto.getVersion() < team.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }


        if(team.getManager().stream().anyMatch(m -> m.getAccount().getId().equals(UUID.fromString(changeTeamAssignmentDto.getAccountId())))){
            throw new AccountIsAlreadyManagerInThisTeam();
        }

        Account newManager = accountFacadeMzl.find(UUID.fromString(changeTeamAssignmentDto.getAccountId()));

        Manager role = new Manager();
        role.setAccount(newManager);
        role.setTeam(team);
        newManager.getRoles().add(role);
        team.getManager().add(role);

        roleFacadeMzl.create(role);
        return team;
    }

    @RolesAllowed("changeTeamAssignment")
    public Team addCaptainToTeam(ChangeTeamAssignmentDto changeTeamAssignmentDto){
        Team team = teamFacade.find(changeTeamAssignmentDto.getId());

        if (changeTeamAssignmentDto.getVersion() < team.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }

        Account newCaptain = accountFacadeMzl.find(UUID.fromString(changeTeamAssignmentDto.getAccountId()));

        if (team.getCaptain() != null){
            throw new RoleOccupiedException();
        }

        Captain role = new Captain();
        role.setAccount(newCaptain);
        role.setTeam(team);
        newCaptain.getRoles().add(role);
        team.setCaptain(role);

        roleFacadeMzl.create(role);
        return team;
    }
    @RolesAllowed("changeTeamAssignment")
    public Team addCoachToTeam(ChangeTeamAssignmentDto changeTeamAssignmentDto) {

        Team team = teamFacade.find(changeTeamAssignmentDto.getId());

        if (changeTeamAssignmentDto.getVersion() < team.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }

        Account newCoach = accountFacadeMzl.find(UUID.fromString(changeTeamAssignmentDto.getAccountId()));

        if (team.getCoach() != null){
            throw new RoleOccupiedException();
        }

        Coach role = new Coach();
        role.setAccount(newCoach);
        role.setTeam(team);
        newCoach.getRoles().add(role);
        team.setCoach(role);

        roleFacadeMzl.create(role);
        return team;
    }

    @RolesAllowed("submitTeamForLeagueAsAdmin")
    public void submitTeamForLeagueAsAdmin(SubmitTeamForLeagueDto submitTeamForLeagueDto) {
        Team team = teamFacade.find(UUID.fromString(submitTeamForLeagueDto.getTeamId()));
        League league = leagueFacade.find(UUID.fromString(submitTeamForLeagueDto.getLeagueId()));

        if (team == null)
            throw new TeamNotFoundException();
        if (league == null)
            throw new LeagueNotFoundException();
        if (team.getLeague() != null)
            throw new TeamIsAlreadyInLeagueException();

        team.setLeague(league);
        league.addTeam(team);

        teamFacade.edit(team);
        leagueFacade.edit(league);
    }
    private String getCurrentSeason() {
        // int currentYear = LocalDateTime.now().getYear();
        // return currentYear + "/" + (currentYear + 1);
        return "2022/2023";
    }

    @RolesAllowed("submitOwnTeamForLeague")
    public void submitOwnTeam(SubmitTeamForLeagueDto submitTeamForLeagueDto) {
        UUID accountUUID = UUID.fromString(securityContext.getCallerPrincipal().getName());

        Team team = teamFacade.find(UUID.fromString(submitTeamForLeagueDto.getTeamId()));
        League league = leagueFacade.find(UUID.fromString(submitTeamForLeagueDto.getLeagueId()));

        if (team == null)
            throw new TeamNotFoundException();
        if (league == null)
            throw new LeagueNotFoundException();
        if (team.getLeague() != null)
            throw new TeamIsAlreadyInLeagueException();
        if (!teamTool.isTeamRepresentative(accountUUID, UUID.fromString(submitTeamForLeagueDto.getTeamId())))
            throw GameException.notATeamRepresentativeException();

        team.setLeague(league);
        league.addTeam(team);

        teamFacade.edit(team);
        leagueFacade.edit(league);
    }

    @RolesAllowed("createTeam")
    public Team createTeam(String teamName, String city) {
        Account account = accountFacadeMzl.find(UUID.fromString(securityContext.getCallerPrincipal().getName()));
        Team team = new Team(teamName, city);

        Optional<Role> currentUserRole = account.getRoles().stream()
                .filter(role -> role.getRoleType() == RoleType.MANAGER ||
                        role.getRoleType() == RoleType.CAPTAIN ||
                        role.getRoleType() == RoleType.COACH ||
                        role.getRoleType() == RoleType.ADMIN)
                .findFirst();

        if (currentUserRole.isEmpty()) {
            throw GameException.invalidRoleException();
        }

        switch (currentUserRole.get().getRoleType()) {
            case MANAGER -> {
                if (!teamFacade.findTeamByManager(currentUserRole.get()).isEmpty()) {
                    throw new AccountAlreadyHasTeamException();
                }
                team.addManager((Manager) currentUserRole.get());
                ((Manager) currentUserRole.get()).setTeam(team);
            }
            case CAPTAIN -> {
                if (!teamFacade.findTeamByCaptain(currentUserRole.get()).isEmpty()) {
                    throw new AccountAlreadyHasTeamException();
                }
                team.setCaptain((Captain) currentUserRole.get());
                ((Captain) currentUserRole.get()).setTeam(team);
            }
            case COACH -> {
                if (!teamFacade.findTeamByCoach(currentUserRole.get()).isEmpty()) {
                    throw new AccountAlreadyHasTeamException();
                }
                team.setCoach((Coach) currentUserRole.get());
                ((Coach) currentUserRole.get()).setTeam(team);
            }
            case ADMIN -> {}
            default -> {
                throw GameException.invalidRoleException();
            }
        }

        teamFacade.create(team);
        return team;
    }

    public boolean doesAccountHaveATeam() {
        Account account = accountFacadeMzl.find(UUID.fromString(securityContext.getCallerPrincipal().getName()));

        Optional<Role> currentUserRole = account.getRoles().stream()
                .filter(role -> role.getRoleType() == RoleType.MANAGER ||
                        role.getRoleType() == RoleType.CAPTAIN ||
                        role.getRoleType() == RoleType.COACH ||
                        role.getRoleType() == RoleType.ADMIN)
                .findFirst();

        if (currentUserRole.isEmpty()) {
            throw GameException.invalidRoleException();
        }

        switch (currentUserRole.get().getRoleType()) {
            case MANAGER -> {
                if (!teamFacade.findTeamByManager(currentUserRole.get()).isEmpty()) {
                    return true;
                }
            }
            case CAPTAIN -> {
                if (!teamFacade.findTeamByCaptain(currentUserRole.get()).isEmpty()) {
                    return true;
                }
            }
            case COACH -> {
                if (!teamFacade.findTeamByCoach(currentUserRole.get()).isEmpty()) {
                    return true;
                }
            }
            default -> throw GameException.invalidRoleException();
        }

        return false;
    }

    @RolesAllowed("getMyTeams")
    public List<Team> getMyTeams() {
        return getAccountTeam(UUID.fromString(securityContext.getCallerPrincipal().getName()));
    }

    //@RolesAllowed("getAllTeams")
    @PermitAll
    public List<Team> getAllTeams() {
        return teamFacade.findAll();
    }

    @RolesAllowed("getAllNotSubmittedTeams")
    public List<Team> getAllNotSubmittedTeams() {
        return teamFacade.findNotSubmitted();
    }

    @RolesAllowed("addRepresentativeToTeam")
    public void addRepresentativeToTeam(AddRepresentativeToTeamDTO addRepresentativeToTeamDTO) {
        String accountUUID = securityContext.getCallerPrincipal().getName();
        Team team = teamFacade.find(addRepresentativeToTeamDTO.getId());
        Account addRoleAccount = accountFacadeMzl.findByLogin(addRepresentativeToTeamDTO.getRoleDTO().getLogin());

        Account account = accountFacadeMzl.find(UUID.fromString(accountUUID));

        if (addRepresentativeToTeamDTO.getVersion() < team.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }

        boolean isAdmin = account.getRoles().stream()
                .anyMatch(roles -> roles.getRoleType().equals(RoleType.ADMIN));

        if (teamTool.isTeamRepresentative(UUID.fromString(accountUUID), team.getId()) || isAdmin) {
            switch (addRepresentativeToTeamDTO.getRoleDTO().getRoleType().toString()) {
                case ("MANAGER") -> {
                   Manager result = addRoleAccount.getRoles().stream()
                            .filter(role -> role.getRoleType().equals(RoleType.MANAGER))
                            .filter(role -> role instanceof Manager)
                            .map(role -> (Manager) role)
                            .filter(manager -> manager.getTeam() == null)
                            .findAny()
                            .orElseThrow(IncorrectRepresentativeException::new);
                        team.getManager().add(result);
                        teamFacade.edit(team);
                        result.setTeam(team);
                        managerFacadeMzl.edit(result);
                    }
                case ("COACH") -> {
                    if (team.getCoach() == null) {
                        Coach result = addRoleAccount.getRoles().stream()
                                .filter(role -> role.getRoleType().equals(RoleType.COACH))
                                .filter(role -> role instanceof Coach)
                                .map(role -> (Coach) role)
                                .filter(manager -> manager.getTeam() == null)
                                .findAny()
                                .orElseThrow(IncorrectRepresentativeException::new);
                            team.setCoach(result);
                            teamFacade.edit(team);
                            result.setTeam(team);
                            coachFacadeMzl.edit(result);
                    } else
                        throw new TeamAlreadyHasCoachException();
                }
                case ("CAPTAIN") -> {
                    if (team.getCaptain() == null) {
                        Captain result = addRoleAccount.getRoles().stream()
                                .filter(role -> role.getRoleType().equals(RoleType.CAPTAIN))
                                .filter(role -> role instanceof Captain)
                                .map(role -> (Captain) role)
                                .filter(manager -> manager.getTeam() == null)
                                .findAny()
                                .orElseThrow(IncorrectRepresentativeException::new);
                        team.setCaptain(result);
                        teamFacade.edit(team);
                        result.setTeam(team);
                        captainFacadeMzl.edit(result);
                    } else
                        throw new TeamAlreadyHasCaptainException();
                }
                default -> throw BaseApplicationException.incorrectRole();
            }
        } else
            throw GameException.notATeamRepresentativeException();
    }



    private List<Team> getAccountTeam(UUID accountUUID) {
        Account account = accountFacadeMzl.find(accountUUID);
        List<Team> teams = new ArrayList<>();

        for (Role r : account.getRoles()) {
            if (r.getRoleType() == RoleType.ADMIN) {
                teams.addAll(teamFacade.findAll());
            } else {
                if (r.getRoleType() == RoleType.COACH) {
                    teams.addAll(teamFacade.findTeamByCoach(r));
                }
                if (r.getRoleType() == RoleType.CAPTAIN) {
                    teams.addAll(teamFacade.findTeamByCaptain(r));
                }
                if (r.getRoleType() == RoleType.MANAGER) {
                    teams.addAll(teamFacade.findTeamByManager(r));
                }
            }
        }
        if (teams.isEmpty()){
            throw new AccountDoesNotHaveAnyTeamsException();
        }
        return teams;
    }


}