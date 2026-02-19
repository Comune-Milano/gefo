package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProDdra;
import it.dmi.dmifonbe.dmifonpro.model.AssociateDdrToDdra;
import it.dmi.dmifonbe.dmifonpro.model.DdraDetail;
import it.dmi.dmifonbe.dmifonpro.model.DdraRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface DdraService {
    DdraDetail getDdra(Integer id) throws MicroServicesException;

    ProDdra inserisciDdra(ProDdra ddraDaInserire) throws MicroServicesException;

    void modificaDdra(ProDdra ddraDaModificare) throws MicroServicesException;

    void cancellaDdra(Integer idDdra) throws MicroServicesException;

    void associaDdrADdra(AssociateDdrToDdra associateDdrToDdra) throws MicroServicesException;

    List<DdraDetail> ricercaDdra(DdraRicerca DdraRic) throws MicroServicesException;
    List<ProDdra> ricercaDdraAutocomplete(String autocomplete) throws MicroServicesException;
}
