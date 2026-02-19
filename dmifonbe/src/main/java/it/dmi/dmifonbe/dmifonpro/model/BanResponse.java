package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProBan;
import it.dmi.dmifonbe.model.Totali;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class BanResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProBan bando;
    private Totali totali;

    public Totali getTotali() {
        return totali;
    }

    public void setTotali(Totali totali) {
        this.totali = totali;
    }

    public ProBan getBando() {
        return bando;
    }

    public void setBando(ProBan bando) {
        this.bando = bando;
    }
}
