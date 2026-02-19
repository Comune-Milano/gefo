package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_ddra", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProDdra extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "codddra", nullable = false, length = 25)
    private String codddra;

    @Basic
    @HtmlEscape
    @Column(name = "desddra", nullable = false, length = 250)
    private String desddra;

    @Basic
    @Column(name = "dtaddra", nullable = false)
    private Date dtaddra;

    @Basic
    @HtmlEscape
    @Column(name = "notddra", nullable = true, length = 250)
    private String notddra;

    @OneToMany(mappedBy = "proDdraByIdDdra", fetch = FetchType.LAZY)
    @Valid
    private List<ProDdr> proDdrsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodddra() {
        return codddra;
    }

    public void setCodddra(String codddra) {
        this.codddra = codddra;
    }

    public String getDesddra() {
        return desddra;
    }

    public void setDesddra(String desddra) {
        this.desddra = desddra;
    }

    public Date getDtaddra() {
        return dtaddra;
    }

    public void setDtaddra(Date dtaddra) {
        this.dtaddra = dtaddra;
    }

    public String getNotddra() {
        return notddra;
    }

    public void setNotddra(String notddra) {
        this.notddra = notddra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProDdra proDdra = (ProDdra) o;

        if (id != proDdra.id) return false;
        if (codddra != null ? !codddra.equals(proDdra.codddra) : proDdra.codddra != null) return false;
        if (desddra != null ? !desddra.equals(proDdra.desddra) : proDdra.desddra != null) return false;
        if (dtaddra != null ? !dtaddra.equals(proDdra.dtaddra) : proDdra.dtaddra != null) return false;
        if (notddra != null ? !notddra.equals(proDdra.notddra) : proDdra.notddra != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (codddra != null ? codddra.hashCode() : 0);
        result = 31 * result + (desddra != null ? desddra.hashCode() : 0);
        result = 31 * result + (dtaddra != null ? dtaddra.hashCode() : 0);
        result = 31 * result + (notddra != null ? notddra.hashCode() : 0);
        return result;
    }

    public List<ProDdr> getProDdrsById() {
        return proDdrsById;
    }

    public void setProDdrsById(List<ProDdr> proDdrsById) {
        this.proDdrsById = proDdrsById;
    }
}
