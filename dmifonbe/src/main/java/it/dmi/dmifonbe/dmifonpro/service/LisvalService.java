package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProLisval;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.model.ListaValoriResponse;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface LisvalService {
    List<ProLisval> getListaValori(String tipologia) throws MicroServicesException;
    List<ProLisval> getListaValoriTipoLista() throws MicroServicesException;
    ListaValoriResponse ricercaListaValori(AutocompleteRicerca lisValRic);
    ProLisval inserisciListaValore(ProLisval proLisValDaInserire) throws MicroServicesException;
    ProLisval modificaListaValore(ProLisval proLisValDaModificare) throws MicroServicesException;
    ProLisval getListaValore(Integer id) throws MicroServicesException;
    void cancellaListaValore(Integer id) throws MicroServicesException;
}
