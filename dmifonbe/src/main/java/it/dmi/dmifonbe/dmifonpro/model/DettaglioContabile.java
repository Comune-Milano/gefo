package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProDetcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import java.io.Serializable;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class DettaglioContabile implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @Valid
    ProDetcon proDetcon;

    @Valid
    ProEntcon proEntcon;

    public ProDetcon getProDetcon() {
        return proDetcon;
    }

    public void setProDetcon(ProDetcon proDetcon) {
        this.proDetcon = proDetcon;
    }

    public ProEntcon getProEntcon() {
        return proEntcon;
    }

    public void setProEntcon(ProEntcon proEntcon) {
        this.proEntcon = proEntcon;
    }
}
