package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Table(name = "pro_ddr", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProDdr extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "codddr", nullable = false, length = 25)
    private String codddr;

    @Basic
    @HtmlEscape
    @Column(name = "desddr", nullable = false, length = 250)
    private String desddr;

    @Basic
    @Column(name = "dtaddr", nullable = false)
    private Date dtaddr;

    @Basic
    @Column(name = "id_pro", nullable = false)
    private Long idPro;

    @Basic
    @Column(name = "id_ddra", nullable = true)
    private Long idDdra;

    @Basic
    @Column(name = "impddr", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impddr deve essere superiore o uguale a 0")
    private BigDecimal impddr;

    @Basic
    @Column(name = "impamm", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impamm deve essere superiore o uguale a 0")
    private BigDecimal impamm;

    @Basic
    @Column(name = "impinc", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impinc deve essere superiore o uguale a 0")
    private BigDecimal impinc;

    @Basic
    @HtmlEscape
    @Column(name = "prgrev", nullable = true, length = 250)
    private String prgrev;

    @Basic
    @HtmlEscape
    @Column(name = "notddr", nullable = true, length = 250)
    private String notddr;

    @Basic
    @Column(name = "imptra", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo imptra deve essere superiore o uguale a 0")
    private BigDecimal imptra;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_pro", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProPro proProByIdPro;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_ddra", referencedColumnName = "id", insertable = false, updatable = false)
    private ProDdra proDdraByIdDdra;

    @OneToMany(mappedBy = "proDdrByIdDdr")
    @Valid
    private Collection<ProDetconddr> proDetconddrsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodddr() {
        return codddr;
    }

    public void setCodddr(String codddr) {
        this.codddr = codddr;
    }

    public String getDesddr() {
        return desddr;
    }

    public void setDesddr(String desddr) {
        this.desddr = desddr;
    }

    public Date getDtaddr() {
        return dtaddr;
    }

    public void setDtaddr(Date dtaddr) {
        this.dtaddr = dtaddr;
    }

    public Long getIdPro() {
        return idPro;
    }

    public void setIdPro(Long idPro) {
        this.idPro = idPro;
    }

    public Long getIdDdra() {
        return idDdra;
    }

    public void setIdDdra(Long idDdra) {
        this.idDdra = idDdra;
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

    public BigDecimal getImpinc() {
        return impinc;
    }

    public void setImpinc(BigDecimal impinc) {
        this.impinc = impinc;
    }

    public String getPrgrev() {
        return prgrev;
    }

    public void setPrgrev(String prgrev) {
        this.prgrev = prgrev;
    }

    public String getNotddr() {
        return notddr;
    }

    public void setNotddr(String notddr) {
        this.notddr = notddr;
    }

    public BigDecimal getImptra() {
        return imptra;
    }

    public void setImptra(BigDecimal imptra) {
        this.imptra = imptra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProDdr proDdr = (ProDdr) o;

        if (id != proDdr.id) return false;
        if (idPro != proDdr.idPro) return false;
        if (codddr != null ? !codddr.equals(proDdr.codddr) : proDdr.codddr != null) return false;
        if (desddr != null ? !desddr.equals(proDdr.desddr) : proDdr.desddr != null) return false;
        if (dtaddr != null ? !dtaddr.equals(proDdr.dtaddr) : proDdr.dtaddr != null) return false;
        if (idDdra != null ? !idDdra.equals(proDdr.idDdra) : proDdr.idDdra != null) return false;
        if (impddr != null ? !impddr.equals(proDdr.impddr) : proDdr.impddr != null) return false;
        if (impamm != null ? !impamm.equals(proDdr.impamm) : proDdr.impamm != null) return false;
        if (impinc != null ? !impinc.equals(proDdr.impinc) : proDdr.impinc != null) return false;
        if (prgrev != null ? !prgrev.equals(proDdr.prgrev) : proDdr.prgrev != null) return false;
        if (notddr != null ? !notddr.equals(proDdr.notddr) : proDdr.notddr != null) return false;
        if (imptra != null ? !imptra.equals(proDdr.imptra) : proDdr.imptra != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (codddr != null ? codddr.hashCode() : 0);
        result = 31 * result + (desddr != null ? desddr.hashCode() : 0);
        result = 31 * result + (dtaddr != null ? dtaddr.hashCode() : 0);
        result = 31 * result + (int) (idPro ^ (idPro >>> 32));
        result = 31 * result + (idDdra != null ? idDdra.hashCode() : 0);
        result = 31 * result + (impddr != null ? impddr.hashCode() : 0);
        result = 31 * result + (impamm != null ? impamm.hashCode() : 0);
        result = 31 * result + (impinc != null ? impinc.hashCode() : 0);
        result = 31 * result + (prgrev != null ? prgrev.hashCode() : 0);
        result = 31 * result + (notddr != null ? notddr.hashCode() : 0);
        result = 31 * result + (imptra != null ? imptra.hashCode() : 0);
        return result;
    }

    public ProPro getProProByIdPro() {
        return proProByIdPro;
    }

    public void setProProByIdPro(ProPro proProByIdPro) {
        this.proProByIdPro = proProByIdPro;
    }

    public ProDdra getProDdraByIdDdra() {
        return proDdraByIdDdra;
    }

    public void setProDdraByIdDdra(ProDdra proDdraByIdDdra) {
        this.proDdraByIdDdra = proDdraByIdDdra;
    }

    public Collection<ProDetconddr> getProDetconddrsById() {
        return proDetconddrsById;
    }

    public void setProDetconddrsById(Collection<ProDetconddr> proDetconddrsById) {
        this.proDetconddrsById = proDetconddrsById;
    }
}
