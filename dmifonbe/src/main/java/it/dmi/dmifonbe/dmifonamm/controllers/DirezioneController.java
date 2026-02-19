package it.dmi.dmifonbe.dmifonamm.controllers;

import it.dmi.dmifonbe.dmifonamm.entities.AmmDir;
import it.dmi.dmifonbe.dmifonamm.service.DirezioneService;
import it.dmi.dmifonbe.model.Response;
import it.dmi.dmifonbe.model.messages.SuccessMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("direzione-v1")
@RequestMapping("/api/direzione")
public class DirezioneController {

    @Autowired
    DirezioneService direzioneService;

    private Logger log = LoggerFactory.getLogger(DirezioneController.class);

    @PreAuthorize("@authorityService.hasPermission('/getDirezione', #idUteRuo)")
    @GetMapping(value = "/getDirezione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmmDir> getDirezione(@RequestParam(value = "idDirezione") Integer idDirezione,
                                               @RequestParam(value = "idUteRuo") Integer idUteRuo) throws MicroServicesException {
        return ResponseEntity.ok(direzioneService.getDirezione(idDirezione));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaDirezione', #idUteRuo)")
    @GetMapping(value = "/ricercaDirezione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AmmDir>> ricercaDirezione(
        @RequestParam(value = "codDir", required = false) String codDir,
        @RequestParam(value = "desDir", required = false) String desDir,
        @RequestParam(value = "autocomplete", required = false) String autocomplete,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) {
        return ResponseEntity.ok(direzioneService.ricercaDirezione(codDir, desDir, autocomplete));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciDirezione', #idUteRuo)")
    @PostMapping(value = "/inserisciDirezione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciUtente(@RequestBody @Valid AmmDir direzioneDaInserire,
                                                    @RequestParam(value = "idUteRuo") Integer idUteRuo) {
        AmmDir direzioneInserita = direzioneService.inserisciDirezione(direzioneDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), direzioneInserita.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaDirezione', #idUteRuo)")
    @DeleteMapping(value = "/cancellaDirezione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaDirezione(@RequestParam(value = "idDirezione") Integer idDirezione,
                                                      @RequestParam(value = "idUteRuo") Integer idUteRuo) {
        direzioneService.cancellaDirezione(idDirezione);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaDirezione', #idUteRuo)")
    @PostMapping(value = "/modificaDirezione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaDirezione(@RequestBody @Valid AmmDir direzioneDaModificare,
                                                      @RequestParam(value = "idUteRuo") Integer idUteRuo) {
        direzioneService.modificaDirezione(direzioneDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }
}
