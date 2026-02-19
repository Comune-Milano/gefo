package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfin;
import it.dmi.dmifonbe.model.Totali;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ProTipFinResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProTipfin tipoFinanziamento;
    private Totali totali;
    private Totali totaliImpegni;
    private Totali totaliDdr;
    private boolean hasChild;

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public Totali getTotaliImpegni() {
        return totaliImpegni;
    }

    public void setTotaliImpegni(Totali totaliImpegni) {
        this.totaliImpegni = totaliImpegni;
    }

    public Totali getTotaliDdr() {
        return totaliDdr;
    }

    public void setTotaliDdr(Totali totaliDdr) {
        this.totaliDdr = totaliDdr;
    }

    public ProTipfin getTipoFinanziamento() {
        return tipoFinanziamento;
    }

    public void setTipoFinanziamento(ProTipfin tipoFinanziamento) {
        this.tipoFinanziamento = tipoFinanziamento;
    }

    public Totali getTotali() {
        return totali;
    }

    public void setTotali(Totali totali) {
        this.totali = totali;
    }
}
