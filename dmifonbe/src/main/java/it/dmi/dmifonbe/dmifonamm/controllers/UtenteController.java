package it.dmi.dmifonbe.dmifonamm.controllers;

import it.dmi.dmifonbe.dmifonamm.entities.AmmUte;
import it.dmi.dmifonbe.dmifonamm.service.UtenteService;
import it.dmi.dmifonbe.model.Response;
import it.dmi.dmifonbe.model.messages.SuccessMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("utente-v1")
@RequestMapping("/api/utente")
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    private Logger log = LoggerFactory.getLogger(UtenteController.class);

    @PreAuthorize("@authorityService.hasPermission('/getUtente', #idUteRuo)")
    @GetMapping(value = "/getUtente", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmmUte> getUtente(
        @NotNull @RequestParam(value = "idUtente") Integer idUtente,
        @NotNull @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        return ResponseEntity.ok(utenteService.getUtente(idUtente, idUteRuo));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaUtente', #idUteRuo)")
    @GetMapping(value = "/ricercaUtente", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AmmUte>> ricercaUtente(
        @RequestParam(value = "username", required = false) String username,
        @RequestParam(value = "cognome", required = false) String cognome,
        @RequestParam(value = "autocomplete", required = false) String autocomplete,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        return ResponseEntity.ok(utenteService.ricercaUtente(username, cognome, autocomplete, idUteRuo));
    }

    //non posso controllare la preAuthorize in quanto viene utilizzata dalla login quanto non è ancora presente il ruolo
    @GetMapping(value = "/ricercaUtenteRuolo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmmUte> ricercaUtenteRuolo(@RequestParam(value = "username", required = false) String username)
        throws MicroServicesException {
        return ResponseEntity.ok(utenteService.ricercaUtenteRuolo(username));
    }

    @PreAuthorize("@authorityService.hasPermission('/inserisciUtente', #idUteRuo)")
    @PostMapping(value = "/inserisciUtente", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciUtente(
        @RequestBody @Valid AmmUte utenteDaInserire,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        AmmUte utenteInserito = utenteService.inserisciUtente(utenteDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), utenteInserito.getId()));
    }

    @PreAuthorize("@authorityService.hasPermission('/abilitazioneUtente', #idUteRuo)")
    @PostMapping(value = "/abilitazioneUtente", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> disabilitaUtente(
        @RequestParam(value = "idUtente") Integer idUtente,
        @RequestParam(value = "abilitato") boolean abilitato,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        utenteService.abilitazioneUtente(idUtente, abilitato);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaUtente', #idUteRuo)")
    @PostMapping(value = "/modificaUtente", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaUtente(
        @RequestBody @Valid AmmUte utenteDaModificare,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        utenteService.modificaUtente(utenteDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaRuoloDirezione', #idUteRuo)")
    @PostMapping(value = "/cancellaRuoloDirezione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> dissociaRuoloDirezione(
        @RequestParam(value = "idUtente") Integer idUtente,
        @RequestParam(value = "idAmmUteruo") Integer idAmmUteruo,
        @RequestParam(value = "idUteRuo") Integer idUteRuo
    ) throws MicroServicesException {
        utenteService.cancellaRuoloDirezione(idUtente, idAmmUteruo);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }
}
