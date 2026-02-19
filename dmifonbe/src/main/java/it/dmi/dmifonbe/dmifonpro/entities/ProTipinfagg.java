package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_tipinfagg", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProTipinfagg extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "destipinfagg", nullable = false, length = 50)
    @HtmlEscape
    private String destipinfagg;

    @Basic
    @Column(name = "ordtipinfagg", nullable = false)
    private int ordtipinfagg;

    @OneToMany(mappedBy = "proTipinfaggByIdTipinfagg", fetch = FetchType.LAZY)
    @Valid
    private List<ProInfagg> proInfaggsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestipinfagg() {
        return destipinfagg;
    }

    public void setDestipinfagg(String destipinfagg) {
        this.destipinfagg = destipinfagg;
    }

    public int getOrdtipinfagg() {
        return ordtipinfagg;
    }

    public void setOrdtipinfagg(int ordtipinfagg) {
        this.ordtipinfagg = ordtipinfagg;
    }

    @Override
    public String toString() {
        return (
            "ProTipinfagg{" +
            "id=" +
            id +
            ", destipinfagg='" +
            destipinfagg +
            '\'' +
            ", ordtipinfagg=" +
            ordtipinfagg +
            ", proInfaggsById=" +
            proInfaggsById +
            '}'
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProTipinfagg that = (ProTipinfagg) o;

        if (id != that.id) return false;
        if (ordtipinfagg != that.ordtipinfagg) return false;
        if (destipinfagg != null ? !destipinfagg.equals(that.destipinfagg) : that.destipinfagg != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (destipinfagg != null ? destipinfagg.hashCode() : 0);
        result = 31 * result + ordtipinfagg;
        return result;
    }

    public List<ProInfagg> getProInfaggsById() {
        return proInfaggsById;
    }

    public void setProInfaggsById(List<ProInfagg> proInfaggsById) {
        this.proInfaggsById = proInfaggsById;
    }
}
