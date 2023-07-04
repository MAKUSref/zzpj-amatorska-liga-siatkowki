package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class MethodNotImplemented  extends BaseApplicationException{
    public MethodNotImplemented(){
        super(Response.Status.NOT_IMPLEMENTED, exception_method_not_implemented);
    }
}
