package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@ApplicationException(rollback = true)
public class BaseApplicationException extends WebApplicationException {

    protected final static String exception_unknown = "exception.unknown";
    protected final static String exception_general_persistence = "exception.general_persistence";
    protected final static String exception_entity_not_found = "exception.entity_not_found";
    protected final static String exception_optimistic_lock = "exception.optimistic_lock";
    protected final static String exception_access_denied = "exception.access_denied";
    protected final static String exception_transaction_rollback = "exception.transaction_rollback";
    protected final static String exception_unauthorized = "exception.unauthorized";
    protected final static String exception_etag = "exception.etag";

    @Getter
    private Throwable cause;

    protected BaseApplicationException(Response.Status status, String key, Throwable cause) {
        super(Response.status(status).entity(key).build());
        this.cause = cause;
    }

    protected BaseApplicationException(Response.Status status, String key) {
        super(Response.status(status).entity(key).build());
    }

    // Wyjątek ogólny opakowuje nie obsługiwane inaczej typy wyjątków
    public static BaseApplicationException createGeneralErrorException(Throwable cause) {
        return new BaseApplicationException(Response.Status.INTERNAL_SERVER_ERROR, exception_unknown, cause);
    }

    public static BaseApplicationException createGeneralErrorException(String key, Throwable cause) {
        return new BaseApplicationException(Response.Status.INTERNAL_SERVER_ERROR, key, cause);
    }

    public static BaseApplicationException createGeneralPersistenceException(Exception cause) {
        return new BaseApplicationException(Response.Status.INTERNAL_SERVER_ERROR, exception_general_persistence, cause);
    }
    
    public static BaseApplicationException createNoAccessException() {
        return new BaseApplicationException(Response.Status.FORBIDDEN, exception_access_denied);
    }
    
    public static BaseApplicationException createTransactionRollbackException() {
        return new BaseApplicationException(Response.Status.INTERNAL_SERVER_ERROR, exception_transaction_rollback);
    }

    public static BaseApplicationException createUnauthorizedException() {
        return new BaseApplicationException(Response.Status.UNAUTHORIZED, exception_unauthorized);
    }

    public static BaseApplicationException eTagException() {
        return new BaseApplicationException(Response.Status.INTERNAL_SERVER_ERROR, exception_etag);
    }
    

    // Poszczególne wyjątki potomne tworzymy wtedy,gdy mamy w planach ich odrębną obsługę

    public static AppNoEntityException createNoEntityException() {
        return new AppNoEntityException();
    }

    public static AppOptimisticLockException createOptimisticLockException() {
        return new AppOptimisticLockException();
    }

}
