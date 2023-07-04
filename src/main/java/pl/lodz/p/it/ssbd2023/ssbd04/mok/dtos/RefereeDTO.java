package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import java.util.UUID;
import java.util.List;

public class RefereeDTO extends RoleDTO {
    List<UUID> gamesId;

    public RefereeDTO(UUID id, long version, List<UUID> gamesId) {
        super(id, version, "REFEREE");
        this.gamesId = gamesId;
    }
}
