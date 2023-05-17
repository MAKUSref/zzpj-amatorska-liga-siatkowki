package pl.lodz.p.it.zzpj2023.mok.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoleDTO extends AbstractDTO{
    private String role;

    public RoleDTO() {
    }

    public RoleDTO(UUID id, long version, String role) {
        super(id, version);
        this.role = role;
    }
}