package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProRic;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.service.RichiestaService;
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

@RestController("richiesta-v1")
@RequestMapping("/api/richiesta")
public class RichiestaController {

    @Autowired
    RichiestaService richiestaService;

    private Logger log = LoggerFactory.getLogger(RichiestaController.class);

    @PreAuthorize("@authorityService.hasPermission('/getRichiesta', #idUteRuo)")
    @GetMapping(value = "/getRichiesta", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRichiesta> getRichiesta(
        @RequestParam(value = "idRic") Integer idRic,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        return ResponseEntity.ok(richiestaService.getRichiesta(idRic));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciRichiesta', #idUteRuo)")
    @PostMapping(value = "/inserisciRichiesta", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciRichiesta(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProRic ricDaInserire
    ) throws MicroServicesException {
        ProRic proRic = richiestaService.inserisciRichiesta(ricDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proRic.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaRichiesta', #idUteRuo)")
    @PostMapping(value = "/modificaRichiesta", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaRichiesta(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProRic ricDaModificare
    ) throws MicroServicesException {
        this.richiestaService.modificaRichiesta(ricDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaRichiesta', #idUteRuo)")
    @DeleteMapping(value = "/cancellaRichiesta", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaRichiesta(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idRic") Integer idRic
    ) throws MicroServicesException {
        richiestaService.cancellaRichiesta(idRic);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaRichiestaUtente', #idUteRuo)")
    @DeleteMapping(value = "/cancellaRichiestaUtente", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaRichiestaUtente(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idRicUte") Integer idRicUte
    ) throws MicroServicesException {
        richiestaService.cancellaRichiestaUtente(idRicUte);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaRichiesta', #idUteRuo)")
    @PostMapping(value = "/ricercaRichiesta", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RichiestaDetail>> ricercaRichiesta(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid RichiestaRicerca ricRic
    ) {
        List<RichiestaDetail> response = richiestaService.ricercaRichiesta(ricRic, idUteRuo);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaRichiestaAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaRichiestaAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProRic>> ricercaRichiestaAutocomplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete", required = false) String autocomplete
    ) throws MicroServicesException {
        List<ProRic> response = richiestaService.ricercaRichiesta(autocomplete, idUteRuo);
        return ResponseEntity.ok(response);
    }
}
