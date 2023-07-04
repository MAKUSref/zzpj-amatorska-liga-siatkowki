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
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Set;

import java.util.List;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class SetFacade  extends AbstractFacade<Set> {
    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;

    public SetFacade() {
        super(Set.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Set> getByScoreId(UUID id) {
        TypedQuery<Set> tq = em.createNamedQuery("Set.getAllSets", Set.class);
        tq.setParameter("id", id);
        return tq.getResultList();
    }
}
