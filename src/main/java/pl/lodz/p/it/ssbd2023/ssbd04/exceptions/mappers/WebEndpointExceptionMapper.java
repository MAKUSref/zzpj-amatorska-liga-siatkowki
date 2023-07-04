package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mappers;

import jakarta.ejb.AccessLocalException;
import jakarta.ejb.EJBAccessException;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class WebEndpointExceptionMapper implements ExceptionMapper<Throwable> {

    private final String UnknownExceptionMessage = "ERROR.UNKNOWN";
    Logger logger = Logger.getLogger(WebEndpointExceptionMapper.class.getName());

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(final Throwable throwable) {
        try {
            throw throwable;
        } catch (WebApplicationException wae) {
            logger.log(Level.SEVERE, wae.getMessage(), throwable);
            return wae.getResponse();
        } catch (EJBAccessException | AccessLocalException ae) {
            return BaseApplicationException.createNoAccessException().getResponse();
        } catch (NoResultException nre){
            return BaseApplicationException.createNoEntityException().getResponse();
        }
        catch (Throwable t) {
            logger.log(Level.SEVERE, UnknownExceptionMessage, throwable);
            return BaseApplicationException.createGeneralErrorException(t).getResponse();
        }
    }
}

