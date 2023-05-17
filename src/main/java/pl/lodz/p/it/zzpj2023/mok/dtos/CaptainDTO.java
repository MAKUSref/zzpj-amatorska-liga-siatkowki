package pl.lodz.p.it.zzpj2023.mok.dtos;

import java.util.UUID;

public class CaptainDTO extends RoleDTO{
    UUID teamId;

    public CaptainDTO(UUID id, long version, UUID teamId) {
        super(id, version, "CAPTAIN");
        this.teamId = teamId;
    }

}
