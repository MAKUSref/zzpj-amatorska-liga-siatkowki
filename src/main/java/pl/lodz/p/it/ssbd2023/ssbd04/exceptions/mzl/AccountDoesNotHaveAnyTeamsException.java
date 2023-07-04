package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl;

import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

public class AccountDoesNotHaveAnyTeamsException extends BaseApplicationException {
    public AccountDoesNotHaveAnyTeamsException() {
        super(Response.Status.CONFLICT, exception_account_does_not_have_any_teams);
    }
}
