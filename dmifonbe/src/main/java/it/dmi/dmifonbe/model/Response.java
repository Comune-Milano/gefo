package it.dmi.dmifonbe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class Response implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;
    private String userMessage;
    private Integer id;

    public Response() {}

    public Response(String message) {
        this.setUserMessage(message);
    }

    public Response(String message, Integer idEntity) {
        this.setUserMessage(message);
        this.setId(idEntity);
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Response [userMessage=" + userMessage + "]";
    }
}
