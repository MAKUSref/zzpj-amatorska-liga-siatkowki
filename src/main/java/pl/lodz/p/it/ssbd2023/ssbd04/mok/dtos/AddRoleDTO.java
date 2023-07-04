package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
public class AddRoleDTO extends AbstractDTO implements SignableEnt {
    private String login;

    private RoleDTO role;

    public AddRoleDTO(UUID id, long version, String login, RoleDTO role) {
        super(id, version);
        this.login = login;
        this.role = role;
    }

    public AddRoleDTO() {}

    @Override
    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }

}
