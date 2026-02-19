package it.dmi.dmifonbe.dmifonamm.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.model.ProgettoAndProgettoPadre;
import it.dmi.dmifonbe.dmifonpro.repository.ProgettoRepository;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilServiceImpl implements UtilService {

    @Autowired
    ProgettoRepository progettoRepository;

    @Override
    @Transactional
    public String getUsernameAuthenticated() {
        return (String) ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaims().get("preferred_username");
    }

    @Override
    @Transactional
    public void setInfoInsertRow(EntitySuperClass entity) {
        entity.setUsrCreate(this.getUsernameAuthenticated());
        entity.setDtCreate(new Date());
        entity.setUsrLstupd(this.getUsernameAuthenticated());
        entity.setDtLstupd(new Date());
    }

    @Override
    @Transactional
    public void setInfoUpdateRow(EntitySuperClass entity) {
        entity.setUsrLstupd(this.getUsernameAuthenticated());
        entity.setDtLstupd(new Date());
    }

    @Override
    public ProgettoAndProgettoPadre getProgettoAndProgettoPadre(Integer idPro) throws MicroServicesException {
        ProgettoAndProgettoPadre result = new ProgettoAndProgettoPadre();
        Optional<ProPro> progettoOpt = progettoRepository.findById(idPro);
        if (progettoOpt.isPresent()) {
            result.setProgetto(progettoOpt.get());
            if (progettoOpt.get().getIdPropad() != null) {
                Optional<ProPro> progettoPadreOpt = progettoRepository.findById(progettoOpt.get().getIdPropad().intValue());
                if (progettoPadreOpt.isPresent()) result.setProgettoPadreLabel(
                    progettoPadreOpt.get().getCodpro() + " - " + progettoPadreOpt.get().getDespro()
                ); else throw new MicroServicesException(
                    ErrorMessages.NO_DATA_FOUND_PARENT_PRO.getUserMessage(),
                    ErrorMessages.NO_DATA_FOUND_PARENT_PRO.getCode()
                );
            } else result.setProgettoPadreLabel(" - ");
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
        return result;
    }
}
