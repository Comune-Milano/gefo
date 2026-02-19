package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.service.ProgettoService;
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

@RestController("progetto-v1")
@RequestMapping("/api/progetto")
public class ProgettoController {

    private Logger log = LoggerFactory.getLogger(ProgettoController.class);

    @Autowired
    ProgettoService progettoService;

    @PreAuthorize("@authorityService.hasPermission('/getProgetto', #idUteRuo)")
    @GetMapping(value = "/getProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProgettoDetail> getProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idpro") Integer idPro
    ) throws MicroServicesException {
        return ResponseEntity.ok(progettoService.getProgetto(idPro));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciProgetto', #idUteRuo)")
    @PostMapping(value = "/inserisciProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProPro proDaInserire
    ) throws MicroServicesException {
        ProPro proPro = progettoService.inserisciProgetto(proDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proPro.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaProgetto', #idUteRuo)")
    @DeleteMapping(value = "/cancellaProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idpro") Integer idPro
    ) throws MicroServicesException {
        progettoService.cancellaProgetto(idPro);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciProgettoFiglio', #idUteRuo)")
    @PostMapping(value = "/inserisciProgettoFiglio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciProgettoFiglio(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idpro") Integer idPro
    ) throws MicroServicesException, CloneNotSupportedException {
        ProPro proPro = progettoService.inserisciProgettoFiglio(idPro);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT_PROGETTO_FIGLIO.getUserMessage(), proPro.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaImportoRisorseFiglio', #idUteRuo)")
    @PostMapping(value = "/modificaImportoRisorseFiglio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaImportoRisorseFiglio(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idpro") Integer idPro
    ) throws MicroServicesException, CloneNotSupportedException {
        ProPro proPro = progettoService.modificaImportoRisorseFiglio(idPro);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT_PROGETTO_FIGLIO.getUserMessage(), proPro.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaProgetto', #idUteRuo)")
    @PostMapping(value = "/modificaProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProgettoDetail proDaModificare
    ) throws MicroServicesException {
        progettoService.modificaProgetto(proDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaProgetto', #idUteRuo)")
    @PostMapping(value = "/ricercaProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProgettoResponse> ricercaProgetti(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProgettoRicerca proRic
    ) throws MicroServicesException {
        ProgettoResponse response = progettoService.ricercaProgetto(proRic, idUteRuo);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaProgettiAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaProgettiAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProPro>> ricercaProgettiAutoComplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete", required = false) String autocomplete
    ) throws MicroServicesException {
        List<ProPro> response = progettoService.ricercaProgetto(autocomplete, idUteRuo);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/getAllAssessorati', #idUteRuo)")
    @GetMapping(value = "/getAllAssessorati", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Assessorato>> getAllAssessorati(@RequestParam(value = "idUteRuo") Integer idUteRuo)
        throws MicroServicesException {
        List<Assessorato> response = progettoService.getAllAssessorati();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/getAllSettori', #idUteRuo)")
    @GetMapping(value = "/getAllSettori", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Settore>> getAllSettori(@RequestParam(value = "idUteRuo") Integer idUteRuo) throws MicroServicesException {
        List<Settore> response = progettoService.getAllSettori();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaSettoriAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaSettoriAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProEntcon>> ricercaSettoriAutocomplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete", required = false) String autocomplete
    ) throws MicroServicesException {
        List<ProEntcon> response = progettoService.ricercaSettoriAutocomplete(autocomplete);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaAssessoratiAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaAssessoratiAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProEntcon>> ricercaAssessoratiAutocomplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete", required = false) String autocomplete
    ) throws MicroServicesException {
        List<ProEntcon> response = progettoService.ricercaAssessoratiAutocomplete(autocomplete);
        return ResponseEntity.ok(response);
    }
}
