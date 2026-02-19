package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.model.Semaforo;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface SemaforoService {
    Semaforo getSemaforoFase(Integer id);
    Semaforo getSemaforoMilestone(Integer id);

    Semaforo getSemaforoMilestoneFase(Integer id);

    Semaforo getSemaforoAvanzamento(Integer id) throws MicroServicesException;
}
