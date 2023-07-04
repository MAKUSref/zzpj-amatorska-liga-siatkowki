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
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Game;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Player;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Scoreboard;

import java.util.List;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class ScoreboardFacade  extends AbstractFacade<Scoreboard> {
    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;

    public ScoreboardFacade() {
        super(Scoreboard.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Scoreboard findByRound(UUID roundId){
        TypedQuery<Scoreboard> tq = em.createNamedQuery("Scoreboard.getByRound", Scoreboard.class);
        tq.setParameter("round", roundId);
        return tq.getSingleResult();
    }

    public List<Scoreboard> findAll(){
        return super.findAll();
    }
}
