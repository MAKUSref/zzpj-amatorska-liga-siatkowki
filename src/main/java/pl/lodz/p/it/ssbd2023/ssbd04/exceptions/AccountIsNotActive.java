package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class AccountIsNotActive extends BaseApplicationException {
    public  AccountIsNotActive() {
        super(Response.Status.EXPECTATION_FAILED, exception_account_inactive);
    }
}
