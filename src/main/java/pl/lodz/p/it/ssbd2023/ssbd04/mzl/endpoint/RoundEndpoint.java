package pl.lodz.p.it.ssbd2023.ssbd04.mzl.endpoint;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.RoundDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager.RoundManager;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers.RoundDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

import java.util.UUID;

@Path("/round")
@RequestScoped
public class RoundEndpoint extends AbstractEndpoint {

    @Inject
    RoundManager roundManager;

    @Inject
    Etag etag;

    @GET
    @Path("/{id}")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRoundById(@PathParam("id") @NotNull UUID id) {
        RoundDTO roundDTO = RoundDTOMapper.roundToDTO(roundManager.getRoundById(id));
        return Response.ok().entity(roundDTO).header("ETAG", etag.calculateSignature(roundDTO)).build();
    }
}
