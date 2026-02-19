package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class BodyResponse implements Serializable {

    List<ContentResponse> impegni;
    List<ContentResponse> accertamenti;
    List<ContentResponseCrono> crono;
    List<ContentResponseCrono> cronoprogrammi;
    List<DatiCapitolo> capitoli;
    List<DatiMandato> mandati;
    List<Assessorato> assessorati;
    List<Settore> settori;


    public List<ContentResponse> getImpegni() {
        return impegni;
    }

    public void setImpegni(List<ContentResponse> impegno) {
        this.impegni = impegno;
    }

    public List<ContentResponse> getAccertamenti() {
        return accertamenti;
    }

    public void setAccertamenti(List<ContentResponse> accertamenti) {
        this.accertamenti = accertamenti;
    }

    public List<ContentResponseCrono> getCrono() {
        return crono;
    }

    public void setCrono(List<ContentResponseCrono> crono) {
        this.crono = crono;
    }

    public List<DatiCapitolo> getCapitoli() {
        return capitoli;
    }

    public void setCapitoli(List<DatiCapitolo> capitoli) {
        this.capitoli = capitoli;
    }

    public List<ContentResponseCrono> getCronoprogrammi() {
        return cronoprogrammi;
    }

    public void setCronoprogrammi(List<ContentResponseCrono> cronoprogrammi) {
        this.cronoprogrammi = cronoprogrammi;
    }

    public List<Assessorato> getAssessorati() {
        return assessorati;
    }

    public void setAssessorati(List<Assessorato> assessorati) {
        this.assessorati = assessorati;
    }

    public List<Settore> getSettori() {
        return settori;
    }

    public void setSettori(List<Settore> settori) {
        this.settori = settori;
    }

    public List<DatiMandato> getMandati() {
        return mandati;
    }

    public void setMandati(List<DatiMandato> mandati) {
        this.mandati = mandati;
    }
}
