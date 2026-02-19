package it.dmi.dmifonbe.dmifonpro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Table(name = "pro_ava", schema = "dmifonpro", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProAva extends EntitySuperClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_pro", nullable = false)
    private Long idPro;

    @Basic
    @Column(name = "nrover", nullable = false)
    private Long nrover;

    @Basic
    @Column(name = "dtarilava", nullable = false)
    private Date dtarilava;

    @Basic
    @HtmlEscape
    @Column(name = "desstaava", nullable = false, length = 500)
    private String desstaava;

    @Basic
    @Column(name = "prcava", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo prcava deve essere superiore o uguale a 0")
    private BigDecimal prcava;

    @Basic
    @Column(name = "id_lisvalfasint", nullable = false)
    private Long idLisvalfasint;

    @Basic
    @Column(name = "id_lisvaltipolfas", nullable = true)
    private Long idlisvaltipolfas;

    @Basic
    @Column(name = "id_lisvallivcri", nullable = false)
    private Long idLisvallivcri;

    @Basic
    @HtmlEscape
    @Column(name = "notcri", nullable = true, length = 250)
    private String notcri;

    @Basic
    @Column(name = "id_lisvaltipapp", nullable = false)
    private Long idLisvaltipapp;

    @Basic
    @HtmlEscape
    @Column(name = "desapp", nullable = true, length = 250)
    private String desapp;

    @Basic
    @Column(name = "id_lisvalstaant", nullable = false)
    private Long idLisvalstaant;

    @Basic
    @Column(name = "impant", nullable = false, precision = 2)
    @Min(value = 0, message = "Il campo impant deve essere superiore o uguale a 0")
    private BigDecimal impant;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_pro", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProPro proProByIdPro;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_lisvalfasint", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProLisval proLisvalByIdLisvalfasint;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_lisvallivcri", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProLisval proLisvalByIdLisvallivcri;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_lisvaltipapp", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProLisval proLisvalByIdLisvaltipapp;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_lisvaltipolfas", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProLisval proLisvalByIdLisvaltipolfas;

    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    @JoinColumn(name = "id_lisvalstaant", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProLisval proLisvalByIdLisvalstaant;

    @OneToMany(mappedBy = "proAvaByIdAva", fetch = FetchType.LAZY)
    @Valid
    @OrderBy("dtainiori ASC")
    private List<ProFas> proFasById;

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

    public Long getNrover() {
        return nrover;
    }

    public void setNrover(Long nrover) {
        this.nrover = nrover;
    }

    public Date getDtarilava() {
        return dtarilava;
    }

    public void setDtarilava(Date dtarilava) {
        this.dtarilava = dtarilava;
    }

    public String getDesstaava() {
        return desstaava;
    }

    public void setDesstaava(String desstaava) {
        this.desstaava = desstaava;
    }

    public BigDecimal getPrcava() {
        return prcava;
    }

    public void setPrcava(BigDecimal prcava) {
        this.prcava = prcava;
    }

    public Long getIdLisvalfasint() {
        return idLisvalfasint;
    }

    public void setIdLisvalfasint(Long idLisvalfasint) {
        this.idLisvalfasint = idLisvalfasint;
    }

    public Long getIdLisvallivcri() {
        return idLisvallivcri;
    }

    public void setIdLisvallivcri(Long idLisvallivcri) {
        this.idLisvallivcri = idLisvallivcri;
    }

    public String getNotcri() {
        return notcri;
    }

    public void setNotcri(String notcri) {
        this.notcri = notcri;
    }

    public Long getIdLisvaltipapp() {
        return idLisvaltipapp;
    }

    public void setIdLisvaltipapp(Long idLisvaltipapp) {
        this.idLisvaltipapp = idLisvaltipapp;
    }

    public String getDesapp() {
        return desapp;
    }

    public void setDesapp(String desapp) {
        this.desapp = desapp;
    }

    public Long getIdLisvalstaant() {
        return idLisvalstaant;
    }

    public void setIdLisvalstaant(Long idLisvalstaant) {
        this.idLisvalstaant = idLisvalstaant;
    }

    public BigDecimal getImpant() {
        return impant;
    }

    public void setImpant(BigDecimal impant) {
        this.impant = impant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProAva proAva = (ProAva) o;

        if (id != proAva.id) return false;
        if (idPro != proAva.idPro) return false;
        if (nrover != proAva.nrover) return false;
        if (idLisvalfasint != proAva.idLisvalfasint) return false;
        if (idLisvallivcri != proAva.idLisvallivcri) return false;
        if (idLisvaltipapp != proAva.idLisvaltipapp) return false;
        if (idLisvalstaant != proAva.idLisvalstaant) return false;
        if (dtarilava != null ? !dtarilava.equals(proAva.dtarilava) : proAva.dtarilava != null) return false;
        if (desstaava != null ? !desstaava.equals(proAva.desstaava) : proAva.desstaava != null) return false;
        if (prcava != null ? !prcava.equals(proAva.prcava) : proAva.prcava != null) return false;
        if (notcri != null ? !notcri.equals(proAva.notcri) : proAva.notcri != null) return false;
        if (desapp != null ? !desapp.equals(proAva.desapp) : proAva.desapp != null) return false;
        if (impant != null ? !impant.equals(proAva.impant) : proAva.impant != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (idPro ^ (idPro >>> 32));
        result = 31 * result + (int) (nrover ^ (nrover >>> 32));
        result = 31 * result + (dtarilava != null ? dtarilava.hashCode() : 0);
        result = 31 * result + (desstaava != null ? desstaava.hashCode() : 0);
        result = 31 * result + (prcava != null ? prcava.hashCode() : 0);
        result = 31 * result + (int) (idLisvalfasint ^ (idLisvalfasint >>> 32));
        result = 31 * result + (int) (idLisvallivcri ^ (idLisvallivcri >>> 32));
        result = 31 * result + (notcri != null ? notcri.hashCode() : 0);
        result = 31 * result + (int) (idLisvaltipapp ^ (idLisvaltipapp >>> 32));
        result = 31 * result + (desapp != null ? desapp.hashCode() : 0);
        result = 31 * result + (int) (idLisvalstaant ^ (idLisvalstaant >>> 32));
        result = 31 * result + (impant != null ? impant.hashCode() : 0);

        return result;
    }

    public ProPro getProProByIdPro() {
        return proProByIdPro;
    }

    public void setProProByIdPro(ProPro proProByIdPro) {
        this.proProByIdPro = proProByIdPro;
    }

    public ProLisval getProLisvalByIdLisvalfasint() {
        return proLisvalByIdLisvalfasint;
    }

    public void setProLisvalByIdLisvalfasint(ProLisval proLisvalByIdLisvalfasint) {
        this.proLisvalByIdLisvalfasint = proLisvalByIdLisvalfasint;
    }

    public ProLisval getProLisvalByIdLisvallivcri() {
        return proLisvalByIdLisvallivcri;
    }

    public void setProLisvalByIdLisvallivcri(ProLisval proLisvalByIdLisvallivcri) {
        this.proLisvalByIdLisvallivcri = proLisvalByIdLisvallivcri;
    }

    public Long getIdlisvaltipolfas() {
        return idlisvaltipolfas;
    }

    public void setIdlisvaltipolfas(Long idlisvaltipolfas) {
        this.idlisvaltipolfas = idlisvaltipolfas;
    }

    public ProLisval getProLisvalByIdLisvaltipapp() {
        return proLisvalByIdLisvaltipapp;
    }

    public void setProLisvalByIdLisvaltipapp(ProLisval proLisvalByIdLisvaltipapp) {
        this.proLisvalByIdLisvaltipapp = proLisvalByIdLisvaltipapp;
    }

    public ProLisval getProLisvalByIdLisvalstaant() {
        return proLisvalByIdLisvalstaant;
    }

    public void setProLisvalByIdLisvalstaant(ProLisval proLisvalByIdLisvalstaant) {
        this.proLisvalByIdLisvalstaant = proLisvalByIdLisvalstaant;
    }

    public List<ProFas> getProFasById() {
        return proFasById;
    }

    public void setProFasById(List<ProFas> proFasById) {
        this.proFasById = proFasById;
    }

    public ProLisval getProLisvalByIdLisvaltipolfas() {
        return proLisvalByIdLisvaltipolfas;
    }

    public void setProLisvalByIdLisvaltipolfas(ProLisval proLisvalByIdLisvaltipolfas) {
        this.proLisvalByIdLisvaltipolfas = proLisvalByIdLisvaltipolfas;
    }

    public static ProAva generateCopy(ProAva item) {
        if (item == null) {
            return null;
        }
        ProAva result = new ProAva();
        result.setIdPro(item.getIdPro());
        result.setNrover(item.getNrover());
        result.setDtarilava(item.getDtarilava());
        result.setImpant(item.getImpant());
        result.setDesstaava(item.getDesstaava());
        result.setPrcava(item.getPrcava());
        result.setIdLisvalfasint(item.getIdLisvalfasint());
        result.setIdlisvaltipolfas(item.getIdlisvaltipolfas());
        result.setIdLisvallivcri(item.getIdLisvallivcri());
        result.setNotcri(item.getNotcri());
        result.setIdLisvaltipapp(item.getIdLisvaltipapp());
        result.setDesapp(item.getDesapp());
        result.setIdLisvalstaant(item.getIdLisvalstaant());
        return result;
    }
}
