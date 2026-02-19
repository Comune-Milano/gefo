package it.dmi.dmifonbe.dmifonpro.controllers;

import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipimp;
import it.dmi.dmifonbe.dmifonpro.model.ImpegniProgetto;
import it.dmi.dmifonbe.dmifonpro.model.RicercaEntitaContabile;
import it.dmi.dmifonbe.dmifonpro.service.ImpegniProgettoService;
import it.dmi.dmifonbe.model.Parameters;
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

@RestController("impegno-v1")
@RequestMapping("/api/detcon")
public class ImpegnoController {

    private Logger log = LoggerFactory.getLogger(ImpegnoController.class);

    @Autowired
    ImpegniProgettoService impegniProgettoService;

    @PreAuthorize("@authorityService.hasPermission('/getImpegniProgetto', #idUteRuo)")
    @GetMapping(value = "/getImpegniProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImpegniProgetto> getImpegniProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idpro") Integer idPro
    ) throws MicroServicesException {
        return ResponseEntity.ok(impegniProgettoService.getImpegniProgetto(idPro));
    }

    @PreAuthorize("@authorityService.hasPermission('/modificaImpegniProgetto', #idUteRuo)")
    @PostMapping(value = "/modificaImpegniProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> modificaProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid ImpegniProgetto impegniProgettoDaModificare
    ) throws MicroServicesException {
        impegniProgettoService.modificaImpegniProgetto(impegniProgettoDaModificare);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_EDIT.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/cancellaDettaglioContabile', #idUteRuo)")
    @DeleteMapping(value = "/cancellaDettaglioContabile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancellaDettaglioContabile(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "idDetCon") Integer idDetCon
    ) throws MicroServicesException {
        this.impegniProgettoService.cancellaDettaglioContabile(idDetCon);
        return ResponseEntity.ok(new Response(SuccessMessages.SUCCESS_DELETE.getUserMessage()));
    }

    @PreAuthorize("@authorityService.hasPermission('/getAccertamento', #idUteRuo)")
    @GetMapping(value = "/getAccertamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProEntcon> getAccertamento(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "codentcon") String codEntCon
    ) throws MicroServicesException {
        return ResponseEntity.ok(impegniProgettoService.getAccertamento(codEntCon));
    }

    @PreAuthorize("@authorityService.hasPermission('/getImpegno', #idUteRuo)")
    @GetMapping(value = "/getImpegno", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProEntcon> getImpegno(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "codentcon") String codEntCon
    ) throws MicroServicesException {
        return ResponseEntity.ok(impegniProgettoService.getImpegno(codEntCon));
    }

    @PreAuthorize("@authorityService.hasPermission('/getCrono', #idUteRuo)")
    @GetMapping(value = "/getCrono", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProEntcon> getCrono(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "codentcon") String codEntCon
    ) throws MicroServicesException {
        return ResponseEntity.ok(impegniProgettoService.getCrono(codEntCon));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaAccertamenti', #idUteRuo)")
    @PostMapping(value = "/ricercaAccertamenti", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProEntcon>> ricercaAccertamenti(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid RicercaEntitaContabile ricEntCont
    ) throws MicroServicesException {
        return ResponseEntity.ok(impegniProgettoService.getEntitaContabile(Parameters.TIPENTACCE.getValue(), ricEntCont));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaImpegni', #idUteRuo)")
    @PostMapping(value = "/ricercaImpegni", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProEntcon>> ricercaImpegni(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestBody @Valid RicercaEntitaContabile ricEntCont
    ) throws MicroServicesException {
        return ResponseEntity.ok(impegniProgettoService.getEntitaContabile(Parameters.TIPENTIMPE.getValue(), ricEntCont));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaAccertamentiProgetto', #idUteRuo)")
    @GetMapping(value = "/ricercaAccertamentiProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProEntcon>> ricercaAccertamentiProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "id") Integer id
    ) throws MicroServicesException {
        return ResponseEntity.ok(impegniProgettoService.ricercaAccertamentiProgetto(id, Parameters.TIPENTACCE.getValue()));
    }

    @PreAuthorize("@authorityService.hasPermission('/ricercaImpegniProgetto', #idUteRuo)")
    @GetMapping(value = "/ricercaImpegniProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProEntcon>> ricercaImpegniProgetto(
        @RequestParam(value = "idUteRuo") Integer idUteRuo,
        @RequestParam(value = "id") Integer id
    ) throws MicroServicesException {
        return ResponseEntity.ok(impegniProgettoService.ricercaAccertamentiProgetto(id, Parameters.TIPENTIMPE.getValue()));
    }

    @PreAuthorize("@authorityService.hasPermission('/getTipoImportoRisorsa', #idUteRuo)")
    @GetMapping(value = "/getTipoImportoRisorsa", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProTipimp>> getTipoImportoRisorsa(@RequestParam(value = "idUteRuo") Integer idUteRuo)
        throws MicroServicesException {
        return ResponseEntity.ok(impegniProgettoService.getTipoImportoRisorsa());
    }
}
