package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.account;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

@ApplicationException(rollback = false)
public class BadLoginOrPasswordException extends BaseApplicationException {

    protected final static String bad_login_or_password="exception.bad_login_or_password";

    protected BadLoginOrPasswordException(String message) {super(Response.Status.BAD_REQUEST, message);}

    public static BadLoginOrPasswordException badLoginOrPassword() {return new BadLoginOrPasswordException(bad_login_or_password);}

}
