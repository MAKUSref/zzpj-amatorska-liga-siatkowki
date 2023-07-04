package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddGamesquadDTO extends AbstractDTO {

    private List<String> playerIds;

    private UUID gameId;


    public AddGamesquadDTO(UUID id, long version, List<String> playerIds, UUID gameId) {
        super(id, version);
        this.playerIds = playerIds;
        this.gameId = gameId;
    }
}
