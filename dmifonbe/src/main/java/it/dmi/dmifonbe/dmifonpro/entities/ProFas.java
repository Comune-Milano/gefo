package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_fas", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProFas extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_ava", nullable = false)
    private Long idAva;

    @Basic
    @Column(name = "id_tipfas", nullable = false)
    private Long idTipfas;

    @Basic
    @Column(name = "dtainiori", nullable = true)
    private Date dtainiori;

    @Basic
    @Column(name = "dtafinori", nullable = true)
    private Date dtafinori;

    @Basic
    @Column(name = "dtainipre", nullable = true)
    private Date dtainipre;

    @Basic
    @Column(name = "dtafinpre", nullable = true)
    private Date dtafinpre;

    @Basic
    @Column(name = "dtainieff", nullable = true)
    private Date dtainieff;

    @Basic
    @Column(name = "dtafineff", nullable = true)
    private Date dtafineff;

    @Basic
    @HtmlEscape
    @Column(name = "notfas", nullable = true, length = 250)
    private String notfas;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_ava", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProAva proAvaByIdAva;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tipfas", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProTipfas proTipfasByIdTipfas;

    @OneToMany(mappedBy = "proFasByIdFas", fetch = FetchType.LAZY)
    @Valid
    @OrderBy("dtaded ASC")
    private List<ProMil> proMilsById;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getIdAva() {
        return idAva;
    }

    public void setIdAva(Long idAva) {
        this.idAva = idAva;
    }

    public Long getIdTipfas() {
        return idTipfas;
    }

    public void setIdTipfas(Long idTipfas) {
        this.idTipfas = idTipfas;
    }

    public Date getDtainiori() {
        return dtainiori;
    }

    public void setDtainiori(Date dtainiori) {
        this.dtainiori = dtainiori;
    }

    public Date getDtafinori() {
        return dtafinori;
    }

    public void setDtafinori(Date dtafinori) {
        this.dtafinori = dtafinori;
    }

    public Date getDtainipre() {
        return dtainipre;
    }

    public void setDtainipre(Date dtainipre) {
        this.dtainipre = dtainipre;
    }

    public Date getDtafinpre() {
        return dtafinpre;
    }

    public void setDtafinpre(Date dtafinpre) {
        this.dtafinpre = dtafinpre;
    }

    public Date getDtainieff() {
        return dtainieff;
    }

    public void setDtainieff(Date dtainieff) {
        this.dtainieff = dtainieff;
    }

    public Date getDtafineff() {
        return dtafineff;
    }

    public void setDtafineff(Date dtafineff) {
        this.dtafineff = dtafineff;
    }

    public String getNotfas() {
        return notfas;
    }

    public void setNotfas(String notfas) {
        this.notfas = notfas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProFas proFas = (ProFas) o;

        if (id != proFas.id) return false;
        if (idAva != proFas.idAva) return false;
        if (idTipfas != proFas.idTipfas) return false;
        if (dtainiori != null ? !dtainiori.equals(proFas.dtainiori) : proFas.dtainiori != null) return false;
        if (dtafinori != null ? !dtafinori.equals(proFas.dtafinori) : proFas.dtafinori != null) return false;
        if (dtainipre != null ? !dtainipre.equals(proFas.dtainipre) : proFas.dtainipre != null) return false;
        if (dtafinpre != null ? !dtafinpre.equals(proFas.dtafinpre) : proFas.dtafinpre != null) return false;
        if (dtainieff != null ? !dtainieff.equals(proFas.dtainieff) : proFas.dtainieff != null) return false;
        if (dtafineff != null ? !dtafineff.equals(proFas.dtafineff) : proFas.dtafineff != null) return false;
        if (notfas != null ? !notfas.equals(proFas.notfas) : proFas.notfas != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (idAva ^ (idAva >>> 32));
        result = 31 * result + (int) (idTipfas ^ (idTipfas >>> 32));
        result = 31 * result + (dtainiori != null ? dtainiori.hashCode() : 0);
        result = 31 * result + (dtafinori != null ? dtafinori.hashCode() : 0);
        result = 31 * result + (dtainipre != null ? dtainipre.hashCode() : 0);
        result = 31 * result + (dtafinpre != null ? dtafinpre.hashCode() : 0);
        result = 31 * result + (dtainieff != null ? dtainieff.hashCode() : 0);
        result = 31 * result + (dtafineff != null ? dtafineff.hashCode() : 0);
        result = 31 * result + (notfas != null ? notfas.hashCode() : 0);
        return result;
    }

    public static ProFas generateCopy(ProFas other) {
        ProFas result = new ProFas();
        result.setIdAva(other.getIdAva());
        result.setIdTipfas(other.getIdTipfas());
        result.setDtainiori(other.getDtainiori());
        result.setDtafinori(other.getDtafinori());
        result.setDtainipre(other.getDtainipre());
        result.setDtafinpre(other.getDtafinpre());
        result.setDtainieff(other.getDtainieff());
        result.setDtafineff(other.getDtafineff());
        result.setNotfas(other.getNotfas());
        return result;
    }

    public ProAva getProAvaByIdAva() {
        return proAvaByIdAva;
    }

    public void setProAvaByIdAva(ProAva proAvaByIdAva) {
        this.proAvaByIdAva = proAvaByIdAva;
    }

    public ProTipfas getProTipfasByIdTipfas() {
        return proTipfasByIdTipfas;
    }

    public void setProTipfasByIdTipfas(ProTipfas proTipfasByIdTipfas) {
        this.proTipfasByIdTipfas = proTipfasByIdTipfas;
    }

    public List<ProMil> getProMilsById() {
        return proMilsById;
    }

    public void setProMilsById(List<ProMil> proMilsById) {
        this.proMilsById = proMilsById;
    }
}
