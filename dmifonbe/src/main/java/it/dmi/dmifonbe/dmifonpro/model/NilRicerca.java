package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize
public class NilRicerca extends AutocompleteRicerca implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @HtmlEscape
    private String codnil;

    @HtmlEscape
    private String desnil;

    private boolean calcolaTotali;

    public String getCodnil() {
        return codnil;
    }

    public void setCodnil(String codnil) {
        this.codnil = codnil;
    }

    public String getDesnil() {
        return desnil;
    }

    public void setDesnil(String desnil) {
        this.desnil = desnil;
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

    public boolean checkNull() {
        return (super.getAutocomplete() == null || super.getAutocomplete().equals(""));
    }
}
