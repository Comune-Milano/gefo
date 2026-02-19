package it.dmi.dmifonbe.dmifonamm.controllers;

import it.dmi.dmifonbe.dmifonamm.entities.AmmFun;
import it.dmi.dmifonbe.dmifonamm.service.FunzioneService;
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
import org.springframework.web.bind.annotation.*;

@RestController("funzione-v1")
@RequestMapping("/api/funzione")
public class FunzioneController {

    @Autowired
    FunzioneService funzioneService;

    private Logger log = LoggerFactory.getLogger(FunzioneController.class);

    @GetMapping(value = "/getFunzione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmmFun> getFunzione(@RequestParam(value = "idFunzione") Integer idFunzione) throws MicroServicesException {
        return ResponseEntity.ok(funzioneService.getFunzione(idFunzione));
    }

    @GetMapping(value = "/getFunzioni", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AmmFun>> getFunzioni() {
        return ResponseEntity.ok(funzioneService.getFunzioni());
    }

    @PostMapping(value = "/inserisciFunzione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> inserisciFunzione(@RequestBody @Valid AmmFun funzioneDaInserire) {
        AmmFun funzioneInserita = funzioneService.inserisciFunzione(funzioneDaInserire);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_INSERT.getUserMessage(), funzioneInserita.getId()));
    }

    @DeleteMapping(value = "/cancellaFunzione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaFunzione(@RequestParam(value = "idFunzione") Integer idFunzione) {
        funzioneService.cancellaFunzione(idFunzione);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PostMapping(value = "/modificaFunzione", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaFunzione(@RequestBody @Valid AmmFun funzioneDaModificare) {
        funzioneService.modificaFunzione(funzioneDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }
}
