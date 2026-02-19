package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ProgettoResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<ProResponse> progetti;
    private String warningMessage;

    public ProgettoResponse() {
        this.progetti = new ArrayList<>();
    }

    public List<ProResponse> getProgetti() {
        return progetti;
    }

    public void setProgetti(List<ProResponse> progetti) {
        this.progetti = progetti;
    }

    public void addProgetto(ProResponse progetto) {
        this.progetti.add(progetto);
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
