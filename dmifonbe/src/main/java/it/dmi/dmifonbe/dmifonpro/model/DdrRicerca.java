package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class DdrRicerca extends AutocompleteRicerca implements Serializable {

    private int idProgetto;
    private int tipoFinanziamento;
    private int direzione;
    private Long ddra;

    @HtmlEscape
    private String flgNoDdra;

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

    public String getFlgNoDdra() {
        return flgNoDdra;
    }

    public void setFlgNoDdra(String flgNoDdra) {
        this.flgNoDdra = flgNoDdra;
    }

    public String getTipLiv() {
        return tipLiv;
    }

    public void setTipLiv(String tipLiv) {
        this.tipLiv = tipLiv;
    }

    public Long getDdra() {
        return ddra;
    }

    public void setDdra(Long ddra) {
        this.ddra = ddra;
    }
}
