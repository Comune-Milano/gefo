package it.dmi.dmifonbe.dmifonpro.entities;

import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "pro_entcon", schema = "dmifonpro", catalog = "dmifon")
public class ProEntcon extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "tipent", nullable = false, length = 4)
    private String tipent;

    @Basic
    @Column(name = "codentcon", nullable = false, length = 25)
    private String codentcon;

    @Basic
    @Column(name = "desimp", nullable = true, length = 250)
    private String desimp;

    @Basic
    @Column(name = "idcap", nullable = true, length = 25)
    private String idcap;

    @Basic
    @Column(name = "anncmp", nullable = false)
    private int anncmp;

    @Basic
    @Column(name = "impass", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impass deve essere superiore o uguale a 0")
    private BigDecimal impass;

    @Basic
    @Column(name = "impliq", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impliq deve essere superiore o uguale a 0")
    private BigDecimal impliq;

    @Basic
    @Column(name = "impman", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impman deve essere superiore o uguale a 0")
    private BigDecimal impman;

    @Basic
    @Column(name = "despdd", nullable = true, length = 250)
    private String despdd;

    @Basic
    @Column(name = "idcro", nullable = true, length = 25)
    private String idcro;

    @Basic
    @Column(name = "codcup", nullable = true, length = 15)
    private String codcup;

    @Basic
    @Column(name = "codcig", nullable = true, length = 10)
    private String codcig;

    @Basic
    @Column(name = "nroconapp", nullable = false)
    private Long nroconapp;

    @Basic
    @Column(name = "impmaneme", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impmaneme deve essere superiore o uguale a 0")
    private BigDecimal impmaneme;

    @Basic
    @Column(name = "impeco", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impeco deve essere superiore o uguale a 0")
    private BigDecimal impeco;

    @Basic
    @Column(name = "codgami", nullable = true, length = 20)
    private String codgami;

    public int getId() {
        return id;
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

    public String getCodentcon() {
        return codentcon;
    }

    public void setCodentcon(String codentcon) {
        this.codentcon = codentcon;
    }

    public String getDesimp() {
        return desimp;
    }

    public void setDesimp(String desimp) {
        this.desimp = desimp;
    }

    public String getIdcap() {
        return idcap;
    }

    public void setIdcap(String idcap) {
        this.idcap = idcap;
    }

    public int getAnncmp() {
        return anncmp;
    }

    public void setAnncmp(int anncmp) {
        this.anncmp = anncmp;
    }

    public BigDecimal getImpass() {
        return impass;
    }

    public void setImpass(BigDecimal impass) {
        this.impass = impass;
    }

    public BigDecimal getImpliq() {
        return impliq;
    }

    public void setImpliq(BigDecimal impliq) {
        this.impliq = impliq;
    }

    public BigDecimal getImpman() {
        return impman;
    }

    public void setImpman(BigDecimal impman) {
        this.impman = impman;
    }

    public String getDespdd() {
        return despdd;
    }

    public void setDespdd(String despdd) {
        this.despdd = despdd;
    }

    public String getIdcro() {
        return idcro;
    }

    public void setIdcro(String idcro) {
        this.idcro = idcro;
    }

    public String getCodcup() {
        return codcup;
    }

    public void setCodcup(String codcup) {
        this.codcup = codcup;
    }

    public String getCodcig() {
        return codcig;
    }

    public void setCodcig(String codcig) {
        this.codcig = codcig;
    }

    public Long getNroconapp() {
        return nroconapp;
    }

    public void setNroconapp(Long nroconapp) {
        this.nroconapp = nroconapp;
    }

    public BigDecimal getImpmaneme() {
        return impmaneme;
    }

    public void setImpmaneme(BigDecimal impmaneme) {
        this.impmaneme = impmaneme;
    }

    public BigDecimal getImpeco() {
        return impeco;
    }

    public void setImpeco(BigDecimal impeco) {
        this.impeco = impeco;
    }

    public String getCodgami() {
        return codgami;
    }

    public void setCodgami(String codgami) {
        this.codgami = codgami;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProEntcon proEntcon = (ProEntcon) o;

        if (id != proEntcon.id) return false;
        if (anncmp != proEntcon.anncmp) return false;
        if (nroconapp != proEntcon.nroconapp) return false;
        if (tipent != null ? !tipent.equals(proEntcon.tipent) : proEntcon.tipent != null) return false;
        if (codentcon != null ? !codentcon.equals(proEntcon.codentcon) : proEntcon.codentcon != null) return false;
        if (desimp != null ? !desimp.equals(proEntcon.desimp) : proEntcon.desimp != null) return false;
        if (idcap != null ? !idcap.equals(proEntcon.idcap) : proEntcon.idcap != null) return false;
        if (impass != null ? !impass.equals(proEntcon.impass) : proEntcon.impass != null) return false;
        if (impliq != null ? !impliq.equals(proEntcon.impliq) : proEntcon.impliq != null) return false;
        if (impman != null ? !impman.equals(proEntcon.impman) : proEntcon.impman != null) return false;
        if (despdd != null ? !despdd.equals(proEntcon.despdd) : proEntcon.despdd != null) return false;
        if (idcro != null ? !idcro.equals(proEntcon.idcro) : proEntcon.idcro != null) return false;
        if (codcup != null ? !codcup.equals(proEntcon.codcup) : proEntcon.codcup != null) return false;
        if (codcig != null ? !codcig.equals(proEntcon.codcig) : proEntcon.codcig != null) return false;
        if (impmaneme != null ? !impmaneme.equals(proEntcon.impmaneme) : proEntcon.impmaneme != null) return false;
        if (impeco != null ? !impeco.equals(proEntcon.impeco) : proEntcon.impeco != null) return false;
        if (codgami != null ? !codgami.equals(proEntcon.codgami) : proEntcon.codgami != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tipent != null ? tipent.hashCode() : 0);
        result = 31 * result + (codentcon != null ? codentcon.hashCode() : 0);
        result = 31 * result + (desimp != null ? desimp.hashCode() : 0);
        result = 31 * result + (idcap != null ? idcap.hashCode() : 0);
        result = 31 * result + anncmp;
        result = 31 * result + (impass != null ? impass.hashCode() : 0);
        result = 31 * result + (impliq != null ? impliq.hashCode() : 0);
        result = 31 * result + (impman != null ? impman.hashCode() : 0);
        result = 31 * result + (despdd != null ? despdd.hashCode() : 0);
        result = 31 * result + (idcro != null ? idcro.hashCode() : 0);
        result = 31 * result + (codcup != null ? codcup.hashCode() : 0);
        result = 31 * result + (codcig != null ? codcig.hashCode() : 0);
        result = 31 * result + (int) (nroconapp ^ (nroconapp >>> 32));
        result = 31 * result + (impmaneme != null ? impmaneme.hashCode() : 0);
        result = 31 * result + (impeco != null ? impeco.hashCode() : 0);
        result = 31 * result + (codgami != null ? codgami.hashCode() : 0);
        return result;
    }
}
