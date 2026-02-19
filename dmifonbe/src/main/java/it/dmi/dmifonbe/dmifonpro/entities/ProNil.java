package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_nil", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProNil extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "codnil", nullable = false, length = 25)
    private String codnil;

    @Basic
    @HtmlEscape
    @Column(name = "desnil", nullable = false, length = 250)
    private String desnil;

    @Basic
    @Column(name = "id_mun", nullable = true)
    private Long idMun;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_mun", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
    private ProMun proMunByIdMun;

    @OneToMany(mappedBy = "proNilByIdNil", fetch = FetchType.LAZY)
    @Valid
    private List<ProPro> proProsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodnil() {
        return codnil;
    }

    public void setCodnil(String codnil) {
        this.codnil = codnil;
    }

    public String getDesnil() {
        return desnil;
    }

    public void setDesnil(String desnil) {
        this.desnil = desnil;
    }

    public Long getIdMun() {
        return idMun;
    }

    public void setIdMun(Long idMun) {
        this.idMun = idMun;
    }

    public ProMun getProMunByIdMun() {
        return proMunByIdMun;
    }

    public void setProMunByIdMun(ProMun proMunByIdMun) {
        this.proMunByIdMun = proMunByIdMun;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProNil proNil = (ProNil) o;

        if (id != proNil.id) return false;
        if (desnil != null ? !desnil.equals(proNil.desnil) : proNil.desnil != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return (
            "ProNil{" +
            "id=" +
            id +
            ", codnil='" +
            codnil +
            '\'' +
            ", desnil='" +
            desnil +
            '\'' +
            ", idMun=" +
            idMun +
            ", proMunByIdMun=" +
            proMunByIdMun +
            ", proProsById=" +
            proProsById +
            '}'
        );
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (desnil != null ? desnil.hashCode() : 0);
        return result;
    }

    public List<ProPro> getProProsById() {
        return proProsById;
    }

    public void setProProsById(List<ProPro> proProsById) {
        this.proProsById = proProsById;
    }
}
