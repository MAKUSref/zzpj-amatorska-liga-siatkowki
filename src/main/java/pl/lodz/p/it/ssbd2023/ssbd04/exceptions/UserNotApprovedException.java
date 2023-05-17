package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class UserNotApprovedException extends AppException {
    public UserNotApprovedException() {
        super(Response.Status.BAD_REQUEST, ERROR_USER_NOT_APPROVED);
    }
}
