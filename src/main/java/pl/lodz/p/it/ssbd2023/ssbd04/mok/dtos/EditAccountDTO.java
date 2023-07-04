package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.UUID;

@Getter
@Setter
public class EditAccountDTO extends AbstractDTO implements SignableEnt {
    @Size(min=4, max = 20)
    private String name;

    @Size(min=4, max = 30)
    private String lastname;

    public EditAccountDTO(UUID id, long version, String name, String lastname) {
        super(id, version);
        this.name = name;
        this.lastname = lastname;
    }

    public EditAccountDTO(){}

    @Override
    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }
}
