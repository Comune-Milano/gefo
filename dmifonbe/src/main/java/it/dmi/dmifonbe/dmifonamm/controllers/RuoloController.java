package it.dmi.dmifonbe.dmifonamm.controllers;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPer;
import it.dmi.dmifonbe.dmifonamm.entities.AmmRuo;
import it.dmi.dmifonbe.dmifonamm.service.RuoloService;
import it.dmi.dmifonbe.model.Response;
import it.dmi.dmifonbe.model.messages.SuccessMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("ruolo-v1")
@RequestMapping("/api/ruolo")
public class RuoloController {

    @Autowired
    RuoloService ruoloService;

    private Logger log = LoggerFactory.getLogger(RuoloController.class);

    @PreAuthorize("@authorityService.hasPermission('/getRuolo', #idUteRuo)")
    @GetMapping(value = "/getRuolo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmmRuo> getRuolo(@RequestParam(value = "idRuolo") Integer idRuolo,
                                           @RequestParam(value = "idUteRuo") Integer idUteRuo) throws MicroServicesException {
        return ResponseEntity.ok(ruoloService.getRuolo(idRuolo));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaRuolo', #idUteRuo)")
    @GetMapping(value = "/ricercaRuolo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AmmRuo>> ricercaRuolo(
        @RequestParam(value = "codRuo", required = false) String codRuo,
        @RequestParam(value = "desRuo", required = false) String desRuo,
        @RequestParam(value = "autocomplete", required = false) String autocomplete,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) {
        return ResponseEntity.ok(ruoloService.ricercaRuolo(codRuo, desRuo, autocomplete));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciRuolo', #idUteRuo)")
    @PostMapping(value = "/inserisciRuolo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciRuolo(@RequestBody @Valid AmmRuo ruoloDaInserire,
                                                   @RequestParam(value = "idUteRuo") Integer idUteRuo) throws MicroServicesException {
        AmmRuo ruoloInserito = ruoloService.inserisciRuolo(ruoloDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), ruoloInserito.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaRuolo', #idUteRuo)")
    @DeleteMapping(value = "/cancellaRuolo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaRuolo(@RequestParam(value = "idRuolo") Integer idRuolo,
                                                  @RequestParam(value = "idUteRuo") Integer idUteRuo) {
        ruoloService.cancellaRuolo(idRuolo);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaRuolo', #idUteRuo)")
    @PostMapping(value = "/modificaRuolo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaRuolo(@RequestBody @Valid AmmRuo ruoloDaModificare,
                                                  @RequestParam(value = "idUteRuo") Integer idUteRuo) {
        ruoloService.modificaRuolo(ruoloDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/associaFunzione', #idUteRuo)")
    @PostMapping(value = "/associaFunzione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmmPer> associaRuoloDirezione(
        @RequestParam(value = "idRuolo") Integer idRuolo,
        @RequestParam(value = "idFunzione") Integer idFunzione,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        return ResponseEntity.ok(ruoloService.associaFunzione(idRuolo, idFunzione));
    }

    @PreAuthorize("@authorityService.hasPermission('/dissociaFunzione', #idUteRuo)")
    @PostMapping(value = "/dissociaFunzione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> dissociaRuoloDirezione(
        @RequestParam(value = "idRuolo") Integer idRuolo,
        @RequestParam(value = "idFunzione") Integer idFunzione,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        ruoloService.dissociaFunzione(idRuolo, idFunzione);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    @PreAuthorize("@authorityService.hasPermission('/getPermessiRuolo', #idUteRuo)")
    @GetMapping(value = "/getPermessiRuolo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getPermessiRuolo(@RequestParam(value = "idRuolo") Integer idRuolo,
                                                         @RequestParam(value = "idUteRuo") Integer idUteRuo) throws MicroServicesException {
        return ResponseEntity.ok(ruoloService.getPermessiRuolo(idRuolo));
    }

}
