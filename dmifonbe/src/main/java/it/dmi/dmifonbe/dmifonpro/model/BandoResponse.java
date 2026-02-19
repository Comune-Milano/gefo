package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class BandoResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<BanResponse> bandi;

    private String warningMessage;

    public BandoResponse() {
        this.bandi = new ArrayList<>();
    }

    public List<BanResponse> getBandi() {
        return bandi;
    }

    public void setBandi(List<BanResponse> bandi) {
        this.bandi = bandi;
    }

    public void addBando(BanResponse bando) {
        this.bandi.add(bando);
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
