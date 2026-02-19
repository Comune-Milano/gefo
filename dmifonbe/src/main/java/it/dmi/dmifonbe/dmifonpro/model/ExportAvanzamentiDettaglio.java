package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;

import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ExportAvanzamentiDettaglio implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProPro proPro;


    ProgettoAndProgettoPadre progettoAndProgettoPadre;

    AvanzamentoDetail avanzamentoDetail;

    FaseDetail faseDetail;

    MilestoneDetail milestoneDetail;


    public MilestoneDetail getMilestoneDetail() {
        return milestoneDetail;
    }

    public void setMilestoneDetail(MilestoneDetail milestoneDetail) {
        this.milestoneDetail = milestoneDetail;
    }


    public FaseDetail getFaseDetail() {
        return faseDetail;
    }

    public void setFaseDetail(FaseDetail faseDetail) {
        this.faseDetail = faseDetail;
    }

    public ProPro getProPro() {
        return proPro;
    }

    public void setProPro(ProPro proPro) {
        this.proPro = proPro;
    }

    public ProgettoAndProgettoPadre getProgettoAndProgettoPadre() {
        return progettoAndProgettoPadre;
    }

    public void setProgettoAndProgettoPadre(ProgettoAndProgettoPadre progettoAndProgettoPadre) {
        this.progettoAndProgettoPadre = progettoAndProgettoPadre;
    }

    public AvanzamentoDetail getAvanzamentoDetail() {
        return avanzamentoDetail;
    }

    public void setAvanzamentoDetail(AvanzamentoDetail avanzamentoDetail) {
        this.avanzamentoDetail = avanzamentoDetail;
    }
}
