package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProMacpro;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfin;
import it.dmi.dmifonbe.dmifonpro.model.MacroProgettoResponse;
import it.dmi.dmifonbe.dmifonpro.model.MacroProgettoRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface MacroProgettoService {
    ProMacpro getMacroProgetto(Integer id) throws MicroServicesException;

    ProMacpro inserisciMacroProgetto(ProMacpro macProDaInserire) throws MicroServicesException;

    ProMacpro modificaMacroProgetto(ProMacpro macProDaModificare) throws MicroServicesException;

    void cancellaMacroProgetto(Integer id) throws MicroServicesException;
    MacroProgettoResponse ricercaMacroProgetto(MacroProgettoRicerca macProRic);
    List<ProMacpro> ricercaMacroProgettiAutocomplete(String autocomplete) throws MicroServicesException;
}
