package pl.lodz.p.it.ssbd2023.ssbd04.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.RoleType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTokenDTO {
    private List<RoleType> roles;

    private UUID clientId;
}
