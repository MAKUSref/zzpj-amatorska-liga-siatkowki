package pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Coach;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Manager;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.AccountFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;

import java.util.List;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, AccountFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class AccountFacadeMzl extends AbstractFacade<Account> {
    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;

    public AccountFacadeMzl() {
        super(Account.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @RolesAllowed({"addRepresentativeToTeam", "addPlayerToTeam", "acceptScore", "declineScore", "removePlayerFromTeam"})
    public Account find(Object id) {
        return super.find(id);
    }

    public Team findManagerTeam(Object team) {
        TypedQuery<Manager> tq = em.createNamedQuery("Manager.getTeam", Manager.class);
        return tq.getSingleResult().getTeam();
    }
    @RolesAllowed("addRepresentativeToTeam")
    public Account findByLogin(String login) {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
        tq.setParameter("login", login);
        return tq.getSingleResult();
    }
    public Account findByRoleId(UUID id) {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByRoleId", Account.class);
        tq.setParameter("roleId", id);
        return tq.getSingleResult();
    }
}