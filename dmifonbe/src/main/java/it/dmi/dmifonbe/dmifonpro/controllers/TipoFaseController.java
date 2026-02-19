package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.model.TipoFaseResponse;
import it.dmi.dmifonbe.dmifonpro.service.TipoFaseService;
import it.dmi.dmifonbe.model.Response;
import it.dmi.dmifonbe.model.messages.SuccessMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("TipoFase-v1")
@RequestMapping("/api/tipofase")
public class TipoFaseController {

    private Logger log = LoggerFactory.getLogger(TipoFaseController.class);

    @Autowired
    TipoFaseService tipoFaseService;

    @PreAuthorize("@authorityService.hasPermission('/getTipoFase', #idUteRuo)")
    @GetMapping(value = "/getTipoFase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProTipfas> getListaValore(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idTipFas") Integer idTipFas
    ) throws MicroServicesException {
        return ResponseEntity.ok(tipoFaseService.getTipoFase(idTipFas));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaTipoFase', #idUteRuo)")
    @PostMapping(value = "/ricercaTipoFase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TipoFaseResponse> ricercaMunicipio(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid AutocompleteRicerca tipFasRic
    ) {
        TipoFaseResponse response = tipoFaseService.ricercaTipoFase(tipFasRic);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciTipoFase', #idUteRuo)")
    @PostMapping(value = "/inserisciTipoFase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciTipoFase(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProTipfas proTipFasDaInserire
    ) throws MicroServicesException {
        ProTipfas proTipfas = tipoFaseService.inserisciTipoFase(proTipFasDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proTipfas.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaTipoFase', #idUteRuo)")
    @PostMapping(value = "/modificaTipoFase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaTipoFase(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProTipfas proTipfasDaModificare
    ) throws MicroServicesException {
        tipoFaseService.modificaTipoFase(proTipfasDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaTipoFase', #idUteRuo)")
    @DeleteMapping(value = "/cancellaTipoFase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaTipoFase(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idTipFas") Integer idTipFas
    ) throws MicroServicesException {
        tipoFaseService.cancellaTipoFase(idTipFas);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }
}
