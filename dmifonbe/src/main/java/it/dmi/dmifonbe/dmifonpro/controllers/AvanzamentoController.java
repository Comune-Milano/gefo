package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.service.AvanzamentoService;
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

@RestController("avanzamento-v1")
@RequestMapping("/api/avanzamento")
public class AvanzamentoController {

    private Logger log = LoggerFactory.getLogger(AvanzamentoController.class);

    @Autowired
    AvanzamentoService avanzamentoService;

    @PreAuthorize("@authorityService.hasPermission('/getAvanzamento', #idUteRuo)")
    @GetMapping(value = "/getAvanzamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AvanzamentoDetail> getAvanzamento(
        @RequestParam(value = "idava") Integer idAva,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        return ResponseEntity.ok(avanzamentoService.getAvanzamento(idAva));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciAvanzamento', #idUteRuo)")
    @PostMapping(value = "/inserisciAvanzamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciAvanzamento(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProAva avaDaInserire
    ) throws MicroServicesException {
        ProAva proAva = avanzamentoService.inserisciAvanzamento(avaDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proAva.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/creaFasiAvanzamento', #idUteRuo)")
    @PostMapping(value = "/creaFasiAvanzamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> creaFasiAvanzamento(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idAva") Integer idAva
    ) throws MicroServicesException {
        this.avanzamentoService.creaFasiAvanzamento(idAva);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT_FASE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaAvanzamento', #idUteRuo)")
    @PostMapping(value = "/modificaAvanzamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaAvanzamento(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid AvanzamentoDetail avaDaModificare
    ) throws MicroServicesException {
        this.avanzamentoService.modificaAvanzamento(avaDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaFase', #idUteRuo)")
    @DeleteMapping(value = "/cancellaFase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaFase(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idFase") Integer idFase
    ) throws MicroServicesException {
        avanzamentoService.cancellaFase(idFase);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaAvanzamento', #idUteRuo)")
    @DeleteMapping(value = "/cancellaAvanzamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaAvanzamento(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idAva") Integer idAva
    ) throws MicroServicesException {
        avanzamentoService.cancellaAvanzamento(idAva);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaAvanzamento', #idUteRuo)")
    @PostMapping(value = "/ricercaAvanzamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AvanzamentoResponse> ricercaAvanzamenti(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid AvanzamentoRicerca avaRic
    ) throws MicroServicesException {
        AvanzamentoResponse response = avanzamentoService.ricercaAvanzamenti(avaRic, idUteRuo);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/getTipoFase', #idUteRuo)")
    @GetMapping(value = "/getTipoFase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProTipfas>> getTipoFase(@RequestParam(value = "idUteRuo") Integer idUteRuo) throws MicroServicesException {
        return ResponseEntity.ok(avanzamentoService.getTipoFase());
    }
}
