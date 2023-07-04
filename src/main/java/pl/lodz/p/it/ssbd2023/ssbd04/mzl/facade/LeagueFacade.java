package pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.League;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;

import java.util.List;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class LeagueFacade  extends AbstractFacade<League> {

    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;

    public LeagueFacade(){super(League.class);}

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public League find(Object id) {
        return super.find(id);
    }

    @Override
    public List<League> findAll() {
        return super.findAll();
    }

    public List<Team> findAllTeamsInLeague(League league) {
        TypedQuery<Team> tq = em.createNamedQuery("League.findAllTeamsInLeague", Team.class);
        tq.setParameter("league", league);
        return tq.getResultList();
    }

    public List<Team> findAllPendingRequests(League league) {
        TypedQuery<Team> tq = em.createNamedQuery("League.findAllPendingRequestsInLeague", Team.class);
        tq.setParameter("league", league);
        return tq.getResultList();
    }

    public List<League> findAllLeaguesInSeason(String season) {
        TypedQuery<League> tq = em.createNamedQuery("League.getLeaguesFromSeason", League.class);
        tq.setParameter("season", season);
        return tq.getResultList();
    }

    public League getLowestLeagueFromSeason(String season) {
        TypedQuery<League> tq = em.createNamedQuery("League.getLowestLeagueFromSeason", League.class);
        tq.setParameter("season", season);
        return tq.getSingleResult();
    }

    public List<Team> findAllTeamsInLeaguesByLeagueIds(List<UUID> leaguesIds) {
        TypedQuery<Team> tq = em.createNamedQuery("League.findAllTeamsByLeaguesByIds", Team.class);
        tq.setParameter("leaguesIds", leaguesIds);
        return tq.getResultList();
    }
}
