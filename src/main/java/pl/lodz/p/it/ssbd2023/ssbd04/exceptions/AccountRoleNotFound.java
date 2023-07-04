package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;


@ApplicationException(rollback = true)
public class AccountRoleNotFound extends BaseApplicationException {
    public AccountRoleNotFound() {
        super(Response.Status.BAD_REQUEST, exception_account_role_not_found);
    }
}
