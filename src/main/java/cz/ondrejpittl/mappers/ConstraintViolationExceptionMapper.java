package cz.ondrejpittl.mappers;

import cz.ondrejpittl.business.validation.ValidationMessages;
import cz.ondrejpittl.rest.dtos.UserDTO;
import cz.ondrejpittl.rest.dtos.ValidationErrorDTO;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    //@Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ValidationErrorDTO> errors = exception.getConstraintViolations()
                .stream()
                .map(this::toValidationError)
                .collect(Collectors.toList());

        /*
        errors = errors.stream()
                .distinct()
                .collect(Collectors.toList());
        */

        List<ValidationErrorDTO> unique = new ArrayList<>();

        for (ValidationErrorDTO e : errors) {
            boolean contained = false;
            for (ValidationErrorDTO e2 : unique) {
                if(e2.equals(e)) {
                    contained = true;
                    break;
                }
            }

            if(!contained) {
                unique.add(e); }
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(unique)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private ValidationErrorDTO toValidationError(ConstraintViolation constraintViolation) {
        ValidationErrorDTO error = new ValidationErrorDTO();

        // form field
        String path = constraintViolation.getPropertyPath().toString();
        String field = path.substring(path.lastIndexOf('.') + 1);
        error.setField(field);

        // message
        String msgKey = constraintViolation.getMessage();
        error.setMessage(ValidationMessages.get(msgKey));

        // value
        Object val = constraintViolation.getInvalidValue();

        if(val instanceof UserDTO) {
            val = ((UserDTO) val).getConfirmPassword();
        }

        error.setValue(val);

        return error;
    }
}