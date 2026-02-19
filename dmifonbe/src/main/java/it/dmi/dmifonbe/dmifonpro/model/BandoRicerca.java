package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class BandoRicerca extends AutocompleteRicerca implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;
    private int tipFin;
    private int tem;
    private int staBan;
    private boolean calcolaTotali;

    public boolean getCalcolaTotali() {
        return calcolaTotali;
    }

    public void setCalcolaTotali(boolean calcolaTotali) {
        this.calcolaTotali = calcolaTotali;
    }

    public int getTipFin() {
        return tipFin;
    }

    public void setTipFin(int tipFin) {
        this.tipFin = tipFin;
    }

    public int getTem() {
        return tem;
    }

    public void setTem(int tem) {
        this.tem = tem;
    }

    public int getStaBan() {
        return staBan;
    }

    public void setStaBan(int staBan) {
        this.staBan = staBan;
    }

    public boolean isCalcolaTotali() {
        return calcolaTotali;
    }

    public boolean checkNull() {
        return (super.getAutocomplete() == null && this.tipFin == 0 && this.tem == 0 && this.staBan == 0);
    }
}
