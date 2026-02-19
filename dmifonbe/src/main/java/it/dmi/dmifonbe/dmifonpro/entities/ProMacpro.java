package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_macpro", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProMacpro extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "codmacpro", nullable = false, length = 25)
    private String codmacpro;

    @Basic
    @HtmlEscape
    @Column(name = "desmacpro", nullable = false, length = 250)
    private String desmacpro;

    @Basic
    @Column(name = "id_tipfinda", nullable = true)
    private Long idTipfinda;

    @Basic
    @Column(name = "id_tipfina", nullable = true)
    private Long idTipfina;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tipfinda", referencedColumnName = "id", insertable = false, updatable = false)
    private ProTipfin proTipfinByIdTipfinda;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tipfina", referencedColumnName = "id", insertable = false, updatable = false)
    private ProTipfin proTipfinByIdTipfina;

    @OneToMany(mappedBy = "proMacproByIdMacpro", fetch = FetchType.LAZY)
    @Valid
    private List<ProPro> proProsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodmacpro() {
        return codmacpro;
    }

    public void setCodmacpro(String codmacpro) {
        this.codmacpro = codmacpro;
    }

    public String getDesmacpro() {
        return desmacpro;
    }

    public void setDesmacpro(String desmacpro) {
        this.desmacpro = desmacpro;
    }

    public Long getIdTipfinda() {
        return idTipfinda;
    }

    public void setIdTipfinda(Long idTipfinda) {
        this.idTipfinda = idTipfinda;
    }

    public Long getIdTipfina() {
        return idTipfina;
    }

    public void setIdTipfina(Long idTipfina) {
        this.idTipfina = idTipfina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProMacpro proMacpro = (ProMacpro) o;

        if (id != proMacpro.id) return false;
        if (codmacpro != null ? !codmacpro.equals(proMacpro.codmacpro) : proMacpro.codmacpro != null) return false;
        if (desmacpro != null ? !desmacpro.equals(proMacpro.desmacpro) : proMacpro.desmacpro != null) return false;
        if (idTipfinda != null ? !idTipfinda.equals(proMacpro.idTipfinda) : proMacpro.idTipfinda != null) return false;
        if (idTipfina != null ? !idTipfina.equals(proMacpro.idTipfina) : proMacpro.idTipfina != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (codmacpro != null ? codmacpro.hashCode() : 0);
        result = 31 * result + (desmacpro != null ? desmacpro.hashCode() : 0);
        result = 31 * result + (idTipfinda != null ? idTipfinda.hashCode() : 0);
        result = 31 * result + (idTipfina != null ? idTipfina.hashCode() : 0);
        return result;
    }

    public ProTipfin getProTipfinByIdTipfinda() {
        return proTipfinByIdTipfinda;
    }

    public void setProTipfinByIdTipfinda(ProTipfin proTipfinByIdTipfinda) {
        this.proTipfinByIdTipfinda = proTipfinByIdTipfinda;
    }

    public ProTipfin getProTipfinByIdTipfina() {
        return proTipfinByIdTipfina;
    }

    public void setProTipfinByIdTipfina(ProTipfin proTipfinByIdTipfina) {
        this.proTipfinByIdTipfina = proTipfinByIdTipfina;
    }

    public List<ProPro> getProProsById() {
        return proProsById;
    }

    public void setProProsById(List<ProPro> proProsById) {
        this.proProsById = proProsById;
    }
}
