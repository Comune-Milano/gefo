package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.model.ExportAvanzamentiResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportAvanzamentiRicerca;
import it.dmi.dmifonbe.dmifonpro.service.ExportAvanzamentiService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("exportAvanzamenti-v1")
@RequestMapping("/api/exportAvanzamenti")
public class ExportAvanzamentiController {

    @Autowired
    ExportAvanzamentiService exportAvanzamentiService;

    private Logger log = LoggerFactory.getLogger(ExportAvanzamentiController.class);

    //
    @PreAuthorize("@authorityService.hasPermission('/exportAvanzamenti', #idUteRuo)")
    @PostMapping(value = "/exportAvanzamenti", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExportAvanzamentiResponse> exportAvanzamenti(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody ExportAvanzamentiRicerca parametri
    ) throws MicroServicesException {
        ExportAvanzamentiResponse response = exportAvanzamentiService.exportAvanzamenti(parametri, idUteRuo);
        return ResponseEntity.ok(response);
    }
}
