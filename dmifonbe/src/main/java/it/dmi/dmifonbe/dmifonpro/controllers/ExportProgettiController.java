package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.model.ExportProgettiResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportProgettiRicerca;
import it.dmi.dmifonbe.dmifonpro.service.ExportProgettiService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("exportProgetti-v1")
@RequestMapping("/api/exportProgetti")
public class ExportProgettiController {

    @Autowired
    ExportProgettiService exportProgettiService;

    private Logger log = LoggerFactory.getLogger(ExportProgettiController.class);

    //
    @PreAuthorize("@authorityService.hasPermission('/exportProgetti', #idUteRuo)")
    @PostMapping(value = "/exportProgetti", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExportProgettiResponse> exportProgetti(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody ExportProgettiRicerca parametri
    ) throws MicroServicesException {
        ExportProgettiResponse response = exportProgettiService.exportProgetti(parametri, idUteRuo);
        return ResponseEntity.ok(response);
    }
}
