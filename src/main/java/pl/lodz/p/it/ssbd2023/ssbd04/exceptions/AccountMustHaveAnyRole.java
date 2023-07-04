package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class AccountMustHaveAnyRole extends BaseApplicationException {
    public AccountMustHaveAnyRole() {
        super(Response.Status.BAD_REQUEST, exception_min_account_role);
    }
}
