package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonamm.entities.AmmDir;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProImp;
import it.dmi.dmifonbe.dmifonpro.entities.ProInfagg;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.model.Totali;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ProgettoDetail implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @Valid
    private ProPro progetto;

    @Valid
    private ProPro progettoPadre;

    @Valid
    private AmmDir progettoDirezione;

    @Valid
    private List<ProImp> risorse;

    @Valid
    private List<ProImp> risorseDiCui;

    @Valid
    private List<ProInfagg> infoAggiuntive;

    @Valid
    private List<Risorse> risorseLivelloBasso;

    @Valid
    private Totali totaliFondi;

    @Valid
    private ProEntcon proEntconSettore;

    @Valid
    private ProEntcon proEntconAssessorato;

    public ProEntcon getProEntconSettore() {
        return proEntconSettore;
    }

    public void setProEntconSettore(ProEntcon proEntconSettore) {
        this.proEntconSettore = proEntconSettore;
    }

    public ProEntcon getProEntconAssessorato() {
        return proEntconAssessorato;
    }

    public void setProEntconAssessorato(ProEntcon proEntconAssessorato) {
        this.proEntconAssessorato = proEntconAssessorato;
    }

    public Totali getTotaliFondi() {
        return totaliFondi;
    }

    public void setTotaliFondi(Totali totaliFondi) {
        this.totaliFondi = totaliFondi;
    }

    public ProPro getProgetto() {
        return progetto;
    }

    public void setProgetto(ProPro progetto) {
        this.progetto = progetto;
    }

    public List<ProImp> getRisorse() {
        return risorse;
    }

    public void setRisorse(List<ProImp> risorse) {
        this.risorse = risorse;
    }

    public List<ProImp> getRisorseDiCui() {
        return risorseDiCui;
    }

    public void setRisorseDiCui(List<ProImp> risorseDiCui) {
        this.risorseDiCui = risorseDiCui;
    }

    public List<ProInfagg> getInfoAggiuntive() {
        return infoAggiuntive;
    }

    public void setInfoAggiuntive(List<ProInfagg> infoAggiuntive) {
        this.infoAggiuntive = infoAggiuntive;
    }

    public List<Risorse> getRisorseLivelloBasso() {
        return risorseLivelloBasso;
    }

    public void setRisorseLivelloBasso(List<Risorse> risorseLivelloBasso) {
        this.risorseLivelloBasso = risorseLivelloBasso;
    }

    public ProPro getProgettoPadre() {
        return progettoPadre;
    }

    public void setProgettoPadre(ProPro progettoPadre) {
        this.progettoPadre = progettoPadre;
    }

    public AmmDir getProgettoDirezione() {
        return progettoDirezione;
    }

    public void setProgettoDirezione(AmmDir progettoDirezione) {
        this.progettoDirezione = progettoDirezione;
    }
}
