package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.model.CapitoliProgettoResponse;
import it.dmi.dmifonbe.dmifonpro.model.DatiCapitolo;
import it.dmi.dmifonbe.dmifonpro.model.FiltriCapitoli;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

import java.util.List;

public interface CapitoliService {
    CapitoliProgettoResponse getCapitoliProgetto(Integer idPro) throws MicroServicesException;
    void modificaCapitoliProgetto(CapitoliProgettoResponse capitoliDaModificare) throws MicroServicesException;
    void cancellaCapitoliProgetto(Integer idDetCon) throws MicroServicesException;
    DatiCapitolo getCapitoli(String codEntCon, String parameter) throws MicroServicesException;
    List<DatiCapitolo> ricercaCapitoli(FiltriCapitoli filtriCapitoli, String parameter) throws MicroServicesException;
}
