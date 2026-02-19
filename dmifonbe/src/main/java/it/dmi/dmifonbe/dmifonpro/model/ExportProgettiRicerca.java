package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ExportProgettiRicerca implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private int idPro;
    private int idTipFin;
    private int idDir;
    private int idMacPro        ;
    private int idBan        ;
    private int idTem        ;
    private int idStaPro     ;
    private int idStaFin     ;
    private String tipLiv;
    private boolean flgDatAnaPro             ;
    private boolean flgIgv                   ;
    private boolean flgInfAgg                ;
    private boolean flgRis                   ;
    private boolean flgRisDiCui              ;
    private boolean flgRes                   ;
    private boolean flgAva                   ;
    private boolean flgPre                   ;
    private String tipPer                   ;
    private int annPreDa                 ;
    private int annPreA                  ;

    public int getIdPro() {
        return idPro;
    }

    public void setIdPro(int idPro) {
        this.idPro = idPro;
    }

    public int getIdTipFin() {
        return idTipFin;
    }

    public void setIdTipFin(int idTipFin) {
        this.idTipFin = idTipFin;
    }

    public int getIdDir() {
        return idDir;
    }

    public void setIdDir(int idDir) {
        this.idDir = idDir;
    }

    public int getIdMacPro() {
        return idMacPro;
    }

    public void setIdMacPro(int idMacPro) {
        this.idMacPro = idMacPro;
    }

    public int getIdBan() {
        return idBan;
    }

    public void setIdBan(int idBan) {
        this.idBan = idBan;
    }

    public int getIdTem() {
        return idTem;
    }

    public void setIdTem(int idTem) {
        this.idTem = idTem;
    }

    public int getIdStaPro() {
        return idStaPro;
    }

    public void setIdStaPro(int idStaPro) {
        this.idStaPro = idStaPro;
    }

    public int getIdStaFin() {
        return idStaFin;
    }

    public void setIdStaFin(int idStaFin) {
        this.idStaFin = idStaFin;
    }

    public String getTipLiv() {
        return tipLiv;
    }

    public void setTipLiv(String tipLiv) {
        this.tipLiv = tipLiv;
    }

    public boolean isFlgDatAnaPro() {
        return flgDatAnaPro;
    }

    public void setFlgDatAnaPro(boolean flgDatAnaPro) {
        this.flgDatAnaPro = flgDatAnaPro;
    }

    public boolean isFlgIgv() {
        return flgIgv;
    }

    public void setFlgIgv(boolean flgIgv) {
        this.flgIgv = flgIgv;
    }

    public boolean isFlgInfAgg() {
        return flgInfAgg;
    }

    public void setFlgInfAgg(boolean flgInfAgg) {
        this.flgInfAgg = flgInfAgg;
    }

    public boolean isFlgRis() {
        return flgRis;
    }

    public void setFlgRis(boolean flgRis) {
        this.flgRis = flgRis;
    }

    public boolean isFlgRisDiCui() {
        return flgRisDiCui;
    }

    public void setFlgRisDiCui(boolean flgRisDiCui) {
        this.flgRisDiCui = flgRisDiCui;
    }

    public boolean isFlgRes() {
        return flgRes;
    }

    public void setFlgRes(boolean flgRes) {
        this.flgRes = flgRes;
    }

    public boolean isFlgAva() {
        return flgAva;
    }

    public void setFlgAva(boolean flgAva) {
        this.flgAva = flgAva;
    }

    public boolean isFlgPre() {
        return flgPre;
    }

    public void setFlgPre(boolean flgPre) {
        this.flgPre = flgPre;
    }

    public String getTipPer() {
        return tipPer;
    }

    public void setTipPer(String tipPer) {
        this.tipPer = tipPer;
    }

    public int getAnnPreDa() {
        return annPreDa;
    }

    public void setAnnPreDa(int annPreDa) {
        this.annPreDa = annPreDa;
    }

    public int getAnnPreA() {
        return annPreA;
    }

    public void setAnnPreA(int annPreA) {
        this.annPreA = annPreA;
    }
}
