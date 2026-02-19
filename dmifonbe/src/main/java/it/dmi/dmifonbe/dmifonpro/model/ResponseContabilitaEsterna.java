package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ResponseContabilitaEsterna implements Serializable {

    private BodyResponse result;

    public BodyResponse getResult() {
        return result;
    }

    public void setResult(BodyResponse result) {
        this.result = result;
    }
}
