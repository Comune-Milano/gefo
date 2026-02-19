package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import it.dmi.dmifonbe.dmifonpro.entities.ProFas;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;
import java.io.Serializable;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class FaseDetail implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @Valid
    private ProFas fase;

    @Valid
    private ProTipfas tipfas;

    @Valid
    private Semaforo semaforoFase;

    @Valid
    private Semaforo semaforoMilestone;

    public ProFas getFase() {
        return fase;
    }

    public void setFase(ProFas fase) {
        this.fase = fase;
    }

    public ProTipfas getTipfas() {
        return tipfas;
    }

    public void setTipfas(ProTipfas tipfas) {
        this.tipfas = tipfas;
    }

    public Semaforo getSemaforoFase() {
        return semaforoFase;
    }

    public void setSemaforoFase(Semaforo semaforoFase) {
        this.semaforoFase = semaforoFase;
    }

    public Semaforo getSemaforoMilestone() {
        return semaforoMilestone;
    }

    public void setSemaforoMilestone(Semaforo semaforoMilestone) {
        this.semaforoMilestone = semaforoMilestone;
    }
}
