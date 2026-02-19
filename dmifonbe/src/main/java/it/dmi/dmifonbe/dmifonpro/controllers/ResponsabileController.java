package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProBan;
import it.dmi.dmifonbe.dmifonpro.entities.ProRes;
import it.dmi.dmifonbe.dmifonpro.model.BandoResponse;
import it.dmi.dmifonbe.dmifonpro.model.BandoRicerca;
import it.dmi.dmifonbe.dmifonpro.model.ResponsabileResponse;
import it.dmi.dmifonbe.dmifonpro.service.BandoService;
import it.dmi.dmifonbe.dmifonpro.service.ResponsabileService;
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

@RestController("responsabile-v1")
@RequestMapping("/api/prores")
public class ResponsabileController {

    private Logger log = LoggerFactory.getLogger(ResponsabileController.class);

    @Autowired
    ResponsabileService responsabileService;

    @PreAuthorize("@authorityService.hasPermission('/getResponsabiliProgetto', #idUteRuo)")
    @GetMapping(value = "/getResponsabiliProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsabileResponse> getResponsabiliProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idPro") Integer idPro
    ) throws MicroServicesException {
        return ResponseEntity.ok(responsabileService.getResponsabiliProgetto(idPro));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaResponsabiliProgetto', #idUteRuo)")
    @PostMapping(value = "/modificaResponsabiliProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaResponsabiliProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid List<ProRes> responsabiliDaModificare
    ) throws MicroServicesException {
        responsabileService.modificaResponsabiliProgetto(responsabiliDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }
}
