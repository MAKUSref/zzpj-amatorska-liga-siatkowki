package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTeamAssignmentDto extends AbstractDTO implements SignableEnt {
    private String accountId;

    public ChangeTeamAssignmentDto(UUID id, int version, String accountId){
        super(id, version);
        this.accountId = accountId;
    }

    @Override
    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }
}
