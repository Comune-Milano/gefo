package it.dmi.dmifonbe.dmifonpro.model;

import it.dmi.dmifonbe.dmifonpro.entities.ProNil;
import it.dmi.dmifonbe.model.Totali;

public class ProNilResponse {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProNil proNil;

    private Totali totali;

    public ProNil getProNil() {
        return proNil;
    }

    public void setProNil(ProNil proNil) {
        this.proNil = proNil;
    }

    public Totali getTotali() {
        return totali;
    }

    public void setTotali(Totali totali) {
        this.totali = totali;
    }
}
