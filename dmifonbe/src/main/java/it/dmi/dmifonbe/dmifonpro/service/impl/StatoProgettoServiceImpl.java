package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonpro.entities.ProStapro;
import it.dmi.dmifonbe.dmifonpro.repository.StatoProgettoRepository;
import it.dmi.dmifonbe.dmifonpro.service.StatoProgettoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatoProgettoServiceImpl implements StatoProgettoService {

    @Autowired
    StatoProgettoRepository statoProgettoRepository;

    @Override
    @Transactional
    public List<ProStapro> getAllStatiProgetto() {
        return statoProgettoRepository.findAll();
    }
}
