package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProDdr;
import it.dmi.dmifonbe.dmifonpro.entities.ProDdra;
import it.dmi.dmifonbe.dmifonpro.model.AssociateDdrToDdra;
import it.dmi.dmifonbe.dmifonpro.model.DdraDetail;
import it.dmi.dmifonbe.dmifonpro.model.DdraRicerca;
import it.dmi.dmifonbe.dmifonpro.service.DdraService;
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

@RestController("ddra-v1")
@RequestMapping("/api/ddra")
public class DdraController {

    @Autowired
    DdraService ddraService;

    private Logger log = LoggerFactory.getLogger(DdraController.class);

    @PreAuthorize("@authorityService.hasPermission('/getDdra', #idUteRuo)")
    @GetMapping(value = "/getDdra", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DdraDetail> getDdra(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idDdra") Integer idDdra
    ) throws MicroServicesException {
        return ResponseEntity.ok(ddraService.getDdra(idDdra));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciDdra', #idUteRuo)")
    @PostMapping(value = "/inserisciDdra", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciDdra(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProDdra ddraDaInserire
    ) throws MicroServicesException {
        ProDdra proDdra = ddraService.inserisciDdra(ddraDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proDdra.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaDdra', #idUteRuo)")
    @PostMapping(value = "/modificaDdra", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaDdra(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProDdra ddraDaModificare
    ) throws MicroServicesException {
        this.ddraService.modificaDdra(ddraDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaDdra', #idUteRuo)")
    @DeleteMapping(value = "/cancellaDdra", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaDdra(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idDdra") Integer idDdra
    ) throws MicroServicesException {
        ddraService.cancellaDdra(idDdra);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/associaDdrADdra', #idUteRuo)")
    @PostMapping(value = "/associaDdrADdra", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> associaDdrADdra(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid AssociateDdrToDdra associateDdrToDdra
    ) throws MicroServicesException {
        this.ddraService.associaDdrADdra(associateDdrToDdra);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaDdra', #idUteRuo)")
    @PostMapping(value = "/ricercaDdra", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DdraDetail>> ricercaDdra(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid DdraRicerca ddraRic
    ) throws MicroServicesException {
        List<DdraDetail> response = ddraService.ricercaDdra(ddraRic);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaDdraAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaDdraAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProDdra>> ricercaDdraAutoComplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete", required = false) String autocomplete
    ) throws MicroServicesException {
        List<ProDdra> response = ddraService.ricercaDdraAutocomplete(autocomplete);
        return ResponseEntity.ok(response);
    }
}
