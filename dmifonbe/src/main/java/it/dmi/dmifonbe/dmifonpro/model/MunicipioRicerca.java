package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize
public class MunicipioRicerca extends AutocompleteRicerca implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @HtmlEscape
    private String desmun;

    private boolean calcolaTotali;

    public String getDesmun() {
        return desmun;
    }

    public void setDesmun(String desmun) {
        this.desmun = desmun;
    }

    public boolean checkNull() {
        return (super.getAutocomplete() == null && this.desmun == null);
    }

    public boolean isCalcolaTotali() {
        return calcolaTotali;
    }

    public void setCalcolaTotali(boolean calcolaTotali) {
        this.calcolaTotali = calcolaTotali;
    }

    public boolean getCalcolaTotali() {
        return calcolaTotali;
    }
}
