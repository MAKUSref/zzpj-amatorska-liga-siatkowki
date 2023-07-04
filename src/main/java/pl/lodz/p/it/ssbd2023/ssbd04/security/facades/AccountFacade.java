package pl.lodz.p.it.ssbd2023.ssbd04.security.facades;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.AccountFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;

import java.util.logging.Logger;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, AccountFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class AccountFacade extends AbstractFacade<Account> {
    @PersistenceContext(unitName = "siatka_admin")
    private EntityManager em;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AccountFacade() {
        super(Account.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Account findByLogin(String login) {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
        tq.setParameter("login", login);
        return tq.getSingleResult();
    }

    public Account findByEmail(String email) {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByEmail", Account.class);
        tq.setParameter("email", email);
        return tq.getSingleResult();
    }

    public Account find(Object id) {
        return super.find(id);
    }

}
