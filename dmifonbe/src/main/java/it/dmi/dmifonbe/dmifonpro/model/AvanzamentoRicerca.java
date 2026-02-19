package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class AvanzamentoRicerca implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private int idProgetto;
    private int tipoFinanziamento;
    private int direzione;

    @HtmlEscape
    private String tipoAvanzamento;

    @HtmlEscape
    private String tipLiv;

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

    public int getDirezione() {
        return direzione;
    }

    public void setDirezione(int direzione) {
        this.direzione = direzione;
    }

    public String getTipoAvanzamento() {
        return tipoAvanzamento;
    }

    public void setTipoAvanzamento(String tipoAvanzamento) {
        this.tipoAvanzamento = tipoAvanzamento;
    }

    public String getTipLiv() {
        return tipLiv;
    }

    public void setTipLiv(String tipLiv) {
        this.tipLiv = tipLiv;
    }

    public boolean checkNull() {
        return (
            this.tipoAvanzamento == null &&
            this.idProgetto == 0 &&
            this.tipoFinanziamento == 0 &&
            this.direzione == 0 &&
            this.tipLiv == null
        );
    }
}
