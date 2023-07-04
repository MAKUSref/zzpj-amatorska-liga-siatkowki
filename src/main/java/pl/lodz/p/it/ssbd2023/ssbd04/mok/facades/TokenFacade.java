package pl.lodz.p.it.ssbd2023.ssbd04.mok.facades;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.InvalidTokenException;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Token;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.TokenType;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;

import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class TokenFacade extends AbstractFacade<Token> {

    @PersistenceContext(unitName = "siatka_admin")
    private EntityManager em;

    public TokenFacade() {
        super(Token.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void remove(Token entity) {
        super.remove(entity);
    }

    public Token findByToken(String token) {
        try {
            TypedQuery<Token> query = em.createNamedQuery("Token.findByToken", Token.class);
            query.setParameter("token", token);
            return query.getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            throw new InvalidTokenException();
        }
    }

    public List<Token> findByAccountIDAndType(Account account, TokenType tokenType) {
        try {
            TypedQuery<Token> query = em.createNamedQuery("Token.findByAccountIDAndType", Token.class);
            query.setParameter("account", account);
            query.setParameter("tokenType", tokenType);
            return query.getResultList();
        } catch (jakarta.persistence.NoResultException e) {
            throw new InvalidTokenException();
        }
    }
}
