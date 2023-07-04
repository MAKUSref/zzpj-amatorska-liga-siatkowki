package pl.lodz.p.it.ssbd2023.ssbd04.mzl.endpoint;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.MethodNotImplemented;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.endpoints.AccountEndpoint;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager.TeamManager;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers.*;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.List;

@Path("/team")
@RequestScoped
public class TeamEndpoint extends AbstractEndpoint {

    @Inject
    TeamManager teamManager;

    @Inject
    private Etag etag;

    private static final Logger LOG = Logger.getLogger(AccountEndpoint.class.getName());


    @GET
    @Path("/{teamId}")
    @PermitAll
    public Response getTeamByUUID(@PathParam("teamId") UUID teamId) {
        Team t = teamManager.getTeamByUUID(teamId);
        LeagueTeamDTO leagueTeamDTO = LeagueTeamDTOMapper.teamToDTO(t);
        return Response.ok().entity(leagueTeamDTO).header("ETAG", etag.calculateSignature(leagueTeamDTO)).build();
    }

    @GET
    @Path("/getTeamWithRepresentatives/{teamId}")
    @RolesAllowed("getAllTeams")
    public Response getAllTeamsWithRepresentatives(@PathParam("teamId") UUID teamId) {
        Team t = teamManager.getTeamByUUID(teamId);
        TeamWithRepresentativesDTO twrdto = TeamWithRepresentativesMapper.teamToTeamWithRepresentativesDTO(t);
        return Response.status(200).entity(twrdto).build();
    }

    @POST
    @Path("/declineSubmission/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("declineTeamSubmission")
    public Response declineTeamSubmission(@PathParam("id") UUID id){
        teamManager.declineSubmission(id.toString());
        return Response.ok().build();
    }

    @POST
    @Path("/acceptSubmission/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("acceptTeamSubmission")
    public Response acceptTeamSubmission(@PathParam("id") UUID id){
        teamManager.acceptSubmission(id.toString());
        return Response.ok().build();
    }
    @POST
    @Path("/removeManagerFromTeam/")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("changeTeamAssignment")
    public Response removeManagerFromTeam(ChangeTeamAssignmentDto changeTeamAssignmentDto) {
//        if(!etag.verifyTag(changeTeamAssignmentDto,tag)) {
//            return Response.status(Response.Status.PRECONDITION_FAILED).build();
//        }

        teamManager.removeManagerFromTeam(changeTeamAssignmentDto);
        return Response.status(200).build();
    }
    @POST
    @Path("/removeCaptainFromTeam/")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("changeTeamAssignment")
    public Response removeCaptainFromTeam( TeamIdDTO teamIdDTO) {

//        if(!etag.verifyTag(teamIdDTO,tag)) {
//            return Response.status(Response.Status.PRECONDITION_FAILED).build();
//        }

        teamManager.removeCaptainFromTeam(teamIdDTO);
        return Response.status(200).build();
    }
    @POST
    @Path("/removeCoachFromTeam/")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("changeTeamAssignment")
    public Response removeCoachFromTeam(TeamIdDTO teamIdDTO) {

//        if(!etag.verifyTag(teamIdDTO,tag)) {
//            return Response.status(Response.Status.PRECONDITION_FAILED).build();
//        }

        teamManager.removeCoachFromTeam(teamIdDTO);
        return Response.status(200).build();
    }
        @POST
    @Path("/addManagerToTeam/")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("changeTeamAssignment")
    public Response addManagerToTeam(@HeaderParam("If-match")@NotEmpty @NotNull String tag, ChangeTeamAssignmentDto changeTeamAssignmentDto){

        if(!etag.verifyTag(changeTeamAssignmentDto,tag)) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        teamManager.addManagerToTeam(changeTeamAssignmentDto);
            return Response.status(200).build();
    }

    @POST
    @Path("/addCaptainToTeam/")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("changeTeamAssignment")
    public Response addCaptainToTeam(@HeaderParam("If-match")@NotEmpty @NotNull String tag, ChangeTeamAssignmentDto changeTeamAssignmentDto){

        if(!etag.verifyTag(changeTeamAssignmentDto,tag)) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        teamManager.addCaptainToTeam(changeTeamAssignmentDto);
        return Response.status(200).build();
    }

    @POST
    @Path("/addCoachToTeam/")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("changeTeamAssignment")
    public Response addCoachToTeam(@HeaderParam("If-match")@NotEmpty @NotNull String tag, ChangeTeamAssignmentDto changeTeamAssignmentDto){

        if(!etag.verifyTag(changeTeamAssignmentDto,tag)) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        teamManager.addCoachToTeam(changeTeamAssignmentDto);
        return Response.status(200).build();

    }

    @POST
    @Path("/createTeam")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("createTeam")
    public Response createTeam(@NotNull @Valid CreateTeamDTO createTeamDto) {
        Team team = teamManager.createTeam(createTeamDto.getTeamName(), createTeamDto.getCity());
        return Response.status(201).entity(ShowTeamDTOMapper.teamToShowTeamDTO(team)).build();
    }

    @POST
    @Path("/submitTeamForLeague")
    @RolesAllowed("submitTeamForLeagueAsAdmin")
    public Response submitTeamForLeagueAsAdmin(@NotNull @Valid SubmitTeamForLeagueDto submitTeamForLeagueDto) {
        repeatTransaction(() ->  teamManager.submitTeamForLeagueAsAdmin(submitTeamForLeagueDto), teamManager);
        return Response.status(202).build();
    }

    @POST
    @Path("/submitOwnTeam")
    @RolesAllowed("submitOwnTeamForLeague")
    public Response submitTeamForLeague(@NotNull @Valid SubmitTeamForLeagueDto submitTeamForLeagueDto) {
        repeatTransaction(() -> teamManager.submitOwnTeam(submitTeamForLeagueDto), teamManager);
        return Response.status(202).build();
    }

    @GET
    @Path("/getTeamById/{id}")
    public Response getTeamById(@PathParam("id") UUID id) {
        Team team = teamManager.getTeamByUUID(id);
        ShowTeamDTO showTeamDTO = ShowTeamDTOMapper.teamToShowTeamDTO(team);
        if (team.getCaptain() != null)
            showTeamDTO.setCaptain(TeamRepresentativeMapper.captainToTeamCaptainDTO(team.getCaptain()));
        if (team.getCoach() != null)
            showTeamDTO.setCoach(TeamRepresentativeMapper.coachToTeamCoachDTO(team.getCoach()));
        if (!team.getManager().isEmpty())
            showTeamDTO.setManagers(TeamRepresentativeMapper.managerListToTeamManagerDTOList(team.getManager()));

        return Response.status(200).entity(showTeamDTO).build();
    }

    @GET
    @Path("/getMyTeams")
    @RolesAllowed("getMyTeams")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyTeams() {
        List<Team> teams = teamManager.getMyTeams();
        return Response.status(200).entity(ShowTeamDTOMapper.teamsToShowTeamDTOList(teams)).build();
    }

    @GET
    @Path("/doesHaveATeam")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doesHaveATeam() {
        return Response.status(200).entity(teamManager.doesAccountHaveATeam()).build();
    }

    @GET
    @Path("/getAllTeams")
    @PermitAll
    public Response getAllTeams() {
        List<Team> teams = teamManager.getAllTeams();
        return Response.status(200).entity(TeamDTOMapper.mapList(teams)).build();
    }



    @GET
    @Path("/getAllNotSubmittedTeams")
    @RolesAllowed("getAllNotSubmittedTeams")
    public Response getAllNotSubmittedTeams() {
        List<Team> teams = teamManager.getAllNotSubmittedTeams();
        return Response.status(200).entity(ShowTeamDTOMapper.teamsToShowTeamDTOList(teams)).build();
    }

    @POST
    @Path("/addRepresentative")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("addRepresentativeToTeam")
    public Response addRepresentativeToTeam(@NotNull @Valid AddRepresentativeToTeamDTO addRepresentativeToTeamDTO) {
//        if(!etag.verifyTag(addRepresentativeToTeamDTO,tag)) {
//            return Response.status(Response.Status.PRECONDITION_FAILED).build();
//        }
        repeatTransaction(() ->  teamManager.addRepresentativeToTeam(addRepresentativeToTeamDTO), teamManager);
        return Response.status(202).build();
    }

    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getTeamById(@PathParam("id") String id) {
        return Response.ok().entity(TeamDTOMapper.teamToDtoWithPlayers(teamManager.getTeamById(id))).build();
    }

    @GET
    @Path("/getByTeamName/{teamName}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getTeamByName")
    public Response getTeamByName(@PathParam("teamName") String teamName) {
        return Response.ok().entity(TeamDTOMapper.teamToDtoWithPlayers(teamManager.getTeamByName(teamName))).build();
    }
}
