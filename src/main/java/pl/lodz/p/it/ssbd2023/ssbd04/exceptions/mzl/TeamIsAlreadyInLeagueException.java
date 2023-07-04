package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)
public class TeamIsAlreadyInLeagueException extends BaseApplicationException {
    public TeamIsAlreadyInLeagueException() {
        super(Response.Status.NOT_ACCEPTABLE, exception_team_is_already_in_league);
    }
}
