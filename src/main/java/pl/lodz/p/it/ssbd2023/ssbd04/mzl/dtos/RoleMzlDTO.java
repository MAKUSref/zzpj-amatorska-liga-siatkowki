package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.RoleType;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RoleMzlDTO  {
    RoleType roleType;

    String login;

    public RoleMzlDTO(RoleType roleType, String login) {
        this.roleType = roleType;
        this.login = login;
    }
}
