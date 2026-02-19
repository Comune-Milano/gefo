package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.model.Totali;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("totali-v1")
@RequestMapping("/api/totali")
public class TotaliController {

    private Logger log = LoggerFactory.getLogger(TotaliController.class);

    @Autowired
    CalcolaTotaliService calcolaTotaliService;

    @PreAuthorize("@authorityService.hasPermission('/getTotali', #idUteRuo)")
    @GetMapping(value = "/getTotali", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Totali> getTotali(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "entityType") String entityType,
        @RequestParam(value = "id") Integer id
    ) throws ParseException, MicroServicesException {
        return ResponseEntity.ok(calcolaTotaliService.getTotali(entityType, id));
    }
}
