package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.UUID;

@Getter
@Setter
public class AcceptScoreDTO implements SignableEnt {
    @NotNull
    private UUID gameId;

    @NotNull
    private long gameVersion;

    public AcceptScoreDTO(UUID gameId, long gameVersion) {
        this.gameId = gameId;
        this.gameVersion = gameVersion;
    }

    public AcceptScoreDTO() {}
    @Override
    public String getPayload() {
        return this.gameId.toString() + this.getGameVersion();
    }
}
