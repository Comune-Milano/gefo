package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProPre;
import it.dmi.dmifonbe.dmifonpro.model.PrevisioneRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface PrevisioneService {
    ProPre getPrevisione(Integer id) throws MicroServicesException;

    ProPre inserisciPrevisione(ProPre previsioneDaInserire) throws MicroServicesException;

    void modificaPrevisione(ProPre previsioneDaModificare) throws MicroServicesException;

    void cancellaPrevisione(Integer idPre) throws MicroServicesException;

    boolean getBloccaPrevisioni() throws MicroServicesException;

    void bloccaSbloccaPrevisioni(Boolean param) throws MicroServicesException;

    List<ProPre> ricercaPrevisione(PrevisioneRicerca preRic, Integer idUteRuo);
}
