package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class TematicaRicerca extends AutocompleteRicerca implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @HtmlEscape
    private String tipLiv;

    private Integer liv1;
    private Integer liv2;
    private boolean calcolaTotali;

    public Integer getLiv1() {
        return liv1;
    }

    public void setLiv1(Integer liv1) {
        this.liv1 = liv1;
    }

    public Integer getLiv2() {
        return liv2;
    }

    public void setLiv2(Integer liv2) {
        this.liv2 = liv2;
    }

    public boolean getCalcolaTotali() {
        return calcolaTotali;
    }

    public void setCalcolaTotali(boolean calcolaTotali) {
        this.calcolaTotali = calcolaTotali;
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

    public boolean checkNull() {
        return (super.getAutocomplete() == null && this.liv1 == null && this.liv2 == null && this.tipLiv == null);
    }
}
