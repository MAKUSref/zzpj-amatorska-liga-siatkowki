package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class ManagerDTO extends RoleDTO{
    UUID teamId;

    public ManagerDTO(UUID id, long version, UUID teamId) {
        super(id, version, "MANAGER");
        this.teamId = teamId;
    }

}
