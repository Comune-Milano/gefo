package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_lisval", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProLisval extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "tiplis", nullable = false, length = 25)
    private String tiplis;

    @Basic
    @HtmlEscape
    @Column(name = "vallis", nullable = false, length = 50)
    private String vallis;

    @OneToMany(mappedBy = "proLisvalByIdLisvalfasint", fetch = FetchType.LAZY)
    @Valid
    private List<ProAva> proAvasById;

    @OneToMany(mappedBy = "proLisvalByIdLisvallivcri", fetch = FetchType.LAZY)
    @Valid
    private List<ProAva> proAvasById_0;

    @OneToMany(mappedBy = "proLisvalByIdLisvaltipapp", fetch = FetchType.LAZY)
    @Valid
    private List<ProAva> proAvasById_1;

    @OneToMany(mappedBy = "proLisvalByIdLisvalstaant", fetch = FetchType.LAZY)
    @Valid
    private List<ProAva> proAvasById_2;

    @OneToMany(mappedBy = "proLisvalByIdLisvaltipolfas", fetch = FetchType.LAZY)
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

    public String getTiplis() {
        return tiplis;
    }

    public void setTiplis(String tiplis) {
        this.tiplis = tiplis;
    }

    public String getVallis() {
        return vallis;
    }

    public void setVallis(String vallis) {
        this.vallis = vallis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProLisval proLisval = (ProLisval) o;

        if (id != proLisval.id) return false;
        if (tiplis != null ? !tiplis.equals(proLisval.tiplis) : proLisval.tiplis != null) return false;
        if (vallis != null ? !vallis.equals(proLisval.vallis) : proLisval.vallis != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tiplis != null ? tiplis.hashCode() : 0);
        result = 31 * result + (vallis != null ? vallis.hashCode() : 0);
        return result;
    }

    public List<ProAva> getProAvasById() {
        return proAvasById;
    }

    public void setProAvasById(List<ProAva> proAvasById) {
        this.proAvasById = proAvasById;
    }

    public List<ProAva> getProAvasById_0() {
        return proAvasById_0;
    }

    public void setProAvasById_0(List<ProAva> proAvasById_0) {
        this.proAvasById_0 = proAvasById_0;
    }

    public List<ProAva> getProAvasById_1() {
        return proAvasById_1;
    }

    public void setProAvasById_1(List<ProAva> proAvasById_1) {
        this.proAvasById_1 = proAvasById_1;
    }

    public List<ProAva> getProAvasById_2() {
        return proAvasById_2;
    }

    public void setProAvasById_2(List<ProAva> proAvasById_2) {
        this.proAvasById_2 = proAvasById_2;
    }

    public List<ProDettipolfas> getProDettipolfasById() {
        return proDettipolfasById;
    }

    public void setProDettipolfasById(List<ProDettipolfas> proDettipolfasById) {
        this.proDettipolfasById = proDettipolfasById;
    }

    @Override
    public String toString() {
        return (
            "ProLisval{" +
            "id=" +
            id +
            ", tiplis='" +
            tiplis +
            '\'' +
            ", vallis='" +
            vallis +
            '\'' +
            ", proAvasById=" +
            proAvasById +
            ", proAvasById_0=" +
            proAvasById_0 +
            ", proAvasById_1=" +
            proAvasById_1 +
            ", proAvasById_2=" +
            proAvasById_2 +
            ", proDettipolfasById=" +
            proDettipolfasById +
            '}'
        );
    }
}
