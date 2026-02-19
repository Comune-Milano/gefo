package it.dmi.dmifonbe.dmifonamm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "amm_per", schema = "dmifonamm", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class AmmPer extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PERMESSO_SEQUENCE")
    @SequenceGenerator(sequenceName = "amm_per_id_seq", schema = "dmifonamm", allocationSize = 1, name = "ID_PERMESSO_SEQUENCE")
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_ruo", nullable = false)
    private Long idRuo;

    @Basic
    @Column(name = "id_fun", nullable = false)
    private Long idFun;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruo", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AmmRuo ammRuoByIdRuo;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fun", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AmmFun ammFunByIdFun;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getIdRuo() {
        return idRuo;
    }

    public void setIdRuo(Long idRuo) {
        this.idRuo = idRuo;
    }

    public Long getIdFun() {
        return idFun;
    }

    public void setIdFun(Long idFun) {
        this.idFun = idFun;
    }

    public AmmRuo getAmmRuoByIdRuo() {
        return ammRuoByIdRuo;
    }

    public void setAmmRuoByIdRuo(AmmRuo ammRuoByIdRuo) {
        this.ammRuoByIdRuo = ammRuoByIdRuo;
    }

    public AmmFun getAmmFunByIdFun() {
        return ammFunByIdFun;
    }

    public void setAmmFunByIdFun(AmmFun ammFunByIdFun) {
        this.ammFunByIdFun = ammFunByIdFun;
    }
}
