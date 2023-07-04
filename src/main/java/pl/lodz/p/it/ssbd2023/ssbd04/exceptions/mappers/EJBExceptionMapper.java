package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mappers;

import jakarta.ejb.EJBException;
import jakarta.ejb.EJBTransactionRequiredException;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class EJBExceptionMapper implements ExceptionMapper<Throwable> {

    private final String UnknownExceptionMessage = "ERROR.UNKNOWN";
    Logger logger = Logger.getLogger(EJBExceptionMapper.class.getName());

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(final Throwable throwable) {
        try {
            throw throwable;
        } catch (EJBTransactionRequiredException e) {
            return BaseApplicationException.transactionRequired().getResponse();
        } catch (EJBTransactionRolledbackException e) {
            return BaseApplicationException.createTransactionRollbackException().getResponse();
        } catch (EJBException e) {
            return BaseApplicationException.generalEJBException().getResponse();
        } catch (NotAuthorizedException e) {
            return BaseApplicationException.createUnauthorizedException().getResponse();
        } catch (ForbiddenException e) {
            return BaseApplicationException.createNoAccessException().getResponse();
        } catch (Throwable t) {
            logger.log(Level.SEVERE, UnknownExceptionMessage, throwable);
            return BaseApplicationException.createGeneralErrorException(t).getResponse();
        }
    }
}
