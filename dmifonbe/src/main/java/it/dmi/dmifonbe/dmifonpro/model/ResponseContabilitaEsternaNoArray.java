package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ResponseContabilitaEsternaNoArray implements Serializable {

    private BodyResponseNoArray result;

    public BodyResponseNoArray getResult() {
        return result;
    }

    public void setResult(BodyResponseNoArray result) {
        this.result = result;
    }
}
