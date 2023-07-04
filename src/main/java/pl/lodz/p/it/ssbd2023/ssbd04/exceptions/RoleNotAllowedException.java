package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class RoleNotAllowedException extends BaseApplicationException {
    public RoleNotAllowedException() {
        super(Response.Status.NOT_ACCEPTABLE, exception_role_not_allowed);
    }
}

