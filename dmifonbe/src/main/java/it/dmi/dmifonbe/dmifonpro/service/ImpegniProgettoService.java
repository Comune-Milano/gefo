package it.dmi.dmifonbe.dmifonpro.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProMun;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipimp;
import it.dmi.dmifonbe.dmifonpro.model.ImpegniProgetto;
import it.dmi.dmifonbe.dmifonpro.model.RicercaEntitaContabile;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface ImpegniProgettoService {
    void modificaImpegniProgetto(ImpegniProgetto impegniProgettoDaModificare) throws MicroServicesException;
    ImpegniProgetto getImpegniProgetto(Integer idPro) throws MicroServicesException;
    ProEntcon getAccertamento(String codEntCon) throws MicroServicesException;
    ProEntcon getImpegno(String codEntCon) throws MicroServicesException;
    ProEntcon getCrono(String codEntCon) throws MicroServicesException;
    List<ProEntcon> getEntitaContabile(String tipEntCon, RicercaEntitaContabile ricEntCon) throws MicroServicesException;
    void cancellaDettaglioContabile(Integer id) throws MicroServicesException;
    List<ProEntcon> ricercaAccertamentiProgetto(Integer id, String tipEntCon) throws MicroServicesException;
    List<ProTipimp> getTipoImportoRisorsa() throws MicroServicesException;
}
