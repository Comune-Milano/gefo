package it.dmi.dmifonbe.dmifonpro.entities;

import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.sql.Timestamp;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_detconddr", schema = "dmifonpro", catalog = "dmifon")
public class ProDetconddr {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_ddr", nullable = false)
    private long idDdr;

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
    @Column(name = "notent", nullable = true, length = 250)
    private String notent;

    @Basic
    @HtmlEscape
    @Column(name = "usr_create", nullable = true, length = 30)
    private String usrCreate;

    @Basic
    @Column(name = "dt_create", nullable = true)
    private Timestamp dtCreate;

    @Basic
    @HtmlEscape
    @Column(name = "usr_lstupd", nullable = true, length = 30)
    private String usrLstupd;

    @Basic
    @Column(name = "dt_lstupd", nullable = true)
    private Timestamp dtLstupd;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_ddr", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProDdr proDdrByIdDdr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getIdDdr() {
        return idDdr;
    }

    public void setIdDdr(long idDdr) {
        this.idDdr = idDdr;
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

    public String getNotent() {
        return notent;
    }

    public void setNotent(String notent) {
        this.notent = notent;
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

        ProDetconddr that = (ProDetconddr) o;

        if (id != that.id) return false;
        if (idDdr != that.idDdr) return false;
        if (tipent != null ? !tipent.equals(that.tipent) : that.tipent != null) return false;
        if (codentcon != null ? !codentcon.equals(that.codentcon) : that.codentcon != null) return false;
        if (notent != null ? !notent.equals(that.notent) : that.notent != null) return false;
        if (usrCreate != null ? !usrCreate.equals(that.usrCreate) : that.usrCreate != null) return false;
        if (dtCreate != null ? !dtCreate.equals(that.dtCreate) : that.dtCreate != null) return false;
        if (usrLstupd != null ? !usrLstupd.equals(that.usrLstupd) : that.usrLstupd != null) return false;
        if (dtLstupd != null ? !dtLstupd.equals(that.dtLstupd) : that.dtLstupd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (idDdr ^ (idDdr >>> 32));
        result = 31 * result + (tipent != null ? tipent.hashCode() : 0);
        result = 31 * result + (codentcon != null ? codentcon.hashCode() : 0);
        result = 31 * result + (notent != null ? notent.hashCode() : 0);
        result = 31 * result + (usrCreate != null ? usrCreate.hashCode() : 0);
        result = 31 * result + (dtCreate != null ? dtCreate.hashCode() : 0);
        result = 31 * result + (usrLstupd != null ? usrLstupd.hashCode() : 0);
        result = 31 * result + (dtLstupd != null ? dtLstupd.hashCode() : 0);
        return result;
    }

    public ProDdr getProDdrByIdDdr() {
        return proDdrByIdDdr;
    }

    public void setProDdrByIdDdr(ProDdr proDdrByIdDdr) {
        this.proDdrByIdDdr = proDdrByIdDdr;
    }
}
