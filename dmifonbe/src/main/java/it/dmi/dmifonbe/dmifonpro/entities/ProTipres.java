package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_tipres", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProTipres extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "destipres", nullable = false, length = 50)
    private String destipres;

    @Basic
    @Column(name = "ordtipres", nullable = false)
    private int ordtipres;

    @OneToMany(mappedBy = "proTipresByIdTipres", fetch = FetchType.LAZY)
    @Valid
    private List<ProRes> proResById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestipres() {
        return destipres;
    }

    public void setDestipres(String destipres) {
        this.destipres = destipres;
    }

    public int getOrdtipres() {
        return ordtipres;
    }

    public void setOrdtipres(int ordtipres) {
        this.ordtipres = ordtipres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProTipres proTipres = (ProTipres) o;

        if (id != proTipres.id) return false;
        if (ordtipres != proTipres.ordtipres) return false;
        if (destipres != null ? !destipres.equals(proTipres.destipres) : proTipres.destipres != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (destipres != null ? destipres.hashCode() : 0);
        result = 31 * result + ordtipres;
        return result;
    }

    public List<ProRes> getProResById() {
        return proResById;
    }

    public void setProResById(List<ProRes> proResById) {
        this.proResById = proResById;
    }
}
