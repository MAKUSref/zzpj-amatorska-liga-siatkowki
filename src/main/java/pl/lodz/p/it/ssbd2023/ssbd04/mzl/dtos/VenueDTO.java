package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class VenueDTO extends AbstractDTO {

    private String address;
    private int courtNumber;

    public VenueDTO(UUID id, long version, String address, int courtNumber) {
        super(id, version);

        this.address = address;
        this.courtNumber = courtNumber;
    }
}
