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
import pl.lodz.p.it.zzpj2023.exceptions.interceptors.AccountFacadeExceptionsInterceptor;
import pl.lodz.p.it.zzpj2023.exceptions.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.zzpj2023.mok.entities.User;

import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, AccountFacadeExceptionsInterceptor.class})
public class AccountFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "siatka_mok")
    private EntityManager em;

    public AccountFacade() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @RolesAllowed("getUserByLogin")
    public User findByLogin(String login) {
        TypedQuery<User> tq = em.createNamedQuery("User.findByLogin", User.class);
        tq.setParameter("login", login);
        return tq.getSingleResult();


    }

    // @RolesAllowed("findByEmail")
    @PermitAll
    public User findByEmail(String email) {
        TypedQuery<User> tq = em.createNamedQuery("User.findByEmail", User.class);
        tq.setParameter("email", email);
        return tq.getSingleResult();
    }

    public User find(Object id) {
        return super.find(id);
    }

    @Override
    @RolesAllowed("getAllUsers")
    public List<User> findAll() {
        return super.findAll();
    }

    @RolesAllowed("getAllAdmins")
    public List<User> findAllAdmins() {
        TypedQuery<User> tq = em.createNamedQuery("Admin.findAllAccountsThatAreAdmin", User.class);
        return tq.getResultList();
    }

    @RolesAllowed("getAllManagers")
    public List<User> findAllManagers() {
        TypedQuery<User> tq = em.createNamedQuery("Manager.findAllAccountsThatAreManager", User.class);
        return tq.getResultList();
    }

    @RolesAllowed("getAllCoaches")
    public List<User> findAllCoaches() {
        TypedQuery<User> tq = em.createNamedQuery("Coach.findAllAccountsThatAreCoach", User.class);
        return tq.getResultList();
    }

    @RolesAllowed("getAllReferees")
    public List<User> findAllReferees() {
        TypedQuery<User> tq = em.createNamedQuery("Referee.findAllAccountsThatAreReferee", User.class);
        return tq.getResultList();
    }

    @RolesAllowed("getAllCaptains")
    public List<User> findAllCaptains() {
        TypedQuery<User> tq = em.createNamedQuery("Captain.findAllAccountsThatAreCaptain", User.class);
        return tq.getResultList();
    }
}
