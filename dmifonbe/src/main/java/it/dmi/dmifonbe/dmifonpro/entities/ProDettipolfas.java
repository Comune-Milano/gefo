package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_dettipolfas", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProDettipolfas extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "id_lisvaltipolfas", nullable = false)
    private Long idLisvaltipolfas;

    @Basic
    @Column(name = "id_tipfas", nullable = false)
    private Long idTipfas;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_lisvaltipolfas", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProLisval proLisvalByIdLisvaltipolfas;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tipfas", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProTipfas proTipfasByIdTipfas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIdLisvaltipolfas() {
        return idLisvaltipolfas;
    }

    public void setIdLisvaltipolfas(Long idLisvaltipolfas) {
        this.idLisvaltipolfas = idLisvaltipolfas;
    }

    public Long getIdTipfas() {
        return idTipfas;
    }

    public void setIdTipfas(Long idTipfas) {
        this.idTipfas = idTipfas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProDettipolfas that = (ProDettipolfas) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (idLisvaltipolfas != null ? !idLisvaltipolfas.equals(that.idLisvaltipolfas) : that.idLisvaltipolfas != null) return false;
        if (idTipfas != null ? !idTipfas.equals(that.idTipfas) : that.idTipfas != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idLisvaltipolfas != null ? idLisvaltipolfas.hashCode() : 0);
        result = 31 * result + (idTipfas != null ? idTipfas.hashCode() : 0);
        return result;
    }

    public ProLisval getProLisvalByIdLisvaltipolfas() {
        return proLisvalByIdLisvaltipolfas;
    }

    public void setProLisvalByIdLisvaltipolfas(ProLisval proLisvalByIdLisvaltipolfas) {
        this.proLisvalByIdLisvaltipolfas = proLisvalByIdLisvaltipolfas;
    }

    public ProTipfas getProTipfasByIdTipfas() {
        return proTipfasByIdTipfas;
    }

    public void setProTipfasByIdTipfas(ProTipfas proTipfasByIdTipfas) {
        this.proTipfasByIdTipfas = proTipfasByIdTipfas;
    }
}
