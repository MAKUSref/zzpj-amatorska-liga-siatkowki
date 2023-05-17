package pl.lodz.p.it.zzpj2023.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class AppOptimisticLockException extends BaseApplicationException {

    AppOptimisticLockException() {
        super(Response.Status.CONFLICT, exception_optimistic_lock);
    }

}
