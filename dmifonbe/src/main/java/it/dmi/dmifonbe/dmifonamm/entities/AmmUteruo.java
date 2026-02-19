package it.dmi.dmifonbe.dmifonamm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "amm_uteruo", schema = "dmifonamm", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class AmmUteruo extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_UTENTERUOLO_SEQUENCE")
    @SequenceGenerator(sequenceName = "amm_uteruo_id_seq", schema = "dmifonamm", allocationSize = 1, name = "ID_UTENTERUOLO_SEQUENCE")
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_ute", nullable = false)
    private long idUte;

    @Basic
    @Column(name = "id_ruo", nullable = false)
    private long idRuo;

    @Basic
    @HtmlEscape
    @Column(name = "flgdef", nullable = false, length = 1)
    private String flgdef;

    @Basic
    @HtmlEscape
    @Column(name = "tipcondat", nullable = false, length = 1)
    private String tipcondat;

    @Basic
    @Column(name = "id_dir", nullable = false)
    private long idDir;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_ute", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AmmUte ammUteByIdUte;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_ruo", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AmmRuo ammRuoByIdRuo;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_dir", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AmmDir ammDirByIdDir;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getIdUte() {
        return idUte;
    }

    public void setIdUte(long idUte) {
        this.idUte = idUte;
    }

    public long getIdRuo() {
        return idRuo;
    }

    public void setIdRuo(long idRuo) {
        this.idRuo = idRuo;
    }

    public String getFlgdef() {
        return flgdef;
    }

    public void setFlgdef(String flgdef) {
        this.flgdef = flgdef;
    }

    public String getTipcondat() {
        return tipcondat;
    }

    public void setTipcondat(String tipcondat) {
        this.tipcondat = tipcondat;
    }

    public long getIdDir() {
        return idDir;
    }

    public void setIdDir(long idDir) {
        this.idDir = idDir;
    }

    public AmmUte getAmmUteByIdUte() {
        return ammUteByIdUte;
    }

    public void setAmmUteByIdUte(AmmUte ammUteByIdUte) {
        this.ammUteByIdUte = ammUteByIdUte;
    }

    public AmmRuo getAmmRuoByIdRuo() {
        return ammRuoByIdRuo;
    }

    public void setAmmRuoByIdRuo(AmmRuo ammRuoByIdRuo) {
        this.ammRuoByIdRuo = ammRuoByIdRuo;
    }

    public AmmDir getAmmDirByIdDir() {
        return ammDirByIdDir;
    }

    public void setAmmDirByIdDir(AmmDir ammDirByIdDir) {
        this.ammDirByIdDir = ammDirByIdDir;
    }
}
