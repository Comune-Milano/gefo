package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.model.ExportDdrResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportDdrRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface ExportDdrService {
    //
    ExportDdrResponse exportDdr(ExportDdrRicerca parametri, Integer idUteRuo) throws MicroServicesException;
}
