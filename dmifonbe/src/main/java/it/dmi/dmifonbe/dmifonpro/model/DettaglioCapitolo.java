package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProDetcon;
import java.io.Serializable;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class DettaglioCapitolo implements Serializable {

    @Valid
    private ProDetcon proDetcon;

    @Valid
    private DatiCapitolo datiCapitolo;

    public ProDetcon getProDetcon() {
        return proDetcon;
    }

    public void setProDetcon(ProDetcon proDetcon) {
        this.proDetcon = proDetcon;
    }

    public DatiCapitolo getDatiCapitolo() {
        return datiCapitolo;
    }

    public void setDatiCapitolo(DatiCapitolo datiCapitolo) {
        this.datiCapitolo = datiCapitolo;
    }
}
