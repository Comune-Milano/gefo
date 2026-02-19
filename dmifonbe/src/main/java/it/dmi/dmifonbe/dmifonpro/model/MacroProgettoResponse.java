package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class MacroProgettoResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<MacProResponse> macroProgetti;

    private String warningMessage;

    public MacroProgettoResponse() {
        this.macroProgetti = new ArrayList<>();
    }

    public List<MacProResponse> getMacroProgetti() {
        return macroProgetti;
    }

    public void setMacroProgetti(List<MacProResponse> macroProgetti) {
        this.macroProgetti = macroProgetti;
    }

    public void addMacroProgetto(MacProResponse macroProgetto) {
        this.macroProgetti.add(macroProgetto);
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
