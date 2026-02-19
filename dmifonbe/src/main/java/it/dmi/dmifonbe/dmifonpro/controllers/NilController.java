package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProNil;
import it.dmi.dmifonbe.dmifonpro.model.NilResponse;
import it.dmi.dmifonbe.dmifonpro.model.NilRicerca;
import it.dmi.dmifonbe.dmifonpro.service.NilService;
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

@RestController("nil-v1")
@RequestMapping("/api/pronil")
public class NilController {

    private Logger log = LoggerFactory.getLogger(NilController.class);

    @Autowired
    NilService nilService;

    @PreAuthorize("@authorityService.hasPermission('/getNil', #idUteRuo)")
    @GetMapping(value = "/getNil", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProNil> getNil(@RequestParam(value = "idUteRuo") Integer idUteRuo, @RequestParam(value = "idNil") Integer idNil)
        throws MicroServicesException {
        return ResponseEntity.ok(nilService.getNil(idNil));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaNilAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaNilAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProNil>> ricercaNilAutocomplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete") String autocomplete
    ) throws MicroServicesException {
        List<ProNil> response = nilService.ricercaNilAutocomplete(autocomplete);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaNil', #idUteRuo)")
    @PostMapping(value = "/ricercaNil", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NilResponse> ricercaNil(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid NilRicerca nilProRic
    ) {
        NilResponse response = nilService.ricercaNil(nilProRic);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciNil', #idUteRuo)")
    @PostMapping(value = "/inserisciNil", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciMunicipio(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProNil proNilDaInserire
    ) throws MicroServicesException {
        ProNil proNil = nilService.inserisciNil(proNilDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proNil.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaNil', #idUteRuo)")
    @PostMapping(value = "/modificaNil", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaNil(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProNil proNilDaModificare
    ) throws MicroServicesException {
        nilService.modificaNil(proNilDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaNil', #idUteRuo)")
    @DeleteMapping(value = "/cancellaNil", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaNil(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idNil") Integer idNil
    ) throws MicroServicesException {
        nilService.cancellaNil(idNil);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }
}
