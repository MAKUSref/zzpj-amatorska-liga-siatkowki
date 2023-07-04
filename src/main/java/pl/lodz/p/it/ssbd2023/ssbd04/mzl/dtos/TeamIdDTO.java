package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.UUID;

@AllArgsConstructor
@Getter @Setter
public class TeamIdDTO  extends AbstractDTO implements SignableEnt {

    public TeamIdDTO(UUID id, int version){
        super(id, version);
    }

    @Override
    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }
}
