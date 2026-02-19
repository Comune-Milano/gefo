package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class BodyResponseNoArray implements Serializable {

    ContentResponse impegni;
    ContentResponse accertamenti;
    ContentResponseCrono crono;
    ContentResponseCrono cronoprogrammi;
    DatiCapitolo capitoli;
    DatiMandato mandati;
    Assessorato assessorati;
    Settore settori;

    public ContentResponse getImpegni() {
        return impegni;
    }

    public void setImpegni(ContentResponse impegni) {
        this.impegni = impegni;
    }

    public ContentResponse getAccertamenti() {
        return accertamenti;
    }

    public void setAccertamenti(ContentResponse accertamenti) {
        this.accertamenti = accertamenti;
    }

    public ContentResponseCrono getCrono() {
        return crono;
    }

    public void setCrono(ContentResponseCrono crono) {
        this.crono = crono;
    }

    public ContentResponseCrono getCronoprogrammi() {
        return cronoprogrammi;
    }

    public void setCronoprogrammi(ContentResponseCrono cronoprogrammi) {
        this.cronoprogrammi = cronoprogrammi;
    }

    public DatiCapitolo getCapitoli() {
        return capitoli;
    }

    public void setCapitoli(DatiCapitolo capitoli) {
        this.capitoli = capitoli;
    }

    public DatiMandato getMandati() {
        return mandati;
    }

    public void setMandati(DatiMandato mandati) {
        this.mandati = mandati;
    }

    public Assessorato getAssessorati() {
        return assessorati;
    }

    public void setAssessorati(Assessorato assessorati) {
        this.assessorati = assessorati;
    }

    public Settore getSettori() {
        return settori;
    }

    public void setSettori(Settore settori) {
        this.settori = settori;
    }
}
