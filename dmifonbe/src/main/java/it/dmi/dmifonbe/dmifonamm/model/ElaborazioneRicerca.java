package it.dmi.dmifonbe.dmifonamm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.util.Date;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ElaborazioneRicerca extends AutocompleteRicerca implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @HtmlEscape
    private String username;

    private Date dtainida;
    private Date dtainia;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDtainida() {
        return dtainida;
    }

    public void setDtainida(Date dtainida) {
        this.dtainida = dtainida;
    }

    public Date getDtainia() {
        return dtainia;
    }

    public void setDtainia(Date dtainia) {
        this.dtainia = dtainia;
    }
}
