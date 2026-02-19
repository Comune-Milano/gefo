package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProNil;
import it.dmi.dmifonbe.dmifonpro.model.NilResponse;
import it.dmi.dmifonbe.dmifonpro.model.NilRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface NilService {
    List<ProNil> ricercaNilAutocomplete(String nilRic) throws MicroServicesException;
    NilResponse ricercaNil(NilRicerca proNilRic);
    ProNil inserisciNil(ProNil proNilDaInserire) throws MicroServicesException;

    ProNil modificaNil(ProNil proNilDaModificare) throws MicroServicesException;

    void cancellaNil(Integer id) throws MicroServicesException;

    ProNil getNil(Integer id) throws MicroServicesException;
}
