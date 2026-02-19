package it.dmi.dmifonbe.dmifonamm.entities;

import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "amm_out", schema = "dmifonamm", catalog = "dmifon")
public class AmmOut extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "id_ela", nullable = false)
    private Long idEla;

    @Basic
    @HtmlEscape
    @Column(name = "desout", nullable = false, length = 250)
    private String desout;

    @Basic
    @HtmlEscape
    @Column(name = "tipout", nullable = true, length = 3)
    private String tipout;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ela", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AmmEla ammElaByIdEla;

    @Valid
    @OneToMany(mappedBy = "ammOutByIdOut", fetch = FetchType.LAZY)
    private List<AmmRig> ammRigsById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIdEla() {
        return idEla;
    }

    public void setIdEla(Long idEla) {
        this.idEla = idEla;
    }

    public String getDesout() {
        return desout;
    }

    public void setDesout(String desout) {
        this.desout = desout;
    }

    public String getTipout() {
        return tipout;
    }

    public void setTipout(String tipout) {
        this.tipout = tipout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmmOut ammOut = (AmmOut) o;

        if (id != null ? !id.equals(ammOut.id) : ammOut.id != null) return false;
        if (idEla != null ? !idEla.equals(ammOut.idEla) : ammOut.idEla != null) return false;
        if (desout != null ? !desout.equals(ammOut.desout) : ammOut.desout != null) return false;
        if (tipout != null ? !tipout.equals(ammOut.tipout) : ammOut.tipout != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idEla != null ? idEla.hashCode() : 0);
        result = 31 * result + (desout != null ? desout.hashCode() : 0);
        result = 31 * result + (tipout != null ? tipout.hashCode() : 0);
        return result;
    }

    public AmmEla getAmmElaByIdEla() {
        return ammElaByIdEla;
    }

    public void setAmmElaByIdEla(AmmEla ammElaByIdEla) {
        this.ammElaByIdEla = ammElaByIdEla;
    }

    public List<AmmRig> getAmmRigsById() {
        return ammRigsById;
    }

    public void setAmmRigsById(List<AmmRig> ammRigsById) {
        this.ammRigsById = ammRigsById;
    }
}
