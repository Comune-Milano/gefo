package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProStafin;
import it.dmi.dmifonbe.dmifonpro.entities.ProStapro;
import it.dmi.dmifonbe.dmifonpro.service.StatoFinanziamentoService;
import it.dmi.dmifonbe.dmifonpro.service.StatoProgettoService;
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

@RestController("statoFinanziamento-v1")
@RequestMapping("/api/stafin")
public class StatoFinanziamentoController {

    private Logger log = LoggerFactory.getLogger(StatoFinanziamentoController.class);

    @Autowired
    StatoFinanziamentoService statoFinanziamentoService;

    @PreAuthorize("@authorityService.hasPermission('/getAllStatiFinanziamento', #idUteRuo)")
    @GetMapping(value = "/getAllStatiFinanziamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProStafin>> getAllStatiFinanziamento(@RequestParam(value = "idUteRuo") Integer idUteRuo) {
        return ResponseEntity.ok(statoFinanziamentoService.getAllStatiFinanziamento());
    }
}
