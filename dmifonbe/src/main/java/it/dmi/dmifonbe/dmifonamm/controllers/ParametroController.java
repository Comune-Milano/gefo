package it.dmi.dmifonbe.dmifonamm.controllers;

import it.dmi.dmifonbe.dmifonamm.service.ParametroService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("Parametro-v1")
@RequestMapping("/api/parametro")
public class ParametroController {

    @Autowired
    ParametroService ParametroService;

    private Logger log = LoggerFactory.getLogger(ParametroController.class);

    @GetMapping(value = "/getParametroByCodice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getParametroByCodice(String codice) throws MicroServicesException {
        return ResponseEntity.ok(ParametroService.getParametroByCodice(codice));
    }

}
