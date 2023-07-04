package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.account;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)
public class AccountNotApprovedException extends BaseApplicationException {

    protected final static String account_not_approved="exception.account_not_approved";

    protected AccountNotApprovedException(String message) {super(Response.Status.FORBIDDEN, message);}

    public static AccountNotApprovedException accountNotapproved() {return new AccountNotApprovedException(account_not_approved);}

}
