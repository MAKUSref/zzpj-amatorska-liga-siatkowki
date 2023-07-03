package pl.lodz.p.it.zzpj2023.interceptors;

import jakarta.ejb.AccessLocalException;
import jakarta.ejb.EJBAccessException;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import pl.lodz.p.it.zzpj2023.exceptions.BaseApplicationException;

public class GenericManagerExceptionsInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext ictx) throws Exception {
        try {
            return ictx.proceed();

        } catch (BaseApplicationException wa) { // Taki wyjątek może być rzucony przez "głębszy" interceptor. Nie powinniśmy go obsługiwać.
            throw wa;
        } catch (EJBAccessException | AccessLocalException ejbae) {
            throw  BaseApplicationException.createUnauthorizedException();
        } catch (Exception e) { //Zakładamy, że ten wyjątek został już zapisany w dzienniku przez TrackerInterceptor. Pozostaje go opakować.
            throw BaseApplicationException.createGeneralErrorException(e);
        }

    }
}
