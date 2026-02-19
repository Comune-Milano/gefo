package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.repository.*;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgettoDeleteServiceImpl {

    @Autowired
    ProgettoRepository progettoRepository;

    @Transactional
    public void cancellaProgettoTransactional(Integer id) throws MicroServicesException {
        this.checkDeleteProgetto(id);
        progettoRepository.deleteById(id);
    }

    private void checkDeleteProgetto(Integer id) throws MicroServicesException {
        Optional<ProPro> proToDeleteOpt = progettoRepository.findById(id);
        if (proToDeleteOpt.isPresent()) {
            ProPro proToDelete = proToDeleteOpt.get();
            Hibernate.initialize(proToDelete.getProRicsById());
            Hibernate.initialize(proToDelete.getProAvasById());
            Hibernate.initialize(proToDelete.getProDdrsById());
            if (!progettoRepository.findAllByIdPropad(id.longValue()).isEmpty()) throw new MicroServicesException(
                ErrorMessages.PROPRO_DELETE_WITH_SONS_NOT_ALLOWED.getUserMessage(),
                ErrorMessages.PROPRO_DELETE_WITH_SONS_NOT_ALLOWED.getCode()
            );
            if (!proToDelete.getProDdrsById().isEmpty()) throw new MicroServicesException(
                ErrorMessages.PROPRO_DELETE_WITH_DDR_NOT_ALLOWED.getUserMessage(),
                ErrorMessages.PROPRO_DELETE_WITH_DDR_NOT_ALLOWED.getCode()
            );
            if (!proToDelete.getProRicsById().isEmpty()) throw new MicroServicesException(
                ErrorMessages.PROPRO_DELETE_WITH_RIC_NOT_ALLOWED.getUserMessage(),
                ErrorMessages.PROPRO_DELETE_WITH_RIC_NOT_ALLOWED.getCode()
            );
            if (!proToDelete.getProAvasById().isEmpty()) throw new MicroServicesException(
                ErrorMessages.PROPRO_DELETE_WITH_AVA_NOT_ALLOWED.getUserMessage(),
                ErrorMessages.PROPRO_DELETE_WITH_AVA_NOT_ALLOWED.getCode()
            );
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }
}
