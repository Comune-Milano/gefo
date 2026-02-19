package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.model.ExportDdrResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportDdrRicerca;
import it.dmi.dmifonbe.dmifonpro.service.ExportDdrService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("exportDdr-v1")
@RequestMapping("/api/exportDdr")
public class ExportDdrController {

    @Autowired
    ExportDdrService exportDdrService;

    private Logger log = LoggerFactory.getLogger(ExportDdrController.class);

    //
    @PreAuthorize("@authorityService.hasPermission('/exportDdr', #idUteRuo)")
    @PostMapping(value = "/exportDdr", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExportDdrResponse> exportDdr(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody ExportDdrRicerca parametri
    ) throws MicroServicesException {
        ExportDdrResponse response = exportDdrService.exportDdr(parametri, idUteRuo);
        return ResponseEntity.ok(response);
    }
}
