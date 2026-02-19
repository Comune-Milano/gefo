package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProRes;
import it.dmi.dmifonbe.dmifonpro.model.ResponsabileResponse;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface ResponsabileService {
    ResponsabileResponse getResponsabiliProgetto(Integer idPro) throws MicroServicesException;
    void modificaResponsabiliProgetto(List<ProRes> responsabiliDaModificare) throws MicroServicesException;
}
