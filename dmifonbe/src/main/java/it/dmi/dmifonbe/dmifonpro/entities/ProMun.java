package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_mun", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProMun extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "desmun", nullable = false, length = 50)
    private String desmun;

    @OneToMany(mappedBy = "proMunByIdMun", fetch = FetchType.LAZY)
    @Valid
    private List<ProPro> proProsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesmun() {
        return desmun;
    }

    public void setDesmun(String desmun) {
        this.desmun = desmun;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProMun proMun = (ProMun) o;

        if (id != proMun.id) return false;
        if (desmun != null ? !desmun.equals(proMun.desmun) : proMun.desmun != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (desmun != null ? desmun.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProMun{" + "id=" + id + ", desmun='" + desmun + '\'' + ", proProsById=" + proProsById + '}';
    }

    public List<ProPro> getProProsById() {
        return proProsById;
    }

    public void setProProsById(List<ProPro> proProsById) {
        this.proProsById = proProsById;
    }
}
