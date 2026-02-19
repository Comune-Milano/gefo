package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.model.Totali;

import java.io.Serializable;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ExportProgettiDettaglio implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProPro proPro;
    ProgettoDetail progettoDetail;

    ResponsabileResponse responsabileResponse;

    AvanzamentoDetail avanzamentoDetail;

    Semaforo semaforo;

    Totali totFondi;
    Totali totImpAcc;

    Totali totDdr;
    Totali totRisorse;

    public Totali getTotRisorse() {
        return totRisorse;
    }

    public void setTotRisorse(Totali totRisorse) {
        this.totRisorse = totRisorse;
    }


    public Totali getTotDdr() {
        return totDdr;
    }

    public void setTotDdr(Totali totDdr) {
        this.totDdr = totDdr;
    }

    List<PrevisionePeriodo> previsionePeriodoList;

    public List<PrevisionePeriodo> getPrevisionePeriodoList() {
        return previsionePeriodoList;
    }

    public void setPrevisionePeriodoList(List<PrevisionePeriodo> previsionePeriodoList) {
        this.previsionePeriodoList = previsionePeriodoList;
    }

    public Semaforo getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }

    public AvanzamentoDetail getAvanzamentoDetail() {
        return avanzamentoDetail;
    }

    public void setAvanzamentoDetail(AvanzamentoDetail avanzamentoDetail) {
        this.avanzamentoDetail = avanzamentoDetail;
    }

    public ResponsabileResponse getResponsabileResponse() {
        return responsabileResponse;
    }

    public void setResponsabileResponse(ResponsabileResponse responsabileResponse) {
        this.responsabileResponse = responsabileResponse;
    }

    public Totali getTotFondi() {
        return totFondi;
    }

    public void setTotFondi(Totali totFondi) {
        this.totFondi = totFondi;
    }

    public Totali getTotImpAcc() {
        return totImpAcc;
    }

    public void setTotImpAcc(Totali totImpAcc) {
        this.totImpAcc = totImpAcc;
    }

    public ProgettoDetail getProgettoDetail() {
        return progettoDetail;
    }

    public void setProgettoDetail(ProgettoDetail progettoDetail) {
        this.progettoDetail = progettoDetail;
    }

    public ProPro getProPro() {
        return proPro;
    }

    public void setProPro(ProPro proPro) {
        this.proPro = proPro;
    }
}
