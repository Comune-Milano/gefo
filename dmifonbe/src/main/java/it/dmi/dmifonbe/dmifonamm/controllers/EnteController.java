package it.dmi.dmifonbe.dmifonamm.controllers;

import it.dmi.dmifonbe.dmifonamm.entities.AmmEnt;
import it.dmi.dmifonbe.dmifonamm.service.EnteService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ente-v1")
@RequestMapping("/api/ente")
public class EnteController {

    @Autowired
    EnteService enteService;

    private Logger log = LoggerFactory.getLogger(EnteController.class);

    @GetMapping(value = "/getEnte", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmmEnt> getEnte() throws MicroServicesException {
        return ResponseEntity.ok(enteService.getEnte());
    }

}
