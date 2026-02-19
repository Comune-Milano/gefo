package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipimp;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.model.TipoImportoResponse;
import it.dmi.dmifonbe.dmifonpro.service.TipoImportoService;
import it.dmi.dmifonbe.model.Response;
import it.dmi.dmifonbe.model.messages.SuccessMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("tipoImporto-v1")
@RequestMapping("/api/tipimp")
public class TipoImportoController {

    @Autowired
    TipoImportoService tipoImportoService;

    @PreAuthorize("@authorityService.hasPermission('/ricercaTipoImporto', #idUteRuo)")
    @PostMapping(value = "/ricercaTipoImporto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TipoImportoResponse> ricercaMunicipio(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid AutocompleteRicerca tipImpRic
    ) {
        TipoImportoResponse response = tipoImportoService.ricercaTipoImporto(tipImpRic);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/getTipoImporto', #idUteRuo)")
    @GetMapping(value = "/getTipoImporto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProTipimp> getRichiesta(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idTipImp") Integer idTipImp
    ) throws MicroServicesException {
        return ResponseEntity.ok(tipoImportoService.getTipoImporto(idTipImp));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciTipoImporto', #idUteRuo)")
    @PostMapping(value = "/inserisciTipoImporto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciMacroProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProTipimp proTipImpDaInserire
    ) throws MicroServicesException {
        ProTipimp proTipimp = tipoImportoService.inserisciTipoImporto(proTipImpDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proTipimp.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaTipoImporto', #idUteRuo)")
    @PostMapping(value = "/modificaTipoImporto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaTipoImporto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProTipimp proTipImpDaInserire
    ) throws MicroServicesException {
        tipoImportoService.modificaTipoImporto(proTipImpDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaTipoImporto', #idUteRuo)")
    @DeleteMapping(value = "/cancellaTipoImporto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaTipoImporto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idTipImp") Integer idTipImp
    ) throws MicroServicesException {
        tipoImportoService.cancellaTipoImporto(idTipImp);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }
}
