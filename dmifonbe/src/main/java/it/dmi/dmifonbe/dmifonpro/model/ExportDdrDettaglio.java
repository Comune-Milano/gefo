package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.*;

import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ExportDdrDettaglio implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProPro proPro;

    private ProDdr proDdr;

    private ProDdra proDdra;
    ProgettoAndProgettoPadre progettoAndProgettoPadre;

    ProgettoDetail progettoDetail;

    public ProDdra getProDdra() {
        return proDdra;
    }

    public void setProDdra(ProDdra proDdra) {
        this.proDdra = proDdra;
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

    public ProDdr getProDdr() {
        return proDdr;
    }

    public void setProDdr(ProDdr proDdr) {
        this.proDdr = proDdr;
    }

    public ProgettoAndProgettoPadre getProgettoAndProgettoPadre() {
        return progettoAndProgettoPadre;
    }

    public void setProgettoAndProgettoPadre(ProgettoAndProgettoPadre progettoAndProgettoPadre) {
        this.progettoAndProgettoPadre = progettoAndProgettoPadre;
    }
}
