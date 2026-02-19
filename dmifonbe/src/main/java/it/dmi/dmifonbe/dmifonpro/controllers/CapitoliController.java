package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.model.CapitoliProgettoResponse;
import it.dmi.dmifonbe.dmifonpro.model.DatiCapitolo;
import it.dmi.dmifonbe.dmifonpro.model.FiltriCapitoli;
import it.dmi.dmifonbe.dmifonpro.service.CapitoliService;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.Response;
import it.dmi.dmifonbe.model.messages.SuccessMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("capitoli-v1")
@RequestMapping("/api/capitoli")
public class CapitoliController {

    private Logger log = LoggerFactory.getLogger(CapitoliController.class);

    @Autowired
    CapitoliService capitoliService;

    @PreAuthorize("@authorityService.hasPermission('/getCapitoliProgetto', #idUteRuo)")
    @GetMapping(value = "/getCapitoliProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CapitoliProgettoResponse> getCapitoliProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idPro") Integer idPro
    ) throws MicroServicesException {
        return ResponseEntity.ok(capitoliService.getCapitoliProgetto(idPro));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaCapitoliProgetto', #idUteRuo)")
    @PostMapping(value = "/modificaCapitoliProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaCapitoliProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid CapitoliProgettoResponse capitoliDaModificare
    ) throws MicroServicesException {
        capitoliService.modificaCapitoliProgetto(capitoliDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaCapitoliProgetto', #idUteRuo)")
    @DeleteMapping(value = "/cancellaCapitoliProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaCapitoloProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idDetCon") Integer idDetCon
    ) throws MicroServicesException {
        capitoliService.cancellaCapitoliProgetto(idDetCon);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/getCapitoloEntrata', #idUteRuo)")
    @GetMapping(value = "/getCapitoloEntrata", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DatiCapitolo> getCapitoloEntrata(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "codentcon") String codEntCon
    ) throws MicroServicesException {
        return ResponseEntity.ok(capitoliService.getCapitoli(codEntCon, Parameters.CAPE.getValue()));
    }

    @PreAuthorize("@authorityService.hasPermission('/getCapitoloUscita', #idUteRuo)")
    @GetMapping(value = "/getCapitoloUscita", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DatiCapitolo> getCapitoloUscita(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "codentcon") String codEntCon
    ) throws MicroServicesException {
        return ResponseEntity.ok(capitoliService.getCapitoli(codEntCon, Parameters.CAPU.getValue()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaCapitoliEntrata', #idUteRuo)")
    @PostMapping(value = "/ricercaCapitoliEntrata", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DatiCapitolo>> ricercaCapitoliEntrata(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid FiltriCapitoli filtriCapitoli
    ) throws MicroServicesException {
        return ResponseEntity.ok(capitoliService.ricercaCapitoli(filtriCapitoli, Parameters.CAPE.getValue()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaCapitoliUscita', #idUteRuo)")
    @PostMapping(value = "/ricercaCapitoliUscita", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DatiCapitolo>> ricercaCapitoliUscita(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid FiltriCapitoli filtriCapitoli
    ) throws MicroServicesException {
        return ResponseEntity.ok(capitoliService.ricercaCapitoli(filtriCapitoli, Parameters.CAPU.getValue()));
    }
}
