package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class InvalidTokenException extends BaseApplicationException {
    public InvalidTokenException() {
        super(Response.Status.BAD_REQUEST, exception_invalid_token);
    }
}
