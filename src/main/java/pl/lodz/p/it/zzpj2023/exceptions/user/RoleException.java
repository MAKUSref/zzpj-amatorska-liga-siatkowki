package pl.lodz.p.it.zzpj2023.exceptions.user;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.zzpj2023.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)

public class RoleException extends BaseApplicationException {

    private static String ROLE_ALREADY_ASSIGNED="ERROR.ROLE_ALREADY_ASSIGNED";
    protected RoleException(Response.Status status, String key) {
        super(status, key);
    }

    private RoleException(String message) {
        super(Response.Status.CONFLICT, message);
    }

    public static RoleException alreadyAssigned() {
        return new RoleException(ROLE_ALREADY_ASSIGNED);
    }
}
