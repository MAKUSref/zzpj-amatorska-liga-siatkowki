package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl;

import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

public class AccountAlreadyHasTeamException extends BaseApplicationException {
    public AccountAlreadyHasTeamException() {
        super(Response.Status.CONFLICT, exception_account_already_has_a_team);
    }
}
