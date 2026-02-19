package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize
public class MandatoRicerca implements Serializable {

    @HtmlEscape
    private String id_impegno;

    @HtmlEscape
    private String id_mandato;

    @HtmlEscape
    private String cig;

    @HtmlEscape
    private String anno_mandato;

    private int id_pro;

    @HtmlEscape
    private String flgNoDdr;

    public String getId_impegno() {
        return id_impegno;
    }

    public void setId_impegno(String id_impegno) {
        this.id_impegno = id_impegno;
    }

    public String getId_mandato() {
        return id_mandato;
    }

    public void setId_mandato(String id_mandato) {
        this.id_mandato = id_mandato;
    }

    public String getCig() {
        return cig;
    }

    public void setCig(String cig) {
        this.cig = cig;
    }

    public String getAnno_mandato() {
        return anno_mandato;
    }

    public void setAnno_mandato(String anno_mandato) {
        this.anno_mandato = anno_mandato;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getFlgNoDdr() {
        return flgNoDdr;
    }

    public void setFlgNoDdr(String flgNoDdr) {
        this.flgNoDdr = flgNoDdr;
    }

    public MultiValueMap toMultiValueMap() {
        MultiValueMap result = new LinkedMultiValueMap();
        if (this.getId_impegno() != null) result.add("id_impegno", this.getId_impegno());
        if (this.getCig() != null) result.add("cig", this.getCig());
        if (this.getAnno_mandato() != null) result.add("anno_mandato", this.getAnno_mandato());
        if (this.getId_mandato() != null) result.add("id_mandato", this.getId_mandato());
        if (this.getId_pro() != 0) result.add("id_pro", this.getId_pro());
        if (this.getFlgNoDdr() != null) result.add("flgNoDdr", this.getFlgNoDdr());
        return result;
    }
}
