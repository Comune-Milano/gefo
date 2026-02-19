package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonamm.entities.AmmAll;
import it.dmi.dmifonbe.dmifonamm.entities.AmmFil;
import it.dmi.dmifonbe.dmifonamm.model.AllegatoResponse;
import it.dmi.dmifonbe.dmifonamm.model.AmmFilBase64;
import it.dmi.dmifonbe.dmifonamm.model.CaricaAllegatoModel;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.io.UnsupportedEncodingException;

public interface AllegatoService {
    AllegatoResponse getAllegato(Integer idEnt, String tipEnt) throws MicroServicesException;
    AmmAll caricaFile(CaricaAllegatoModel allegatoDaCaricare) throws MicroServicesException;
    AmmFilBase64 scaricaFile(Integer idFile) throws MicroServicesException;
    void modificaAllegato(AmmAll allegatoDaModificare) throws MicroServicesException;
    void cancellaAllegato(Integer idAllegato) throws MicroServicesException;
}
