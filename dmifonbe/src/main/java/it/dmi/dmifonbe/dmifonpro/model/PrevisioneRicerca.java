package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.util.Date;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class PrevisioneRicerca implements Serializable {

    private int idProgetto;
    private int tipoFinanziamento;
    private int direzione;
    private Date dataPreDa;
    private Date dataPreA;

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

    public Date getDataPreDa() {
        return dataPreDa;
    }

    public void setDataPreDa(Date dataPreDa) {
        this.dataPreDa = dataPreDa;
    }

    public Date getDataPreA() {
        return dataPreA;
    }

    public void setDataPreA(Date dataPreA) {
        this.dataPreA = dataPreA;
    }

    public String getTipLiv() {
        return tipLiv;
    }

    public void setTipLiv(String tipLiv) {
        this.tipLiv = tipLiv;
    }
}
