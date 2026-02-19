package it.dmi.dmifonbe.dmifonamm.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmDir;
import it.dmi.dmifonbe.dmifonamm.repository.DirezioneRepository;
import it.dmi.dmifonbe.dmifonamm.service.DirezioneService;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DirezioneServiceImpl implements DirezioneService {

    @Autowired
    DirezioneRepository direzioneRepository;

    @Autowired
    UtilServiceImpl utilService;

    @Override
    public AmmDir getDirezione(Integer id) throws MicroServicesException {
        Optional<AmmDir> direzione = direzioneRepository.findById(id);
        if (direzione.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else return direzione.get();
    }

    @Override
    public AmmDir inserisciDirezione(AmmDir direzioneDaInserire) {
        utilService.setInfoInsertRow(direzioneDaInserire);
        return direzioneRepository.saveAndFlush(direzioneDaInserire);
    }

    @Override
    public void cancellaDirezione(Integer idDirezione) {
        direzioneRepository.deleteById(idDirezione);
    }

    @Override
    public AmmDir modificaDirezione(AmmDir direzioneDaModificare) {
        Optional<AmmDir> direzioneOriginaleOpt = direzioneRepository.findById(direzioneDaModificare.getId());
        if (direzioneOriginaleOpt.isPresent()) {
            direzioneDaModificare.setUsrCreate(direzioneOriginaleOpt.get().getUsrCreate());
            direzioneDaModificare.setDtCreate(direzioneOriginaleOpt.get().getDtCreate());
        }
        utilService.setInfoUpdateRow(direzioneDaModificare);
        return direzioneRepository.saveAndFlush(direzioneDaModificare);
    }

    @Override
    public List<AmmDir> ricercaDirezione(String codDir, String desDir, String autocomplete) {
        if (codDir != null) {
            List<AmmDir> results = new ArrayList<>();
            Optional<AmmDir> ammDirBycodDir = direzioneRepository.findByCoddirOrderByCoddir(codDir);
            if (ammDirBycodDir.isPresent()) results.add(ammDirBycodDir.get());
            return results;
        } else if (desDir != null) {
            return direzioneRepository.findByDesdir(desDir);
        } else if (autocomplete != null) {
            if (autocomplete != null && !autocomplete.isBlank()) {
                return direzioneRepository.findAutocomplete(autocomplete.toUpperCase());
            } else {
                return direzioneRepository.findAllByOrderByCoddirAsc();
            }
            //return direzioneRepository.findAutocomplete(autocomplete.toUpperCase());
        } else return direzioneRepository.findAll();
    }
}
