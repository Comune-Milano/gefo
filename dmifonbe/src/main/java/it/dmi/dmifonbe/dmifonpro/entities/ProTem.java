package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_tem", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProTem extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "destem", nullable = false, length = 250)
    private String destem;

    @Basic
    @HtmlEscape
    @Column(name = "codtem", nullable = false, length = 25)
    private String codtem;

    @Basic
    @Column(name = "livuno", nullable = false)
    private int livuno;

    @Basic
    @Column(name = "livdue", nullable = false)
    private int livdue;

    @OneToMany(mappedBy = "proTemByIdTem", fetch = FetchType.LAZY)
    @Valid
    private List<ProBan> proBansById;

    @OneToMany(mappedBy = "proTemByIdTem", fetch = FetchType.LAZY)
    @Valid
    private List<ProPro> proProsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestem() {
        return destem;
    }

    public void setDestem(String destem) {
        this.destem = destem;
    }

    public String getCodtem() {
        return codtem;
    }

    public void setCodtem(String codtem) {
        this.codtem = codtem;
    }

    public int getLivuno() {
        return livuno;
    }

    public void setLivuno(int livuno) {
        this.livuno = livuno;
    }

    public int getLivdue() {
        return livdue;
    }

    public void setLivdue(int livdue) {
        this.livdue = livdue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProTem proTem = (ProTem) o;

        if (id != proTem.id) return false;
        if (destem != null ? !destem.equals(proTem.destem) : proTem.destem != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (destem != null ? destem.hashCode() : 0);
        return result;
    }

    public List<ProBan> getProBansById() {
        return proBansById;
    }

    public void setProBansById(List<ProBan> proBansById) {
        this.proBansById = proBansById;
    }

    public List<ProPro> getProProsById() {
        return proProsById;
    }

    public void setProProsById(List<ProPro> proProsById) {
        this.proProsById = proProsById;
    }
}
