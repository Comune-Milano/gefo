package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_ricute", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProRicute extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_ric", nullable = false)
    private Long idRic;

    @Basic
    @Column(name = "id_ute", nullable = true, length = 30)
    private Integer idUte;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_ric", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProRic proRicByIdRic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getIdRic() {
        return idRic;
    }

    public void setIdRic(Long idRic) {
        this.idRic = idRic;
    }

    public Integer getIdUte() {
        return idUte;
    }

    public void setIdUte(Integer idUte) {
        this.idUte = idUte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProRicute proRicute = (ProRicute) o;

        if (id != proRicute.id) return false;
        if (idRic != proRicute.idRic) return false;
        if (!Objects.equals(idUte, proRicute.idUte)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (idRic ^ (idRic >>> 32));
        result = 31 * result + (int) (idUte ^ (idUte >>> 32));
        return result;
    }

    public ProRic getProRicByIdRic() {
        return proRicByIdRic;
    }

    public void setProRicByIdRic(ProRic proRicByIdRic) {
        this.proRicByIdRic = proRicByIdRic;
    }
}
