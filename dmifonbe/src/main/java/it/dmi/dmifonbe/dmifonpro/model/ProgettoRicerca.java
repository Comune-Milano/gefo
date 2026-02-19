package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ProgettoRicerca extends AutocompleteRicerca implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;
    private int tipoFinanziamento;
    private int macroProgetto;
    private int municipio;
    private int nil;
    private int bando;
    private int tematica;
    private int direzione;
    private int statoProgetto;
    private int statoFinanziario;

    @HtmlEscape
    private String tipLiv;

    private boolean calcolaTotali;

    public boolean getCalcolaTotali() {
        return calcolaTotali;
    }

    public void setCalcolaTotali(boolean calcolaTotali) {
        this.calcolaTotali = calcolaTotali;
    }

    public int getTipoFinanziamento() {
        return tipoFinanziamento;
    }

    public void setTipoFinanziamento(int tipoFinanziamento) {
        this.tipoFinanziamento = tipoFinanziamento;
    }

    public int getMacroProgetto() {
        return macroProgetto;
    }

    public void setMacroProgetto(int macroProgetto) {
        this.macroProgetto = macroProgetto;
    }

    public int getBando() {
        return bando;
    }

    public void setBando(int bando) {
        this.bando = bando;
    }

    public int getTematica() {
        return tematica;
    }

    public void setTematica(int tematica) {
        this.tematica = tematica;
    }

    public int getDirezione() {
        return direzione;
    }

    public void setDirezione(int direzione) {
        this.direzione = direzione;
    }

    public int getStatoProgetto() {
        return statoProgetto;
    }

    public void setStatoProgetto(int statoProgetto) {
        this.statoProgetto = statoProgetto;
    }

    public int getStatoFinanziario() {
        return statoFinanziario;
    }

    public void setStatoFinanziario(int statoFinanziario) {
        this.statoFinanziario = statoFinanziario;
    }

    public String getTipLiv() {
        return tipLiv;
    }

    public void setTipLiv(String tipLiv) {
        this.tipLiv = tipLiv;
    }

    public boolean isCalcolaTotali() {
        return calcolaTotali;
    }

    public int getMunicipio() {
        return municipio;
    }

    public void setMunicipio(int municipio) {
        this.municipio = municipio;
    }

    public int getNil() {
        return nil;
    }

    public void setNil(int nil) {
        this.nil = nil;
    }

    public boolean checkNull() {
        return (
            super.getAutocomplete() == null &&
            this.tipoFinanziamento == 0 &&
            this.macroProgetto == 0 &&
            this.bando == 0 &&
            this.tematica == 0 &&
            this.direzione == 0 &&
            this.statoProgetto == 0 &&
            this.statoFinanziario == 0 &&
            this.municipio == 0 &&
            this.nil == 0 &&
            this.tipLiv == null
        );
    }
}
