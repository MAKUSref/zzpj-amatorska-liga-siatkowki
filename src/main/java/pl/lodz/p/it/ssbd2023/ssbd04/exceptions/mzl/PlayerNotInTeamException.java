package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl;

import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

public class PlayerNotInTeamException extends BaseApplicationException {
    public PlayerNotInTeamException() {
        super(Response.Status.NOT_FOUND, exception_player_not_in_team);
    }
}