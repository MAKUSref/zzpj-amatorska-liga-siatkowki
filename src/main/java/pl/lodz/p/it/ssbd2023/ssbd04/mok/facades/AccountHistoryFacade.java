package pl.lodz.p.it.ssbd2023.ssbd04.mok.facades;

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
import pl.lodz.p.it.ssbd2023.ssbd04.entities.AccountHistory;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, AccountFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class AccountHistoryFacade extends AbstractFacade {

    @PersistenceContext(unitName = "siatka_admin")
    private EntityManager em;

    public AccountHistoryFacade() {
        super(AccountHistory.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountHistory findByLogin(String login) {
        TypedQuery<AccountHistory> tq = em.createNamedQuery("AccountHistory.findByLogin", AccountHistory.class);
        tq.setParameter("login", login);
        return tq.getSingleResult();
    }

    public List<AccountHistory> findByAccount(Account account) {
        TypedQuery<AccountHistory> tq = em.createNamedQuery("AccountHistory.findByAccount", AccountHistory.class);
        tq.setParameter("account", account);
        return tq.getResultList();
    }
}
