package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.service.BatchService;
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

@RestController("batch-v1")
@RequestMapping("/api/batch")
public class BatchController {

    private Logger log = LoggerFactory.getLogger(BatchController.class);

    @Autowired
    BatchService batchService;

    @PreAuthorize("@authorityService.hasPermission('/aggiornamentoEntitaContabili', #idUteRuo)")
    @GetMapping(value = "/aggiornamentoEntitaContabili", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> aggiornamentoEntitaContabili(@RequestParam(value = "idUteRuo") Integer idUteRuo)
        throws MicroServicesException {
        batchService.aggiornamentoEntitaContabili();
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_BATCH.getUserMessage()));
    }
}
