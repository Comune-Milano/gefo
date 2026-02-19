package it.dmi.dmifonbe.dmifonamm.service;

import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface ParametroService {
    String getParametroByCodice(String codice) throws MicroServicesException;

}
