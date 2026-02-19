package it.dmi.dmifonbe.dmifonpro.model;

import java.util.ArrayList;
import java.util.List;

public class NilResponse {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<ProNilResponse> nil;

    private String warningMessage;

    public List<ProNilResponse> getNil() {
        return nil;
    }

    public NilResponse() {
        this.nil = new ArrayList<>();
    }

    public void setNil(List<ProNilResponse> nil) {
        this.nil = nil;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void addNil(ProNilResponse nil) {
        this.nil.add(nil);
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
