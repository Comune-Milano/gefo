package it.dmi.dmifonbe.dmifonamm.entities;

import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.Date;
import javax.persistence.*;

@MappedSuperclass
public class EntitySuperClass {

    @Basic
    @HtmlEscape
    @Column(name = "usr_create", nullable = true, length = 30)
    private String usrCreate;

    @Basic
    @Column(name = "dt_create", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtCreate;

    @Basic
    @HtmlEscape
    @Column(name = "usr_lstupd", nullable = true, length = 30)
    private String usrLstupd;

    @Basic
    @Column(name = "dt_lstupd", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtLstupd;

    public String getUsrCreate() {
        return usrCreate;
    }

    public void setUsrCreate(String usrCreate) {
        this.usrCreate = usrCreate;
    }

    public Date getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Date dtCreate) {
        this.dtCreate = dtCreate;
    }

    public String getUsrLstupd() {
        return usrLstupd;
    }

    public void setUsrLstupd(String usrLstupd) {
        this.usrLstupd = usrLstupd;
    }

    public Date getDtLstupd() {
        return dtLstupd;
    }

    public void setDtLstupd(Date dtLstupd) {
        this.dtLstupd = dtLstupd;
    }
}
