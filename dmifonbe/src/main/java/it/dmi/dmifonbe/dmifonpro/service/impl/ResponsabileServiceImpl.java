package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmUte;
import it.dmi.dmifonbe.dmifonamm.repository.UtenteRepository;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.entities.ProRes;
import it.dmi.dmifonbe.dmifonpro.model.DettaglioResponsabile;
import it.dmi.dmifonbe.dmifonpro.model.ResponsabileResponse;
import it.dmi.dmifonbe.dmifonpro.repository.ProgettoRepository;
import it.dmi.dmifonbe.dmifonpro.repository.ResponsabileRepository;
import it.dmi.dmifonbe.dmifonpro.repository.TipoResponsabileRepository;
import it.dmi.dmifonbe.dmifonpro.service.ResponsabileService;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class ResponsabileServiceImpl implements ResponsabileService {

    @Value("${prores}")
    String prores;

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    EntityManager em;

    @Autowired
    TipoResponsabileRepository tipoResponsabileRepository;

    @Autowired
    ResponsabileRepository responsabileRepository;

    @Override
    public ResponsabileResponse getResponsabiliProgetto(Integer idPro) throws MicroServicesException {
        ResponsabileResponse response = new ResponsabileResponse();
        Optional<ProPro> progettoOpt = progettoRepository.findById(idPro);
        if (!progettoOpt.isPresent()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            response.setProgetto(progettoOpt.get());
            if (progettoOpt.get().getIdPropad() != null) {
                Optional<ProPro> progettoPadreOpt = progettoRepository.findById(progettoOpt.get().getIdPropad().intValue());
                if (progettoPadreOpt.isPresent()) response.setProgettoPadreLabel(
                    progettoPadreOpt.get().getCodpro() + " - " + progettoPadreOpt.get().getDespro()
                ); else throw new MicroServicesException(
                    ErrorMessages.NO_DATA_FOUND_PARENT_PRO.getUserMessage(),
                    ErrorMessages.NO_DATA_FOUND_PARENT_PRO.getCode()
                );
            } else response.setProgettoPadreLabel(" - ");
        }
        Query res = em.createNativeQuery(prores);
        res.setParameter(1, idPro);
        List<Object[]> responsabiliListObjects = (List<Object[]>) res.getResultList();
        List<DettaglioResponsabile> dettaglioResponsabileList = new ArrayList<>();
        for (Object[] responsabileObj : responsabiliListObjects) {
            DettaglioResponsabile detres = new DettaglioResponsabile();
            ProRes responsabile = new ProRes();
            responsabile.setId(responsabileObj[0] == null ? 0 : (Integer) responsabileObj[0]);
            responsabile.setIdPro(idPro.longValue());
            BigInteger idTipRes = (BigInteger) responsabileObj[1];
            responsabile.setIdTipres(idTipRes.longValue());
            BigInteger idUte = (BigInteger) responsabileObj[4];
            responsabile.setIdUte(idUte.longValue());
            responsabile.setNotres((String) responsabileObj[5]);
            detres.setResponsabile(responsabile);
            if (responsabile.getIdUte() != 0) {
                Optional<AmmUte> utenteOpt = utenteRepository.findById(responsabile.getIdUte().intValue());
                if (utenteOpt.isPresent()) detres.setUtente(utenteOpt.get());
            }
            responsabile.setProTipresByIdTipres(tipoResponsabileRepository.findById(idTipRes.intValue()).get());
            dettaglioResponsabileList.add(detres);
        }
        response.setResponsabili(dettaglioResponsabileList);
        return response;
    }

    @Override
    public void modificaResponsabiliProgetto(List<ProRes> responsabiliDaModificare) throws MicroServicesException {
        this.checkModificaResponsabiliProgetto(responsabiliDaModificare);
        responsabileRepository.saveAll(responsabiliDaModificare);
    }

    private void checkModificaResponsabiliProgetto(List<ProRes> responsabiliDaModificare) throws MicroServicesException {
        for (ProRes responsabile : responsabiliDaModificare) {
            if (
                !responsabile.getIdUte().equals(0L) && !utenteRepository.existsById(responsabile.getIdUte().intValue())
            ) throw new MicroServicesException(ErrorMessages.USER_NOT_FOUND.getUserMessage(), ErrorMessages.USER_NOT_FOUND.getCode());
            if (responsabile.getIdPro() == null || responsabile.getIdPro().equals(0L)) throw new MicroServicesException(
                ErrorMessages.MANDATORY_IDPRO.getUserMessage(),
                ErrorMessages.MANDATORY_IDPRO.getCode()
            );
            if (responsabile.getIdTipres() == null || responsabile.getIdTipres().equals(0L)) throw new MicroServicesException(
                ErrorMessages.MANDATORY_TIPRES.getUserMessage(),
                ErrorMessages.MANDATORY_TIPRES.getCode()
            );
            if (!tipoResponsabileRepository.existsById(responsabile.getIdTipres().intValue())) throw new MicroServicesException(
                ErrorMessages.TIPRES_NOT_FOUND.getUserMessage(),
                ErrorMessages.TIPRES_NOT_FOUND.getCode()
            );
        }
    }
}
