package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl;

import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

public class PlayerIsInGameSquadException extends BaseApplicationException {
    public PlayerIsInGameSquadException() {
        super(Response.Status.NOT_FOUND, exception_player_in_game_squad);
    }
}