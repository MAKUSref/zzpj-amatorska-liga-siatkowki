package pl.lodz.p.it.ssbd2023.ssbd04.interceptors;

import jakarta.ejb.AccessLocalException;
import jakarta.ejb.EJBAccessException;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

public class GenericManagerExceptionsInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext ictx) throws Exception {
        try {
            return ictx.proceed();

        } catch (BaseApplicationException wa) {
            throw wa;
        } catch (EJBAccessException | AccessLocalException ejbae) {
            throw  BaseApplicationException.createUnauthorizedException();
        } catch (Exception e) {
            throw BaseApplicationException.createGeneralErrorException(e);
        }

    }
}
