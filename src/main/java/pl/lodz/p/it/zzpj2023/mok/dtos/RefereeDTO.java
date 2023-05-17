package pl.lodz.p.it.zzpj2023.mok.dtos;

import java.util.UUID;

public class RefereeDTO extends RoleDTO {

    public RefereeDTO(UUID id, long version) {
        super(id, version, "REFEREE");
    }
}
