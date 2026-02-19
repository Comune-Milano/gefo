package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.entities.ProRes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ResponsabileResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @Valid
    private List<DettaglioResponsabile> responsabili;

    @Valid
    private ProPro progetto;

    private String progettoPadreLabel;

    public List<DettaglioResponsabile> getResponsabili() {
        return responsabili;
    }

    public void setResponsabili(List<DettaglioResponsabile> responsabili) {
        this.responsabili = responsabili;
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
