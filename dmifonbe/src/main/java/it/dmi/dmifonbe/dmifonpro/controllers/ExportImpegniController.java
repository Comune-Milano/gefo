package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.model.ExportImpegniResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportImpegniRicerca;
import it.dmi.dmifonbe.dmifonpro.service.ExportImpegniService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("exportImpegni-v1")
@RequestMapping("/api/exportImpegni")
public class ExportImpegniController {

    @Autowired
    ExportImpegniService exportImpegniService;

    private Logger log = LoggerFactory.getLogger(ExportImpegniController.class);

    //
    @PreAuthorize("@authorityService.hasPermission('/exportImpegni', #idUteRuo)")
    @PostMapping(value = "/exportImpegni", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExportImpegniResponse> exportImpegni(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody ExportImpegniRicerca parametri
    ) throws MicroServicesException {
        ExportImpegniResponse response = exportImpegniService.exportImpegni(parametri, idUteRuo);
        return ResponseEntity.ok(response);
    }
}
