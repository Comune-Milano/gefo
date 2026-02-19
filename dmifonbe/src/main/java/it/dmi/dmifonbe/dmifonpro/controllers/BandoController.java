package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProBan;
import it.dmi.dmifonbe.dmifonpro.model.BandoResponse;
import it.dmi.dmifonbe.dmifonpro.model.BandoRicerca;
import it.dmi.dmifonbe.dmifonpro.service.BandoService;
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

@RestController("bando-v1")
@RequestMapping("/api/bando")
public class BandoController {

    private Logger log = LoggerFactory.getLogger(BandoController.class);

    @Autowired
    BandoService bandoService;

    @PreAuthorize("@authorityService.hasPermission('/getBando', #idUteRuo)")
    @GetMapping(value = "/getBando", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProBan> getBando(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idban") Integer idBan
    ) throws MicroServicesException {
        return ResponseEntity.ok(bandoService.getBando(idBan));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciBando', #idUteRuo)")
    @PostMapping(value = "/inserisciBando", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciBando(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProBan banDaInserire
    ) throws MicroServicesException {
        ProBan proBan = bandoService.inserisciBando(banDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proBan.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaBando', #idUteRuo)")
    @PostMapping(value = "/modificaBando", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaBando(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProBan banDaModificare
    ) throws MicroServicesException {
        bandoService.modificaBando(banDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaBando', #idUteRuo)")
    @DeleteMapping(value = "/cancellaBando", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaBando(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idban") Integer idBan
    ) throws MicroServicesException {
        bandoService.cancellaBando(idBan);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaBando', #idUteRuo)")
    @PostMapping(value = "/ricercaBando", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BandoResponse> ricercaBando(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid BandoRicerca banRic
    ) throws MicroServicesException {
        BandoResponse response = bandoService.ricercaBando(banRic);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaBandoAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaBandoAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProBan>> ricercaBandoAutocomplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete", required = false) String autocomplete
    ) throws MicroServicesException {
        List<ProBan> response = bandoService.ricercaBandoAutocomplete(autocomplete);
        return ResponseEntity.ok(response);
    }
}
