package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.GameSquad;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.GameSquadDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.PlayerDTO;


public class GameSquadDTOMapper {
    public static GameSquadDTO gameSquadToDTO (GameSquad gameSquad) {
        return new GameSquadDTO(gameSquad.getId(), gameSquad.getVersion(),
                PlayerDTOMapper.mapList(gameSquad.getPlayers()),
                gameSquad.getTeam().getId().toString());
    }

}
