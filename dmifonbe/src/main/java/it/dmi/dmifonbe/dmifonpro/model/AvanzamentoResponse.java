package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class AvanzamentoResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<AvaResponse> avanzamenti = new ArrayList<>();
    private String warningMessage;

    public List<AvaResponse> getAvanzamenti() {
        return avanzamenti;
    }

    public void setAvanzamenti(List<AvaResponse> avanzamenti) {
        this.avanzamenti = avanzamenti;
    }

    public void addAvanzamento(AvaResponse avaResponse) {
        this.avanzamenti.add(avaResponse);
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
