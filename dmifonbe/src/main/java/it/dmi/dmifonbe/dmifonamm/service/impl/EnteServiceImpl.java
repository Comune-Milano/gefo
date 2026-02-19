package it.dmi.dmifonbe.dmifonamm.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmEnt;
import it.dmi.dmifonbe.dmifonamm.repository.EnteRepository;
import it.dmi.dmifonbe.dmifonamm.service.EnteService;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnteServiceImpl implements EnteService {

    @Autowired
    EnteRepository enteRepository;

    @Override
    public AmmEnt getEnte() throws MicroServicesException {
        Optional<AmmEnt> ente = enteRepository.findById(Integer.valueOf(1));
        if (ente.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else return ente.get();
    }
}
