package pl.lodz.p.it.zzpj2023.mok.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.zzpj2023.utils.etag.SignableEnt;

import java.util.UUID;

@Getter
@Setter
public class EditUserDTO extends AbstractDTO implements SignableEnt {
    private String login;
    private String name;
    private String lastname;
    private String email;

    public EditUserDTO(UUID id, long version, String name, String lastname, String email) {
        super(id, version);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public EditUserDTO(){}

    @Override
    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }
}
