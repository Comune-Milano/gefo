package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProLisval;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize
public class ListaValoriResponse {

    private static final long serialVersionUID = 1978760595385556376L;

    private List<ProLisval> proLisvalpro;

    private String warningMessage;

    public List<ProLisval> getProLisval() {
        return proLisvalpro;
    }

    public void setProLisval(List<ProLisval> proLisval) {
        this.proLisvalpro = proLisval;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public void addListaValore(ProLisval proLisval) {
        this.proLisvalpro.add(proLisval);
    }
}
