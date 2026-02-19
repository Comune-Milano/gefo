package it.dmi.dmifonbe.dmifonamm.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "amm_all", schema = "dmifonamm", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class AmmAll extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tipent", nullable = false, length = 4)
    @HtmlEscape
    private String tipent;

    @Column(name = "ident", nullable = false)
    private Long ident;

    @Column(name = "nomfil", nullable = false, length = 100)
    @HtmlEscape
    private String nomfil;

    @Column(name = "notfil", nullable = true, length = 250)
    @HtmlEscape
    private String notfil;

    @Column(name = "id_fil", nullable = true)
    private Long idFil;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_fil", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Valid
    private AmmFil ammFilByIdFil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipent() {
        return tipent;
    }

    public void setTipent(String tipent) {
        this.tipent = tipent;
    }

    public Long getIdent() {
        return ident;
    }

    public void setIdent(Long ident) {
        this.ident = ident;
    }

    public void setIdent(long ident) {
        this.ident = ident;
    }

    public String getNomfil() {
        return nomfil;
    }

    public void setNomfil(String nomfil) {
        this.nomfil = nomfil;
    }

    public String getNotfil() {
        return notfil;
    }

    public void setNotfil(String notfil) {
        this.notfil = notfil;
    }

    public Long getIdFil() {
        return idFil;
    }

    public void setIdFil(Long idFil) {
        this.idFil = idFil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmmAll ammAll = (AmmAll) o;

        if (id != ammAll.id) return false;
        if (ident != ammAll.ident) return false;
        if (tipent != null ? !tipent.equals(ammAll.tipent) : ammAll.tipent != null) return false;
        if (nomfil != null ? !nomfil.equals(ammAll.nomfil) : ammAll.nomfil != null) return false;
        if (notfil != null ? !notfil.equals(ammAll.notfil) : ammAll.notfil != null) return false;
        if (idFil != null ? !idFil.equals(ammAll.idFil) : ammAll.idFil != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tipent != null ? tipent.hashCode() : 0);
        result = 31 * result + (int) (ident ^ (ident >>> 32));
        result = 31 * result + (nomfil != null ? nomfil.hashCode() : 0);
        result = 31 * result + (notfil != null ? notfil.hashCode() : 0);
        result = 31 * result + (idFil != null ? idFil.hashCode() : 0);
        return result;
    }

    public AmmFil getAmmFilByIdFil() {
        return ammFilByIdFil;
    }

    public void setAmmFilByIdFil(AmmFil ammFilByIdFil) {
        this.ammFilByIdFil = ammFilByIdFil;
    }
}
