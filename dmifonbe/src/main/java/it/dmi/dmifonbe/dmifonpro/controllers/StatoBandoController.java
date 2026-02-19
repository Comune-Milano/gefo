package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProStaban;
import it.dmi.dmifonbe.dmifonpro.service.StatoBandoService;
import it.dmi.dmifonbe.dmifonpro.service.StatoFinanziamentoService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("statoBando-v1")
@RequestMapping("/api/staban")
public class StatoBandoController {

    private Logger log = LoggerFactory.getLogger(StatoBandoController.class);

    @Autowired
    StatoFinanziamentoService statoFinanziamentoService;

    @Autowired
    StatoBandoService statoBandoService;

    @PreAuthorize("@authorityService.hasPermission('/getAllStatiBando', #idUteRuo)")
    @GetMapping(value = "/getAllStatiBando", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProStaban>> getAllStatiFinanziamento(@RequestParam(value = "idUteRuo") Integer idUteRuo) {
        return ResponseEntity.ok(statoBandoService.getAllStatiBando());
    }
}
