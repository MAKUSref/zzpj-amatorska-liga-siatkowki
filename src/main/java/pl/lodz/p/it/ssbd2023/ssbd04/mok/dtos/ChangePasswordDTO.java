package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import jakarta.validation.constraints.Size;
import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO extends AbstractDTO implements SignableEnt {


    @Size(min = 8, max = 64)
    String newPassword;

    public ChangePasswordDTO(UUID id, long version, String newPassword) {
        super(id, version);
        this.newPassword = newPassword;
    }
    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }

}
