package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.model.MandatoResponse;
import it.dmi.dmifonbe.dmifonpro.model.MandatoRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.text.ParseException;
import java.util.List;

public interface MandatiService {
    MandatoResponse getMandato(String codEntCon) throws MicroServicesException, ParseException;
    List<MandatoResponse> ricercaMandato(MandatoRicerca filtriMandati) throws MicroServicesException, ParseException;
    //
    //    List<MandatoResponse> ricercaReversale(MandatoRicerca mandatoRicerca) throws MicroServicesException;
    //
    //    MandatoResponse getReversale(String codEntCon) throws MicroServicesException;
}
