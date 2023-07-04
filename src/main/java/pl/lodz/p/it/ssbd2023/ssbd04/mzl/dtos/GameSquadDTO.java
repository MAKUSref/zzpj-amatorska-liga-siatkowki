package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.util.List;
import java.util.UUID;


@Data
@Getter
@Setter
@AllArgsConstructor
public class GameSquadDTO extends AbstractDTO {
    List<PlayerDTO> players;
    String teamId;

    public GameSquadDTO(UUID id, long version, List<PlayerDTO> players, String teamName) {
        super(id, version);
        this.players = players;
        this.teamId = teamName;
    }

    public GameSquadDTO() {
    }
}
