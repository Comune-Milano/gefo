package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProLisval;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.model.ListaValoriResponse;
import it.dmi.dmifonbe.dmifonpro.service.LisvalService;
import it.dmi.dmifonbe.model.Parameters;
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

@RestController("lisval-v1")
@RequestMapping("/api/lisval")
public class ListaValoriController {

    private Logger log = LoggerFactory.getLogger(ListaValoriController.class);

    @Autowired
    LisvalService lisvalService;

    @PreAuthorize("@authorityService.hasPermission('/getListaValoriFaseIntervento', #idUteRuo)")
    @GetMapping(value = "/getListaValoriFaseIntervento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProLisval>> getListaValoriFaseIntervento(@RequestParam(value = "idUteRuo") Integer idUteRuo)
        throws MicroServicesException {
        return ResponseEntity.ok(lisvalService.getListaValori(Parameters.AVAFASINT.getValue()));
    }

    @PreAuthorize("@authorityService.hasPermission('/getListaValoriLivelloCriticita', #idUteRuo)")
    @GetMapping(value = "/getListaValoriLivelloCriticita", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProLisval>> getListaValoriLivelloCriticita(@RequestParam(value = "idUteRuo") Integer idUteRuo)
        throws MicroServicesException {
        return ResponseEntity.ok(lisvalService.getListaValori(Parameters.AVACRI.getValue()));
    }

    @PreAuthorize("@authorityService.hasPermission('/getListaValoriTipoAppalto', #idUteRuo)")
    @GetMapping(value = "/getListaValoriTipoAppalto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProLisval>> getListaValoriTipoAppalto(@RequestParam(value = "idUteRuo") Integer idUteRuo)
        throws MicroServicesException {
        return ResponseEntity.ok(lisvalService.getListaValori(Parameters.AVATIPAPP.getValue()));
    }

    @PreAuthorize("@authorityService.hasPermission('/getListaValoriStatoAnticipazione', #idUteRuo)")
    @GetMapping(value = "/getListaValoriStatoAnticipazione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProLisval>> getListaValoriStatoAnticipazione(@RequestParam(value = "idUteRuo") Integer idUteRuo)
        throws MicroServicesException {
        return ResponseEntity.ok(lisvalService.getListaValori(Parameters.AVASTAANT.getValue()));
    }

    @PreAuthorize("@authorityService.hasPermission('/getListaValoriTipologiaFasi', #idUteRuo)")
    @GetMapping(value = "/getListaValoriTipologiaFasi", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProLisval>> getListaValoriTipologiaFasi(@RequestParam(value = "idUteRuo") Integer idUteRuo)
        throws MicroServicesException {
        return ResponseEntity.ok(lisvalService.getListaValori(Parameters.TIPOLFAS.getValue()));
    }

    @PreAuthorize("@authorityService.hasPermission('/getListaValore', #idUteRuo)")
    @GetMapping(value = "/getListaValore", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProLisval> getListaValore(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idLisVal") Integer idLisVal
    ) throws MicroServicesException {
        return ResponseEntity.ok(lisvalService.getListaValore(idLisVal));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaListaValore', #idUteRuo)")
    @PostMapping(value = "/ricercaListaValore", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListaValoriResponse> ricercaListaValori(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid AutocompleteRicerca lisValRic
    ) {
        ListaValoriResponse response = lisvalService.ricercaListaValori(lisValRic);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciListaValore', #idUteRuo)")
    @PostMapping(value = "/inserisciListaValore", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciListaValore(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProLisval proLisValDaInserire
    ) throws MicroServicesException {
        ProLisval proLisval = lisvalService.inserisciListaValore(proLisValDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proLisval.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaListaValore', #idUteRuo)")
    @PostMapping(value = "/modificaListaValore", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaListaValore(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProLisval proLisValDaModificare
    ) throws MicroServicesException {
        lisvalService.modificaListaValore(proLisValDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaListaValore', #idUteRuo)")
    @DeleteMapping(value = "/cancellaListaValore", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaMacroProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idLisVal") Integer idLisVal
    ) throws MicroServicesException {
        lisvalService.cancellaListaValore(idLisVal);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/getListaValoriTipoLista', #idUteRuo)")
    @GetMapping(value = "/getListaValoriTipoLista", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProLisval>> getListaValoriTipoLista(@RequestParam(value = "idUteRuo") Integer idUteRuo)
        throws MicroServicesException {
        return ResponseEntity.ok(lisvalService.getListaValoriTipoLista());
    }
}
