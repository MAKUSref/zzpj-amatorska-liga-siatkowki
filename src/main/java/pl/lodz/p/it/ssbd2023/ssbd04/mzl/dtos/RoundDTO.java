package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RoundDTO extends AbstractDTO implements SignableEnt {

    @NotNull
    private int roundNumber;
    @NotNull
    private List<UUID> leagues;
    @NotNull
    private List<UUID> games;

    public RoundDTO(UUID id, long version, int roundNumber, List<UUID> leagues, List<UUID> games) {
        super(id, version);
        this.roundNumber = roundNumber;
        this.leagues = leagues;
        this.games = games;
    }

    @Override
    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }
}
