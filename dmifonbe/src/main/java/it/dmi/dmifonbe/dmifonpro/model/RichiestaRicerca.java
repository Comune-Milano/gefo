package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class RichiestaRicerca extends AutocompleteRicerca implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private int idProgetto;
    private int tipoFinanziamento;

    @HtmlEscape
    private String tipo;

    @HtmlEscape
    private String stato;

    private Integer utente;
    private Integer proRicPad;

    public int getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(int idProgetto) {
        this.idProgetto = idProgetto;
    }

    public int getTipoFinanziamento() {
        return tipoFinanziamento;
    }

    public void setTipoFinanziamento(int tipoFinanziamento) {
        this.tipoFinanziamento = tipoFinanziamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Integer getUtente() {
        return utente;
    }

    public void setUtente(Integer utente) {
        this.utente = utente;
    }

    public Integer getProRicPad() {
        return proRicPad;
    }

    public void setProRicPad(Integer proRicPad) {
        this.proRicPad = proRicPad;
    }

    public boolean checkNull() {
        return (this.tipo == null && this.idProgetto == 0 && this.tipoFinanziamento == 0 && this.stato == null && this.utente == null);
    }
}
