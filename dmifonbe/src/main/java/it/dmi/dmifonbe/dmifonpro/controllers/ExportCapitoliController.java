package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.model.ExportCapitoliResponse;
import it.dmi.dmifonbe.dmifonpro.model.ExportCapitoliRicerca;
import it.dmi.dmifonbe.dmifonpro.service.ExportCapitoliService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("exportCapitoli-v1")
@RequestMapping("/api/exportCapitoli")
public class ExportCapitoliController {

    @Autowired
    ExportCapitoliService exportCapitoliService;

    private Logger log = LoggerFactory.getLogger(ExportCapitoliController.class);

    //
    @PreAuthorize("@authorityService.hasPermission('/exportCapitoli', #idUteRuo)")
    @PostMapping(value = "/exportCapitoli", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExportCapitoliResponse> exportCapitoli(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody ExportCapitoliRicerca parametri
    ) throws MicroServicesException {
        ExportCapitoliResponse response = exportCapitoliService.exportCapitoli(parametri, idUteRuo);
        return ResponseEntity.ok(response);
    }
}
