package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import java.util.UUID;

public class CoachDTO extends RoleDTO{
    UUID teamId;

    public CoachDTO(UUID id, long version, UUID teamId) {
        super(id, version, "COACH");
        this.teamId = teamId;
    }
}
