package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

import java.util.List;

public interface ProgettoService {
    ProgettoDetail getProgetto(Integer id) throws MicroServicesException;

    ProPro inserisciProgetto(ProPro proDaInserire) throws MicroServicesException;

    ProPro inserisciProgettoFiglio(Integer idPro) throws MicroServicesException, CloneNotSupportedException;

    ProPro modificaImportoRisorseFiglio(Integer idPro) throws MicroServicesException, CloneNotSupportedException;

    void cancellaProgetto(Integer id) throws MicroServicesException;

    ProPro modificaProgetto(ProgettoDetail proDaModificare) throws MicroServicesException;

    ProPro modificaProgetto(ProPro proDaModificare) throws MicroServicesException;

    List<Settore> getAllSettori() throws MicroServicesException;

    List<Assessorato> getAllAssessorati() throws MicroServicesException;

    public List<ProEntcon> ricercaSettoriAutocomplete(String autocomplete) throws MicroServicesException;

    public List<ProEntcon> ricercaAssessoratiAutocomplete(String autocomplete) throws MicroServicesException;

    ProgettoResponse ricercaProgetto(ProgettoRicerca proRic, Integer idUteRuo) throws MicroServicesException;

    List<ProPro> ricercaProgetto(String autocomplete, Integer idUteRuo) throws MicroServicesException;

}
