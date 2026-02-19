package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProTem;
import it.dmi.dmifonbe.dmifonpro.model.TematicaResponse;
import it.dmi.dmifonbe.dmifonpro.model.TematicaRicerca;
import it.dmi.dmifonbe.dmifonpro.service.TematicaService;
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

@RestController("tematica-v1")
@RequestMapping("/api/protem")
public class TematicaController {

    private Logger log = LoggerFactory.getLogger(TematicaController.class);

    @Autowired
    TematicaService tematicaService;

    @PreAuthorize("@authorityService.hasPermission('/getTematica', #idUteRuo)")
    @GetMapping(value = "/getTematica", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProTem> getTematica(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idprotem") Integer idProTem
    ) throws MicroServicesException {
        return ResponseEntity.ok(tematicaService.getTematica(idProTem));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciTematica', #idUteRuo)")
    @PostMapping(value = "/inserisciTematica", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciTematica(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProTem temDaInserire
    ) throws MicroServicesException {
        ProTem proTem = tematicaService.inserisciTematica(temDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proTem.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaTematica', #idUteRuo)")
    @PostMapping(value = "/modificaTematica", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaTematica(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProTem temProDaModificare
    ) throws MicroServicesException {
        tematicaService.modificaTematica(temProDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaTematica', #idUteRuo)")
    @DeleteMapping(value = "/cancellaTematica", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaTematica(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idprotem") Integer idProTem
    ) throws MicroServicesException {
        tematicaService.cancellaTematica(idProTem);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaTematica', #idUteRuo)")
    @PostMapping(value = "/ricercaTematica", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TematicaResponse> ricercaTematica(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid TematicaRicerca temRic
    ) {
        return ResponseEntity.ok(tematicaService.ricercaTematica(temRic));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaTematicaAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaTematicaAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProTem>> ricercaTematicaAutocomplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete") String autocomplete
    ) throws MicroServicesException {
        List<ProTem> response = tematicaService.ricercaTematicaAutocomplete(autocomplete);
        return ResponseEntity.ok(response);
    }
}
