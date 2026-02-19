package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface AvanzamentoService {
    AvanzamentoDetail getAvanzamento(Integer id) throws MicroServicesException;
    ProAva inserisciAvanzamento(ProAva avaDaInserire) throws MicroServicesException;
    void creaFasiAvanzamento(Integer idAva) throws MicroServicesException;
    void modificaAvanzamento(AvanzamentoDetail avaDaModificare) throws MicroServicesException;
    void cancellaFase(Integer idFase) throws MicroServicesException;
    List<ProTipfas> getTipoFase() throws MicroServicesException;

    void cancellaAvanzamento(Integer idAva) throws MicroServicesException;

    AvanzamentoResponse ricercaAvanzamenti(AvanzamentoRicerca avaRi, Integer idUteRuo) throws MicroServicesException;
}
