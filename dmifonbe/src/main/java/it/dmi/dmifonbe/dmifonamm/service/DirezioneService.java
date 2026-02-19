package it.dmi.dmifonbe.dmifonamm.service;

import it.dmi.dmifonbe.dmifonamm.entities.AmmDir;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface DirezioneService {
    AmmDir getDirezione(Integer id) throws MicroServicesException;

    AmmDir inserisciDirezione(AmmDir DirezioneDaInserire);

    void cancellaDirezione(Integer idDirezione);

    AmmDir modificaDirezione(AmmDir DirezioneDaModificare);

    List<AmmDir> ricercaDirezione(String codDir, String desDir, String autocomplete);
}
