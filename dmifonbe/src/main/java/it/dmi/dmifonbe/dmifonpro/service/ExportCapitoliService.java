package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.model.ExportCapitoliResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportCapitoliRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface ExportCapitoliService {
    //
    ExportCapitoliResponse exportCapitoli(ExportCapitoliRicerca parametri, Integer idUteRuo) throws MicroServicesException;
}
