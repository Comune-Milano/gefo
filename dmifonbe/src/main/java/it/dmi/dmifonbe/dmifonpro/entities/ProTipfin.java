package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_tipfin", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProTipfin extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "livuno", nullable = false)
    private Long livuno;

    @Basic
    @Column(name = "livdue", nullable = false)
    private Long livdue;

    @Basic
    @Column(name = "livtre", nullable = false)
    private Long livtre;

    @Basic
    @Column(name = "livqua", nullable = false)
    private Long livqua;

    @Basic
    @Column(name = "livcin", nullable = false)
    private Long livcin;

    @Basic
    @HtmlEscape
    @Column(name = "codtipfin", nullable = false, length = 25)
    private String codtipfin;

    @Basic
    @HtmlEscape
    @Column(name = "destipfin", nullable = false, length = 250)
    private String destipfin;

    @Basic
    @Column(name = "livsei", nullable = false)
    private Long livsei;

    @OneToMany(mappedBy = "proTipfinByIdTipfin", fetch = FetchType.LAZY)
    @Valid
    private List<ProBan> proBansById;

    @OneToMany(mappedBy = "proTipfinByIdTipfinda", fetch = FetchType.LAZY)
    @Valid
    private List<ProMacpro> proMacprosById;

    @OneToMany(mappedBy = "proTipfinByIdTipfina", fetch = FetchType.LAZY)
    @Valid
    private List<ProMacpro> proMacprosById_0;

    @OneToMany(mappedBy = "proTipfinByIdTipfin", fetch = FetchType.LAZY)
    @Valid
    private List<ProPro> proProsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getLivuno() {
        return livuno;
    }

    public void setLivuno(Long livuno) {
        this.livuno = livuno;
    }

    public Long getLivdue() {
        return livdue;
    }

    public void setLivdue(Long livdue) {
        this.livdue = livdue;
    }

    public Long getLivtre() {
        return livtre;
    }

    public void setLivtre(Long livtre) {
        this.livtre = livtre;
    }

    public Long getLivqua() {
        return livqua;
    }

    public void setLivqua(Long livqua) {
        this.livqua = livqua;
    }

    public Long getLivcin() {
        return livcin;
    }

    public void setLivcin(Long livcin) {
        this.livcin = livcin;
    }

    public String getCodtipfin() {
        return codtipfin;
    }

    public void setCodtipfin(String codtipfin) {
        this.codtipfin = codtipfin;
    }

    public String getDestipfin() {
        return destipfin;
    }

    public void setDestipfin(String destipfin) {
        this.destipfin = destipfin;
    }

    public Long getLivsei() {
        return livsei;
    }

    public void setLivsei(Long livsei) {
        this.livsei = livsei;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProTipfin proTipfin = (ProTipfin) o;

        if (id != proTipfin.id) return false;
        if (livuno != proTipfin.livuno) return false;
        if (livdue != proTipfin.livdue) return false;
        if (livtre != proTipfin.livtre) return false;
        if (livqua != proTipfin.livqua) return false;
        if (livcin != proTipfin.livcin) return false;
        if (livsei != proTipfin.livsei) return false;
        if (codtipfin != null ? !codtipfin.equals(proTipfin.codtipfin) : proTipfin.codtipfin != null) return false;
        if (destipfin != null ? !destipfin.equals(proTipfin.destipfin) : proTipfin.destipfin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (livuno ^ (livuno >>> 32));
        result = 31 * result + (int) (livdue ^ (livdue >>> 32));
        result = 31 * result + (int) (livtre ^ (livtre >>> 32));
        result = 31 * result + (int) (livqua ^ (livqua >>> 32));
        result = 31 * result + (int) (livcin ^ (livcin >>> 32));
        result = 31 * result + (codtipfin != null ? codtipfin.hashCode() : 0);
        result = 31 * result + (destipfin != null ? destipfin.hashCode() : 0);
        result = 31 * result + (int) (livsei ^ (livsei >>> 32));
        return result;
    }

    public List<ProBan> getProBansById() {
        return proBansById;
    }

    public void setProBansById(List<ProBan> proBansById) {
        this.proBansById = proBansById;
    }

    public List<ProMacpro> getProMacprosById() {
        return proMacprosById;
    }

    public void setProMacprosById(List<ProMacpro> proMacprosById) {
        this.proMacprosById = proMacprosById;
    }

    public List<ProMacpro> getProMacprosById_0() {
        return proMacprosById_0;
    }

    public void setProMacprosById_0(List<ProMacpro> proMacprosById_0) {
        this.proMacprosById_0 = proMacprosById_0;
    }

    public List<ProPro> getProProsById() {
        return proProsById;
    }

    public void setProProsById(List<ProPro> proProsById) {
        this.proProsById = proProsById;
    }
}
