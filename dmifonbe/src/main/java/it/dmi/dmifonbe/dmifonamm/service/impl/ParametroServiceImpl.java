package it.dmi.dmifonbe.dmifonamm.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.ParametroService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParametroServiceImpl implements ParametroService {

    @Autowired
    ParametriRepository parametriRepository;

    @Override
    public String getParametroByCodice(String codice) throws MicroServicesException {
        Optional<AmmPar> ammPar = parametriRepository.getAmmParByCodiceIgnoreCase(codice);
        if (ammPar.isEmpty())
            return "";
        else
            return ammPar.get().getValore();
    }
}
