package pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Venue;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericManagerExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.VenueDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.VenueFacade;

import java.util.List;
import java.util.UUID;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({GenericManagerExceptionsInterceptor.class, TrackerInterceptor.class})
public class VenueManager {
    @Inject
    VenueFacade venueFacade;

    @RolesAllowed("generateTimetable")
    public List<Venue> getAllVenueByIds(List<UUID> venuesIds) {
        return this.venueFacade.getAllVenueByIds(venuesIds);
    }

    @PermitAll
    public List<Venue> getAllVenues() {
        return this.venueFacade.getAll();
    }
}
