package pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TeamFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;

import java.util.List;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TeamFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class TeamFacade extends AbstractFacade<Team> {
    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;

    public TeamFacade() {
        super(Team.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Team entity) {
        super.create(entity);
    }

    @Override
    @RolesAllowed({"addRepresentativeToTeam","removePlayerFromTeam", "addPlayerToTeam"})
    public void edit(Team entity) {
        super.edit(entity);
    }

    @Override
    public void remove(Team entity) {
        super.remove(entity);
    }

    @Override
    public List<Team> findAll() {
        return super.findAll();
    }

    @Override
    @PermitAll
//    @RolesAllowed({"addRepresentativeToTeam", "addPlayerToTeam"})
    public Team find(Object id) {
        return super.find(id);
    }

    public Team findByTeamName(String name) {
        TypedQuery<Team> tq = em.createNamedQuery("Team.getByTeamName", Team.class);
        tq.setParameter("name", name);
        return tq.getSingleResult();
    }

    public List<Team> findTeamByCoach(Object id) {
        TypedQuery<Team> tq = em.createNamedQuery("Team.findByCoach", Team.class);
        tq.setParameter("coach", id);
        return tq.getResultList();
    }

    public List<Team> findTeamByCaptain(Object id) {
        TypedQuery<Team> tq = em.createNamedQuery("Team.findByCaptain", Team.class);
        tq.setParameter("captain", id);
        return tq.getResultList();
    }

    public List<Team> findTeamByManager(Object id) {
        TypedQuery<Team> tq = em.createNamedQuery("Team.findByManager", Team.class);
        tq.setParameter("manager", id);
        return tq.getResultList();
    }

    public List<Team> findNotSubmitted() {
        TypedQuery<Team> tq = em.createNamedQuery("Team.findNotSubmitted", Team.class);
        return tq.getResultList();
    }
}
