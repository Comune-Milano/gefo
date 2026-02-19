package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class FiltriCapitoli implements Serializable {

    @HtmlEscape
    private String descrizione;

    @HtmlEscape
    private String titolo;

    @HtmlEscape
    private String macro;

    @HtmlEscape
    private String missione;

    @HtmlEscape
    private String programma;

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getMacro() {
        return macro;
    }

    public void setMacro(String macro) {
        this.macro = macro;
    }

    public String getMissione() {
        return missione;
    }

    public void setMissione(String missione) {
        this.missione = missione;
    }

    public String getProgramma() {
        return programma;
    }

    public void setProgramma(String programma) {
        this.programma = programma;
    }

    public MultiValueMap toMultiValueMap() {
        MultiValueMap result = new LinkedMultiValueMap();
        if (this.getTitolo() != null) result.add("titolo", this.getTitolo());
        if (this.getMacro() != null) result.add("macro", this.getMacro());
        if (this.getMissione() != null) result.add("missione", this.getMissione());
        if (this.getDescrizione() != null) result.add("descrizione", this.getDescrizione());
        if (this.getProgramma() != null) result.add("programma", this.getProgramma());

        return result;
    }
}
