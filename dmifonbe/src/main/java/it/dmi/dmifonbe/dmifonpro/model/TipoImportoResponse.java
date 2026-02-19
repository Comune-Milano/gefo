package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipimp;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize
public class TipoImportoResponse {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<ProTipimp> proTipimp;

    private String warningMessage;

    public List<ProTipimp> getProTipimp() {
        return proTipimp;
    }

    public void setProTipimp(List<ProTipimp> proTipimp) {
        this.proTipimp = proTipimp;
    }

    public void addTipoImporto(ProTipimp proTipimp) {
        this.proTipimp.add(proTipimp);
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
