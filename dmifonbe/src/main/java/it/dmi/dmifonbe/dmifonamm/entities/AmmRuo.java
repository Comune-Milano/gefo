package it.dmi.dmifonbe.dmifonamm.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "amm_ruo", schema = "dmifonamm", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class AmmRuo extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_RUOLO_SEQUENCE")
    @SequenceGenerator(sequenceName = "amm_ruo_id_seq", schema = "dmifonamm", allocationSize = 1, name = "ID_RUOLO_SEQUENCE")
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "codruo", nullable = false, length = 15)
    @HtmlEscape
    private String codruo;

    @Basic
    @Column(name = "desruo", nullable = false, length = 250)
    @HtmlEscape
    private String desruo;

    @Valid
    @OneToMany(mappedBy = "ammRuoByIdRuo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AmmPer> ammPersById;

    @Valid
    @OneToMany(mappedBy = "ammRuoByIdRuo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AmmUteruo> ammUteruosById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodruo() {
        return codruo;
    }

    public void setCodruo(String codruo) {
        this.codruo = codruo;
    }

    public String getDesruo() {
        return desruo;
    }

    public void setDesruo(String desruo) {
        this.desruo = desruo;
    }

    public List<AmmPer> getAmmPersById() {
        return ammPersById;
    }

    public void setAmmPersById(List<AmmPer> ammPersById) {
        this.ammPersById = ammPersById;
    }

    public List<AmmUteruo> getAmmUteruosById() {
        return ammUteruosById;
    }

    public void setAmmUteruosById(List<AmmUteruo> ammUteruosById) {
        this.ammUteruosById = ammUteruosById;
    }
}
