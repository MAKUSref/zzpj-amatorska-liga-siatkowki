package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Player;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.PlayerDTO;


import java.util.ArrayList;
import java.util.List;

public class PlayerDTOMapper {
    public static PlayerDTO playerToDTO (Player player) {
        return new PlayerDTO(player.getId(), player.getVersion(), player.getFirstName(), player.getLastName(), player.getAge(), player.isPro());
    }

    public static List<PlayerDTO> mapList (List<Player> players) {
        List<PlayerDTO> playerDTOS = new ArrayList<>();

        for (Player player: players) {
            playerDTOS.add(playerToDTO(player));
        }
        return playerDTOS;
    }
}
