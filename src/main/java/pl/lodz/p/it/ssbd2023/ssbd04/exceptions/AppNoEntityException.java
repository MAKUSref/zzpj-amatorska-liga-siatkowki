
package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class AppNoEntityException extends BaseApplicationException {

    AppNoEntityException() {
        super(Response.Status.NOT_FOUND, exception_entity_not_found);
    }

}
