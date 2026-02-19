package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize
public class MunicipioResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<MunProResponse> proMun;

    private String warningMessage;

    public MunicipioResponse() {
        this.proMun = new ArrayList<>();
    }

    public List<MunProResponse> getProMun() {
        return proMun;
    }

    public void setProMun(List<MunProResponse> proMun) {
        this.proMun = proMun;
    }

    public void addMunicipio(MunProResponse proMun) {
        this.proMun.add(proMun);
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
