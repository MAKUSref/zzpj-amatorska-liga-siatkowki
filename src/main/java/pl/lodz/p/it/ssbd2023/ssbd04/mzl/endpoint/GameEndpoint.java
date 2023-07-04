package pl.lodz.p.it.ssbd2023.ssbd04.mzl.endpoint;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Game;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.*;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.GameSquad;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager.GameManager;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.MethodNotImplemented;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers.GameDTOMapper;


import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Path("/game")
@RequestScoped
public class GameEndpoint extends AbstractEndpoint {
    @Inject
    private Etag etag;
    @Inject
    GameManager gameManager;

    Logger log = Logger.getLogger(GameEndpoint.class.getName());


    @POST
    @Path("/editGamesquad")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("editGamesquad")
    public Response editGameSquad(@Valid @NotNull EditGameSquadDTO editGameSquadDTO){
        repeatTransaction(() -> gameManager.editGamesquad(editGameSquadDTO), gameManager);

        return Response.ok(202).build();
    }

    @PATCH
    @Path("/requestPostpone/{uuid}")
    @RolesAllowed("requestPostpone")
    public Response requestPostpone(@PathParam("uuid") String uuid) {
        gameManager.postponeGameRequest(uuid);
        return Response.status(204).build();
    }

    @POST
    @Path("/changeGameDate/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("changeGameDate")
    public Response changeGameDate(@PathParam("uuid")UUID uuid, @NotNull @Valid NewGameDateDTO newGameDateDTO){
        gameManager.changeGameDate(uuid, newGameDateDTO);
        return Response.ok().build();
    }

    @PATCH
    @Path("/acceptScore/{teamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("acceptScore")
    public Response acceptScore(@PathParam("teamId")UUID teamId, AcceptScoreDTO acceptScoreDTO) {
//        if(!etag.verifyTag(acceptScoreDTO, tag)) {
//            return Response.status(Response.Status.PRECONDITION_FAILED).build();
//        }
        repeatTransactionAgregate(() -> gameManager.acceptScore(teamId, acceptScoreDTO), gameManager);
        return Response.ok().build();
    }

    @PATCH
    @Path("/declineScore/{teamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("declineScore")
    public Response declineScore(@PathParam("teamId")UUID teamId, DeclineScoreDTO declineScoreDTO) {
//        if(!etag.verifyTag(declineScoreDTO, tag)) {
//            return Response.status(Response.Status.PRECONDITION_FAILED).build();
//        }
        repeatTransaction(() -> gameManager.declineScore(teamId, declineScoreDTO), gameManager);
        return Response.ok().build();
    }

    @POST
    @Path("/addGameScore")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("addScoreToGame")
    public Response addScoreToGame(AddGameScoreDTO addGameScoreDTO) {
        gameManager.addScoreToGame(addGameScoreDTO);
        return Response.ok().build();
    }

    @GET
    @Path("/getGamesByTeam/{teamId}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getGamesByTeam(@PathParam("teamId") String teamId) {
        List<GameDTO> games = gameManager.getAllByTeam(teamId);
        return Response.ok().entity(games).build();
    }

    @GET
    @Path("/getGameById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getGameById(@PathParam("id") String id) {
        GameDTO game = gameManager.getById(id);
        log.info(game.toString());
        return Response.ok().entity(game).header("ETAG", etag.calculateSignature(game)).build();
    }

    @GET
    @Path("/getRefereeGames")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getRefereeGames")
    public Response getRefereeGames(@Context SecurityContext ctx) {
        List<Game> games = gameManager.getRefereeGames(ctx.getUserPrincipal().getName());
        return Response.ok().entity(GameDTOMapper.mapRefereeGames(games)).build();
    }

    @POST
    @Path("/getGameListByIdList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getGameListByIdList(@Valid List<String> idList) {
        List<GameDTO> game = gameManager.getAllByIdList(idList);
        log.info(game.toString());
        return Response.ok().entity(game).build();
    }

    @PUT
    @Path("/{id}/addReferee")
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response addRefereeToGame(@PathParam("id") @NotNull UUID gameId, AddRefereeToGameDTO addRefereeToGameDTO) {
        gameManager.addReferee(gameId,UUID.fromString(addRefereeToGameDTO.getRefereeId()));
        return Response.ok().build();
    }
}
