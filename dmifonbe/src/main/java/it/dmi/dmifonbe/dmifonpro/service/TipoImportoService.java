package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipimp;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.model.TipoImportoResponse;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface TipoImportoService {
    TipoImportoResponse ricercaTipoImporto(AutocompleteRicerca tipImpRic);
    ProTipimp getTipoImporto(Integer id) throws MicroServicesException;
    ProTipimp inserisciTipoImporto(ProTipimp proTipimpDaInserire) throws MicroServicesException;
    ProTipimp modificaTipoImporto(ProTipimp proTipImpDaModificare) throws MicroServicesException;
    void cancellaTipoImporto(Integer id) throws MicroServicesException;
}
