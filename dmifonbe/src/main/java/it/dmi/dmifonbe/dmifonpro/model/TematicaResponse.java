package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class TematicaResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<ProTemResponse> tematiche;

    private String warningMessage;

    public TematicaResponse() {
        this.tematiche = new ArrayList<>();
    }

    public List<ProTemResponse> getTematiche() {
        return tematiche;
    }

    public void setTematiche(List<ProTemResponse> tematiche) {
        this.tematiche = tematiche;
    }

    public void addTematiche(ProTemResponse tematica) {
        this.tematiche.add(tematica);
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
