package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class SelfBlockingException extends BaseApplicationException {
    public SelfBlockingException() {
        super(Response.Status.METHOD_NOT_ALLOWED, exception_self_blocking);
    }
}
