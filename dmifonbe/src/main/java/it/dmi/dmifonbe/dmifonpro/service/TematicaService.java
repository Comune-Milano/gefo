package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProTem;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfin;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.model.TematicaResponse;
import it.dmi.dmifonbe.dmifonpro.model.TematicaRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface TematicaService {
    ProTem getTematica(Integer id) throws MicroServicesException;

    ProTem inserisciTematica(ProTem temDaInserire) throws MicroServicesException;

    ProTem modificaTematica(ProTem temDaModificare) throws MicroServicesException;

    void cancellaTematica(Integer id) throws MicroServicesException;
    TematicaResponse ricercaTematica(TematicaRicerca temRic);
    List<ProTem> ricercaTematicaAutocomplete(String temRic) throws MicroServicesException;
    List<ProTem> getEredi(ProTem padre);
}
