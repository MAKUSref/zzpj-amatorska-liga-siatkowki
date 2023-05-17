package pl.lodz.p.it.zzpj2023.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@ApplicationException(rollback = true)
public class AppException extends WebApplicationException {

    protected final static String ERROR_UNKNOWN = "ERROR.UNKNOWN";
    protected final static String ERROR_GENERAL_PERSISTENCE = "ERROR.GENERAL_PERSISTENCE";
    protected final static String ERROR_ENTITY_NOT_FOUND = "ERROR.ENTITY_NOT_FOUND";
    protected final static String ERROR_OPTIMISTIC_LOCK = "ERROR.OPTIMISTIC_LOCK";
    protected final static String ERROR_ACCESS_DENIED = "ERROR.ACCESS_DENIED";
    protected final static String ERROR_TRANSACTION_ROLLEDBACK = "ERROR.TRANSACTION_ROLLEDBACK";
    protected final static String ERROR_TOKEN_EXPIRED = "ERROR.TOKEN_EXPIRED";
    protected final static String ERROR_NOT_FOUND_ACCOUNT = "ERROR.NOT_FOUND_ACCOUNT";
    protected final static String ERROR_USER_NOT_APPROVED = "ERROR.USER_NOT_APPROVED";

    @Getter
    private Throwable cause;

    protected AppException(Response.Status status, String key, Throwable cause) {
        super(Response.status(status).entity(key).build());
        this.cause = cause;
    }

    protected AppException(Response.Status status, String key) {
        super(Response.status(status).entity(key).build());
    }

    // Wyjątek ogólny opakowuje nie obsługiwane inaczej typy wyjątków
    public static AppException createGeneralExceptionError(Throwable cause) {
        return new AppException(Response.Status.INTERNAL_SERVER_ERROR, ERROR_UNKNOWN, cause);
    }

    public static AppException createGeneralExceptionError(String key, Throwable cause) {
        return new AppException(Response.Status.INTERNAL_SERVER_ERROR, key, cause);
    }

    public static AppException createGeneralPersistenceError(Exception cause) {
        return new AppException(Response.Status.INTERNAL_SERVER_ERROR, ERROR_GENERAL_PERSISTENCE, cause);
    }
    
    public static AppException createNoAccessError() {
        return new AppException(Response.Status.FORBIDDEN, ERROR_ACCESS_DENIED);
    }
    
    public static AppException createTransactionRollbackError() {
        return new AppException(Response.Status.INTERNAL_SERVER_ERROR, ERROR_TRANSACTION_ROLLEDBACK);
    }
    

    // Poszczególne wyjątki potomne tworzymy wtedy,gdy mamy w planach ich odrębną obsługę

    // public static AppNoEntityException createNoEntityError() {
    //     return new AppNoEntityException();
    // }
    //
    // public static AppOptimisticBlockadeException createOptimisticBlockadeException() {
    //     return new AppOptimisticBlockadeException();
    // }

}
