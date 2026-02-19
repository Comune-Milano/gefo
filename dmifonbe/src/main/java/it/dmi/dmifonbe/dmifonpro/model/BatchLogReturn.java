package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonamm.entities.AmmEla;
import it.dmi.dmifonbe.dmifonamm.entities.AmmOut;
import it.dmi.dmifonbe.dmifonamm.entities.AmmRig;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class BatchLogReturn implements Serializable {

    private AmmEla ammEla;
    private AmmOut ammOut;
    private AmmRig ammRig;

    public AmmEla getAmmEla() {
        return ammEla;
    }

    public void setAmmEla(AmmEla ammEla) {
        this.ammEla = ammEla;
    }

    public AmmOut getAmmOut() {
        return ammOut;
    }

    public void setAmmOut(AmmOut ammOut) {
        this.ammOut = ammOut;
    }

    public AmmRig getAmmRig() {
        return ammRig;
    }

    public void setAmmRig(AmmRig ammRig) {
        this.ammRig = ammRig;
    }
}
