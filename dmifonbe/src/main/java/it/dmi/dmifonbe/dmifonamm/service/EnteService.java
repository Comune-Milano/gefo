package it.dmi.dmifonbe.dmifonamm.service;

import it.dmi.dmifonbe.dmifonamm.entities.AmmEnt;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface EnteService {
    AmmEnt getEnte() throws MicroServicesException;

}
