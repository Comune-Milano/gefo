package it.dmi.dmifonbe.dmifonamm.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmFun;
import it.dmi.dmifonbe.dmifonamm.entities.AmmPer;
import it.dmi.dmifonbe.dmifonamm.entities.AmmRuo;
import it.dmi.dmifonbe.dmifonamm.repository.AmmPerRepository;
import it.dmi.dmifonbe.dmifonamm.repository.FunzioneRepository;
import it.dmi.dmifonbe.dmifonamm.repository.RuoloRepository;
import it.dmi.dmifonbe.dmifonamm.service.FunzioneService;
import it.dmi.dmifonbe.dmifonamm.service.RuoloService;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RuoloServiceImpl implements RuoloService {

    @Autowired
    RuoloRepository ruoloRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    FunzioneService funzioneService;

    @Autowired
    AmmPerRepository ammPerRepository;

    @Autowired
    FunzioneRepository funzioneRepository;

    @Override
    public AmmRuo getRuolo(Integer id) throws MicroServicesException {
        Optional<AmmRuo> ruolo = ruoloRepository.findById(id);
        if (ruolo.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else return ruolo.get();
    }

    @Override
    public AmmRuo inserisciRuolo(AmmRuo ruoloDaInserire) throws MicroServicesException {
        if (ruoloDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.IDRUOLO_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.IDRUOLO_NOT_ALLOWED.getCode()
        );
        utilService.setInfoInsertRow(ruoloDaInserire);
        return ruoloRepository.saveAndFlush(ruoloDaInserire);
    }

    @Override
    public void cancellaRuolo(Integer idRuolo) {
        ruoloRepository.deleteById(idRuolo);
    }

    @Override
    public AmmRuo modificaRuolo(AmmRuo ruoloDaModificare) {
        Optional<AmmRuo> ruoloOriginaleOpt = ruoloRepository.findById(ruoloDaModificare.getId());
        if (ruoloOriginaleOpt.isPresent()) {
            ruoloDaModificare.setUsrCreate(ruoloOriginaleOpt.get().getUsrCreate());
            ruoloDaModificare.setDtCreate(ruoloOriginaleOpt.get().getDtCreate());
        }
        utilService.setInfoUpdateRow(ruoloDaModificare);
        return ruoloRepository.saveAndFlush(ruoloDaModificare);
    }

    @Override
    public List<AmmRuo> ricercaRuolo(String codRuo, String desRuo, String autocomplete) {
        if (codRuo != null) {
            List<AmmRuo> results = new ArrayList<>();
            Optional<AmmRuo> ammRuoBycodRuo = ruoloRepository.findByCodruo(codRuo);
            if (ammRuoBycodRuo.isPresent()) results.add(ammRuoBycodRuo.get());
            return results;
        } else if (desRuo != null) {
            return ruoloRepository.findByDesruo(desRuo);
        } else if (autocomplete != null) {
            return ruoloRepository.findAutocomplete(autocomplete.toUpperCase());
        } else return ruoloRepository.findAll();
    }

    @Override
    public AmmPer associaFunzione(Integer idRuolo, Integer idFunzione) throws MicroServicesException {
        AmmRuo ruolo = this.getRuolo(idRuolo);
        AmmFun funzione = funzioneService.getFunzione(idFunzione);
        AmmPer ammPer = new AmmPer();
        ammPer.setIdRuo(Long.valueOf(ruolo.getId()));
        ammPer.setIdFun(Long.valueOf(funzione.getId()));
        utilService.setInfoInsertRow(ammPer);
        ruolo.getAmmPersById().add(ammPer);
        ruoloRepository.saveAndFlush(ruolo);
        return ammPer;
    }

    @Override
    public void dissociaFunzione(Integer idRuolo, Integer idPermesso) throws MicroServicesException {
        Optional<AmmPer> ammPerToDeleteOptional = ammPerRepository.findById(idPermesso);
        if (ammPerToDeleteOptional.isPresent()) {
            if (ammPerToDeleteOptional.get().getIdRuo() != Long.valueOf(idRuolo)) {
                throw new MicroServicesException(
                    ErrorMessages.AMMPER_NOT_PRESENT.getUserMessage(),
                    ErrorMessages.AMMPER_NOT_PRESENT.getCode()
                );
            } else {
                ammPerRepository.deleteById(idPermesso);
            }
        } else throw new MicroServicesException(
            ErrorMessages.AMMPER_NOT_PRESENT.getUserMessage(),
            ErrorMessages.AMMPER_NOT_PRESENT.getCode()
        );
    }

    @Override
    @Transactional
    public List<String> getPermessiRuolo(Integer idRuolo) {
        List<String> results = new ArrayList<>();
        List<AmmPer> perList = ammPerRepository.findByIdRuo(Long.valueOf(idRuolo.longValue()));
        if (!perList.isEmpty()) for (AmmPer ammPer : perList) {
            Hibernate.initialize(ammPer.getAmmFunByIdFun());
            results.add(ammPer.getAmmFunByIdFun().getNome());
        }
        return results;
    }
}
