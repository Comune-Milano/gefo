package it.dmi.dmifonbe.dmifonpro.model;

import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.validation.constraints.Min;

public class Risorse {

    private BigInteger idTipImp;

    @HtmlEscape
    private String desTipImp;

    private int ordTipImp;

    @Min(value = 0, message = "Il campo sumImpPro deve essere superiore o uguale a 0")
    private BigDecimal sumImpPro;

    private BigDecimal sumImpAcc;
    private BigDecimal sumImpRev;
    private BigDecimal sumImpImp;
    private BigDecimal sumImpMan;

    public BigDecimal getSumImpAcc() {
        return sumImpAcc;
    }

    public void setSumImpAcc(BigDecimal sumImpAcc) {
        this.sumImpAcc = sumImpAcc;
    }

    public BigDecimal getSumImpRev() {
        return sumImpRev;
    }

    public void setSumImpRev(BigDecimal sumImpRev) {
        this.sumImpRev = sumImpRev;
    }

    public BigDecimal getSumImpImp() {
        return sumImpImp;
    }

    public void setSumImpImp(BigDecimal sumImpImp) {
        this.sumImpImp = sumImpImp;
    }

    public BigDecimal getSumImpMan() {
        return sumImpMan;
    }

    public void setSumImpMan(BigDecimal sumImpMan) {
        this.sumImpMan = sumImpMan;
    }

    public BigInteger getIdTipImp() {
        return idTipImp;
    }

    public void setIdTipImp(BigInteger idTipImp) {
        this.idTipImp = idTipImp;
    }

    public String getDesTipImp() {
        return desTipImp;
    }

    public void setDesTipImp(String desTipImp) {
        this.desTipImp = desTipImp;
    }

    public int getOrdTipImp() {
        return ordTipImp;
    }

    public void setOrdTipImp(int ordTipImp) {
        this.ordTipImp = ordTipImp;
    }

    public BigDecimal getSumImpPro() {
        return sumImpPro;
    }

    public void setSumImpPro(BigDecimal sumImpPro) {
        this.sumImpPro = sumImpPro;
    }

    public static Risorse getRisorsaFromObject(Object[] object) {
        Risorse risorsa = new Risorse();
        risorsa.setIdTipImp(object[0] == null ? BigInteger.ZERO : (BigInteger) object[0]);
        risorsa.setDesTipImp((String) object[1]);
        risorsa.setOrdTipImp((Integer) object[2]);
        risorsa.setSumImpPro(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
        return risorsa;
    }
}
