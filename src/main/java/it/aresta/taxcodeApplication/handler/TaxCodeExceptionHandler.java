package it.aresta.taxcodeApplication.handler;

import it.aresta.taxcodeApplication.exception.CityServiceException;
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

/**
 * Application RestController Advices to catch service exception in controller execution
 * and set appropriate http status errors
 *
 * @author A.Aresta
 */
@RestControllerAdvice
public class TaxCodeExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(TaxCodeExceptionHandler.class.getName());

    /**
     * Handler for exception @{@link ValidationException}
     * @param
     * @return error message
     */
    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericErrorResponse handleValidationException(ValidationException e) {
        LOGGER.error("Validation Exception: " + e.getMessage());
        GenericErrorResponse error = new GenericErrorResponse(e.getMessage());
        return error;
    }

    /**
     * Handler for exception @{@link it.aresta.taxcodeApplication.exception.CityServiceException}
     * @param
     * @return error message
     */
    @ExceptionHandler({CityServiceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericErrorResponse handleCityServiceException(CityServiceException e) {
        LOGGER.error("CityServiceException: " + e.getMessage());
        GenericErrorResponse error = new GenericErrorResponse(e.getMessage());
        return error;
    }

    /**
     * Handler for exception @{@link MethodArgumentNotValidException}
     * @param
     * @return error message
     */
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

    /**
     * Handler for generic exception @{@link Exception}
     * @param
     * @return error message
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericErrorResponse handleGenericException(Exception e) {
        LOGGER.error(e.getMessage());
        GenericErrorResponse error = new GenericErrorResponse(e.getMessage());
        return error;
    }
}
