package pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Game;

import java.util.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class GameFacade extends AbstractFacade<Game> {
    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;

    public GameFacade() {
        super(Game.class);
    }

    public List<Game> findAll() {
        return super.findAll();
    }

    public List<Game> findByTeamId(UUID teamId) {
        TypedQuery<Game> tq = em.createNamedQuery("Game.getByTeam", Game.class);
        tq.setParameter("team", teamId);
        return tq.getResultList();
    }

    public List<Game> findByIdList(List<String> idList) {
        return findAll().stream().filter(game -> idList.contains(game.getId().toString())).toList();
    }

    public List<Game> findByTeamAndRound(UUID teamId, UUID roundId) {
        TypedQuery<Game> tq = em.createNamedQuery("Game.getByTeamAndRound", Game.class);
        tq.setParameter("team", teamId);
        tq.setParameter("round", roundId);
        return tq.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @PermitAll
    public Game find(Object id) {
        return super.find(id);
    }


    @RolesAllowed("getRefereeGames")
    public List<Game> findRefereeGames(String refereeId) {
        TypedQuery<Game> tq = em.createNamedQuery("Game.getRefereeGames", Game.class);
        tq.setParameter("refereeId", UUID.fromString(refereeId));
        return tq.getResultList();
    }
}
