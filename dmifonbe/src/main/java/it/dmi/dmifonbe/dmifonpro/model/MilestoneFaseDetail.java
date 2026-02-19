package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProFas;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;

import java.io.Serializable;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class MilestoneFaseDetail implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProFas Fase;

    private ProTipfas proTipfas;
    private List<MilestoneDetail> milestone;
    private ProPro progetto;
    private String progettoPadreLabel;

    public ProTipfas getProTipfas() {
        return proTipfas;
    }

    public void setProTipfas(ProTipfas proTipfas) {
        this.proTipfas = proTipfas;
    }

    public ProFas getFase() {
        return Fase;
    }

    public void setFase(ProFas fase) {
        Fase = fase;
    }

    public List<MilestoneDetail> getMilestone() {
        return milestone;
    }

    public void setMilestone(List<MilestoneDetail> milestone) {
        this.milestone = milestone;
    }

    public ProPro getProgetto() {
        return progetto;
    }

    public void setProgetto(ProPro progetto) {
        this.progetto = progetto;
    }

    public String getProgettoPadreLabel() {
        return progettoPadreLabel;
    }

    public void setProgettoPadreLabel(String progettoPadreLabel) {
        this.progettoPadreLabel = progettoPadreLabel;
    }
}
