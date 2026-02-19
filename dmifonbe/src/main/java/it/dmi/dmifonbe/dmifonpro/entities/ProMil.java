package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.Date;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_mil", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProMil extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "id_fas", nullable = false)
    private Long idFas;

    @Basic
    @HtmlEscape
    @Column(name = "desmil", nullable = false, length = 250)
    private String desmil;

    @Basic
    @Column(name = "dtaded", nullable = false)
    private Date dtaded;

    @Basic
    @Column(name = "dtaeff", nullable = true)
    private Date dtaeff;

    @Basic
    @HtmlEscape
    @Column(name = "notmil", nullable = true, length = 250)
    private String notmil;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_fas", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProFas proFasByIdFas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIdFas() {
        return idFas;
    }

    public void setIdFas(Long idFas) {
        this.idFas = idFas;
    }

    public String getDesmil() {
        return desmil;
    }

    public void setDesmil(String desmil) {
        this.desmil = desmil;
    }

    public Date getDtaded() {
        return dtaded;
    }

    public void setDtaded(Date dtaded) {
        this.dtaded = dtaded;
    }

    public Date getDtaeff() {
        return dtaeff;
    }

    public void setDtaeff(Date dtaeff) {
        this.dtaeff = dtaeff;
    }

    public String getNotmil() {
        return notmil;
    }

    public void setNotmil(String notmil) {
        this.notmil = notmil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProMil proMil = (ProMil) o;

        if (id != null ? !id.equals(proMil.id) : proMil.id != null) return false;
        if (idFas != null ? !idFas.equals(proMil.idFas) : proMil.idFas != null) return false;
        if (desmil != null ? !desmil.equals(proMil.desmil) : proMil.desmil != null) return false;
        if (dtaded != null ? !dtaded.equals(proMil.dtaded) : proMil.dtaded != null) return false;
        if (dtaeff != null ? !dtaeff.equals(proMil.dtaeff) : proMil.dtaeff != null) return false;
        if (notmil != null ? !notmil.equals(proMil.notmil) : proMil.notmil != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idFas != null ? idFas.hashCode() : 0);
        result = 31 * result + (desmil != null ? desmil.hashCode() : 0);
        result = 31 * result + (dtaded != null ? dtaded.hashCode() : 0);
        result = 31 * result + (dtaeff != null ? dtaeff.hashCode() : 0);
        result = 31 * result + (notmil != null ? notmil.hashCode() : 0);
        return result;
    }

    public ProFas getProFasByIdFas() {
        return proFasByIdFas;
    }

    public void setProFasByIdFas(ProFas proFasByIdFas) {
        this.proFasByIdFas = proFasByIdFas;
    }
}
