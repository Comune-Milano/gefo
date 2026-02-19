package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_stafin", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProStafin extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "dessta", nullable = false, length = 50)
    private String dessta;

    @Basic
    @HtmlEscape
    @Column(name = "tipsta", nullable = false, length = 1)
    private String tipsta;

    @OneToMany(mappedBy = "proStafinByIdStafin", fetch = FetchType.LAZY)
    @Valid
    private List<ProPro> proProsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDessta() {
        return dessta;
    }

    public void setDessta(String dessta) {
        this.dessta = dessta;
    }

    public String getTipsta() {
        return tipsta;
    }

    public void setTipsta(String tipsta) {
        this.tipsta = tipsta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProStafin proStafin = (ProStafin) o;

        if (id != proStafin.id) return false;
        if (dessta != null ? !dessta.equals(proStafin.dessta) : proStafin.dessta != null) return false;
        if (tipsta != null ? !tipsta.equals(proStafin.tipsta) : proStafin.tipsta != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dessta != null ? dessta.hashCode() : 0);
        result = 31 * result + (tipsta != null ? tipsta.hashCode() : 0);
        return result;
    }

    public List<ProPro> getProProsById() {
        return proProsById;
    }

    public void setProProsById(List<ProPro> proProsById) {
        this.proProsById = proProsById;
    }
}
