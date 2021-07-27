package it.aresta.taxcodeApplication.model;

/**
 * Generic Error Response returned by Controller in case of error
 *
 * @author A.Aresta
 */
public class GenericErrorResponse {

    private String message;

    public GenericErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
