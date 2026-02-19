package it.dmi.dmifonbe.dmifonamm.entities;

import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "amm_rig", schema = "dmifonamm", catalog = "dmifon")
public class AmmRig extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "id_out", nullable = false)
    private Long idOut;

    @Basic
    @Column(name = "nrorig", nullable = false)
    private Long nrorig;

    @Basic
    @Column(name = "prgrig", nullable = false)
    private Integer prgrig;

    @Basic
    @Column(name = "tesrig", nullable = false, length = 250)
    private String tesrig;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_out", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AmmOut ammOutByIdOut;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIdOut() {
        return idOut;
    }

    public void setIdOut(Long idOut) {
        this.idOut = idOut;
    }

    public Long getNrorig() {
        return nrorig;
    }

    public void setNrorig(Long nrorig) {
        this.nrorig = nrorig;
    }

    public Integer getPrgrig() {
        return prgrig;
    }

    public void setPrgrig(Integer prgrig) {
        this.prgrig = prgrig;
    }

    public String getTesrig() {
        return tesrig;
    }

    public void setTesrig(String tesrig) {
        this.tesrig = tesrig;
    }

    public AmmOut getAmmOutByIdOut() {
        return ammOutByIdOut;
    }

    public void setAmmOutByIdOut(AmmOut ammOutByIdOut) {
        this.ammOutByIdOut = ammOutByIdOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmmRig ammRig = (AmmRig) o;

        if (id != null ? !id.equals(ammRig.id) : ammRig.id != null) return false;
        if (idOut != null ? !idOut.equals(ammRig.idOut) : ammRig.idOut != null) return false;
        if (nrorig != null ? !nrorig.equals(ammRig.nrorig) : ammRig.nrorig != null) return false;
        if (prgrig != null ? !prgrig.equals(ammRig.prgrig) : ammRig.prgrig != null) return false;
        if (tesrig != null ? !tesrig.equals(ammRig.tesrig) : ammRig.tesrig != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idOut != null ? idOut.hashCode() : 0);
        result = 31 * result + (nrorig != null ? nrorig.hashCode() : 0);
        result = 31 * result + (prgrig != null ? prgrig.hashCode() : 0);
        result = 31 * result + (tesrig != null ? tesrig.hashCode() : 0);
        return result;
    }
}
