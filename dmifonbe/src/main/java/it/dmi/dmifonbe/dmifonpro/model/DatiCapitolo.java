package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class DatiCapitolo implements Serializable {

    private String id;

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

    @HtmlEscape
    private String previsione;

    @HtmlEscape
    private String assestato;

    @HtmlEscape
    private String impegnato;

    @HtmlEscape
    private String previsione_1;

    @HtmlEscape
    private String assestato_1;

    @HtmlEscape
    private String impegnato_1;

    @HtmlEscape
    private String previsione_2;

    @HtmlEscape
    private String assestato_2;

    @HtmlEscape
    private String impegnato_2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPrevisione() {
        return previsione;
    }

    public void setPrevisione(String previsione) {
        this.previsione = previsione;
    }

    public String getAssestato() {
        return assestato;
    }

    public void setassestato(String assestato) {
        this.assestato = assestato;
    }

    public String getImpegnato() {
        return impegnato;
    }

    public void setImpegnato(String impegnato) {
        this.impegnato = impegnato;
    }

    public String getPrevisione_1() {
        return previsione_1;
    }

    public void setPrevisione_1(String previsione_1) {
        this.previsione_1 = previsione_1;
    }

    public String getassestato_1() {
        return assestato_1;
    }

    public void setassestato_1(String assestato_1) {
        this.assestato_1 = assestato_1;
    }

    public String getImpegnato_1() {
        return impegnato_1;
    }

    public void setImpegnato_1(String impegnato_1) {
        this.impegnato_1 = impegnato_1;
    }

    public String getPrevisione_2() {
        return previsione_2;
    }

    public void setPrevisione_2(String previsione_2) {
        this.previsione_2 = previsione_2;
    }

    public String getassestato_2() {
        return assestato_2;
    }

    public void setassestato_2(String assestato_2) {
        this.assestato_2 = assestato_2;
    }

    public String getImpegnato_2() {
        return impegnato_2;
    }

    public void setImpegnato_2(String impegnato_2) {
        this.impegnato_2 = impegnato_2;
    }
}
