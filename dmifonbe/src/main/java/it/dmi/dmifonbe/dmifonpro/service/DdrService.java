package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProDdr;
import it.dmi.dmifonbe.dmifonpro.model.DdrRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface DdrService {
    ProDdr getDdr(Integer id) throws MicroServicesException;

    ProDdr inserisciDdr(ProDdr ddrDaInserire) throws MicroServicesException;

    void modificaDdr(ProDdr ddrDaModificare) throws MicroServicesException;

    void cancellaDdr(Integer idDdr) throws MicroServicesException;

    List<ProDdr> ricercaDdrAutocomplete(String autocomplete) throws MicroServicesException;

    List<ProDdr> ricercaDdr(DdrRicerca ddrRic, Integer idUteRuo);
}
