package it.dmi.dmifonbe.dmifonamm.controllers;

import it.dmi.dmifonbe.dmifonamm.entities.AmmEla;
import it.dmi.dmifonbe.dmifonamm.model.ElaborazioneRicerca;
import it.dmi.dmifonbe.dmifonamm.service.ElaborazioneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("elaborazione-v1")
@RequestMapping("/api/elaborazione")
public class ElaborazioneController {

    @Autowired
    ElaborazioneService elaborazioneService;

    private Logger log = LoggerFactory.getLogger(ElaborazioneController.class);


    @PreAuthorize("@authorityService.hasPermission('/ricercaElaborazione', #idUteRuo)")
    @PostMapping(value = "/ricercaElaborazione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AmmEla>> ricercaElaborazione(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody ElaborazioneRicerca elaRic
    ) {
        return ResponseEntity.ok(elaborazioneService.ricercaElaborazione(elaRic));
    }

}
