package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class Semaforo implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @Valid
    @HtmlEscape
    private String colore;

    @Valid
    @HtmlEscape
    private String descrizione;

    @Valid
    private BigDecimal percentuale;

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public BigDecimal getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(BigDecimal percentuale) {
        this.percentuale = percentuale;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
