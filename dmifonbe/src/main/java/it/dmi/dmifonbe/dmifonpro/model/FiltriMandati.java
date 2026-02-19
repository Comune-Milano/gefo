package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class FiltriMandati implements Serializable {

    private String id_impegno;
    private String id_mandato;
    private String cig;
    private String anno_mandato;
    private Integer id_pro;
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

    public Integer getId_pro() {
        return id_pro;
    }

    public void setId_pro(Integer id_pro) {
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
    /*
    private String tipo;
    private String anno;
    private String numero_impegno;
    private String cig;
    private String descrizione_fattura;
    private String ragione_sociale;
    private String codice_fiscale;
    private String partita_iva;
    private String id_mandato;
    private String numero_mandato;
    private String data_registrazione;
    private String data_pagamento;
    private String anno_mandato;

    //ddg aggiunto forse è da eliminare numero_impegno che è in DatiMandato
    private String id_impegno;

    public String getId_impegno() {
        return id_impegno;
    }

    public void setId_impegno(String id_impegno) {
        this.id_impegno = id_impegno;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getNumero_impegno() {
        return numero_impegno;
    }

    public void setNumero_impegno(String numero_impegno) {
        this.numero_impegno = numero_impegno;
    }

    public String getCig() {
        return cig;
    }

    public void setCig(String cig) {
        this.cig = cig;
    }

    public String getDescrizione_fattura() {
        return descrizione_fattura;
    }

    public void setDescrizione_fattura(String descrizione_fattura) {
        this.descrizione_fattura = descrizione_fattura;
    }

    public String getRagione_sociale() {
        return ragione_sociale;
    }

    public void setRagione_sociale(String ragione_sociale) {
        this.ragione_sociale = ragione_sociale;
    }

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    public void setCodice_fiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    public String getPartita_iva() {
        return partita_iva;
    }

    public void setPartita_iva(String partita_iva) {
        this.partita_iva = partita_iva;
    }

    public String getId_mandato() {
        return id_mandato;
    }

    public void setId_mandato(String id_mandato) {
        this.id_mandato = id_mandato;
    }

    public String getNumero_mandato() {
        return numero_mandato;
    }

    public void setNumero_mandato(String numero_mandato) {
        this.numero_mandato = numero_mandato;
    }

    public String getData_registrazione() {
        return data_registrazione;
    }

    public void setData_registrazione(String data_registrazione) {
        this.data_registrazione = data_registrazione;
    }

    public String getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(String data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public String getAnno_mandato() {
        return anno_mandato;
    }

    public void setAnno_mandato(String anno_mandato) {
        this.anno_mandato = anno_mandato;
    }

    public MultiValueMap toMultiValueMap() {
        MultiValueMap result = new LinkedMultiValueMap();
        if (this.getTipo() != null) result.add("tipo", this.getTipo());
        if (this.getAnno() != null) result.add("anno", this.getAnno());
        if (this.getAnno_mandato() != null) result.add("anno_mandato", this.getAnno_mandato());
        if (this.getNumero_impegno() != null) result.add("numero_impegno", this.getNumero_impegno());
        if (this.getDescrizione_fattura() != null) result.add("descrizione_fattura", this.getDescrizione_fattura());
        if (this.getRagione_sociale() != null) result.add("ragione_sociale", this.getRagione_sociale());
        if (this.getCodice_fiscale() != null) result.add("codice_fiscale", this.getCodice_fiscale());
        if (this.getPartita_iva() != null) result.add("partita_iva", this.getPartita_iva());
        if (this.getId_mandato() != null) result.add("id_mandato", this.getId_mandato());
        if (this.getNumero_mandato() != null) result.add("numero_mandato", this.getNumero_mandato());
        if (this.getData_registrazione() != null) result.add("data_registrazione", this.getData_registrazione());
        if (this.getData_pagamento() != null) result.add("data_pagamento", this.getData_pagamento());
        return result;
    }

 */
}
