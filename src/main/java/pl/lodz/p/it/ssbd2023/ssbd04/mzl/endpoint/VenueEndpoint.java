package pl.lodz.p.it.ssbd2023.ssbd04.mzl.endpoint;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.VenueDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager.VenueManager;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers.VenueDTOMapper;

import java.util.List;

@Path("/venue")
@RequestScoped
public class VenueEndpoint {
    @Inject
    VenueManager venueManager;
    @GET
    @PermitAll
    public List<VenueDTO> getVenues() {
        return venueManager.getAllVenues().stream().map(VenueDTOMapper::captainToVenueDTO).toList();
    }
}
