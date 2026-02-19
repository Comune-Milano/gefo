package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonpro.entities.ProStaban;
import it.dmi.dmifonbe.dmifonpro.repository.StatoBandoRepository;
import it.dmi.dmifonbe.dmifonpro.service.StatoBandoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatoBandoServiceImpl implements StatoBandoService {

    @Autowired
    StatoBandoRepository statoBandoRepository;

    @Override
    @Transactional
    public List<ProStaban> getAllStatiBando() {
        return statoBandoRepository.findAll();
    }
}
