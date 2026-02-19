package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonpro.entities.ProStafin;
import it.dmi.dmifonbe.dmifonpro.repository.StatoFinanziamentoRepository;
import it.dmi.dmifonbe.dmifonpro.service.StatoFinanziamentoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatoFinanziamentoServiceImpl implements StatoFinanziamentoService {

    @Autowired
    StatoFinanziamentoRepository statoFinanziamentoRepository;

    @Override
    @Transactional
    public List<ProStafin> getAllStatiFinanziamento() {
        return statoFinanziamentoRepository.findAll();
    }
}
