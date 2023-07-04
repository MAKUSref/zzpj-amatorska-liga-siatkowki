package pl.lodz.p.it.ssbd2023.ssbd04.mzl.endpoint;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Timetable;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.GetTimetableDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TimetableDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.TimetableFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager.TimetableManager;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers.TimetableDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

import java.util.List;
import java.util.UUID;

@Path("/timetable")
@RequestScoped
public class TimetableEndpoint {

    @Inject
    TimetableManager timetableManager;

    @Inject
    Etag etag;

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("generateTimetable")
    public Response generateTimetable(TimetableDTO timetable){
        timetableManager.generateTimetable(timetable);
        return Response.ok().build();
    }

    @GET
    @Path("/")
    @PermitAll
    public Response getTimetables() {
        List<GetTimetableDTO> timetables = TimetableDTOMapper.timetablesToDTO(timetableManager.findAll());
        return Response.ok().entity(timetables).build();
    };

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getTimetable(@PathParam("id") @NotNull UUID id) {
        GetTimetableDTO getTimetableDTO = TimetableDTOMapper.timetableToDTO(timetableManager.getTimetable(id));
        return Response.ok().entity(getTimetableDTO).header("ETAG", etag.calculateSignature(getTimetableDTO)).build();
    }
}
