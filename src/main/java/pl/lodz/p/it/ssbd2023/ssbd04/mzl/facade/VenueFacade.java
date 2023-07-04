package pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade;

import jakarta.annotation.security.PermitAll;
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
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Venue;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.VenueDTO;

import java.util.List;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class VenueFacade extends AbstractFacade<Venue> {
    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;

    public VenueFacade() {
        super(Venue.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Venue> getAllVenueByIds(List<UUID> venuesIds) {
        TypedQuery<Venue> tq = em.createNamedQuery("Venue.getAllVenueByIds", Venue.class);
        tq.setParameter("venuesIds", venuesIds);
        return tq.getResultList();
    }

    @PermitAll
    public List<Venue> getAll() {
        TypedQuery<Venue> tq = em.createNamedQuery("Venue.getAllVenues", Venue.class);
        return tq.getResultList();
    }
}



