package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfin;
import it.dmi.dmifonbe.dmifonpro.model.TipoFinanziamentoResponse;
import it.dmi.dmifonbe.dmifonpro.model.TipoFinanziamentoRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface TipoFinanziamentoService {
    ProTipfin getTipoFinanziamento(Integer id) throws MicroServicesException;

    ProTipfin inserisciTipoFinanziamento(ProTipfin tipFinDaInserire) throws MicroServicesException;

    ProTipfin modificaTipoFinanziamento(ProTipfin tipfinDaModificare) throws MicroServicesException;

    void cancellaTipoFinanziamento(Integer id) throws MicroServicesException;
    TipoFinanziamentoResponse ricercaTipoFinanziamento(TipoFinanziamentoRicerca tipFinric) throws MicroServicesException;
    List<ProTipfin> ricercaTipoFinanziamentoAutocomplete(String autocomplete) throws MicroServicesException;

    List<ProTipfin> getEredi(ProTipfin padre);
}
