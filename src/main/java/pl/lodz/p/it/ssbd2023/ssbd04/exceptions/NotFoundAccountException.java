package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class NotFoundAccountException extends AppException {
    public NotFoundAccountException() {
        super(Response.Status.NOT_FOUND, ERROR_NOT_FOUND_ACCOUNT);
    }
}
