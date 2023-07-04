package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.account;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)
public class TooManyFailedLoginAttemptsException extends BaseApplicationException {
    protected final static String too_many_failed_login_attempts="exception.too_many_failed_login_attempts";

    protected TooManyFailedLoginAttemptsException(String message) {super(Response.Status.FORBIDDEN, message);}

    public static TooManyFailedLoginAttemptsException tooManyFailedLoginAttempts() {return new TooManyFailedLoginAttemptsException(too_many_failed_login_attempts);}
}
