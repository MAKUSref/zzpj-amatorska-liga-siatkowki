package pl.lodz.p.it.ssbd2023.ssbd04.interceptors;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolation;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

import java.util.Objects;


public class GenericFacadeExceptionsInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext ictx) {
        try {
            return ictx.proceed();
        }
        catch(OptimisticLockException ole) {
            throw AppOptimisticLockException.createOptimisticLockException();
        }
        catch (jakarta.validation.ConstraintViolationException e) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<?> constraintViolation :
                    e.getConstraintViolations()) {
                builder.append(constraintViolation.getMessage())
                        .append(':')
                        .append(Objects.toString(constraintViolation.getInvalidValue(), ""))
                        .append(',');
            }
            throw BaseApplicationException.createCustomConstraintException(builder.toString());
        } catch (EntityNotFoundException e) {
            throw BaseApplicationException.createNoEntityException();
        }
        catch(PersistenceException | java.sql.SQLException e) {
            throw BaseApplicationException.createGeneralPersistenceException(e);
        }
        catch (BaseApplicationException e) {
            throw e;
        }

        catch (Exception e) {
            throw BaseApplicationException.createGeneralErrorException(e);
        }
    }
}
