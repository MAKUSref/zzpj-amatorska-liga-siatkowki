package pl.lodz.p.it.ssbd2023.ssbd04.mzl.endpoint;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Scoreboard;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.ScoreboardDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Game;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Scoreboard;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager.ScoreboardManager;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers.ScoreboardMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.roles.Roles;

import java.util.List;
import java.util.UUID;

@Path("/scoreboard")
@RequestScoped
public class ScoreboardEndpoint {

    @Inject
    ScoreboardManager scoreboardManager;


    @GET
    @Path("/")
    @PermitAll
    public Response getScoreboards(){
        List<Scoreboard> scoreboards = scoreboardManager.getAllScoreboards();
        List<ScoreboardDTO> scoreboardDTOS = scoreboards.stream().map(ScoreboardMapper::ScoreboardToDTO).toList();
        return Response.ok(scoreboardDTOS).build();
    }

    @GET
    @PermitAll
    @Path("/{id}")
    public Response getScoreboard(@PathParam("id") @Valid UUID id){
        Scoreboard scoreboard = scoreboardManager.getScoreboard(id);
        return Response.ok(ScoreboardMapper.ScoreboardToDTO(scoreboard)).build();
    }


//    @GET
//    @PermitAll
//    @Path("/team/{id}")
//    public Response getTeamGames(@PathParam("id") @Valid UUID id){
//        scoreboardManager.updateScoreboard(id, UUID.fromString("123e4567-e89b-12d3-a456-426655440005"));
//        return Response.ok().build();
//    }





}
