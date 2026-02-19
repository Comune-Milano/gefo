package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_tipfas", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProTipfas extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "desfas", nullable = false, length = 50)
    private String desfas;

    @Basic
    @HtmlEscape
    @Column(name = "tipcon", nullable = false, length = 1)
    private String tipcon;

    @Basic
    @Column(name = "ordfas", nullable = false)
    private int ordfas;

    @Basic
    @HtmlEscape
    @Column(name = "tipfas", nullable = true, length = 1)
    private String tipfas;

    @OneToMany(mappedBy = "proTipfasByIdTipfas", fetch = FetchType.LAZY)
    @Valid
    private List<ProFas> proFasById;

    @OneToMany(mappedBy = "proTipfasByIdTipfas", fetch = FetchType.LAZY)
    @Valid
    private List<ProDettipolfas> proDettipolfasById;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesfas() {
        return desfas;
    }

    public void setDesfas(String desfas) {
        this.desfas = desfas;
    }

    public String getTipcon() {
        return tipcon;
    }

    public void setTipcon(String tipcon) {
        this.tipcon = tipcon;
    }

    public int getOrdfas() {
        return ordfas;
    }

    public void setOrdfas(Integer ordfas) {
        this.ordfas = ordfas;
    }

    public void setOrdfas(int ordfas) {
        this.ordfas = ordfas;
    }

    public String getTipfas() {
        return tipfas;
    }

    public void setTipfas(String tipfas) {
        this.tipfas = tipfas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProTipfas proTipfas = (ProTipfas) o;

        if (id != proTipfas.id) return false;
        if (ordfas != proTipfas.ordfas) return false;
        if (desfas != null ? !desfas.equals(proTipfas.desfas) : proTipfas.desfas != null) return false;
        if (tipcon != null ? !tipcon.equals(proTipfas.tipcon) : proTipfas.tipcon != null) return false;
        if (tipfas != null ? !tipfas.equals(proTipfas.tipfas) : proTipfas.tipfas != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return (
            "ProTipfas{" +
            "id=" +
            id +
            ", desfas='" +
            desfas +
            '\'' +
            ", tipcon='" +
            tipcon +
            '\'' +
            ", ordfas=" +
            ordfas +
            ", tipfas='" +
            tipfas +
            '\'' +
            ", proFasById=" +
            proFasById +
            ", proDettipolfasById=" +
            proDettipolfasById +
            '}'
        );
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (desfas != null ? desfas.hashCode() : 0);
        result = 31 * result + (tipcon != null ? tipcon.hashCode() : 0);
        result = 31 * result + ordfas;
        result = 31 * result + (tipfas != null ? tipfas.hashCode() : 0);
        return result;
    }

    public List<ProFas> getProFasById() {
        return proFasById;
    }

    public void setProFasById(List<ProFas> proFasById) {
        this.proFasById = proFasById;
    }

    public List<ProDettipolfas> getProDettipolfasById() {
        return proDettipolfasById;
    }

    public void setProDettipolfasById(List<ProDettipolfas> proDettipolfasById) {
        this.proDettipolfasById = proDettipolfasById;
    }
}
