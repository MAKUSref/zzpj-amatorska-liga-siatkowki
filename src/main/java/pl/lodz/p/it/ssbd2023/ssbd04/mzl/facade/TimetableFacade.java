package pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade;

import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Timetable;

import java.util.Date;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class TimetableFacade  extends AbstractFacade<Timetable> {
    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;

    public TimetableFacade() {
        super(Timetable.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @PermitAll
    public Timetable find(Object id) {
        return super.find(id);
    }

    @Override
    @PermitAll
    public List<Timetable> findAll() {
        return super.findAll();
    }

    public List<Timetable> getTimetablesBetween(Date startDate, Date endDate) {
        var ts = em.createNamedQuery("Timetable.getTimetablesBetween", Timetable.class);
        ts.setParameter("startDate", startDate);
        ts.setParameter("endDate", endDate);
        return ts.getResultList();
    }
}
