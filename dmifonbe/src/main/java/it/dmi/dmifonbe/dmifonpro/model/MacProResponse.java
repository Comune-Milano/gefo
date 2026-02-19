package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProMacpro;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfin;
import it.dmi.dmifonbe.model.Totali;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class MacProResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProMacpro macroProgetto;
    private Totali totali;

    public ProMacpro getMacroProgetto() {
        return macroProgetto;
    }

    public void setMacroProgetto(ProMacpro macroProgetto) {
        this.macroProgetto = macroProgetto;
    }

    public Totali getTotali() {
        return totali;
    }

    public void setTotali(Totali totali) {
        this.totali = totali;
    }
}
