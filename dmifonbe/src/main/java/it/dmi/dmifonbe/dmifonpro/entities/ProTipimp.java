package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "pro_tipimp", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProTipimp extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "destipimp", nullable = false, length = 50)
    private String destipimp;

    @Basic
    @HtmlEscape
    @Column(name = "flgtipimp", nullable = false, length = 1)
    private String flgtipimp;

    @Basic
    @HtmlEscape
    @Column(name = "flgdicui", nullable = false, length = 1)
    private String flgdicui;

    @Basic
    @Column(name = "ordtipimp", nullable = false)
    private int ordtipimp;

    @OneToMany(mappedBy = "proTipimpByIdTipimp", fetch = FetchType.LAZY)
    @Valid
    private List<ProDetcon> proDetconsById;

    @OneToMany(mappedBy = "proTipimpByIdTipimp", fetch = FetchType.LAZY)
    @Valid
    private List<ProImp> proImpsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestipimp() {
        return destipimp;
    }

    public void setDestipimp(String destipimp) {
        this.destipimp = destipimp;
    }

    public String getFlgtipimp() {
        return flgtipimp;
    }

    public void setFlgtipimp(String flgtipimp) {
        this.flgtipimp = flgtipimp;
    }

    public String getFlgdicui() {
        return flgdicui;
    }

    public void setFlgdicui(String flgdicui) {
        this.flgdicui = flgdicui;
    }

    public int getOrdtipimp() {
        return ordtipimp;
    }

    public void setOrdtipimp(int ordtipimp) {
        this.ordtipimp = ordtipimp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProTipimp proTipimp = (ProTipimp) o;

        if (id != proTipimp.id) return false;
        if (ordtipimp != proTipimp.ordtipimp) return false;
        if (destipimp != null ? !destipimp.equals(proTipimp.destipimp) : proTipimp.destipimp != null) return false;
        if (flgtipimp != null ? !flgtipimp.equals(proTipimp.flgtipimp) : proTipimp.flgtipimp != null) return false;
        if (flgdicui != null ? !flgdicui.equals(proTipimp.flgdicui) : proTipimp.flgdicui != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (destipimp != null ? destipimp.hashCode() : 0);
        result = 31 * result + (flgtipimp != null ? flgtipimp.hashCode() : 0);
        result = 31 * result + (flgdicui != null ? flgdicui.hashCode() : 0);
        result = 31 * result + ordtipimp;
        return result;
    }

    public List<ProDetcon> getProDetconsById() {
        return proDetconsById;
    }

    public void setProDetconsById(List<ProDetcon> proDetconsById) {
        this.proDetconsById = proDetconsById;
    }

    public List<ProImp> getProImpsById() {
        return proImpsById;
    }

    public void setProImpsById(List<ProImp> proImpsById) {
        this.proImpsById = proImpsById;
    }

    @Override
    public String toString() {
        return (
            "ProTipimp{" +
            "id=" +
            id +
            ", destipimp='" +
            destipimp +
            '\'' +
            ", flgtipimp='" +
            flgtipimp +
            '\'' +
            ", flgdicui='" +
            flgdicui +
            '\'' +
            ", ordtipimp=" +
            ordtipimp +
            ", proDetconsById=" +
            proDetconsById +
            ", proImpsById=" +
            proImpsById +
            '}'
        );
    }
}
