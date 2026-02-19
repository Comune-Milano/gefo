package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.model.ExportImpegniResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportImpegniRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface ExportImpegniService {
    //
    ExportImpegniResponse exportImpegni(ExportImpegniRicerca parametri, Integer idUteRuo) throws MicroServicesException;
}
