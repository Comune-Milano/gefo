package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import it.dmi.dmifonbe.dmifonpro.entities.ProLisval;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class AvaResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProAva avanzamento;
    private ProLisval faseIntervento;
    private ProLisval livelloCriticita;
    private Semaforo semaforoAvanzamento;
    private String codDesProgetto;

    public ProAva getAvanzamento() {
        return avanzamento;
    }

    public void setAvanzamento(ProAva avanzamento) {
        this.avanzamento = avanzamento;
    }

    public ProLisval getFaseIntervento() {
        return faseIntervento;
    }

    public void setFaseIntervento(ProLisval faseIntervento) {
        this.faseIntervento = faseIntervento;
    }

    public ProLisval getLivelloCriticita() {
        return livelloCriticita;
    }

    public void setLivelloCriticita(ProLisval livelloCriticita) {
        this.livelloCriticita = livelloCriticita;
    }

    public String getCodDesProgetto() {
        return codDesProgetto;
    }

    public void setCodDesProgetto(String codDesProgetto) {
        this.codDesProgetto = codDesProgetto;
    }

    public Semaforo getSemaforoAvanzamento() {
        return semaforoAvanzamento;
    }

    public void setSemaforoAvanzamento(Semaforo semaforoAvanzamento) {
        this.semaforoAvanzamento = semaforoAvanzamento;
    }
}
