package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProMun;
import it.dmi.dmifonbe.dmifonpro.model.MunicipioResponse;
import it.dmi.dmifonbe.dmifonpro.model.MunicipioRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface MunicipioService {
    List<ProMun> getAllMunicipi();
    MunicipioResponse ricercaMunicipio(MunicipioRicerca munProRic);
    void cancellaMunicipio(Integer id) throws MicroServicesException;
    ProMun inserisciMunicipio(ProMun proMunDaInserire) throws MicroServicesException;
    ProMun modificaMunicipio(ProMun proMunDaModificare) throws MicroServicesException;
    ProMun getMunicipio(Integer id) throws MicroServicesException;

    List<ProMun> ricercaMunicipiAutocomplete(String autocomplete) throws MicroServicesException;
}
