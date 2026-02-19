package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_detcon", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProDetcon extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_pro", nullable = false)
    private Long idPro;

    @Basic
    @HtmlEscape
    @Column(name = "tipent", nullable = false, length = 4)
    private String tipent;

    @Basic
    @HtmlEscape
    @Column(name = "codentcon", nullable = false, length = 25)
    private String codentcon;

    @Basic
    @HtmlEscape
    @Column(name = "tipentcol", nullable = true, length = 4)
    private String tipentcol;

    @Basic
    @HtmlEscape
    @Column(name = "codentcol", nullable = true, length = 25)
    private String codentcol;

    @Basic
    @HtmlEscape
    @Column(name = "notent", nullable = true, length = 250)
    private String notent;

    @Basic
    @Column(name = "id_tipimp", nullable = true)
    private Long idTipimp;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_pro", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProPro proProByIdPro;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tipimp", referencedColumnName = "id", insertable = false, updatable = false)
    private ProTipimp proTipimpByIdTipimp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getIdPro() {
        return idPro;
    }

    public void setIdPro(Long idPro) {
        this.idPro = idPro;
    }

    public String getTipent() {
        return tipent;
    }

    public void setTipent(String tipent) {
        this.tipent = tipent;
    }

    public String getCodentcon() {
        return codentcon;
    }

    public void setCodentcon(String codentcon) {
        this.codentcon = codentcon;
    }

    public String getTipentcol() {
        return tipentcol;
    }

    public void setTipentcol(String tipentcol) {
        this.tipentcol = tipentcol;
    }

    public String getCodentcol() {
        return codentcol;
    }

    public void setCodentcol(String codentcol) {
        this.codentcol = codentcol;
    }

    public String getNotent() {
        return notent;
    }

    public void setNotent(String notent) {
        this.notent = notent;
    }

    public Long getIdTipimp() {
        return idTipimp;
    }

    public void setIdTipimp(Long idTipimp) {
        this.idTipimp = idTipimp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProDetcon proDetcon = (ProDetcon) o;

        if (id != proDetcon.id) return false;
        if (idPro != proDetcon.idPro) return false;
        if (tipent != null ? !tipent.equals(proDetcon.tipent) : proDetcon.tipent != null) return false;
        if (codentcon != null ? !codentcon.equals(proDetcon.codentcon) : proDetcon.codentcon != null) return false;
        if (tipentcol != null ? !tipentcol.equals(proDetcon.tipentcol) : proDetcon.tipentcol != null) return false;
        if (codentcol != null ? !codentcol.equals(proDetcon.codentcol) : proDetcon.codentcol != null) return false;
        if (notent != null ? !notent.equals(proDetcon.notent) : proDetcon.notent != null) return false;
        if (idTipimp != null ? !idTipimp.equals(proDetcon.idTipimp) : proDetcon.idTipimp != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (idPro ^ (idPro >>> 32));
        result = 31 * result + (tipent != null ? tipent.hashCode() : 0);
        result = 31 * result + (codentcon != null ? codentcon.hashCode() : 0);
        result = 31 * result + (tipentcol != null ? tipentcol.hashCode() : 0);
        result = 31 * result + (codentcol != null ? codentcol.hashCode() : 0);
        result = 31 * result + (notent != null ? notent.hashCode() : 0);
        result = 31 * result + (idTipimp != null ? idTipimp.hashCode() : 0);
        return result;
    }

    public ProPro getProProByIdPro() {
        return proProByIdPro;
    }

    public void setProProByIdPro(ProPro proProByIdPro) {
        this.proProByIdPro = proProByIdPro;
    }

    public ProTipimp getProTipimpByIdTipimp() {
        return proTipimpByIdTipimp;
    }

    public void setProTipimpByIdTipimp(ProTipimp proTipimpByIdTipimp) {
        this.proTipimpByIdTipimp = proTipimpByIdTipimp;
    }
}
