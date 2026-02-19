package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProPre;
import it.dmi.dmifonbe.dmifonpro.model.PrevisioneRicerca;
import it.dmi.dmifonbe.dmifonpro.service.PrevisioneService;
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

@RestController("previsione-v1")
@RequestMapping("/api/prev")
public class PrevisioneController {

    @Autowired
    PrevisioneService previsioneService;

    private Logger log = LoggerFactory.getLogger(PrevisioneController.class);

    @PreAuthorize("@authorityService.hasPermission('/getPrevisione', #idUteRuo)")
    @GetMapping(value = "/getPrevisione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProPre> getPrevisione(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idPrev") Integer idPrev
    ) throws MicroServicesException {
        return ResponseEntity.ok(previsioneService.getPrevisione(idPrev));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciPrevisione', #idUteRuo)")
    @PostMapping(value = "/inserisciPrevisione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciPrevisione(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProPre previsoneDaInserire
    ) throws MicroServicesException {
        ProPre proPre = previsioneService.inserisciPrevisione(previsoneDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proPre.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaPrevisione', #idUteRuo)")
    @PostMapping(value = "/modificaPrevisione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaPrevisione(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProPre previsioneDaModificare
    ) throws MicroServicesException {
        this.previsioneService.modificaPrevisione(previsioneDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaPrevisione', #idUteRuo)")
    @DeleteMapping(value = "/cancellaPrevisione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaPrevisione(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idPre") Integer idPre
    ) throws MicroServicesException {
        previsioneService.cancellaPrevisione(idPre);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaPrevisione', #idUteRuo)")
    @PostMapping(value = "/ricercaPrevisione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProPre>> ricercaPrevisione(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid PrevisioneRicerca preRic
    ) {
        return ResponseEntity.ok(previsioneService.ricercaPrevisione(preRic, idUteRuo));
    }

    @PreAuthorize("@authorityService.hasPermission('/getBloccaPrevisioni', #idUteRuo)")
    @GetMapping(value = "/getBloccaPrevisioni", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getBloccaPrevisioni(@RequestParam(value = "idUteRuo") Integer idUteRuo) throws MicroServicesException {
        Boolean response = previsioneService.getBloccaPrevisioni();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/bloccaSbloccaPrevisioni', #idUteRuo)")
    @PostMapping(value = "/bloccaSbloccaPrevisioni", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> bloccaSbloccaPrevisioni(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "param") Boolean param
    ) throws MicroServicesException {
        this.previsioneService.bloccaSbloccaPrevisioni(param);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }
}
