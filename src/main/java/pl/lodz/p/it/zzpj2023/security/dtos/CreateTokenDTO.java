package pl.lodz.p.it.zzpj2023.security.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.roles.RoleTypeEnum;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateTokenDTO {
    private List<RoleTypeEnum> roles;

    private UUID clientId;
}
