package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.model.BodyResponse;
import it.dmi.dmifonbe.dmifonpro.model.RicercaEntitaContabile;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface ContabilitaService {
    ResponseEntity<BodyResponse> clientApiContabilita(String uri, String codEntCon, Object ricEntCon, HttpMethod httpMethod)
        throws MicroServicesException;
    ResponseEntity<BodyResponse> clientExternalApiContabilita(String uri, String codEntCon, Object ricEntCon, HttpMethod httpMethod)
        throws MicroServicesException;
    String getUri(String parameter) throws MicroServicesException;
    String getBody(String codEntCon, Object ricEntCon) throws MicroServicesException;
}
