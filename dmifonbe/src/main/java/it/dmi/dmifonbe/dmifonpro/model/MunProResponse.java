package it.dmi.dmifonbe.dmifonpro.model;

import it.dmi.dmifonbe.dmifonpro.entities.ProMacpro;
import it.dmi.dmifonbe.dmifonpro.entities.ProMun;
import it.dmi.dmifonbe.model.Totali;
import java.io.Serializable;

public class MunProResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProMun proMun;
    private Totali totali;

    public ProMun getProMun() {
        return proMun;
    }

    public void setProMun(ProMun proMun) {
        this.proMun = proMun;
    }

    public Totali getTotali() {
        return totali;
    }

    public void setTotali(Totali totali) {
        this.totali = totali;
    }
}
