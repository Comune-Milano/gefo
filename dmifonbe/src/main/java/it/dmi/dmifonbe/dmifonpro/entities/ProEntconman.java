package it.dmi.dmifonbe.dmifonpro.entities;

import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "pro_entconman", schema = "dmifonpro", catalog = "dmifon")
public class ProEntconman {

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
    @Column(name = "tipele", nullable = true, length = 1)
    private String tipele;

    @Basic
    @Column(name = "anncmp", nullable = false)
    private int anncmp;

    @Basic
    @Column(name = "nroipg", nullable = false)
    private long nroipg;

    @Basic
    @Column(name = "idimp", nullable = true, length = 25)
    private String idimp;

    @Basic
    @Column(name = "impman", nullable = false, precision = 3)
    private BigDecimal impman;

    @Basic
    @Column(name = "codcig", nullable = true, length = 10)
    private String codcig;

    @Basic
    @Column(name = "desfat", nullable = true, length = 250)
    private String desfat;

    @Basic
    @Column(name = "ragsoc", nullable = true, length = 250)
    private String ragsoc;

    @Basic
    @Column(name = "codfis", nullable = true, length = 16)
    private String codfis;

    @Basic
    @Column(name = "codiva", nullable = true, length = 11)
    private String codiva;

    @Basic
    @Column(name = "idman", nullable = true, length = 25)
    private String idman;

    @Basic
    @Column(name = "nroman", nullable = false)
    private long nroman;

    @Basic
    @Column(name = "dtareg", nullable = true)
    private Date dtareg;

    @Basic
    @Column(name = "dtapag", nullable = true)
    private Date dtapag;

    @Basic
    @Column(name = "annman", nullable = false)
    private int annman;

    @Basic
    @Column(name = "tipatt", nullable = true, length = 25)
    private String tipatt;

    @Basic
    @Column(name = "idatt", nullable = true, length = 25)
    private String idatt;

    @Basic
    @Column(name = "codcup", nullable = true, length = 15)
    private String codcup;

    @Basic
    @Column(name = "benragsoc", nullable = true, length = 250)
    private String benragsoc;

    @Basic
    @Column(name = "bencodfis", nullable = true, length = 16)
    private String bencodfis;

    @Basic
    @Column(name = "bencodiva", nullable = true, length = 11)
    private String bencodiva;

    @Basic
    @Column(name = "usr_create", nullable = true, length = 30)
    private String usrCreate;

    @Basic
    @Column(name = "dt_create", nullable = true)
    private Timestamp dtCreate;

    @Basic
    @Column(name = "usr_lstupd", nullable = true, length = 30)
    private String usrLstupd;

    @Basic
    @Column(name = "dt_lstupd", nullable = true)
    private Timestamp dtLstupd;

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

    public String getTipele() {
        return tipele;
    }

    public void setTipele(String tipele) {
        this.tipele = tipele;
    }

    public int getAnncmp() {
        return anncmp;
    }

    public void setAnncmp(int anncmp) {
        this.anncmp = anncmp;
    }

    public long getNroipg() {
        return nroipg;
    }

    public void setNroipg(long nroipg) {
        this.nroipg = nroipg;
    }

    public String getIdimp() {
        return idimp;
    }

    public void setIdimp(String idimp) {
        this.idimp = idimp;
    }

    public BigDecimal getImpman() {
        return impman;
    }

    public void setImpman(BigDecimal impman) {
        this.impman = impman;
    }

    public String getCodcig() {
        return codcig;
    }

    public void setCodcig(String codcig) {
        this.codcig = codcig;
    }

    public String getDesfat() {
        return desfat;
    }

    public void setDesfat(String desfat) {
        this.desfat = desfat;
    }

    public String getRagsoc() {
        return ragsoc;
    }

    public void setRagsoc(String ragsoc) {
        this.ragsoc = ragsoc;
    }

    public String getCodfis() {
        return codfis;
    }

    public void setCodfis(String codfis) {
        this.codfis = codfis;
    }

    public String getCodiva() {
        return codiva;
    }

    public void setCodiva(String codiva) {
        this.codiva = codiva;
    }

    public String getIdman() {
        return idman;
    }

    public void setIdman(String idman) {
        this.idman = idman;
    }

    public long getNroman() {
        return nroman;
    }

    public void setNroman(long nroman) {
        this.nroman = nroman;
    }

    public Date getDtareg() {
        return dtareg;
    }

    public void setDtareg(Date dtareg) {
        this.dtareg = dtareg;
    }

    public Date getDtapag() {
        return dtapag;
    }

    public void setDtapag(Date dtapag) {
        this.dtapag = dtapag;
    }

    public int getAnnman() {
        return annman;
    }

    public void setAnnman(int annman) {
        this.annman = annman;
    }

    public String getTipatt() {
        return tipatt;
    }

    public void setTipatt(String tipatt) {
        this.tipatt = tipatt;
    }

    public String getIdatt() {
        return idatt;
    }

    public void setIdatt(String idatt) {
        this.idatt = idatt;
    }

    public String getCodcup() {
        return codcup;
    }

    public void setCodcup(String codcup) {
        this.codcup = codcup;
    }

    public String getBenragsoc() {
        return benragsoc;
    }

    public void setBenragsoc(String benragsoc) {
        this.benragsoc = benragsoc;
    }

    public String getBencodfis() {
        return bencodfis;
    }

    public void setBencodfis(String bencodfis) {
        this.bencodfis = bencodfis;
    }

    public String getBencodiva() {
        return bencodiva;
    }

    public void setBencodiva(String bencodiva) {
        this.bencodiva = bencodiva;
    }

    public String getUsrCreate() {
        return usrCreate;
    }

    public void setUsrCreate(String usrCreate) {
        this.usrCreate = usrCreate;
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp dtCreate) {
        this.dtCreate = dtCreate;
    }

    public String getUsrLstupd() {
        return usrLstupd;
    }

    public void setUsrLstupd(String usrLstupd) {
        this.usrLstupd = usrLstupd;
    }

    public Timestamp getDtLstupd() {
        return dtLstupd;
    }

    public void setDtLstupd(Timestamp dtLstupd) {
        this.dtLstupd = dtLstupd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProEntconman that = (ProEntconman) o;

        if (id != that.id) return false;
        if (anncmp != that.anncmp) return false;
        if (nroipg != that.nroipg) return false;
        if (nroman != that.nroman) return false;
        if (annman != that.annman) return false;
        if (tipent != null ? !tipent.equals(that.tipent) : that.tipent != null) return false;
        if (codentcon != null ? !codentcon.equals(that.codentcon) : that.codentcon != null) return false;
        if (tipele != null ? !tipele.equals(that.tipele) : that.tipele != null) return false;
        if (idimp != null ? !idimp.equals(that.idimp) : that.idimp != null) return false;
        if (impman != null ? !impman.equals(that.impman) : that.impman != null) return false;
        if (codcig != null ? !codcig.equals(that.codcig) : that.codcig != null) return false;
        if (desfat != null ? !desfat.equals(that.desfat) : that.desfat != null) return false;
        if (ragsoc != null ? !ragsoc.equals(that.ragsoc) : that.ragsoc != null) return false;
        if (codfis != null ? !codfis.equals(that.codfis) : that.codfis != null) return false;
        if (codiva != null ? !codiva.equals(that.codiva) : that.codiva != null) return false;
        if (idman != null ? !idman.equals(that.idman) : that.idman != null) return false;
        if (dtareg != null ? !dtareg.equals(that.dtareg) : that.dtareg != null) return false;
        if (dtapag != null ? !dtapag.equals(that.dtapag) : that.dtapag != null) return false;
        if (tipatt != null ? !tipatt.equals(that.tipatt) : that.tipatt != null) return false;
        if (idatt != null ? !idatt.equals(that.idatt) : that.idatt != null) return false;
        if (codcup != null ? !codcup.equals(that.codcup) : that.codcup != null) return false;
        if (benragsoc != null ? !benragsoc.equals(that.benragsoc) : that.benragsoc != null) return false;
        if (bencodfis != null ? !bencodfis.equals(that.bencodfis) : that.bencodfis != null) return false;
        if (bencodiva != null ? !bencodiva.equals(that.bencodiva) : that.bencodiva != null) return false;
        if (usrCreate != null ? !usrCreate.equals(that.usrCreate) : that.usrCreate != null) return false;
        if (dtCreate != null ? !dtCreate.equals(that.dtCreate) : that.dtCreate != null) return false;
        if (usrLstupd != null ? !usrLstupd.equals(that.usrLstupd) : that.usrLstupd != null) return false;
        if (dtLstupd != null ? !dtLstupd.equals(that.dtLstupd) : that.dtLstupd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tipent != null ? tipent.hashCode() : 0);
        result = 31 * result + (codentcon != null ? codentcon.hashCode() : 0);
        result = 31 * result + (tipele != null ? tipele.hashCode() : 0);
        result = 31 * result + anncmp;
        result = 31 * result + (int) (nroipg ^ (nroipg >>> 32));
        result = 31 * result + (idimp != null ? idimp.hashCode() : 0);
        result = 31 * result + (impman != null ? impman.hashCode() : 0);
        result = 31 * result + (codcig != null ? codcig.hashCode() : 0);
        result = 31 * result + (desfat != null ? desfat.hashCode() : 0);
        result = 31 * result + (ragsoc != null ? ragsoc.hashCode() : 0);
        result = 31 * result + (codfis != null ? codfis.hashCode() : 0);
        result = 31 * result + (codiva != null ? codiva.hashCode() : 0);
        result = 31 * result + (idman != null ? idman.hashCode() : 0);
        result = 31 * result + (int) (nroman ^ (nroman >>> 32));
        result = 31 * result + (dtareg != null ? dtareg.hashCode() : 0);
        result = 31 * result + (dtapag != null ? dtapag.hashCode() : 0);
        result = 31 * result + annman;
        result = 31 * result + (tipatt != null ? tipatt.hashCode() : 0);
        result = 31 * result + (idatt != null ? idatt.hashCode() : 0);
        result = 31 * result + (codcup != null ? codcup.hashCode() : 0);
        result = 31 * result + (benragsoc != null ? benragsoc.hashCode() : 0);
        result = 31 * result + (bencodfis != null ? bencodfis.hashCode() : 0);
        result = 31 * result + (bencodiva != null ? bencodiva.hashCode() : 0);
        result = 31 * result + (usrCreate != null ? usrCreate.hashCode() : 0);
        result = 31 * result + (dtCreate != null ? dtCreate.hashCode() : 0);
        result = 31 * result + (usrLstupd != null ? usrLstupd.hashCode() : 0);
        result = 31 * result + (dtLstupd != null ? dtLstupd.hashCode() : 0);
        return result;
    }
}
