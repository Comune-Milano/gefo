package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProMil;
import it.dmi.dmifonbe.dmifonpro.model.MilestoneFaseDetail;
import it.dmi.dmifonbe.dmifonpro.service.MilestoneService;
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

@RestController("milestone-v1")
@RequestMapping("/api/milestone")
public class MilestoneController {

    @Autowired
    MilestoneService milestoneService;

    private Logger log = LoggerFactory.getLogger(MilestoneController.class);

    @PreAuthorize("@authorityService.hasPermission('/getMilestoneFase', #idUteRuo)")
    @GetMapping(value = "/getMilestoneFase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MilestoneFaseDetail> getMilestoneFase(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idFas") Integer idFas
    ) throws MicroServicesException {
        return ResponseEntity.ok(milestoneService.getMilestoneFase(idFas));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaMilestoneFase', #idUteRuo)")
    @PostMapping(value = "/modificaMilestoneFase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaMilestoneFase(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid List<ProMil> milsDaModificare
    ) throws MicroServicesException {
        milestoneService.modificaMilestoneFase(milsDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaMilestone', #idUteRuo)")
    @DeleteMapping(value = "/cancellaMilestone", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaMilestone(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idMil") Integer idMil
    ) throws MicroServicesException {
        milestoneService.cancellaMilestone(idMil);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }
}
