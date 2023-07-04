package pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.GameSquad;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class GameSquadFacade  extends AbstractFacade<GameSquad> {
    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;

    public GameSquadFacade() {
        super(GameSquad.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
