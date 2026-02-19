package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.model.TipoFaseResponse;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface TipoFaseService {
    TipoFaseResponse ricercaTipoFase(AutocompleteRicerca tipFasRic);
    ProTipfas getTipoFase(Integer id) throws MicroServicesException;
    ProTipfas inserisciTipoFase(ProTipfas proTipFasDaInserire) throws MicroServicesException;
    ProTipfas modificaTipoFase(ProTipfas proTipFasDaModificare) throws MicroServicesException;
    void cancellaTipoFase(Integer id) throws MicroServicesException;
}
