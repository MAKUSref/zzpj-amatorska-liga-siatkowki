package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveRoleDTO extends AbstractDTO implements SignableEnt {
    private String login;

    private RoleDTO role;

    public RemoveRoleDTO(UUID id, long version) {
        super(id, version);
    }

    public RemoveRoleDTO(UUID id, long version, String login, RoleDTO role) {
        super(id, version);
        this.login = login;
        this.role = role;
    }

    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }


}
