package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class NotFoundAccountException extends BaseApplicationException {
    public NotFoundAccountException() {
        super(Response.Status.NOT_FOUND, exception_not_found);
    }
}
