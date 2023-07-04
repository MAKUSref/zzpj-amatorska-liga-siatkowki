package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class AccountNotFoundException extends BaseApplicationException {
    public AccountNotFoundException() {
        super(Response.Status.NOT_FOUND, exception_account_not_found);
    }
}
