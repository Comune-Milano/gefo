package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonamm.entities.AmmUte;
import it.dmi.dmifonbe.dmifonpro.entities.ProDetcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProRes;
import java.io.Serializable;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class DettaglioResponsabile implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @Valid
    ProRes responsabile;

    @Valid
    AmmUte utente;

    public ProRes getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(ProRes responsabile) {
        this.responsabile = responsabile;
    }

    public AmmUte getUtente() {
        return utente;
    }

    public void setUtente(AmmUte utente) {
        this.utente = utente;
    }
}
