package pl.lodz.p.it.zzpj2023.interceptors;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceException;
import pl.lodz.p.it.zzpj2023.exceptions.AppOptimisticLockException;
import pl.lodz.p.it.zzpj2023.exceptions.BaseApplicationException;


public class GenericFacadeExceptionsInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext ictx) {
        try {
            return ictx.proceed();
        }
        catch(OptimisticLockException ole) {
            throw AppOptimisticLockException.createOptimisticLockException();
        } catch (EntityNotFoundException e) {
            throw BaseApplicationException.createNoEntityException();
        } catch(PersistenceException | java.sql.SQLException e) {
            throw BaseApplicationException.createGeneralPersistenceException(e);
        } catch (BaseApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw BaseApplicationException.createGeneralErrorException(e);
        }
    }
}
