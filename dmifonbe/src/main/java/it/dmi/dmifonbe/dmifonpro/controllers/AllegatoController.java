package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonamm.entities.AmmAll;
import it.dmi.dmifonbe.dmifonamm.entities.AmmFil;
import it.dmi.dmifonbe.dmifonamm.model.AllegatoResponse;
import it.dmi.dmifonbe.dmifonamm.model.AmmFilBase64;
import it.dmi.dmifonbe.dmifonamm.model.CaricaAllegatoModel;
import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;
import it.dmi.dmifonbe.dmifonpro.model.AvanzamentoDetail;
import it.dmi.dmifonbe.dmifonpro.model.AvanzamentoResponse;
import it.dmi.dmifonbe.dmifonpro.model.AvanzamentoRicerca;
import it.dmi.dmifonbe.dmifonpro.service.AllegatoService;
import it.dmi.dmifonbe.dmifonpro.service.AvanzamentoService;
import it.dmi.dmifonbe.model.Response;
import it.dmi.dmifonbe.model.messages.SuccessMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("allegato-v1")
@RequestMapping("/api/allegato")
public class AllegatoController {

    private Logger log = LoggerFactory.getLogger(AllegatoController.class);

    @Autowired
    AllegatoService allegatoService;

    @PreAuthorize("@authorityService.hasPermission('/getAllegato', #idUteRuo)")
    @GetMapping(value = "/getAllegato", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AllegatoResponse> getAllegato(
        @RequestParam(value = "idEnt") Integer idEnt,
        @RequestParam(value = "tipEnt") String tipEnt,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        return ResponseEntity.ok(allegatoService.getAllegato(idEnt, tipEnt));
    }

    @PreAuthorize("@authorityService.hasPermission('/caricaFileAllegato', #idUteRuo)")
    @PostMapping(value = "/caricaFileAllegato", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> caricaFileAllegato(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid CaricaAllegatoModel allegatoDaCaricare
    ) throws MicroServicesException {
        AmmAll ammAll = allegatoService.caricaFile(allegatoDaCaricare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), ammAll.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/scaricaAllegato', #idUteRuo)")
    @GetMapping(value = "/scaricaAllegato", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmmFilBase64> scaricaAllegato(
        @RequestParam(value = "idFile") Integer idFile,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        return ResponseEntity.ok(allegatoService.scaricaFile(idFile));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaAllegato', #idUteRuo)")
    @PostMapping(value = "/modificaAllegato", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaAllegato(@RequestBody @Valid AmmAll ammAll, @RequestParam(value = "idUteRuo") Integer idUteRuo)
        throws MicroServicesException {
        this.allegatoService.modificaAllegato(ammAll);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaAllegato', #idUteRuo)")
    @DeleteMapping(value = "/cancellaAllegato", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaAllegato(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idAll") Integer idAll
    ) throws MicroServicesException {
        this.allegatoService.cancellaAllegato(idAll);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }
}
