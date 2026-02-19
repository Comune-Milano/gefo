package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProMun;
import it.dmi.dmifonbe.dmifonpro.model.MunicipioResponse;
import it.dmi.dmifonbe.dmifonpro.model.MunicipioRicerca;
import it.dmi.dmifonbe.dmifonpro.service.MunicipioService;
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

@RestController("municipio-v1")
@RequestMapping("/api/Municipio")
public class MunicipioController {

    private Logger log = LoggerFactory.getLogger(MunicipioController.class);

    @Autowired
    MunicipioService municipioService;

    @PreAuthorize("@authorityService.hasPermission('/getAllMunicipi', #idUteRuo)")
    @GetMapping(value = "/getAllMunicipi", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProMun>> getAllMunicipi(@RequestParam(value = "idUteRuo") Integer idUteRuo) {
        return ResponseEntity.ok(municipioService.getAllMunicipi());
    }

    @PreAuthorize("@authorityService.hasPermission('/getMunicipio', #idUteRuo)")
    @GetMapping(value = "/getMunicipio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProMun> getMacroProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idMun") Integer idMun
    ) throws MicroServicesException {
        return ResponseEntity.ok(municipioService.getMunicipio(idMun));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaMunicipiAutocomplete', #idUteRuo)")
    @GetMapping(value = "/ricercaMunicipiAutocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProMun>> ricercaMacroProgettiAutocomplete(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "autocomplete", required = false) String autocomplete
    ) throws MicroServicesException {
        List<ProMun> response = municipioService.ricercaMunicipiAutocomplete(autocomplete);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaMunicipio', #idUteRuo)")
    @PostMapping(value = "/ricercaMunicipio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MunicipioResponse> ricercaMunicipio(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid MunicipioRicerca munProRic
    ) {
        MunicipioResponse response = municipioService.ricercaMunicipio(munProRic);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciMunicipio', #idUteRuo)")
    @PostMapping(value = "/inserisciMunicipio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciMunicipio(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProMun proMunDaInserire
    ) throws MicroServicesException {
        ProMun proMun = municipioService.inserisciMunicipio(proMunDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), proMun.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaMunicipio', #idUteRuo)")
    @PostMapping(value = "/modificaMunicipio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaMunicipio(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ProMun proMunDaModificare
    ) throws MicroServicesException {
        municipioService.modificaMunicipio(proMunDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaMunicipio', #idUteRuo)")
    @DeleteMapping(value = "/cancellaMunicipio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaMunicipio(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idProMun") Integer idProMun
    ) throws MicroServicesException {
        municipioService.cancellaMunicipio(idProMun);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }
}
