package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.model.ExportAvanzamentiResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportAvanzamentiRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface ExportAvanzamentiService {
    //
    ExportAvanzamentiResponse exportAvanzamenti(ExportAvanzamentiRicerca parametri, Integer idUteRuo) throws MicroServicesException;
}
