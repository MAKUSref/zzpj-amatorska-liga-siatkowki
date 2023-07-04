package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.account;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)
public class RoleException extends BaseApplicationException {

    protected final static String role_already_assigned="exception.role_already_assigned";
    protected RoleException(Response.Status status, String key) {
        super(status, key);
    }

    private RoleException(String message) {
        super(Response.Status.CONFLICT, message);
    }

    public static RoleException alreadyAssigned() {
        return new RoleException(role_already_assigned);
    }
}
