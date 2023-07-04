package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class AppAccountException extends BaseApplicationException {

    public static String delete_confirmed_account = "exception.delete_confirmed_account";
    public static String account_constrain_violation = "exception.account_constrain_violation";

    private AppAccountException(Response.Status status, String key, Throwable cause) {
        super(status, key, cause);
    }

    private AppAccountException(Response.Status status, String key) {
        super(status, key);
    }

    public static AppAccountException createDeletingApprovedAccountException() {
        return new AppAccountException(Response.Status.EXPECTATION_FAILED, delete_confirmed_account);
    }

    public static AppAccountException createViolationOfAccountRestrictionException(Exception e) {
        return new AppAccountException(Response.Status.EXPECTATION_FAILED, account_constrain_violation, e);
    }
}
