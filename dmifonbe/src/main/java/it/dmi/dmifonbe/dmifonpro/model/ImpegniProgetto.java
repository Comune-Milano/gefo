package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ImpegniProgetto implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @Valid
    private ProPro progetto;

    @HtmlEscape
    private String progettoPadreLabel;

    @Valid
    private List<DettaglioContabile> accertamenti;

    @Valid
    private List<DettaglioContabile> impegni;

    @Valid
    private List<DettaglioContabile> crono;

    public ProPro getProgetto() {
        return progetto;
    }

    public void setProgetto(ProPro progetto) {
        this.progetto = progetto;
    }

    public List<DettaglioContabile> getAccertamenti() {
        return accertamenti;
    }

    public void setAccertamenti(List<DettaglioContabile> accertamenti) {
        this.accertamenti = accertamenti;
    }

    public List<DettaglioContabile> getImpegni() {
        return impegni;
    }

    public void setImpegni(List<DettaglioContabile> impegni) {
        this.impegni = impegni;
    }

    public List<DettaglioContabile> getCrono() {
        return crono;
    }

    public void setCrono(List<DettaglioContabile> crono) {
        this.crono = crono;
    }

    public String getProgettoPadreLabel() {
        return progettoPadreLabel;
    }

    public void setProgettoPadreLabel(String progettoPadreLabel) {
        this.progettoPadreLabel = progettoPadreLabel;
    }
}
