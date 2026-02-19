package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.model.ExportMandatiResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportMandatiRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface ExportMandatiService {
    //
    ExportMandatiResponse exportMandati(ExportMandatiRicerca parametri, Integer idUteRuo) throws MicroServicesException;
}
