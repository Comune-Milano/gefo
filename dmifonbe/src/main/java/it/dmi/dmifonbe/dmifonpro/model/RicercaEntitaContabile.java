package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize
public class RicercaEntitaContabile implements Serializable {

    @HtmlEscape
    private String ente;

    @HtmlEscape
    private String codiceUtente;

    @HtmlEscape
    private String id;

    @HtmlEscape
    private String descrizione;

    @HtmlEscape
    private String id_capitolo;

    @HtmlEscape
    private String id_crono;

    @HtmlEscape
    private String cup;

    @HtmlEscape
    private String cig;

    @HtmlEscape
    private String anno;

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getCodiceUtente() {
        return codiceUtente;
    }

    public void setCodiceUtente(String codiceUtente) {
        this.codiceUtente = codiceUtente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getId_capitolo() {
        return id_capitolo;
    }

    public void setId_capitolo(String id_capitolo) {
        this.id_capitolo = id_capitolo;
    }

    public String getId_crono() {
        return id_crono;
    }

    public void setId_crono(String id_crono) {
        this.id_crono = id_crono;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public String getCig() {
        return cig;
    }

    public void setCig(String cig) {
        this.cig = cig;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public MultiValueMap toMultiValueMap() {
        MultiValueMap result = new LinkedMultiValueMap();
        if (this.getEnte() != null) result.add("ente", this.getEnte());
        if (this.getCodiceUtente() != null) result.add("codiceUtente", this.getCodiceUtente());
        if (this.getId() != null) result.add("id", this.getId());
        if (this.getDescrizione() != null) result.add("descrizione", this.getDescrizione());
        if (this.getId_capitolo() != null) result.add("id_capitolo", this.getId_capitolo());
        if (this.getId_crono() != null) result.add("id_crono", this.getId_crono());
        if (this.getCup() != null) result.add("cup", this.getCup());
        if (this.getCig() != null) result.add("cig", this.getCig());
        if (this.getAnno() != null) result.add("anno", this.getAnno());

        return result;
    }
}
