package pl.lodz.p.it.ssbd2023.ssbd04.mzl.endpoint;


import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.MethodNotImplemented;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.CreatePlayerDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.LeagueTeamDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.RemovePlayerFromTeamDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager.PlayerManager;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers.LeagueTeamDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

import java.util.UUID;

@Path("/player")
@RequestScoped
public class PlayerEndpoint extends AbstractEndpoint {

    @Inject
    PlayerManager playerManager;

    @Inject
    Etag etag;
    @GET
    public Response ping(){
        return Response.ok("pong").build();
    }

    @POST
    @RolesAllowed("addPlayerToTeam")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlayerToTeam(@NotNull CreatePlayerDTO createPlayerDTO) {
//        if(!etag.verifyTag(createPlayerDTO.getTeamDTO(),tag)) {
//            return Response.status(Response.Status.PRECONDITION_FAILED).build();
//        }
        repeatTransaction(() -> playerManager.addPlayerToTeam(createPlayerDTO), playerManager);
        return Response.ok().build();
    }

    @PATCH
    @Path("/removePlayer")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("removePlayerFromTeam")
    public Response removePlayerFromTeam(RemovePlayerFromTeamDTO removePlayerFromTeamDTO) {
        playerManager.removePlayerFromTeam(removePlayerFromTeamDTO);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
