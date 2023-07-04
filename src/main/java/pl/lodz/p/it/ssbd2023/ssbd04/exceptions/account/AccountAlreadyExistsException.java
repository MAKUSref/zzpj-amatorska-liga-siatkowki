package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.account;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)
public class AccountAlreadyExistsException extends BaseApplicationException {

    protected static final String LOGIN_TAKEN = "exception.account_login_exists";
    protected static final String EMAIL_TAKEN = "exception.account_email_exists";

    protected AccountAlreadyExistsException(String message) {
        super(Response.Status.CONFLICT, message);
    }

    public static AccountAlreadyExistsException loginTaken(){
        return new AccountAlreadyExistsException(LOGIN_TAKEN);
    }

    public static AccountAlreadyExistsException emailTaken(){
        return new AccountAlreadyExistsException(EMAIL_TAKEN);
    }

}
