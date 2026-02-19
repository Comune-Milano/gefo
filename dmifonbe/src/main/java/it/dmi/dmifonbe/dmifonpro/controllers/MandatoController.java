package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.model.MandatoResponse;
import it.dmi.dmifonbe.dmifonpro.model.MandatoRicerca;
import it.dmi.dmifonbe.dmifonpro.service.MandatiService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.text.ParseException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("mandati-v1")
@RequestMapping("/api/mandato")
public class MandatoController {

    private Logger log = LoggerFactory.getLogger(MandatoController.class);

    @Autowired
    MandatiService mandatiService;

    @PreAuthorize("@authorityService.hasPermission('/getMandato', #idUteRuo)")
    @GetMapping(value = "/getMandato", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MandatoResponse> getMandato(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "codentcon") String codEntCon
    ) throws MicroServicesException, ParseException {
        return ResponseEntity.ok(mandatiService.getMandato(codEntCon));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaMandato', #idUteRuo)")
    @PostMapping(value = "/ricercaMandato", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MandatoResponse>> ricercaMandati(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid MandatoRicerca filtriMandati
    ) throws MicroServicesException, ParseException {
        return ResponseEntity.ok(mandatiService.ricercaMandato(filtriMandati));
    }
}
