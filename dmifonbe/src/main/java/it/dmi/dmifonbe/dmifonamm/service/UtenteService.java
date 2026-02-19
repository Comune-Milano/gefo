package it.dmi.dmifonbe.dmifonamm.service;

import it.dmi.dmifonbe.dmifonamm.entities.AmmUte;
import it.dmi.dmifonbe.dmifonamm.entities.AmmUteruo;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface UtenteService {
    AmmUte getUtente(Integer id, Integer idUteRuo) throws MicroServicesException;

    AmmUte inserisciUtente(AmmUte utenteDaInserire) throws MicroServicesException;

    void abilitazioneUtente(Integer idUtente, boolean abilitato) throws MicroServicesException;

    AmmUte modificaUtente(AmmUte utenteDaModificare) throws MicroServicesException;

    List<AmmUte> ricercaUtente(String username, String cognome, String autocomplete, Integer idUteRuo) throws MicroServicesException;

    AmmUte ricercaUtenteRuolo(String username) throws MicroServicesException;

    AmmUteruo associaRuoloDirezione(Integer idUtente, Integer idRuolo, Integer idDirezione) throws MicroServicesException;
    void cancellaRuoloDirezione(Integer idUtente, Integer idAmmUteruo) throws MicroServicesException;
}
