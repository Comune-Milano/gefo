package it.dmi.dmifonbe.dmifonamm.entities;

import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "amm_ent", schema = "dmifonamm", catalog = "dmifon")
public class AmmEnt extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "codent", nullable = true, length = 16)
    private String codent;

    @Basic
    @HtmlEscape
    @Column(name = "nome", nullable = true, length = 100)
    private String nome;

    @Basic
    @HtmlEscape
    @Column(name = "descrizione", nullable = true, length = 250)
    private String descrizione;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodent() {
        return codent;
    }

    public void setCodent(String codent) {
        this.codent = codent;
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
}
