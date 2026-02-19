package it.dmi.dmifonbe.dmifonamm.entities;

import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "amm_ela", schema = "dmifonamm", catalog = "dmifon")
public class AmmEla extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @HtmlEscape
    @Column(name = "desela", nullable = false, length = 250)
    private String desela;

    @Basic
    @HtmlEscape
    @Column(name = "staela", nullable = false, length = 1)
    private String staela;

    @Basic
    @Column(name = "dtaini", nullable = false)
    private Date dtaini;

    @Basic
    @Column(name = "dtafin", nullable = true)
    private Date dtafin;

    @Valid
    @OneToMany(mappedBy = "ammElaByIdEla", fetch = FetchType.LAZY)
    private List<AmmOut> ammOutsById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesela() {
        return desela;
    }

    public void setDesela(String desela) {
        this.desela = desela;
    }

    public String getStaela() {
        return staela;
    }

    public void setStaela(String staela) {
        this.staela = staela;
    }

    public Date getDtaini() {
        return dtaini;
    }

    public void setDtaini(Date dtaini) {
        this.dtaini = dtaini;
    }

    public Date getDtafin() {
        return dtafin;
    }

    public void setDtafin(Date dtafin) {
        this.dtafin = dtafin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmmEla ammEla = (AmmEla) o;

        if (id != null ? !id.equals(ammEla.id) : ammEla.id != null) return false;
        if (desela != null ? !desela.equals(ammEla.desela) : ammEla.desela != null) return false;
        if (staela != null ? !staela.equals(ammEla.staela) : ammEla.staela != null) return false;
        if (dtaini != null ? !dtaini.equals(ammEla.dtaini) : ammEla.dtaini != null) return false;
        if (dtafin != null ? !dtafin.equals(ammEla.dtafin) : ammEla.dtafin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (desela != null ? desela.hashCode() : 0);
        result = 31 * result + (staela != null ? staela.hashCode() : 0);
        result = 31 * result + (dtaini != null ? dtaini.hashCode() : 0);
        result = 31 * result + (dtafin != null ? dtafin.hashCode() : 0);
        return result;
    }

    public List<AmmOut> getAmmOutsById() {
        return ammOutsById;
    }

    public void setAmmOutsById(List<AmmOut> ammOutsById) {
        this.ammOutsById = ammOutsById;
    }
}
