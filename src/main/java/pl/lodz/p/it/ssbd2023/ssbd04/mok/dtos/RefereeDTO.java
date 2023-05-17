package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import java.util.UUID;

public class RefereeDTO extends RoleDTO {

    public RefereeDTO(UUID id, long version) {
        super(id, version, "REFEREE");
    }
}
