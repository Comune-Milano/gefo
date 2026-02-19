package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.model.ExportMandatiResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportMandatiRicerca;
import it.dmi.dmifonbe.dmifonpro.service.ExportMandatiService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("exportMandati-v1")
@RequestMapping("/api/exportMandati")
public class ExportMandatiController {

    @Autowired
    ExportMandatiService exportMandatiService;

    private Logger log = LoggerFactory.getLogger(ExportMandatiController.class);

    //
    @PreAuthorize("@authorityService.hasPermission('/exportMandati', #idUteRuo)")
    @PostMapping(value = "/exportMandati", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExportMandatiResponse> exportMandati(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody ExportMandatiRicerca parametri
    ) throws MicroServicesException {
        ExportMandatiResponse response = exportMandatiService.exportMandati(parametri, idUteRuo);
        return ResponseEntity.ok(response);
    }
}
