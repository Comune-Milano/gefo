package it.dmi.dmifonbe.dmifonpro.model;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipinfagg;
import java.util.List;

public class TipiInformazioneResponse {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<ProTipinfagg> proTipInFagg;

    private String warningMessage;

    public List<ProTipinfagg> getProTipInFagg() {
        return proTipInFagg;
    }

    public void setProTipInFagg(List<ProTipinfagg> proTipInFagg) {
        this.proTipInFagg = proTipInFagg;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void addTipiInformazioni(ProTipinfagg proTipinfagg) {
        this.proTipInFagg.add(proTipinfagg);
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
