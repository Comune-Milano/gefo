package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ContentResponse implements Serializable {

    private String cig;
    private String codice_gami;
    private String cup;
    private String descrizione;
    private String descrizione_PDD;
    private String id;
    private String id_capitolo;
    private String id_crono;
    private String importo_assestato;
    private String importo_economie;
    private String importo_liquidato;
    private String importo_mandati;
    private String importo_mandati_emessi;
    private String numero_contratto_applicativo;
    private String anno;

    public String getCig() {
        return cig;
    }

    public void setCig(String cig) {
        this.cig = cig;
    }

    public String getCodice_gami() {
        return codice_gami;
    }

    public void setCodice_gami(String codice_gami) {
        this.codice_gami = codice_gami;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione_PDD() {
        return descrizione_PDD;
    }

    public void setDescrizione_PDD(String descrizione_PDD) {
        this.descrizione_PDD = descrizione_PDD;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImporto_assestato() {
        return importo_assestato;
    }

    public void setImporto_assestato(String importo_assestato) {
        this.importo_assestato = importo_assestato;
    }

    public String getImporto_economie() {
        return importo_economie;
    }

    public void setImporto_economie(String importo_economie) {
        this.importo_economie = importo_economie;
    }

    public String getImporto_liquidato() {
        return importo_liquidato;
    }

    public void setImporto_liquidato(String importo_liquidato) {
        this.importo_liquidato = importo_liquidato;
    }

    public String getImporto_mandati() {
        return importo_mandati;
    }

    public void setImporto_mandati(String importo_mandati) {
        this.importo_mandati = importo_mandati;
    }

    public String getImporto_mandati_emessi() {
        return importo_mandati_emessi;
    }

    public void setImporto_mandati_emessi(String importo_mandati_emessi) {
        this.importo_mandati_emessi = importo_mandati_emessi;
    }

    public String getNumero_contratto_applicativo() {
        return numero_contratto_applicativo;
    }

    public void setNumero_contratto_applicativo(String numero_contratto_applicativo) {
        this.numero_contratto_applicativo = numero_contratto_applicativo;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }
}
