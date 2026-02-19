package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class MacroProgettoRicerca extends AutocompleteRicerca implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;
    private int tipFinDa;
    private int tipFinA;
    private boolean calcolaTotali;

    public boolean getCalcolaTotali() {
        return calcolaTotali;
    }

    public void setCalcolaTotali(boolean calcolaTotali) {
        this.calcolaTotali = calcolaTotali;
    }

    public int getTipFinDa() {
        return tipFinDa;
    }

    public void setTipFinDa(int tipFinDa) {
        this.tipFinDa = tipFinDa;
    }

    public int getTipFinA() {
        return tipFinA;
    }

    public void setTipFinA(int tipFinA) {
        this.tipFinA = tipFinA;
    }

    public boolean isCalcolaTotali() {
        return calcolaTotali;
    }

    public boolean checkNull() {
        return (super.getAutocomplete() == null && this.tipFinA == 0 && this.tipFinDa == 0);
    }
}
