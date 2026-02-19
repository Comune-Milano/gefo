package it.dmi.dmifonbe.dmifonamm.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "amm_ute", schema = "dmifonamm", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class AmmUte extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_UTENTE_SEQUENCE")
    @SequenceGenerator(sequenceName = "amm_ute_id_seq", schema = "dmifonamm", allocationSize = 1, name = "ID_UTENTE_SEQUENCE")
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @NotNull(message = "Username non può essere null")
    @HtmlEscape
    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Basic
    @NotNull(message = "Nome non può essere null")
    @HtmlEscape
    @Column(name = "nome", length = 100)
    private String nome;

    @Basic
    @HtmlEscape
    @NotNull(message = "Cognome non può essere null")
    @Column(name = "cognome", length = 100)
    private String cognome;

    @Basic
    @HtmlEscape
    @Column(name = "email", length = 100)
    private String email;

    @Basic
    @HtmlEscape
    @Column(name = "emailalt", length = 100)
    private String emailalt;

    @Basic
    @Column(name = "abilitato")
    private boolean abilitato;

    @Valid
    @OneToMany(mappedBy = "ammUteByIdUte", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AmmUteruo> ammUteruosById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailalt() {
        return emailalt;
    }

    public void setEmailalt(String emailalt) {
        this.emailalt = emailalt;
    }

    public Boolean getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }

    public List<AmmUteruo> getAmmUteruosById() {
        return ammUteruosById;
    }

    public void setAmmUteruosById(List<AmmUteruo> ammUteruosById) {
        this.ammUteruosById = ammUteruosById;
    }
}
