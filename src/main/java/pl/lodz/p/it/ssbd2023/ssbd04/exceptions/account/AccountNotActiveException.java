package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.account;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)
public class AccountNotActiveException extends BaseApplicationException {

    protected final static String account_not_active="exception.account_not_active";

    protected AccountNotActiveException(String message) {super(Response.Status.FORBIDDEN, message);}

    public static AccountNotActiveException accountNotActive() {return new AccountNotActiveException(account_not_active);}

}
