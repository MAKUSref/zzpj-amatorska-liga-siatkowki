package pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.MethodNotImplemented;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericManagerExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.League;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.LeagueFacade;

import java.util.List;
import java.util.UUID;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({GenericManagerExceptionsInterceptor.class, TrackerInterceptor.class})
public class LeagueManager {
    @Inject
    LeagueFacade leagueFacade;

    @PermitAll
    public void getCurrentLeague() {
        throw new MethodNotImplemented();
    }

    @RolesAllowed("showPendingTeamRequests")
//    @PermitAll
    public List<Team> getAllPendingRequests(UUID uuid) {
        return leagueFacade.findAllPendingRequests(leagueFacade.find(uuid));
    }

    @PermitAll
    public List<Team> getAllTeamsInLeague(UUID uuid) {
        return leagueFacade.findAllTeamsInLeague(leagueFacade.find(uuid));
    }

    @RolesAllowed("generateTimetable")
    public List<Team> getAllLeaguesByUUIDs(List<UUID> leaguesIds) {
        return leagueFacade.findAllTeamsInLeaguesByLeagueIds(leaguesIds);
    }

    public List<League> getAllLeagues() {
        return leagueFacade.findAll();
    }

    public List<League> getAllLeaguesFromSeason(String season) {
        return leagueFacade.findAllLeaguesInSeason(season);
    }

    public void createLeague(League league) {
        leagueFacade.create(league);
    }
}
