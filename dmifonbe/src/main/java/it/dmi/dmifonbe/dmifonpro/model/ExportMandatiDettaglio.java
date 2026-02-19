package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ExportMandatiDettaglio implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProPro proPro;

    private ProEntcon proEntcon;
    ProgettoAndProgettoPadre progettoAndProgettoPadre;

    MandatoResponse datiMandato;

    public ProPro getProPro() {
        return proPro;
    }

    public void setProPro(ProPro proPro) {
        this.proPro = proPro;
    }

    public ProEntcon getProEntcon() {
        return proEntcon;
    }

    public void setProEntcon(ProEntcon proEntcon) {
        this.proEntcon = proEntcon;
    }

    public ProgettoAndProgettoPadre getProgettoAndProgettoPadre() {
        return progettoAndProgettoPadre;
    }

    public void setProgettoAndProgettoPadre(ProgettoAndProgettoPadre progettoAndProgettoPadre) {
        this.progettoAndProgettoPadre = progettoAndProgettoPadre;
    }

    public MandatoResponse getDatiMandato() {
        return datiMandato;
    }

    public void setDatiMandato(MandatoResponse datiMandato) {
        this.datiMandato = datiMandato;
    }
}
