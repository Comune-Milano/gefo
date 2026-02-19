package it.dmi.dmifonbe.dmifonamm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonamm.entities.AmmAll;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class AllegatoResponse implements Serializable {

    @Valid
    private List<AmmAll> allegati = new ArrayList<>();

    @HtmlEscape
    private String codice;

    @HtmlEscape
    private String descrizione;

    public List<AmmAll> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<AmmAll> allegati) {
        this.allegati = allegati;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
