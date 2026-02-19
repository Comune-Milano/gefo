package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class DatiMandato implements Serializable {

    private String id;
    private String id_impegno;
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
    private String anno_mandato;
    private String data_registrazione;
    private String data_pagamento;
    private String importo_mandato;
    private String tipo_atto;
    private String id_atto;
    private String cup;
    private String beneficiario_ragione_sociale;
    private String beneficiario_codice_fiscale;
    private String beneficiario_partita_iva;

    public String getTipo_atto() {
        return tipo_atto;
    }

    public void setTipo_atto(String tipo_atto) {
        this.tipo_atto = tipo_atto;
    }

    public String getId_atto() {
        return id_atto;
    }

    public void setId_atto(String id_atto) {
        this.id_atto = id_atto;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public String getBeneficiario_ragione_sociale() {
        return beneficiario_ragione_sociale;
    }

    public void setBeneficiario_ragione_sociale(String beneficiario_ragione_sociale) {
        this.beneficiario_ragione_sociale = beneficiario_ragione_sociale;
    }

    public String getBeneficiario_codice_fiscale() {
        return beneficiario_codice_fiscale;
    }

    public void setBeneficiario_codice_fiscale(String beneficiario_codice_fiscale) {
        this.beneficiario_codice_fiscale = beneficiario_codice_fiscale;
    }

    public String getBeneficiario_partita_iva() {
        return beneficiario_partita_iva;
    }

    public void setBeneficiario_partita_iva(String beneficiario_partita_iva) {
        this.beneficiario_partita_iva = beneficiario_partita_iva;
    }

    public String getImporto_mandato() {
        return importo_mandato;
    }

    public void setImporto_mandato(String importo_mandato) {
        this.importo_mandato = importo_mandato;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getId_impegno() {
        return id_impegno;
    }

    public void setId_impegno(String id_impegno) {
        this.id_impegno = id_impegno;
    }
}
