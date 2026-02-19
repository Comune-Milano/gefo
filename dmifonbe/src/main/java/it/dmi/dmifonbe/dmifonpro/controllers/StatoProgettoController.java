package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProStapro;
import it.dmi.dmifonbe.dmifonpro.service.StatoProgettoService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("statoProgetto-v1")
@RequestMapping("/api/stapro")
public class StatoProgettoController {

    private Logger log = LoggerFactory.getLogger(StatoProgettoController.class);

    @Autowired
    StatoProgettoService statoProgettoService;

    @PreAuthorize("@authorityService.hasPermission('/getAllStatiProgetto', #idUteRuo)")
    @GetMapping(value = "/getAllStatiProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProStapro>> getAllStatiProgetto(@RequestParam(value = "idUteRuo") Integer idUteRuo) {
        return ResponseEntity.ok(statoProgettoService.getAllStatiProgetto());
    }
}
