package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

@ApplicationException(rollback = true)
public class RoleOccupiedException extends BaseApplicationException {

    public RoleOccupiedException() {
        super(Response.Status.BAD_REQUEST, exception_role_occupied);
    }
}