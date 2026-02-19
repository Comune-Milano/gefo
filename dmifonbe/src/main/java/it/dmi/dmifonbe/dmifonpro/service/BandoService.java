package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProBan;
import it.dmi.dmifonbe.dmifonpro.entities.ProMacpro;
import it.dmi.dmifonbe.dmifonpro.model.BandoResponse;
import it.dmi.dmifonbe.dmifonpro.model.BandoRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface BandoService {
    ProBan getBando(Integer id) throws MicroServicesException;

    ProBan inserisciBando(ProBan banDaInserire) throws MicroServicesException;

    ProBan modificaBando(ProBan banDaModificare) throws MicroServicesException;

    void cancellaBando(Integer id) throws MicroServicesException;
    BandoResponse ricercaBando(BandoRicerca banRic) throws MicroServicesException;

    List<ProBan> ricercaBandoAutocomplete(String autocomplete) throws MicroServicesException;
}
