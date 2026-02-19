package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import javax.validation.Valid;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize
public class AutocompleteRicerca implements Serializable {

    @HtmlEscape
    private String autocomplete;

    public String getAutocomplete() {
        return autocomplete;
    }

    public void setAutocomplete(String autocomplete) {
        this.autocomplete = autocomplete;
    }
}
