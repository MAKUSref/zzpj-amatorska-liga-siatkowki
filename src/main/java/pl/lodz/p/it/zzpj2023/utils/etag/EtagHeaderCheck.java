package pl.lodz.p.it.zzpj2023.utils.etag;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import pl.lodz.p.it.zzpj2023.exceptions.BaseApplicationException;

@Provider
@EtagFilter
public class EtagHeaderCheck implements ContainerRequestFilter {

    @Inject
    Etag etag;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String header = requestContext.getHeaderString(HttpHeaders.IF_MATCH);
        if (header.isEmpty()){
            requestContext.abortWith(Response.status(Response.Status.PRECONDITION_REQUIRED).entity("etag.missing").build());
        } else {
            try {
                etag.validateSignature(header);
            } catch (BaseApplicationException e){
                requestContext.abortWith(Response.status(Response.Status.PRECONDITION_FAILED).entity("etag.invalid").build());
            }
        }
    }
}
