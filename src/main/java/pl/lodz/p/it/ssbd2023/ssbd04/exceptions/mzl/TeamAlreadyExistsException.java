package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)
public class TeamAlreadyExistsException extends BaseApplicationException {

    public TeamAlreadyExistsException() {
        super(Response.Status.CONFLICT, exception_team_already_exists);
    }
}
