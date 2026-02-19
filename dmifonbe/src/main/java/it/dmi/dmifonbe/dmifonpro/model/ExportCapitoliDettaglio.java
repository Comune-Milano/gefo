package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProDetcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.model.Totali;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ExportCapitoliDettaglio implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProPro proPro;
    ProgettoAndProgettoPadre progettoAndProgettoPadre;
    ProgettoDetail progettoDetail;
    ResponsabileResponse responsabileResponse;
    Totali totFondi;
    BigDecimal totImpegniCapitoloPregressi;
    BigDecimal totImpegniCapitoloProgetto;
    ProDetcon proDetcon;
    DatiCapitolo datiCapitoloUscita;
    DatiCapitolo datiCapitoloEntrata;

    public BigDecimal getTotImpegniCapitoloPregressi() {
        return totImpegniCapitoloPregressi;
    }

    public void setTotImpegniCapitoloPregressi(BigDecimal totImpegniCapitoloPregressi) {
        this.totImpegniCapitoloPregressi = totImpegniCapitoloPregressi;
    }

    public BigDecimal getTotImpegniCapitoloProgetto() {
        return totImpegniCapitoloProgetto;
    }

    public void setTotImpegniCapitoloProgetto(BigDecimal totImpegniCapitoloProgetto) {
        this.totImpegniCapitoloProgetto = totImpegniCapitoloProgetto;
    }

    public DatiCapitolo getDatiCapitoloUscita() {
        return datiCapitoloUscita;
    }

    public void setDatiCapitoloUscita(DatiCapitolo datiCapitoloUscita) {
        this.datiCapitoloUscita = datiCapitoloUscita;
    }

    public DatiCapitolo getDatiCapitoloEntrata() {
        return datiCapitoloEntrata;
    }

    public void setDatiCapitoloEntrata(DatiCapitolo datiCapitoloEntrata) {
        this.datiCapitoloEntrata = datiCapitoloEntrata;
    }

    public ProDetcon getProDetcon() {
        return proDetcon;
    }

    public void setProDetcon(ProDetcon proDetcon) {
        this.proDetcon = proDetcon;
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

    public ProgettoDetail getProgettoDetail() {
        return progettoDetail;
    }

    public void setProgettoDetail(ProgettoDetail progettoDetail) {
        this.progettoDetail = progettoDetail;
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
}
