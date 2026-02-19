package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipinfagg;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.model.TipiInformazioneResponse;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface TipoInformazioneService {
    TipiInformazioneResponse ricercaTipoInformazione(AutocompleteRicerca tipInRic);
    ProTipinfagg inserisciTipoInformazione(ProTipinfagg proTipinfaggDaInserire) throws MicroServicesException;
    ProTipinfagg modificaTipoInformazione(ProTipinfagg proTipinfaggDaModificare) throws MicroServicesException;
    ProTipinfagg getTipoInformazione(Integer id) throws MicroServicesException;
    void cancellaTipoInformazione(Integer id) throws MicroServicesException;
}
