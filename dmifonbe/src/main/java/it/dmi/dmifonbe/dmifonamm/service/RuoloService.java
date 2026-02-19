package it.dmi.dmifonbe.dmifonamm.service;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPer;
import it.dmi.dmifonbe.dmifonamm.entities.AmmRuo;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface RuoloService {
    AmmRuo getRuolo(Integer id) throws MicroServicesException;

    AmmRuo inserisciRuolo(AmmRuo RuoloDaInserire) throws MicroServicesException;

    void cancellaRuolo(Integer idRuolo);

    AmmRuo modificaRuolo(AmmRuo RuoloDaModificare);

    List<AmmRuo> ricercaRuolo(String codRuo, String desRuo, String autocomplete);

    AmmPer associaFunzione(Integer idRuolo, Integer idFunzione) throws MicroServicesException;
    void dissociaFunzione(Integer idRuolo, Integer idFunzione) throws MicroServicesException;

    List<String> getPermessiRuolo(Integer idRuolo) throws MicroServicesException;
}
