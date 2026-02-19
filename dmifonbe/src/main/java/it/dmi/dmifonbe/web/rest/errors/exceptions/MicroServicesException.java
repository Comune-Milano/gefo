package it.dmi.dmifonbe.web.rest.errors.exceptions;

public class MicroServicesException extends Exception {

    private static final long serialVersionUID = -2907152626020162291L;

    private String userMessage;
    private int code;

    public MicroServicesException(String userMessage, int code) {
        super();
        this.userMessage = userMessage;
        this.code = code;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
