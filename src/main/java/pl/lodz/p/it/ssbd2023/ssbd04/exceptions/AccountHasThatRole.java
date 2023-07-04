package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class AccountHasThatRole extends BaseApplicationException {
    public  AccountHasThatRole() {
        super(Response.Status.CONFLICT, exception_account_has_that_role);
    }
}