package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Venue;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.VenueDTO;

public class VenueDTOMapper {
    public static VenueDTO captainToVenueDTO(Venue venue) {
        return new VenueDTO(venue.getId(), venue.getVersion(), venue.getAddress(), venue.getCourtNumber());
    }
}
