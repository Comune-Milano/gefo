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
@Table(name = "pro_ric", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProRic extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_pro", nullable = false)
    private Long idPro;

    @Basic
    @HtmlEscape
    @Column(name = "tipric", nullable = false, length = 1)
    private String tipric;

    @Basic
    @HtmlEscape
    @Column(name = "desric", nullable = true, length = 250)
    private String desric;

    @Basic
    @HtmlEscape
    @Column(name = "staric", nullable = true, length = 3)
    private String staric;

    @Basic
    @Column(name = "dtasca", nullable = true)
    private Date dtasca;

    @Basic
    @Column(name = "dtainv", nullable = true)
    private Date dtainv;

    @Basic
    @HtmlEscape
    @Column(name = "risric", nullable = true, length = 250)
    private String risric;

    @Basic
    @Column(name = "id_ricpad", nullable = true)
    private Long idRicpad;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_pro", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProPro proProByIdPro;

    @OneToMany(mappedBy = "proRicByIdRic", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Valid
    private List<ProRicute> proRicutesById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getIdPro() {
        return idPro;
    }

    public void setIdPro(Long idPro) {
        this.idPro = idPro;
    }

    public String getTipric() {
        return tipric;
    }

    public void setTipric(String tipric) {
        this.tipric = tipric;
    }

    public String getDesric() {
        return desric;
    }

    public void setDesric(String desric) {
        this.desric = desric;
    }

    public String getStaric() {
        return staric;
    }

    public void setStaric(String staric) {
        this.staric = staric;
    }

    public Date getDtasca() {
        return dtasca;
    }

    public void setDtasca(Date dtasca) {
        this.dtasca = dtasca;
    }

    public Date getDtainv() {
        return dtainv;
    }

    public void setDtainv(Date dtainv) {
        this.dtainv = dtainv;
    }

    public String getRisric() {
        return risric;
    }

    public void setRisric(String risric) {
        this.risric = risric;
    }

    public Long getIdRicpad() {
        return idRicpad;
    }

    public void setIdRicpad(Long idRicpad) {
        this.idRicpad = idRicpad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProRic proRic = (ProRic) o;

        if (id != proRic.id) return false;
        if (idPro != proRic.idPro) return false;
        if (tipric != null ? !tipric.equals(proRic.tipric) : proRic.tipric != null) return false;
        if (desric != null ? !desric.equals(proRic.desric) : proRic.desric != null) return false;
        if (staric != null ? !staric.equals(proRic.staric) : proRic.staric != null) return false;
        if (dtasca != null ? !dtasca.equals(proRic.dtasca) : proRic.dtasca != null) return false;
        if (dtainv != null ? !dtainv.equals(proRic.dtainv) : proRic.dtainv != null) return false;
        if (risric != null ? !risric.equals(proRic.risric) : proRic.risric != null) return false;
        if (idRicpad != null ? !idRicpad.equals(proRic.idRicpad) : proRic.idRicpad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (idPro ^ (idPro >>> 32));
        result = 31 * result + (tipric != null ? tipric.hashCode() : 0);
        result = 31 * result + (desric != null ? desric.hashCode() : 0);
        result = 31 * result + (staric != null ? staric.hashCode() : 0);
        result = 31 * result + (dtasca != null ? dtasca.hashCode() : 0);
        result = 31 * result + (dtainv != null ? dtainv.hashCode() : 0);
        result = 31 * result + (risric != null ? risric.hashCode() : 0);
        result = 31 * result + (idRicpad != null ? idRicpad.hashCode() : 0);
        return result;
    }

    public ProPro getProProByIdPro() {
        return proProByIdPro;
    }

    public void setProProByIdPro(ProPro proProByIdPro) {
        this.proProByIdPro = proProByIdPro;
    }

    public List<ProRicute> getProRicutesById() {
        return proRicutesById;
    }

    public void setProRicutesById(List<ProRicute> proRicutesById) {
        this.proRicutesById = proRicutesById;
    }
}
