package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.RoleDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class AddRepresentativeToTeamDTO extends AbstractDTO implements SignableEnt {

    @NotNull
    private RoleMzlDTO roleDTO;

    public AddRepresentativeToTeamDTO(UUID id, long version, RoleMzlDTO roleDTO) {
        super(id, version);
        this.roleDTO = roleDTO;
    }

    @Override
    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }
}
