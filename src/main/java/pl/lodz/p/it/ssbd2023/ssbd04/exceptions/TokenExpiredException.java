package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class TokenExpiredException extends AppException {
    public TokenExpiredException() {
        super(Response.Status.BAD_REQUEST, ERROR_TOKEN_EXPIRED);
    }
}
