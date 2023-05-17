package pl.lodz.p.it.zzpj2023.mok.facades;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.zzpj2023.mok.entities.PasswordResetToken;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TokenFacade extends AbstractFacade<PasswordResetToken> {

    @PersistenceContext(unitName = "siatka_admin")
    private EntityManager em;

    public TokenFacade() {
        super(PasswordResetToken.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PasswordResetToken findByToken(String token) {
        TypedQuery<PasswordResetToken> query = em.createNamedQuery("PasswordResetToken.findByToken", PasswordResetToken.class);
        query.setParameter("token", token);
        return query.getSingleResult();
    }
}
