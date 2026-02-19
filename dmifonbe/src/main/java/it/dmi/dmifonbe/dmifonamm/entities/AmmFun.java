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
@Table(name = "amm_fun", schema = "dmifonamm", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class AmmFun extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_FUNZIONE_SEQUENCE")
    @SequenceGenerator(sequenceName = "amm_fun_id_seq", schema = "dmifonamm", allocationSize = 1, name = "ID_FUNZIONE_SEQUENCE")
    @Column(name = "id", nullable = false)
    @Id
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Basic
    @HtmlEscape
    @Column(name = "descrizione", nullable = false, length = 250)
    private String descrizione;

    @Valid
    @OneToMany(mappedBy = "ammFunByIdFun", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AmmPer> ammPersById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<AmmPer> getAmmPersById() {
        return ammPersById;
    }

    public void setAmmPersById(List<AmmPer> ammPersById) {
        this.ammPersById = ammPersById;
    }
}
