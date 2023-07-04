package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeclineScoreDTO extends AbstractDTO implements SignableEnt {
    @NotNull
    private UUID gameId;

    @NotNull
    private long gameVersion;

    public String getPayload() {
        return this.gameId.toString() + this.getGameVersion();
    }
}
