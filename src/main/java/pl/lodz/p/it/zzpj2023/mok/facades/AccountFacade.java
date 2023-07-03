package pl.lodz.p.it.zzpj2023.mok.facades;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.zzpj2023.interceptors.AccountFacadeExceptionsInterceptor;
import pl.lodz.p.it.zzpj2023.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.zzpj2023.mok.entities.Account;

import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, AccountFacadeExceptionsInterceptor.class})
public class AccountFacade extends AbstractFacade<Account> {
    @PersistenceContext(unitName = "siatka_mok")
    private EntityManager em;

    public AccountFacade() {
        super(Account.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @RolesAllowed("getUserByLogin")
    public Account findByLogin(String login) {
        TypedQuery<Account> tq = em.createNamedQuery("User.findByLogin", Account.class);
        tq.setParameter("login", login);
        return tq.getSingleResult();


    }

    // @RolesAllowed("findByEmail")
    @PermitAll
    public Account findByEmail(String email) {
        TypedQuery<Account> tq = em.createNamedQuery("User.findByEmail", Account.class);
        tq.setParameter("email", email);
        return tq.getSingleResult();
    }

    public Account find(Object id) {
        return super.find(id);
    }

    @Override
    @RolesAllowed("getAllUsers")
    public List<Account> findAll() {
        return super.findAll();
    }

    @RolesAllowed("getAllAdmins")
    public List<Account> findAllAdmins() {
        TypedQuery<Account> tq = em.createNamedQuery("Admin.findAllAccountsThatAreAdmin", Account.class);
        return tq.getResultList();
    }

    @RolesAllowed("getAllManagers")
    public List<Account> findAllManagers() {
        TypedQuery<Account> tq = em.createNamedQuery("Manager.findAllAccountsThatAreManager", Account.class);
        return tq.getResultList();
    }

    @RolesAllowed("getAllCoaches")
    public List<Account> findAllCoaches() {
        TypedQuery<Account> tq = em.createNamedQuery("Coach.findAllAccountsThatAreCoach", Account.class);
        return tq.getResultList();
    }

    @RolesAllowed("getAllReferees")
    public List<Account> findAllReferees() {
        TypedQuery<Account> tq = em.createNamedQuery("Referee.findAllAccountsThatAreReferee", Account.class);
        return tq.getResultList();
    }

    @RolesAllowed("getAllCaptains")
    public List<Account> findAllCaptains() {
        TypedQuery<Account> tq = em.createNamedQuery("Captain.findAllAccountsThatAreCaptain", Account.class);
        return tq.getResultList();
    }
}
