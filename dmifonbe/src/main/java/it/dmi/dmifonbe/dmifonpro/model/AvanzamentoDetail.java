package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class AvanzamentoDetail implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @Valid
    private ProAva avanzamento;

    @Valid
    @HtmlEscape
    private String progettoPadreLabel;

    @Valid
    private List<FaseDetail> fasi;

    @Valid
    private Date datinilav;

    @Valid
    private Date datfinlav;

    public ProAva getAvanzamento() {
        return avanzamento;
    }

    public void setAvanzamento(ProAva avanzamento) {
        this.avanzamento = avanzamento;
    }

    public String getProgettoPadreLabel() {
        return progettoPadreLabel;
    }

    public void setProgettoPadreLabel(String progettoPadreLabel) {
        this.progettoPadreLabel = progettoPadreLabel;
    }

    public List<FaseDetail> getFasi() {
        return fasi;
    }

    public void setFasi(List<FaseDetail> fasi) {
        this.fasi = fasi;
    }

    public Date getDatinilav() {
        return datinilav;
    }

    public void setDatinilav(Date datinilav) {
        this.datinilav = datinilav;
    }

    public Date getDatfinlav() {
        return datfinlav;
    }

    public void setDatfinlav(Date datfinlav) {
        this.datfinlav = datfinlav;
    }
}
