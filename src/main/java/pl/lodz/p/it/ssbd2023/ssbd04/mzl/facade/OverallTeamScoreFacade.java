package pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.OverallTeamScore;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;

import java.util.List;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class OverallTeamScoreFacade extends AbstractFacade<OverallTeamScore> {
    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;

    public OverallTeamScoreFacade() {
        super(OverallTeamScore.class);
    }

    public List<OverallTeamScore> findByTeamAndScoreboard(UUID teamId, UUID scoreboardId){
        TypedQuery<OverallTeamScore> tq = em.createNamedQuery("OverallTeamScore.getByTeamAndScoreboard", OverallTeamScore.class);
        tq.setParameter("team", teamId);
        tq.setParameter("scoreboard", scoreboardId);
        return tq.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}

