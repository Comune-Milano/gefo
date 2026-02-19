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
public class CapitoliProgettoResponse implements Serializable {

    @Valid
    private ProPro progetto;

    @HtmlEscape
    private String progettoPadreLabel;

    @Valid
    private List<DettaglioCapitolo> capitoliEntrata;

    @Valid
    private List<DettaglioCapitolo> capitoliUscita;

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

    public List<DettaglioCapitolo> getCapitoliEntrata() {
        return capitoliEntrata;
    }

    public void setCapitoliEntrata(List<DettaglioCapitolo> capitoliEntrata) {
        this.capitoliEntrata = capitoliEntrata;
    }

    public List<DettaglioCapitolo> getCapitoliUscita() {
        return capitoliUscita;
    }

    public void setCapitoliUscita(List<DettaglioCapitolo> capitoliUscita) {
        this.capitoliUscita = capitoliUscita;
    }
}
