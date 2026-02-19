package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize
public class TipoFaseResponse {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<ProTipfas> proTipfas;

    private String warningMessage;

    public List<ProTipfas> getProTipfas() {
        return proTipfas;
    }

    public void setProTipfas(List<ProTipfas> proTipfas) {
        this.proTipfas = proTipfas;
    }

    public void addTipoFase(ProTipfas proTipfas) {
        this.proTipfas.add(proTipfas);
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
