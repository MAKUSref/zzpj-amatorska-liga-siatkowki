package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import java.util.UUID;

public class AdminDTO extends RoleDTO {

    public AdminDTO(UUID id, long version) {
        super(id, version, "ADMIN");
    }
}
