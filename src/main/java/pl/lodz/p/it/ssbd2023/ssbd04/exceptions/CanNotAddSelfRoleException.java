package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class CanNotAddSelfRoleException extends BaseApplicationException {
    public CanNotAddSelfRoleException() {
        super(Response.Status.FORBIDDEN, exception_forbidden_access);
    }
}
