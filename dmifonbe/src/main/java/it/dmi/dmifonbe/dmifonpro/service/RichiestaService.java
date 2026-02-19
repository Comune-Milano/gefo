package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.entities.ProRic;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface RichiestaService {
    ResponseRichiesta getRichiesta(Integer id) throws MicroServicesException;
    ProRic inserisciRichiesta(ProRic ricDaInserire) throws MicroServicesException;
    void modificaRichiesta(ProRic ricDaModificare) throws MicroServicesException;
    void cancellaRichiesta(Integer idRic) throws MicroServicesException;
    void cancellaRichiestaUtente(Integer idRicUte) throws MicroServicesException;
    List<RichiestaDetail> ricercaRichiesta(RichiestaRicerca ricRic, Integer idUteRuo);
    List<ProRic> ricercaRichiesta(String autocomplete, Integer idUteRuo) throws MicroServicesException;
}
