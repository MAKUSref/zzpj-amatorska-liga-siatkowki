package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mappers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Objects;
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(getMessage(e)).build();
    }

    private String getMessage(ConstraintViolationException exception) {
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation :
                exception.getConstraintViolations()) {
            builder.append(constraintViolation.getMessage())
                    .append(':')
                    .append(Objects.toString(constraintViolation.getInvalidValue(), ""))
                    .append(',');
        }
        return builder.toString();
    }
}