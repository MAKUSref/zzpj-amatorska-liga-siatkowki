package pl.lodz.p.it.ssbd2023.ssbd04.interceptors;

import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackerInterceptor {

    @Resource
    private SessionContext sctx;
    private static final Logger loger = Logger.getLogger(TrackerInterceptor.class.getName());

    @AroundInvoke
    public Object traceInvoke(InvocationContext ictx) throws Exception {
        StringBuilder message = new StringBuilder("Method called: ");
        Object result;
        try {
            try {
                message.append(ictx.getMethod().toString());
                message.append(" user: ").append(sctx.getCallerPrincipal().getName());
                message.append(" params values: ");
                if (null != ictx.getParameters()) {
                    for (Object param : ictx.getParameters()) {
                        message.append(String.valueOf(param)).append(" ");
                    }
                }
            } catch (Exception e) {
                loger.log(Level.SEVERE, "unexpected exceptions in interceptor code: ", e);
                throw e;
            }

            result = ictx.proceed();
            
        } catch (Exception e) {
            message.append(" ended with exception: ").append(e.toString());
            loger.log(Level.SEVERE, message.toString(), e);
            throw e;
        }

        message.append(" value returned: ").append(String.valueOf(result)).append(" ");

        loger.info(message.toString());

        return result;
    }
}
