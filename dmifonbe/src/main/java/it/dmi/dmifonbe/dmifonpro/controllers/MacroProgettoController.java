package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProMacpro;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.service.MacroProgettoService;
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

@RestController("macroProgetto-v1")
@RequestMapping("/api/macpro")
public class MacroProgettoController {

    private Logger log = LoggerFactory.getLogger(MacroProgettoController.class);

    @Autowired
    MacroProgettoService macroProgettoService;

    @PreAuthorize("@authorityService.hasPermission('/getMacroProgetto', #idUteRuo)")
    @GetMapping(value = "/getMacroProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProMacpro> getMacroProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idmacpro") Integer idMacPro
    ) throws MicroServicesException {
        return ResponseEntity.ok(macroProgettoService.getMacroProgetto(idMacPro));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciMacroProgetto', #idUteRuo)")
    @PostMapping(value = "/inserisciMacroProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciMacroProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProMacpro macProDaInserire
    ) throws MicroServicesException {
        ProMacpro proMacpro = macroProgettoService.inserisciMacroProgetto(macProDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proMacpro.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaMacroProgetto', #idUteRuo)")
    @PostMapping(value = "/modificaMacroProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaMacroProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProMacpro macProDaModificare
    ) throws MicroServicesException {
        macroProgettoService.modificaMacroProgetto(macProDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaMacroProgetto', #idUteRuo)")
    @DeleteMapping(value = "/cancellaMacroProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaMacroProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idmacpro") Integer idMacPro
    ) throws MicroServicesException {
        macroProgettoService.cancellaMacroProgetto(idMacPro);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaMacroProgetto', #idUteRuo)")
    @PostMapping(value = "/ricercaMacroProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MacroProgettoResponse> ricercaMacroProgetti(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid MacroProgettoRicerca macProRic
    ) {
        MacroProgettoResponse response = macroProgettoService.ricercaMacroProgetto(macProRic);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaMacroProgettiAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaMacroProgettiAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProMacpro>> ricercaMacroProgettiAutocomplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete", required = false) String autocomplete
    ) throws MicroServicesException {
        List<ProMacpro> response = macroProgettoService.ricercaMacroProgettiAutocomplete(autocomplete);
        return ResponseEntity.ok(response);
    }
}
