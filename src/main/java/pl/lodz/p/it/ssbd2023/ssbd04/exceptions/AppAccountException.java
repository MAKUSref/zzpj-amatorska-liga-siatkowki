package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class AppAccountException extends BaseApplicationException {

    public static String ERROR_DELETE_CONFIRMED_ACCOUNT = "ERROR.DELETE_CONFIRMED_ACCOUNT";
    public static String ERROR_ACCOUNT_CONSTRAINT_VIOLATION = "ERROR.ACCOUNT_CONSTRAINT_VIOLATION";

    private AppAccountException(Response.Status status, String key, Throwable cause) {
        super(status, key, cause);
    }

    private AppAccountException(Response.Status status, String key) {
        super(status, key);
    }

    public static AppAccountException createDeletingApprovedAccountException() {
        return new AppAccountException(Response.Status.EXPECTATION_FAILED, ERROR_DELETE_CONFIRMED_ACCOUNT);
    }

    public static AppAccountException createViolationOfAccountRestrictionException(Exception e) {
        return new AppAccountException(Response.Status.EXPECTATION_FAILED, ERROR_ACCOUNT_CONSTRAINT_VIOLATION, e);
    }
}
