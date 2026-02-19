package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProDdr;
import it.dmi.dmifonbe.dmifonpro.model.DdrRicerca;
import it.dmi.dmifonbe.dmifonpro.service.DdrService;
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

@RestController("ddr-v1")
@RequestMapping("/api/ddr")
public class DdrController {

    @Autowired
    DdrService ddrService;

    private Logger log = LoggerFactory.getLogger(DdrController.class);

    @PreAuthorize("@authorityService.hasPermission('/getDdr', #idUteRuo)")
    @GetMapping(value = "/getDdr", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProDdr> getDdr(@RequestParam(value = "idUteRuo") Integer idUteRuo, @RequestParam(value = "idDdr") Integer idDdr)
        throws MicroServicesException {
        return ResponseEntity.ok(ddrService.getDdr(idDdr));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciDdr', #idUteRuo)")
    @PostMapping(value = "/inserisciDdr", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciDdr(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProDdr ddrDaInserire
    ) throws MicroServicesException {
        ProDdr proDdr = ddrService.inserisciDdr(ddrDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proDdr.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaDdr', #idUteRuo)")
    @PostMapping(value = "/modificaDdr", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaDdr(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProDdr ddrDaModificare
    ) throws MicroServicesException {
        this.ddrService.modificaDdr(ddrDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaDdr', #idUteRuo)")
    @DeleteMapping(value = "/cancellaDdr", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaDdr(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idDdr") Integer idDdr
    ) throws MicroServicesException {
        ddrService.cancellaDdr(idDdr);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaDdr', #idUteRuo)")
    @PostMapping(value = "/ricercaDdr", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProDdr>> ricercaDdr(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid DdrRicerca ddrRic
    ) {
        return ResponseEntity.ok(ddrService.ricercaDdr(ddrRic, idUteRuo));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaDdrAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaDdrAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProDdr>> ricercaDdrAutoComplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete", required = false) String autocomplete
    ) throws MicroServicesException {
        List<ProDdr> response = ddrService.ricercaDdrAutocomplete(autocomplete);
        return ResponseEntity.ok(response);
    }
}
