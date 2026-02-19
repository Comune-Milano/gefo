package it.dmi.dmifonbe.dmifonpro.model;

import it.dmi.dmifonbe.dmifonpro.entities.ProDdr;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntconman;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;

public class MandatoResponse {

    private static final long serialVersionUID = 1978760595385556376L;

    ProEntconman proEntconman;

    ProDdr proDdr;

    ProPro proPro;

    private String warningMessage;

    public ProEntconman getProEntconman() {
        return proEntconman;
    }

    public void setProEntconman(ProEntconman proEntconman) {
        this.proEntconman = proEntconman;
    }

    public ProDdr getProDdr() {
        return proDdr;
    }

    public void setProDdr(ProDdr proDdr) {
        this.proDdr = proDdr;
    }

    public ProPro getProPro() {
        return proPro;
    }

    public void setProPro(ProPro proPro) {
        this.proPro = proPro;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
