package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Table(name = "pro_imp", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProImp extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_pro", nullable = false)
    private Long idPro;

    @Basic
    @Column(name = "id_tipimp", nullable = false)
    private Long idTipimp;

    @Basic
    @Column(name = "imppro", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo imppro deve essere superiore o uguale a 0")
    private Double imppro;

    @Basic
    @HtmlEscape
    @Column(name = "notimp", nullable = true, length = 250)
    private String notimp;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_pro", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProPro proProByIdPro;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tipimp", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProTipimp proTipimpByIdTipimp;

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

    public Long getIdTipimp() {
        return idTipimp;
    }

    public void setIdTipimp(Long idTipimp) {
        this.idTipimp = idTipimp;
    }

    public Double getImppro() {
        return imppro;
    }

    public void setImppro(Double imppro) {
        this.imppro = imppro;
    }

    public String getNotimp() {
        return notimp;
    }

    public void setNotimp(String notimp) {
        this.notimp = notimp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProImp proImp = (ProImp) o;

        if (id != proImp.id) return false;
        if (idPro != proImp.idPro) return false;
        if (idTipimp != proImp.idTipimp) return false;
        if (imppro != null ? !imppro.equals(proImp.imppro) : proImp.imppro != null) return false;
        if (notimp != null ? !notimp.equals(proImp.notimp) : proImp.notimp != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (idPro ^ (idPro >>> 32));
        result = 31 * result + (int) (idTipimp ^ (idTipimp >>> 32));
        result = 31 * result + (imppro != null ? imppro.hashCode() : 0);
        result = 31 * result + (notimp != null ? notimp.hashCode() : 0);
        return result;
    }

    public ProPro getProProByIdPro() {
        return proProByIdPro;
    }

    public void setProProByIdPro(ProPro proProByIdPro) {
        this.proProByIdPro = proProByIdPro;
    }

    public ProTipimp getProTipimpByIdTipimp() {
        return proTipimpByIdTipimp;
    }

    public void setProTipimpByIdTipimp(ProTipimp proTipimpByIdTipimp) {
        this.proTipimpByIdTipimp = proTipimpByIdTipimp;
    }
}
