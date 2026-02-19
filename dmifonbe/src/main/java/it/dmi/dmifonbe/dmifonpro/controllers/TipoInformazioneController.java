package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipimp;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipinfagg;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.service.TipoInformazioneService;
import it.dmi.dmifonbe.model.Response;
import it.dmi.dmifonbe.model.messages.SuccessMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("tipoInformazione-v1")
@RequestMapping("/api/tipinf")
public class TipoInformazioneController {

    @Autowired
    TipoInformazioneService tipoInformazioneService;

    @PreAuthorize("@authorityService.hasPermission('/getInformazioneAggiuntiva', #idUteRuo)")
    @GetMapping(value = "/getInformazioneAggiuntiva", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProTipinfagg> getRichiesta(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idTipIn") Integer idTipIn
    ) throws MicroServicesException {
        return ResponseEntity.ok(tipoInformazioneService.getTipoInformazione(idTipIn));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaInformazioneAggiuntiva', #idUteRuo)")
    @PostMapping(value = "/ricercaInformazioneAggiuntiva", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TipiInformazioneResponse> ricercaMunicipio(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid AutocompleteRicerca tipInRic
    ) {
        TipiInformazioneResponse response = tipoInformazioneService.ricercaTipoInformazione(tipInRic);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciInformazioneAggiuntiva', #idUteRuo)")
    @PostMapping(value = "/inserisciInformazioneAggiuntiva", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciInformazioneAggiuntiva(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProTipinfagg proTipinfaggDaInserire
    ) throws MicroServicesException {
        ProTipinfagg proTipIn = tipoInformazioneService.inserisciTipoInformazione(proTipinfaggDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proTipIn.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaInformazioneAggiuntiva', #idUteRuo)")
    @PostMapping(value = "/modificaInformazioneAggiuntiva", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaTipoInformazione(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProTipinfagg proTipinfaggDaModificare
    ) throws MicroServicesException {
        tipoInformazioneService.modificaTipoInformazione(proTipinfaggDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaInformazioneAggiuntiva', #idUteRuo)")
    @DeleteMapping(value = "/cancellaInformazioneAggiuntiva", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaInformazioneAggiuntiva(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idTipIn") Integer idTipIn
    ) throws MicroServicesException {
        tipoInformazioneService.cancellaTipoInformazione(idTipIn);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }
}
