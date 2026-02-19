package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_infagg", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProInfagg extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_pro", nullable = false)
    private Long idPro;

    @Basic
    @Column(name = "id_tipinfagg", nullable = false)
    private Long idTipinfagg;

    @Basic
    @HtmlEscape
    @Column(name = "infagg", nullable = true, length = 250)
    private String infagg;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_pro", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProPro proProByIdPro;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tipinfagg", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProTipinfagg proTipinfaggByIdTipinfagg;

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

    public Long getIdTipinfagg() {
        return idTipinfagg;
    }

    public void setIdTipinfagg(Long idTipinfagg) {
        this.idTipinfagg = idTipinfagg;
    }

    public String getInfagg() {
        return infagg;
    }

    public void setInfagg(String infagg) {
        this.infagg = infagg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProInfagg proInfagg = (ProInfagg) o;

        if (id != proInfagg.id) return false;
        if (idPro != proInfagg.idPro) return false;
        if (idTipinfagg != proInfagg.idTipinfagg) return false;
        if (infagg != null ? !infagg.equals(proInfagg.infagg) : proInfagg.infagg != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (idPro ^ (idPro >>> 32));
        result = 31 * result + (int) (idTipinfagg ^ (idTipinfagg >>> 32));
        result = 31 * result + (infagg != null ? infagg.hashCode() : 0);
        return result;
    }

    public ProPro getProProByIdPro() {
        return proProByIdPro;
    }

    public void setProProByIdPro(ProPro proProByIdPro) {
        this.proProByIdPro = proProByIdPro;
    }

    public ProTipinfagg getProTipinfaggByIdTipinfagg() {
        return proTipinfaggByIdTipinfagg;
    }

    public void setProTipinfaggByIdTipinfagg(ProTipinfagg proTipinfaggByIdTipinfagg) {
        this.proTipinfaggByIdTipinfagg = proTipinfaggByIdTipinfagg;
    }
}
