package it.dmi.dmifonbe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.model.Risorse;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class Totali implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private BigInteger nroprofon;
    private BigDecimal imprisfon;
    private BigDecimal imprisfonpro;
    private BigDecimal impristotpro;
    private BigDecimal impacc;
    private BigDecimal impecoacc;
    private BigDecimal imprev;
    private BigDecimal impimp;
    private BigDecimal impecoimp;
    private BigDecimal impliq;
    private BigDecimal impman;
    private BigDecimal impdaacc;
    private BigDecimal impdaimp;
    private BigDecimal impddr;
    private BigDecimal impamm;
    private BigDecimal imptra;
    private BigDecimal impinc;
    private BigDecimal impprvese;
    private BigDecimal impprvese1;
    private BigDecimal impprvsuc;

    @Valid
    private List<Risorse> risorse;

    @Valid
    private List<Risorse> risorseDiCui;

    @HtmlEscape
    private String desTipEnt;

    @HtmlEscape
    private String desEnt;

    private BigDecimal impigv;
    private BigDecimal impigvdel;

    public BigDecimal getImpristotpro() {
        return impristotpro;
    }

    public void setImpristotpro(BigDecimal impristotpro) {
        this.impristotpro = impristotpro;
    }

    public BigDecimal getImpigv() {
        return impigv;
    }

    public void setImpigv(BigDecimal impigv) {
        this.impigv = impigv;
    }

    public BigDecimal getImpigvdel() {
        return impigvdel;
    }

    public void setImpigvdel(BigDecimal impigvdel) {
        this.impigvdel = impigvdel;
    }

    public BigInteger getNroprofon() {
        return nroprofon;
    }

    public void setNroprofon(BigInteger nroprofon) {
        this.nroprofon = nroprofon;
    }

    public BigDecimal getImprisfon() {
        return imprisfon;
    }

    public void setImprisfon(BigDecimal imprisfon) {
        this.imprisfon = imprisfon;
    }

    public BigDecimal getImpacc() {
        return impacc;
    }

    public void setImpacc(BigDecimal impacc) {
        this.impacc = impacc;
    }

    public BigDecimal getImpecoacc() {
        return impecoacc;
    }

    public void setImpecoacc(BigDecimal impecoacc) {
        this.impecoacc = impecoacc;
    }

    public BigDecimal getImprev() {
        return imprev;
    }

    public void setImprev(BigDecimal imprev) {
        this.imprev = imprev;
    }

    public BigDecimal getImpimp() {
        return impimp;
    }

    public void setImpimp(BigDecimal impimp) {
        this.impimp = impimp;
    }

    public BigDecimal getImpecoimp() {
        return impecoimp;
    }

    public void setImpecoimp(BigDecimal impecoimp) {
        this.impecoimp = impecoimp;
    }

    public BigDecimal getImpliq() {
        return impliq;
    }

    public void setImpliq(BigDecimal impliq) {
        this.impliq = impliq;
    }

    public BigDecimal getImpman() {
        return impman;
    }

    public void setImpman(BigDecimal impman) {
        this.impman = impman;
    }

    public BigDecimal getImpdaacc() {
        return impdaacc;
    }

    public void setImpdaacc(BigDecimal impdaacc) {
        this.impdaacc = impdaacc;
    }

    public BigDecimal getImpdaimp() {
        return impdaimp;
    }

    public void setImpdaimp(BigDecimal impdaimp) {
        this.impdaimp = impdaimp;
    }

    public BigDecimal getImpddr() {
        return impddr;
    }

    public void setImpddr(BigDecimal impddr) {
        this.impddr = impddr;
    }

    public BigDecimal getImpamm() {
        return impamm;
    }

    public void setImpamm(BigDecimal impamm) {
        this.impamm = impamm;
    }

    public BigDecimal getImptra() {
        return imptra;
    }

    public void setImptra(BigDecimal imptra) {
        this.imptra = imptra;
    }

    public BigDecimal getImpinc() {
        return impinc;
    }

    public void setImpinc(BigDecimal impinc) {
        this.impinc = impinc;
    }

    public BigDecimal getImpprvese() {
        return impprvese;
    }

    public void setImpprvese(BigDecimal impprvese) {
        this.impprvese = impprvese;
    }

    public BigDecimal getImpprvese1() {
        return impprvese1;
    }

    public void setImpprvese1(BigDecimal impprvese1) {
        this.impprvese1 = impprvese1;
    }

    public BigDecimal getImpprvsuc() {
        return impprvsuc;
    }

    public void setImpprvsuc(BigDecimal impprvsuc) {
        this.impprvsuc = impprvsuc;
    }

    public List<Risorse> getRisorse() {
        return risorse;
    }

    public void setRisorse(List<Risorse> risorse) {
        this.risorse = risorse;
    }

    public List<Risorse> getRisorseDiCui() {
        return risorseDiCui;
    }

    public void setRisorseDiCui(List<Risorse> risorseDiCui) {
        this.risorseDiCui = risorseDiCui;
    }

    public String getDesTipEnt() {
        return desTipEnt;
    }

    public void setDesTipEnt(String desTipEnt) {
        this.desTipEnt = desTipEnt;
    }

    public String getDesEnt() {
        return desEnt;
    }

    public void setDesEnt(String desEnt) {
        this.desEnt = desEnt;
    }

    public BigDecimal getImprisfonpro() {
        return imprisfonpro;
    }

    public void setImprisfonpro(BigDecimal imprisfonpro) {
        this.imprisfonpro = imprisfonpro;
    }
}
