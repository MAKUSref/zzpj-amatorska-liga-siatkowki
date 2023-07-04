package pl.lodz.p.it.ssbd2023.ssbd04.mzl.endpoint;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.MethodNotImplemented;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.LeagueDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.LeagueTeamDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.League;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.LeagueWithScoreboardsDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager.LeagueManager;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers.LeagueDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers.LeagueTeamDTOMapper;

import java.util.List;
import java.util.UUID;

@Path("/league")
@RequestScoped
public class LeagueEndpoint {
    @Inject
    LeagueManager leagueManager;

    @GET
    @Path("/currentLeague")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getCurrentLeague() {
        leagueManager.getCurrentLeague();
        throw new MethodNotImplemented();
    }

    @GET
    @Path("/teamsInLeague/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public List<LeagueTeamDTO> showTeamsInLeague(@PathParam("id") UUID id){
        return LeagueTeamDTOMapper.teamsToLeagueTeamDTOList(leagueManager.getAllTeamsInLeague(id));
    }

    @GET
    @Path("/teamsRequests/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("showPendingTeamRequests")
    public List<LeagueTeamDTO> showPendingRequests(@PathParam("id") UUID id){
        return LeagueTeamDTOMapper.teamsToLeagueTeamDTOList(leagueManager.getAllPendingRequests(id));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<LeagueDTO> getAllLeagues() {
        return LeagueDTOMapper.leaguesToLeagueDTOList(leagueManager.getAllLeagues());
    }

    @GET
    @Path("/scoreboards")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LeagueWithScoreboardsDTO> getLeaguesWithScoreboards() {
        return  LeagueDTOMapper.leaguesToLeagueWithScoreboardsDTO(leagueManager.getAllLeagues());
    }

    @GET
    @Path("/getLeaguesBySeason")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LeagueDTO> getAllLeaguesFromSeason(@QueryParam("season") String season) {
        return LeagueDTOMapper.leaguesToLeagueDTOList(leagueManager.getAllLeaguesFromSeason(season));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createLeague")
    @RolesAllowed("createLeague")
    public Response createLeague(@NotNull @Valid LeagueDTO leagueDTO) {
        League newLeague = LeagueDTOMapper.leagueDTOToLeague(leagueDTO);
        leagueManager.createLeague(newLeague);
        return Response.ok().build();
    }
}
