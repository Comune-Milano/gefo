package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.model.ExportProgettiResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportProgettiRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface ExportProgettiService {
    //
    ExportProgettiResponse exportProgetti(ExportProgettiRicerca parametri, Integer idUteRuo) throws MicroServicesException;
}
