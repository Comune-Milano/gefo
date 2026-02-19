package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ExportImpegniRicerca implements Serializable {

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

    private boolean flgSolImpPro;

    public boolean isFlgSolImpPro() {
        return flgSolImpPro;
    }

    public void setFlgSolImpPro(boolean flgSolImpPro) {
        this.flgSolImpPro = flgSolImpPro;
    }

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
}
