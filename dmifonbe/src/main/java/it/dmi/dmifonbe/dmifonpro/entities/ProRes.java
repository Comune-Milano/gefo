package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_res", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProRes extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_pro", nullable = false)
    private Long idPro;

    @Basic
    @Column(name = "id_tipres", nullable = false)
    private Long idTipres;

    @Basic
    @Column(name = "id_ute", nullable = true)
    private Long idUte;

    @Basic
    @HtmlEscape
    @Column(name = "notres", length = 250)
    private String notres;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_pro", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProPro proProByIdPro;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tipres", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProTipres proTipresByIdTipres;

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

    public Long getIdTipres() {
        return idTipres;
    }

    public void setIdTipres(Long idTipres) {
        this.idTipres = idTipres;
    }

    public Long getIdUte() {
        return idUte;
    }

    public void setIdUte(Long idUte) {
        this.idUte = idUte;
    }

    public String getNotres() {
        return notres;
    }

    public void setNotres(String notres) {
        this.notres = notres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProRes proRes = (ProRes) o;

        if (id != proRes.id) return false;
        if (idPro != proRes.idPro) return false;
        if (idTipres != proRes.idTipres) return false;
        if (idUte != proRes.idUte) return false;
        if (notres != null ? !notres.equals(proRes.notres) : proRes.notres != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (idPro ^ (idPro >>> 32));
        result = 31 * result + (int) (idTipres ^ (idTipres >>> 32));
        result = 31 * result + (int) (idUte ^ (idUte >>> 32));
        result = 31 * result + (notres != null ? notres.hashCode() : 0);
        return result;
    }

    public ProPro getProProByIdPro() {
        return proProByIdPro;
    }

    public void setProProByIdPro(ProPro proProByIdPro) {
        this.proProByIdPro = proProByIdPro;
    }

    public ProTipres getProTipresByIdTipres() {
        return proTipresByIdTipres;
    }

    public void setProTipresByIdTipres(ProTipres proTipresByIdTipres) {
        this.proTipresByIdTipres = proTipresByIdTipres;
    }
}
