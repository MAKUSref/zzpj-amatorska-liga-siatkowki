package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)
public class LeagueNotFoundException extends BaseApplicationException {
    public LeagueNotFoundException() {
        super(Response.Status.NOT_FOUND, exception_league_not_found);
    }
}
