package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProMil;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class MilestoneDetail implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private ProMil milestone;
    private Semaforo semaforoMilestone;

    public ProMil getMilestone() {
        return milestone;
    }

    public void setMilestone(ProMil milestone) {
        this.milestone = milestone;
    }

    public Semaforo getSemaforoMilestone() {
        return semaforoMilestone;
    }

    public void setSemaforoMilestone(Semaforo semaforoMilestone) {
        this.semaforoMilestone = semaforoMilestone;
    }
}
