package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.account;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)
public class AccountBlockedException extends BaseApplicationException {

    protected final static String account_blocked="exception.account_blocked";

    protected AccountBlockedException(String message) {super(Response.Status.FORBIDDEN, message);}

    public static AccountBlockedException accountBlocked() {return new AccountBlockedException(account_blocked);}

}
