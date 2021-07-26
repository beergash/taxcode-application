package it.aresta.taxcodeApplication.handler;

import it.aresta.taxcodeApplication.exception.ValidationException;
import it.aresta.taxcodeApplication.model.GenericErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class.getName());

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericErrorResponse handleValidationException(ValidationException e) {
        LOGGER.error("Validation Exception: " + e.getMessage());
        GenericErrorResponse error = new GenericErrorResponse(e.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(f -> {
                    String fieldName = ((FieldError) f).getField();
                    String message = f.getDefaultMessage();
                    return String.format("Property %s is not valid because %s", fieldName, message);
                }).collect(Collectors.joining(";"));
        return new GenericErrorResponse(errorMessage);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericErrorResponse handleGenericException(Exception e) {
        LOGGER.error(e.getMessage());
        GenericErrorResponse error = new GenericErrorResponse(e.getMessage());
        return error;
    }
}
