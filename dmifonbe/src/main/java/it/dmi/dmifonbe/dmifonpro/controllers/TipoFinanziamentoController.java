package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipfin;
import it.dmi.dmifonbe.dmifonpro.model.TipoFinanziamentoResponse;
import it.dmi.dmifonbe.dmifonpro.model.TipoFinanziamentoRicerca;
import it.dmi.dmifonbe.dmifonpro.service.TipoFinanziamentoService;
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

@RestController("tipoFinanziamento-v1")
@RequestMapping("/api/tipfin")
public class TipoFinanziamentoController {

    private Logger log = LoggerFactory.getLogger(TipoFinanziamentoController.class);

    @Autowired
    TipoFinanziamentoService tipoFinanziamentoService;

    @PreAuthorize("@authorityService.hasPermission('/getTipoFinanziamento', #idUteRuo)")
    @GetMapping(value = "/getTipoFinanziamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProTipfin> getTipoFinanziamento(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idtipfin") Integer idTipFin
    ) throws MicroServicesException {
        return ResponseEntity.ok(tipoFinanziamentoService.getTipoFinanziamento(idTipFin));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciTipoFinanziamento', #idUteRuo)")
    @PostMapping(value = "/inserisciTipoFinanziamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciTipoFinanziamento(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProTipfin tipFinDaInserire
    ) throws MicroServicesException {
        ProTipfin proTipfin = tipoFinanziamentoService.inserisciTipoFinanziamento(tipFinDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proTipfin.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaTipoFinanziamento', #idUteRuo)")
    @PostMapping(value = "/modificaTipoFinanziamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaTipoFinanziamento(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProTipfin tipFinDaModificare
    ) throws MicroServicesException {
        tipoFinanziamentoService.modificaTipoFinanziamento(tipFinDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaTipoFinanziamento', #idUteRuo)")
    @DeleteMapping(value = "/cancellaTipoFinanziamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaTipoFinanziamento(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idtipfin") Integer idTipFin
    ) throws MicroServicesException {
        tipoFinanziamentoService.cancellaTipoFinanziamento(idTipFin);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaTipoFinanziamento', #idUteRuo)")
    @PostMapping(value = "/ricercaTipoFinanziamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TipoFinanziamentoResponse> ricercaTipiFinanziamento(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid TipoFinanziamentoRicerca tipFinric
    ) throws MicroServicesException {
        return ResponseEntity.ok(tipoFinanziamentoService.ricercaTipoFinanziamento(tipFinric));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaTipoFinanziamentoAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaTipoFinanziamentoAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProTipfin>> ricercaTipoFinanziamentoAutocomplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete", required = false) String autocomplete
    ) throws MicroServicesException {
        List<ProTipfin> response = tipoFinanziamentoService.ricercaTipoFinanziamentoAutocomplete(autocomplete);
        return ResponseEntity.ok(response);
    }
}
