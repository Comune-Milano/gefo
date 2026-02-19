package it.dmi.dmifonbe.dmifonamm.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmFun;
import it.dmi.dmifonbe.dmifonamm.repository.FunzioneRepository;
import it.dmi.dmifonbe.dmifonamm.service.FunzioneService;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FunzioneServiceImpl implements FunzioneService {

    @Autowired
    FunzioneRepository funzioneRepository;

    @Autowired
    UtilService utilService;

    @Override
    public AmmFun getFunzione(Integer id) throws MicroServicesException {
        Optional<AmmFun> funzione = funzioneRepository.findById(id);
        if (funzione.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else return funzione.get();
    }

    @Override
    public AmmFun inserisciFunzione(AmmFun funzioneDaInserire) {
        utilService.setInfoInsertRow(funzioneDaInserire);
        return funzioneRepository.saveAndFlush(funzioneDaInserire);
    }

    @Override
    public void cancellaFunzione(Integer idFunzione) {
        funzioneRepository.deleteById(idFunzione);
    }

    @Override
    public AmmFun modificaFunzione(AmmFun funzioneDaModificare) {
        Optional<AmmFun> funzioneOriginaleOpt = funzioneRepository.findById(funzioneDaModificare.getId());
        if (funzioneOriginaleOpt.isPresent()) {
            funzioneDaModificare.setUsrCreate(funzioneOriginaleOpt.get().getUsrCreate());
            funzioneDaModificare.setDtCreate(funzioneOriginaleOpt.get().getDtCreate());
        }
        utilService.setInfoUpdateRow(funzioneDaModificare);
        return funzioneRepository.saveAndFlush(funzioneDaModificare);
    }

    @Override
    public List<AmmFun> getFunzioni() {
        return funzioneRepository.findAll();
    }
}
