package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProDetcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipimp;

import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ExportImpegniDettaglio implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProPro proPro;

    private ProEntcon proEntcon;
    ProgettoAndProgettoPadre progettoAndProgettoPadre;
    ProDetcon proDetCon;
    ProTipimp proTipImp;
    ProEntcon ProEntconCol;

    public ProTipimp getProTipImp() {
        return proTipImp;
    }

    public void setProTipImp(ProTipimp proTipImp) {
        this.proTipImp = proTipImp;
    }

    public ProEntcon getProEntconCol() {
        return ProEntconCol;
    }

    public void setProEntconCol(ProEntcon proEntconCol) {
        ProEntconCol = proEntconCol;
    }

    public ProDetcon getProDetCon() {
        return proDetCon;
    }

    public void setProDetCon(ProDetcon proDetCon) {
        this.proDetCon = proDetCon;
    }


    public ProgettoAndProgettoPadre getProgettoAndProgettoPadre() {
        return progettoAndProgettoPadre;
    }

    public void setProgettoAndProgettoPadre(ProgettoAndProgettoPadre progettoAndProgettoPadre) {
        this.progettoAndProgettoPadre = progettoAndProgettoPadre;
    }

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
}
