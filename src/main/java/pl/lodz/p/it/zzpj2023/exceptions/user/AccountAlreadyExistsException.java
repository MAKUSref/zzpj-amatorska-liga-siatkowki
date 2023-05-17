package pl.lodz.p.it.zzpj2023.exceptions.user;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.zzpj2023.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)
public class AccountAlreadyExistsException extends BaseApplicationException {

    private static final String LOGIN_TAKEN = "exception.account_login_exists";
    private static final String EMAIL_TAKEN = "exception.acount_email_exists";

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
