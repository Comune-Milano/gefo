package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.model.Totali;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class TipoFinanziamentoResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<ProTipFinResponse> tipiFinanziamento;

    private String warningMessage;

    public TipoFinanziamentoResponse() {
        this.tipiFinanziamento = new ArrayList<>();
    }

    public List<ProTipFinResponse> getTipiFinanziamento() {
        return tipiFinanziamento;
    }

    public void setTipiFinanziamento(List<ProTipFinResponse> tipiFinanziamento) {
        this.tipiFinanziamento = tipiFinanziamento;
    }

    public void addTipoFinanziamento(ProTipFinResponse tipoFinanziamento) {
        this.tipiFinanziamento.add(tipoFinanziamento);
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
