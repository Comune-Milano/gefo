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
@Table(name = "amm_dir", schema = "dmifonamm", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class AmmDir extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_DIREZIONE_SEQUENCE")
    @SequenceGenerator(sequenceName = "prf_dir_id_seq", schema = "dmifonamm", allocationSize = 1, name = "ID_DIREZIONE_SEQUENCE")
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "coddir", nullable = true, length = 25)
    private String coddir;

    @Basic
    @HtmlEscape
    @Column(name = "desdir", nullable = true, length = 250)
    private String desdir;

    @Valid
    @OneToMany(mappedBy = "ammDirByIdDir", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AmmUteruo> ammUteruosById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoddir() {
        return coddir;
    }

    public void setCoddir(String coddir) {
        this.coddir = coddir;
    }

    public String getDesdir() {
        return desdir;
    }

    public void setDesdir(String desdir) {
        this.desdir = desdir;
    }

    public List<AmmUteruo> getAmmUteruosById() {
        return ammUteruosById;
    }

    public void setAmmUteruosById(List<AmmUteruo> ammUteruosById) {
        this.ammUteruosById = ammUteruosById;
    }
}
