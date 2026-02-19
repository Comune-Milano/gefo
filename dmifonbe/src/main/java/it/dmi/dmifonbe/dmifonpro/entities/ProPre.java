package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Table(name = "pro_pre", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProPre extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_pro", nullable = false)
    private Long idPro;

    @Basic
    @Column(name = "dtapre", nullable = false)
    private Date dtapre;

    @Basic
    @Column(name = "imppre", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo imppre deve essere superiore o uguale a 0")
    private BigDecimal imppre;

    @Basic
    @HtmlEscape
    @Column(name = "notpre", nullable = true, length = 250)
    private String notpre;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_pro", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProPro proProByIdPro;

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

    public Date getDtapre() {
        return dtapre;
    }

    public void setDtapre(Date dtapre) {
        this.dtapre = dtapre;
    }

    public BigDecimal getImppre() {
        return imppre;
    }

    public void setImppre(BigDecimal imppre) {
        this.imppre = imppre;
    }

    public String getNotpre() {
        return notpre;
    }

    public void setNotpre(String notpre) {
        this.notpre = notpre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProPre proPre = (ProPre) o;

        if (id != proPre.id) return false;
        if (idPro != proPre.idPro) return false;
        if (dtapre != null ? !dtapre.equals(proPre.dtapre) : proPre.dtapre != null) return false;
        if (imppre != null ? !imppre.equals(proPre.imppre) : proPre.imppre != null) return false;
        if (notpre != null ? !notpre.equals(proPre.notpre) : proPre.notpre != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (idPro ^ (idPro >>> 32));
        result = 31 * result + (dtapre != null ? dtapre.hashCode() : 0);
        result = 31 * result + (imppre != null ? imppre.hashCode() : 0);
        result = 31 * result + (notpre != null ? notpre.hashCode() : 0);
        return result;
    }

    public ProPro getProProByIdPro() {
        return proProByIdPro;
    }

    public void setProProByIdPro(ProPro proProByIdPro) {
        this.proProByIdPro = proProByIdPro;
    }
}
