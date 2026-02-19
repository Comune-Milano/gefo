package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Table(name = "pro_pro", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProPro extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "codpro", nullable = false, length = 25)
    private String codpro;

    @Basic
    @HtmlEscape
    @Column(name = "despro", nullable = false, length = 250)
    private String despro;

    @Basic
    @Column(name = "id_tipfin", nullable = false)
    private Long idTipfin;

    @Basic
    @Column(name = "id_macpro", nullable = false)
    private Long idMacpro;

    @Basic
    @Column(name = "id_ban", nullable = true)
    private Long idBan;

    @Basic
    @Column(name = "id_propad", nullable = true)
    private Long idPropad;

    @Basic
    @HtmlEscape
    @Column(name = "iddecpro", nullable = false, length = 90)
    private String iddecpro;

    @Basic
    @Column(name = "livpro", nullable = false)
    private int livpro;

    @Basic
    @Column(name = "cntlivinf", nullable = false)
    private int cntlivinf;

    @Basic
    @HtmlEscape
    @Column(name = "codappren", nullable = true, length = 25)
    private String codappren;

    @Basic
    @HtmlEscape
    @Column(name = "codcup", nullable = true, length = 15)
    private String codcup;

    @Basic
    @HtmlEscape
    @Column(name = "codcig", nullable = true, length = 10)
    private String codcig;

    @Basic
    @HtmlEscape
    @Column(name = "tippro", nullable = true, length = 3)
    private String tippro;

    @Basic
    @HtmlEscape
    @Column(name = "codtippro", nullable = true, length = 25)
    private String codtippro;

    @Basic
    @HtmlEscape
    @Column(name = "desaffhou", nullable = true, length = 250)
    private String desaffhou;

    @Basic
    @HtmlEscape
    @Column(name = "flgopeavv", nullable = true, length = 1)
    private String flgopeavv;

    @Basic
    @HtmlEscape
    @Column(name = "notpro", nullable = true, length = 500)
    private String notpro;

    @Basic
    @Column(name = "impigv", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impigv deve essere superiore o uguale a 0")
    private BigDecimal impigv;

    @Basic
    @HtmlEscape
    @Column(name = "rifigv", nullable = true, length = 250)
    private String rifigv;

    @Basic
    @Column(name = "impigvdel", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impigvdel deve essere superiore o uguale a 0")
    private BigDecimal impigvdel;

    @Basic
    @HtmlEscape
    @Column(name = "notigv", nullable = true, length = 250)
    private String notigv;

    @Basic
    @HtmlEscape
    @Column(name = "notaff", nullable = true, length = 250)
    private String notaff;

    @Basic
    @Column(name = "impimppre", nullable = false, precision = 2)
    //10/05/2023 ammettiamo anche i negativi
    //@Min(value = 0, message = "Il campo impimppre deve essere superiore o uguale a 0")
    private BigDecimal impimppre;

    @Basic
    @Column(name = "impaccpre", nullable = false, precision = 2)
    //10/05/2023 ammettiamo anche i negativi
    //@Min(value = 0, message = "Il campo impaccpre deve essere superiore o uguale a 0")
    private BigDecimal impaccpre;

    @Basic
    @Column(name = "impmanpre", nullable = false, precision = 2)
    //10/05/2023 ammettiamo anche i negativi
    //@Min(value = 0, message = "Il campo impmanpre deve essere superiore o uguale a 0")
    private BigDecimal impmanpre;

    @Basic
    @Column(name = "imprevpre", nullable = false, precision = 2)
    //10/05/2023 ammettiamo anche i negativi
    //@Min(value = 0, message = "Il campo imprevpre deve essere superiore o uguale a 0")
    private BigDecimal imprevpre;

    @Basic
    @Column(name = "impimpric", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impimpric deve essere superiore o uguale a 0")
    private BigDecimal impimpric;

    @Basic
    @HtmlEscape
    @Column(name = "codgami", nullable = true, length = 20)
    private String codgami;

    @Basic
    @Column(name = "id_dir", nullable = true, length = 25)
    private Integer idDir;

    @Basic
    @HtmlEscape
    @Column(name = "codset", nullable = true, length = 25)
    private String codset;

    @Basic
    @HtmlEscape
    @Column(name = "codass", nullable = true, length = 25)
    private String codass;

    @Basic
    @HtmlEscape
    @Column(name = "veraff", nullable = true, length = 250)
    private String veraff;

    @Basic
    @HtmlEscape
    @Column(name = "estaff", nullable = true, length = 250)
    private String estaff;

    @Basic
    @HtmlEscape
    @Column(name = "codcui", nullable = true, length = 25)
    private String codcui;

    @Basic
    @Column(name = "id_stafin", nullable = false)
    private Long idStafin;

    @Basic
    @Column(name = "id_stapro", nullable = false)
    private Long idStapro;

    @Basic
    @Column(name = "id_tem", nullable = true)
    private Long idTem;

    @Basic
    @HtmlEscape
    @Column(name = "delcan", nullable = true, length = 250)
    private String delcan;

    @Basic
    @HtmlEscape
    @Column(name = "notimp", nullable = true, length = 250)
    private String notimp;

    @Basic
    @HtmlEscape
    @Column(name = "notpre", nullable = true, length = 250)
    private String notpre;

    @Basic
    @Column(name = "id_mun", nullable = true)
    private Long idMun;

    @Basic
    @Column(name = "id_nil", nullable = true)
    private Long idNil;

    @OneToMany(mappedBy = "proProByIdPro", fetch = FetchType.LAZY)
    @Valid
    private List<ProAva> proAvasById;

    @OneToMany(mappedBy = "proProByIdPro", fetch = FetchType.LAZY)
    @Valid
    private List<ProDdr> proDdrsById;

    @OneToMany(mappedBy = "proProByIdPro", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Valid
    private List<ProDetcon> proDetconsById;

    @OneToMany(mappedBy = "proProByIdPro", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Valid
    private List<ProImp> proImpsById;

    @OneToMany(mappedBy = "proProByIdPro", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Valid
    private List<ProInfagg> proInfaggsById;

    @OneToMany(mappedBy = "proProByIdPro", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Valid
    private List<ProPre> proPresById;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tipfin", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProTipfin proTipfinByIdTipfin;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_macpro", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProMacpro proMacproByIdMacpro;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_ban", referencedColumnName = "id", insertable = false, updatable = false)
    private ProBan proBanByIdBan;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_stafin", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProStafin proStafinByIdStafin;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_stapro", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProStapro proStaproByIdStapro;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_tem", referencedColumnName = "id", insertable = false, updatable = false)
    private ProTem proTemByIdTem;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_mun", referencedColumnName = "id", insertable = false, updatable = false)
    private ProMun proMunByIdMun;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_nil", referencedColumnName = "id", insertable = false, updatable = false)
    private ProNil proNilByIdNil;

    @OneToMany(mappedBy = "proProByIdPro", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Valid
    private List<ProRes> proResById;

    @OneToMany(mappedBy = "proProByIdPro", fetch = FetchType.LAZY)
    @Valid
    private List<ProRic> proRicsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodpro() {
        return codpro;
    }

    public void setCodpro(String codpro) {
        this.codpro = codpro;
    }

    public String getDespro() {
        return despro;
    }

    public void setDespro(String despro) {
        this.despro = despro;
    }

    public Long getIdTipfin() {
        return idTipfin;
    }

    public void setIdTipfin(Long idTipfin) {
        this.idTipfin = idTipfin;
    }

    public Long getIdMacpro() {
        return idMacpro;
    }

    public void setIdMacpro(Long idMacpro) {
        this.idMacpro = idMacpro;
    }

    public Long getIdBan() {
        return idBan;
    }

    public void setIdBan(Long idBan) {
        this.idBan = idBan;
    }

    public Long getIdPropad() {
        return idPropad;
    }

    public void setIdPropad(Long idPropad) {
        this.idPropad = idPropad;
    }

    public String getIddecpro() {
        return iddecpro;
    }

    public void setIddecpro(String iddecpro) {
        this.iddecpro = iddecpro;
    }

    public int getLivpro() {
        return livpro;
    }

    public void setLivpro(int livpro) {
        this.livpro = livpro;
    }

    public int getCntlivinf() {
        return cntlivinf;
    }

    public void setCntlivinf(int cntlivinf) {
        this.cntlivinf = cntlivinf;
    }

    public String getCodappren() {
        return codappren;
    }

    public void setCodappren(String codappren) {
        this.codappren = codappren;
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

    public String getTippro() {
        return tippro;
    }

    public void setTippro(String tippro) {
        this.tippro = tippro;
    }

    public String getCodtippro() {
        return codtippro;
    }

    public void setCodtippro(String codtippro) {
        this.codtippro = codtippro;
    }

    public String getDesaffhou() {
        return desaffhou;
    }

    public void setDesaffhou(String desaffhou) {
        this.desaffhou = desaffhou;
    }

    public String getFlgopeavv() {
        return flgopeavv;
    }

    public void setFlgopeavv(String flgopeavv) {
        this.flgopeavv = flgopeavv;
    }

    public String getNotpro() {
        return notpro;
    }

    public void setNotpro(String notpro) {
        this.notpro = notpro;
    }

    public BigDecimal getImpigv() {
        return impigv;
    }

    public void setImpigv(BigDecimal impigv) {
        this.impigv = impigv;
    }

    public String getRifigv() {
        return rifigv;
    }

    public void setRifigv(String rifigv) {
        this.rifigv = rifigv;
    }

    public BigDecimal getImpigvdel() {
        return impigvdel;
    }

    public void setImpigvdel(BigDecimal impigvdel) {
        this.impigvdel = impigvdel;
    }

    public String getNotigv() {
        return notigv;
    }

    public void setNotigv(String notigv) {
        this.notigv = notigv;
    }

    public String getNotaff() {
        return notaff;
    }

    public void setNotaff(String notaff) {
        this.notaff = notaff;
    }

    public BigDecimal getImpimppre() {
        return impimppre;
    }

    public void setImpimppre(BigDecimal impimppre) {
        this.impimppre = impimppre;
    }

    public BigDecimal getImpaccpre() {
        return impaccpre;
    }

    public void setImpaccpre(BigDecimal impaccpre) {
        this.impaccpre = impaccpre;
    }

    public BigDecimal getImpmanpre() {
        return impmanpre;
    }

    public void setImpmanpre(BigDecimal impmanpre) {
        this.impmanpre = impmanpre;
    }

    public BigDecimal getImprevpre() {
        return imprevpre;
    }

    public void setImprevpre(BigDecimal imprevpre) {
        this.imprevpre = imprevpre;
    }

    public BigDecimal getImpimpric() {
        return impimpric;
    }

    public void setImpimpric(BigDecimal impimpric) {
        this.impimpric = impimpric;
    }

    public String getCodgami() {
        return codgami;
    }

    public void setCodgami(String codgami) {
        this.codgami = codgami;
    }

    public Integer getIdDir() {
        return idDir;
    }

    public void setIdDir(Integer idDir) {
        this.idDir = idDir;
    }

    public String getCodset() {
        return codset;
    }

    public void setCodset(String codset) {
        this.codset = codset;
    }

    public String getCodass() {
        return codass;
    }

    public void setCodass(String codass) {
        this.codass = codass;
    }

    public String getVeraff() {
        return veraff;
    }

    public void setVeraff(String veraff) {
        this.veraff = veraff;
    }

    public String getEstaff() {
        return estaff;
    }

    public void setEstaff(String estaff) {
        this.estaff = estaff;
    }

    public String getCodcui() {
        return codcui;
    }

    public void setCodcui(String codcui) {
        this.codcui = codcui;
    }

    public Long getIdStafin() {
        return idStafin;
    }

    public void setIdStafin(Long idStafin) {
        this.idStafin = idStafin;
    }

    public Long getIdStapro() {
        return idStapro;
    }

    public void setIdStapro(Long idStapro) {
        this.idStapro = idStapro;
    }

    public Long getIdTem() {
        return idTem;
    }

    public void setIdTem(Long idTem) {
        this.idTem = idTem;
    }

    public String getDelcan() {
        return delcan;
    }

    public void setDelcan(String delcan) {
        this.delcan = delcan;
    }

    public String getNotimp() {
        return notimp;
    }

    public void setNotimp(String notimp) {
        this.notimp = notimp;
    }

    public String getNotpre() {
        return notpre;
    }

    public void setNotpre(String notpre) {
        this.notpre = notpre;
    }

    public Long getIdMun() {
        return idMun;
    }

    public void setIdMun(Long idMun) {
        this.idMun = idMun;
    }

    public Long getIdNil() {
        return idNil;
    }

    public void setIdNil(Long idNil) {
        this.idNil = idNil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProPro proPro = (ProPro) o;

        if (id != proPro.id) return false;
        if (idTipfin != proPro.idTipfin) return false;
        if (idMacpro != proPro.idMacpro) return false;
        if (livpro != proPro.livpro) return false;
        if (cntlivinf != proPro.cntlivinf) return false;
        if (idStafin != proPro.idStafin) return false;
        if (idStapro != proPro.idStapro) return false;
        if (idDir != proPro.idDir) return false;
        if (codpro != null ? !codpro.equals(proPro.codpro) : proPro.codpro != null) return false;
        if (despro != null ? !despro.equals(proPro.despro) : proPro.despro != null) return false;
        if (idBan != null ? !idBan.equals(proPro.idBan) : proPro.idBan != null) return false;
        if (idPropad != null ? !idPropad.equals(proPro.idPropad) : proPro.idPropad != null) return false;
        if (iddecpro != null ? !iddecpro.equals(proPro.iddecpro) : proPro.iddecpro != null) return false;
        if (codappren != null ? !codappren.equals(proPro.codappren) : proPro.codappren != null) return false;
        if (codcup != null ? !codcup.equals(proPro.codcup) : proPro.codcup != null) return false;
        if (codcig != null ? !codcig.equals(proPro.codcig) : proPro.codcig != null) return false;
        if (tippro != null ? !tippro.equals(proPro.tippro) : proPro.tippro != null) return false;
        if (codtippro != null ? !codtippro.equals(proPro.codtippro) : proPro.codtippro != null) return false;
        if (desaffhou != null ? !desaffhou.equals(proPro.desaffhou) : proPro.desaffhou != null) return false;
        if (flgopeavv != null ? !flgopeavv.equals(proPro.flgopeavv) : proPro.flgopeavv != null) return false;
        if (notpro != null ? !notpro.equals(proPro.notpro) : proPro.notpro != null) return false;
        if (impigv != null ? !impigv.equals(proPro.impigv) : proPro.impigv != null) return false;
        if (rifigv != null ? !rifigv.equals(proPro.rifigv) : proPro.rifigv != null) return false;
        if (impigvdel != null ? !impigvdel.equals(proPro.impigvdel) : proPro.impigvdel != null) return false;
        if (notigv != null ? !notigv.equals(proPro.notigv) : proPro.notigv != null) return false;
        if (notaff != null ? !notaff.equals(proPro.notaff) : proPro.notaff != null) return false;
        if (impimppre != null ? !impimppre.equals(proPro.impimppre) : proPro.impimppre != null) return false;
        if (impaccpre != null ? !impaccpre.equals(proPro.impaccpre) : proPro.impaccpre != null) return false;
        if (impmanpre != null ? !impmanpre.equals(proPro.impmanpre) : proPro.impmanpre != null) return false;
        if (imprevpre != null ? !imprevpre.equals(proPro.imprevpre) : proPro.imprevpre != null) return false;
        if (impimpric != null ? !impimpric.equals(proPro.impimpric) : proPro.impimpric != null) return false;
        if (codgami != null ? !codgami.equals(proPro.codgami) : proPro.codgami != null) return false;
        if (codset != null ? !codset.equals(proPro.codset) : proPro.codset != null) return false;
        if (codass != null ? !codass.equals(proPro.codass) : proPro.codass != null) return false;
        if (veraff != null ? !veraff.equals(proPro.veraff) : proPro.veraff != null) return false;
        if (estaff != null ? !estaff.equals(proPro.estaff) : proPro.estaff != null) return false;
        if (codcui != null ? !codcui.equals(proPro.codcui) : proPro.codcui != null) return false;
        if (idTem != null ? !idTem.equals(proPro.idTem) : proPro.idTem != null) return false;
        if (delcan != null ? !delcan.equals(proPro.delcan) : proPro.delcan != null) return false;
        if (notimp != null ? !notimp.equals(proPro.notimp) : proPro.notimp != null) return false;
        if (notpre != null ? !notpre.equals(proPro.notpre) : proPro.notpre != null) return false;
        if (idMun != null ? !idMun.equals(proPro.idMun) : proPro.idMun != null) return false;
        if (idNil != null ? !idNil.equals(proPro.idNil) : proPro.idNil != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (codpro != null ? codpro.hashCode() : 0);
        result = 31 * result + (despro != null ? despro.hashCode() : 0);
        result = 31 * result + (int) (idTipfin ^ (idTipfin >>> 32));
        result = 31 * result + (int) (idMacpro ^ (idMacpro >>> 32));
        result = 31 * result + (idBan != null ? idBan.hashCode() : 0);
        result = 31 * result + (idPropad != null ? idPropad.hashCode() : 0);
        result = 31 * result + (iddecpro != null ? iddecpro.hashCode() : 0);
        result = 31 * result + livpro;
        result = 31 * result + cntlivinf;
        result = 31 * result + (codappren != null ? codappren.hashCode() : 0);
        result = 31 * result + (codcup != null ? codcup.hashCode() : 0);
        result = 31 * result + (codcig != null ? codcig.hashCode() : 0);
        result = 31 * result + (tippro != null ? tippro.hashCode() : 0);
        result = 31 * result + (codtippro != null ? codtippro.hashCode() : 0);
        result = 31 * result + (desaffhou != null ? desaffhou.hashCode() : 0);
        result = 31 * result + (flgopeavv != null ? flgopeavv.hashCode() : 0);
        result = 31 * result + (notpro != null ? notpro.hashCode() : 0);
        result = 31 * result + (impigv != null ? impigv.hashCode() : 0);
        result = 31 * result + (rifigv != null ? rifigv.hashCode() : 0);
        result = 31 * result + (impigvdel != null ? impigvdel.hashCode() : 0);
        result = 31 * result + (notigv != null ? notigv.hashCode() : 0);
        result = 31 * result + (notaff != null ? notaff.hashCode() : 0);
        result = 31 * result + (impimppre != null ? impimppre.hashCode() : 0);
        result = 31 * result + (impaccpre != null ? impaccpre.hashCode() : 0);
        result = 31 * result + (impmanpre != null ? impmanpre.hashCode() : 0);
        result = 31 * result + (imprevpre != null ? imprevpre.hashCode() : 0);
        result = 31 * result + (impimpric != null ? impimpric.hashCode() : 0);
        result = 31 * result + (codgami != null ? codgami.hashCode() : 0);
        result = 31 * result + (idDir ^ (idDir >>> 32));
        result = 31 * result + (codset != null ? codset.hashCode() : 0);
        result = 31 * result + (codass != null ? codass.hashCode() : 0);
        result = 31 * result + (veraff != null ? veraff.hashCode() : 0);
        result = 31 * result + (estaff != null ? estaff.hashCode() : 0);
        result = 31 * result + (codcui != null ? codcui.hashCode() : 0);
        result = 31 * result + (int) (idStafin ^ (idStafin >>> 32));
        result = 31 * result + (int) (idStapro ^ (idStapro >>> 32));
        result = 31 * result + (idTem != null ? idTem.hashCode() : 0);
        result = 31 * result + (delcan != null ? delcan.hashCode() : 0);
        result = 31 * result + (notimp != null ? notimp.hashCode() : 0);
        result = 31 * result + (notpre != null ? notpre.hashCode() : 0);
        result = 31 * result + (idMun != null ? idMun.hashCode() : 0);
        result = 31 * result + (idNil != null ? idNil.hashCode() : 0);
        return result;
    }

    public static ProPro generateCopy(ProPro item) {
        if (item == null) {
            return null;
        }
        ProPro result = new ProPro();
        result.setId(item.getId());
        result.setCodpro(item.getCodpro());
        result.setDespro(item.getDespro());
        result.setIdTipfin(item.getIdTipfin());
        result.setIdMacpro(item.getIdMacpro());
        result.setIdBan(item.getIdBan());
        result.setIdPropad(item.getIdPropad());
        result.setIddecpro(item.getIddecpro());
        result.setLivpro(item.getLivpro());
        result.setCntlivinf(item.getCntlivinf());
        result.setCodappren(item.getCodappren());
        result.setCodcup(item.getCodcup());
        result.setCodcig(item.getCodcig());
        result.setTippro(item.getTippro());
        result.setCodtippro(item.getCodtippro());
        result.setDesaffhou(item.getDesaffhou());
        result.setFlgopeavv(item.getFlgopeavv());
        result.setNotpro(item.getNotpro());
        result.setImpigv(item.getImpigv());
        result.setRifigv(item.getRifigv());
        result.setImpigvdel(item.getImpigvdel());
        result.setNotigv(item.getNotigv());
        result.setNotaff(item.getNotaff());
        result.setImpimppre(item.getImpimppre());
        result.setImpaccpre(item.getImpaccpre());
        result.setImpmanpre(item.getImpmanpre());
        result.setImprevpre(item.getImprevpre());
        result.setImpimpric(item.getImpimpric());
        result.setCodgami(item.getCodgami());
        result.setIdDir(item.getIdDir());
        result.setCodset(item.getCodset());
        result.setCodass(item.getCodass());
        result.setVeraff(item.getVeraff());
        result.setEstaff(item.getEstaff());
        result.setCodcui(item.getCodcui());
        result.setIdStafin(item.getIdStafin());
        result.setIdStapro(item.getIdStapro());
        result.setIdTem(item.getIdTem());
        result.setDelcan(item.getDelcan());
        result.setNotimp(item.getNotimp());
        result.setNotpre(item.getNotpre());
        result.setIdMun(item.getIdMun());
        result.setIdNil(item.getIdNil());
        return result;
    }

    public List<ProAva> getProAvasById() {
        return proAvasById;
    }

    public void setProAvasById(List<ProAva> proAvasById) {
        this.proAvasById = proAvasById;
    }

    public List<ProDdr> getProDdrsById() {
        return proDdrsById;
    }

    public void setProDdrsById(List<ProDdr> proDdrsById) {
        this.proDdrsById = proDdrsById;
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

    public List<ProInfagg> getProInfaggsById() {
        return proInfaggsById;
    }

    public void setProInfaggsById(List<ProInfagg> proInfaggsById) {
        this.proInfaggsById = proInfaggsById;
    }

    public List<ProPre> getProPresById() {
        return proPresById;
    }

    public void setProPresById(List<ProPre> proPresById) {
        this.proPresById = proPresById;
    }

    public ProTipfin getProTipfinByIdTipfin() {
        return proTipfinByIdTipfin;
    }

    public void setProTipfinByIdTipfin(ProTipfin proTipfinByIdTipfin) {
        this.proTipfinByIdTipfin = proTipfinByIdTipfin;
    }

    public ProMacpro getProMacproByIdMacpro() {
        return proMacproByIdMacpro;
    }

    public void setProMacproByIdMacpro(ProMacpro proMacproByIdMacpro) {
        this.proMacproByIdMacpro = proMacproByIdMacpro;
    }

    public ProBan getProBanByIdBan() {
        return proBanByIdBan;
    }

    public void setProBanByIdBan(ProBan proBanByIdBan) {
        this.proBanByIdBan = proBanByIdBan;
    }

    public ProStafin getProStafinByIdStafin() {
        return proStafinByIdStafin;
    }

    public void setProStafinByIdStafin(ProStafin proStafinByIdStafin) {
        this.proStafinByIdStafin = proStafinByIdStafin;
    }

    public ProStapro getProStaproByIdStapro() {
        return proStaproByIdStapro;
    }

    public void setProStaproByIdStapro(ProStapro proStaproByIdStapro) {
        this.proStaproByIdStapro = proStaproByIdStapro;
    }

    public ProTem getProTemByIdTem() {
        return proTemByIdTem;
    }

    public void setProTemByIdTem(ProTem proTemByIdTem) {
        this.proTemByIdTem = proTemByIdTem;
    }

    public ProMun getProMunByIdMun() {
        return proMunByIdMun;
    }

    public void setProMunByIdMun(ProMun proMunByIdMun) {
        this.proMunByIdMun = proMunByIdMun;
    }

    public ProNil getProNilByIdNil() {
        return proNilByIdNil;
    }

    public void setProNilByIdNil(ProNil proNilByIdNil) {
        this.proNilByIdNil = proNilByIdNil;
    }

    public List<ProRes> getProResById() {
        return proResById;
    }

    public void setProResById(List<ProRes> proResById) {
        this.proResById = proResById;
    }

    public List<ProRic> getProRicsById() {
        return proRicsById;
    }

    public void setProRicsById(List<ProRic> proRicsById) {
        this.proRicsById = proRicsById;
    }
}
