package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Table(name = "pro_ban", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProBan extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "codban", nullable = false, length = 25)
    private String codban;

    @Basic
    @HtmlEscape
    @Column(name = "desban", nullable = false, length = 250)
    private String desban;

    @Basic
    @Column(name = "id_staban", nullable = true)
    private Long idStaban;

    @Basic
    @Column(name = "id_tipfin", nullable = false)
    private Long idTipfin;

    @Basic
    @Column(name = "id_tem", nullable = true)
    private Long idTem;

    @Basic
    @HtmlEscape
    @Column(name = "desent", nullable = true, length = 250)
    private String desent;

    @Basic
    @HtmlEscape
    @Column(name = "desprvent", nullable = true, length = 250)
    private String desprvent;

    @Basic
    @HtmlEscape
    @Column(name = "refmin", nullable = true, length = 250)
    private String refmin;

    @Basic
    @HtmlEscape
    @Column(name = "desben", nullable = true, length = 250)
    private String desben;

    @Basic
    @Column(name = "impinv", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impinv deve essere superiore o uguale a 0")
    private BigDecimal impinv;

    @Basic
    @Column(name = "impban", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impban deve essere superiore o uguale a 0")
    private BigDecimal impban;

    @Basic
    @Column(name = "impmaspro", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impmaspro deve essere superiore o uguale a 0")
    private BigDecimal impmaspro;

    @Basic
    @Column(name = "impmaspar", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impmaspar deve essere superiore o uguale a 0")
    private BigDecimal impmaspar;

    @Basic
    @Column(name = "dtapubban", nullable = true)
    private Date dtapubban;

    @Basic
    @Column(name = "dtachiatt", nullable = true)
    private Date dtachiatt;

    @Basic
    @Column(name = "dtascaban", nullable = true)
    private Date dtascaban;

    @Basic
    @HtmlEscape
    @Column(name = "desdecfin", nullable = true, length = 250)
    private String desdecfin;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_staban", referencedColumnName = "id", insertable = false, updatable = false)
    private ProStaban proStabanByIdStaban;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tipfin", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProTipfin proTipfinByIdTipfin;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tem", referencedColumnName = "id", insertable = false, updatable = false)
    private ProTem proTemByIdTem;

    @Valid
    @OneToMany(mappedBy = "proBanByIdBan", fetch = FetchType.LAZY)
    private List<ProPro> proProsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodban() {
        return codban;
    }

    public void setCodban(String codban) {
        this.codban = codban;
    }

    public String getDesban() {
        return desban;
    }

    public void setDesban(String desban) {
        this.desban = desban;
    }

    public Long getIdStaban() {
        return idStaban;
    }

    public void setIdStaban(Long idStaban) {
        this.idStaban = idStaban;
    }

    public Long getIdTipfin() {
        return idTipfin;
    }

    public void setIdTipfin(Long idTipfin) {
        this.idTipfin = idTipfin;
    }

    public Long getIdTem() {
        return idTem;
    }

    public void setIdTem(Long idTem) {
        this.idTem = idTem;
    }

    public String getDesent() {
        return desent;
    }

    public void setDesent(String desent) {
        this.desent = desent;
    }

    public String getDesprvent() {
        return desprvent;
    }

    public void setDesprvent(String desprvent) {
        this.desprvent = desprvent;
    }

    public String getRefmin() {
        return refmin;
    }

    public void setRefmin(String refmin) {
        this.refmin = refmin;
    }

    public String getDesben() {
        return desben;
    }

    public void setDesben(String desben) {
        this.desben = desben;
    }

    public BigDecimal getImpinv() {
        return impinv;
    }

    public void setImpinv(BigDecimal impinv) {
        this.impinv = impinv;
    }

    public BigDecimal getImpban() {
        return impban;
    }

    public void setImpban(BigDecimal impban) {
        this.impban = impban;
    }

    public BigDecimal getImpmaspro() {
        return impmaspro;
    }

    public void setImpmaspro(BigDecimal impmaspro) {
        this.impmaspro = impmaspro;
    }

    public BigDecimal getImpmaspar() {
        return impmaspar;
    }

    public void setImpmaspar(BigDecimal impmaspar) {
        this.impmaspar = impmaspar;
    }

    public Date getDtapubban() {
        return dtapubban;
    }

    public void setDtapubban(Date dtapubban) {
        this.dtapubban = dtapubban;
    }

    public Date getDtachiatt() {
        return dtachiatt;
    }

    public void setDtachiatt(Date dtachiatt) {
        this.dtachiatt = dtachiatt;
    }

    public Date getDtascaban() {
        return dtascaban;
    }

    public void setDtascaban(Date dtascaban) {
        this.dtascaban = dtascaban;
    }

    public String getDesdecfin() {
        return desdecfin;
    }

    public void setDesdecfin(String desdecfin) {
        this.desdecfin = desdecfin;
    }

    public ProStaban getProStabanByIdStaban() {
        return proStabanByIdStaban;
    }

    public void setProStabanByIdStaban(ProStaban proStabanByIdStaban) {
        this.proStabanByIdStaban = proStabanByIdStaban;
    }

    public ProTipfin getProTipfinByIdTipfin() {
        return proTipfinByIdTipfin;
    }

    public void setProTipfinByIdTipfin(ProTipfin proTipfinByIdTipfin) {
        this.proTipfinByIdTipfin = proTipfinByIdTipfin;
    }

    public ProTem getProTemByIdTem() {
        return proTemByIdTem;
    }

    public void setProTemByIdTem(ProTem proTemByIdTem) {
        this.proTemByIdTem = proTemByIdTem;
    }

    public List<ProPro> getProProsById() {
        return proProsById;
    }

    public void setProProsById(List<ProPro> proProsById) {
        this.proProsById = proProsById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProBan proBan = (ProBan) o;

        if (id != proBan.id) return false;
        if (idTipfin != proBan.idTipfin) return false;
        if (codban != null ? !codban.equals(proBan.codban) : proBan.codban != null) return false;
        if (desban != null ? !desban.equals(proBan.desban) : proBan.desban != null) return false;
        if (idStaban != null ? !idStaban.equals(proBan.idStaban) : proBan.idStaban != null) return false;
        if (idTem != null ? !idTem.equals(proBan.idTem) : proBan.idTem != null) return false;
        if (desent != null ? !desent.equals(proBan.desent) : proBan.desent != null) return false;
        if (desprvent != null ? !desprvent.equals(proBan.desprvent) : proBan.desprvent != null) return false;
        if (refmin != null ? !refmin.equals(proBan.refmin) : proBan.refmin != null) return false;
        if (desben != null ? !desben.equals(proBan.desben) : proBan.desben != null) return false;
        if (impinv != null ? !impinv.equals(proBan.impinv) : proBan.impinv != null) return false;
        if (impban != null ? !impban.equals(proBan.impban) : proBan.impban != null) return false;
        if (impmaspro != null ? !impmaspro.equals(proBan.impmaspro) : proBan.impmaspro != null) return false;
        if (impmaspar != null ? !impmaspar.equals(proBan.impmaspar) : proBan.impmaspar != null) return false;
        if (dtapubban != null ? !dtapubban.equals(proBan.dtapubban) : proBan.dtapubban != null) return false;
        if (dtachiatt != null ? !dtachiatt.equals(proBan.dtachiatt) : proBan.dtachiatt != null) return false;
        if (dtascaban != null ? !dtascaban.equals(proBan.dtascaban) : proBan.dtascaban != null) return false;
        if (desdecfin != null ? !desdecfin.equals(proBan.desdecfin) : proBan.desdecfin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (codban != null ? codban.hashCode() : 0);
        result = 31 * result + (desban != null ? desban.hashCode() : 0);
        result = 31 * result + (idStaban != null ? idStaban.hashCode() : 0);
        result = 31 * result + (int) (idTipfin ^ (idTipfin >>> 32));
        result = 31 * result + (idTem != null ? idTem.hashCode() : 0);
        result = 31 * result + (desent != null ? desent.hashCode() : 0);
        result = 31 * result + (desprvent != null ? desprvent.hashCode() : 0);
        result = 31 * result + (refmin != null ? refmin.hashCode() : 0);
        result = 31 * result + (desben != null ? desben.hashCode() : 0);
        result = 31 * result + (impinv != null ? impinv.hashCode() : 0);
        result = 31 * result + (impban != null ? impban.hashCode() : 0);
        result = 31 * result + (impmaspro != null ? impmaspro.hashCode() : 0);
        result = 31 * result + (impmaspar != null ? impmaspar.hashCode() : 0);
        result = 31 * result + (dtapubban != null ? dtapubban.hashCode() : 0);
        result = 31 * result + (dtachiatt != null ? dtachiatt.hashCode() : 0);
        result = 31 * result + (dtascaban != null ? dtascaban.hashCode() : 0);
        result = 31 * result + (desdecfin != null ? desdecfin.hashCode() : 0);
        return result;
    }
}
