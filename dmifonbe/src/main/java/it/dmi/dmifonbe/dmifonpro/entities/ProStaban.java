package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_staban", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProStaban extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "dessta", nullable = false, length = 50)
    private String dessta;

    @OneToMany(mappedBy = "proStabanByIdStaban", fetch = FetchType.LAZY)
    @Valid
    private List<ProBan> proBansById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProStaban proStaban = (ProStaban) o;

        if (id != proStaban.id) return false;
        if (dessta != null ? !dessta.equals(proStaban.dessta) : proStaban.dessta != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dessta != null ? dessta.hashCode() : 0);
        return result;
    }

    public List<ProBan> getProBansById() {
        return proBansById;
    }

    public void setProBansById(List<ProBan> proBansById) {
        this.proBansById = proBansById;
    }
}
