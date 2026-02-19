package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProTem;
import it.dmi.dmifonbe.model.Totali;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ProTemResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProTem tematica;
    private Totali totali;

    public ProTem getTematica() {
        return tematica;
    }

    public void setTematica(ProTem tematica) {
        this.tematica = tematica;
    }

    public Totali getTotali() {
        return totali;
    }

    public void setTotali(Totali totali) {
        this.totali = totali;
    }
}
