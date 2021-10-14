package it.beergash.taxcodeApplication.exception;

/**
 * Exception handling all validation errors (input bad formatted, null values not allowed, ecc..)
 *
 * @author A.Aresta
 */
public class ValidationException extends RuntimeException {

    public ValidationException() {
    }

    public ValidationException(String s) {
        super(s);
    }

    public ValidationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ValidationException(Throwable throwable) {
        super(throwable);
    }
}
