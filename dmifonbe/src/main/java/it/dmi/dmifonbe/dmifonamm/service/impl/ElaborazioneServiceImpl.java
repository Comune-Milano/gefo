package it.dmi.dmifonbe.dmifonamm.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmEla;
import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.model.ElaborazioneRicerca;
import it.dmi.dmifonbe.dmifonamm.repository.ElaborazioneRepository;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.ElaborazioneService;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.model.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class ElaborazioneServiceImpl implements ElaborazioneService {

    @Autowired
    ElaborazioneRepository elaborazioneRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    EntityManager em;

    @Autowired
    ParametriRepository parametriRepository;

    private String desEla;
    private String tipOut;

    //AmmEla ammEla;
    @Override
    public List<AmmEla> ricercaElaborazione(ElaborazioneRicerca elaRic) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AmmEla> query = cb.createQuery(AmmEla.class);
        Root<AmmEla> root = query.from(AmmEla.class);
        List<Predicate> predicates = new ArrayList<>();
        if (elaRic.getUsername() != null && !elaRic.getUsername().isEmpty()) {
            predicates.add(cb.like(cb.upper(root.get("usrCreate")), "%" + elaRic.getUsername().toUpperCase() + "%"));
        }
        if (elaRic.getDtainida() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("dtaini"), elaRic.getDtainida()));
        }
        if (elaRic.getDtainia() != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(elaRic.getDtainia());
            c.add(Calendar.DATE, 1);
            predicates.add(cb.lessThanOrEqualTo(root.get("dtaini"), c.getTime()));
        }
        query.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.desc(root.get("dtaini")));
        List<AmmEla> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
        return results;
    }

    private int getMaxRecords() {
        int maxRecords = 0;
        Optional<AmmPar> optParMaxRecords = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCAROWSONLY.getValue());
        if (optParMaxRecords.isPresent()) maxRecords = Integer.parseInt(optParMaxRecords.get().getValore());
        return maxRecords;
    }


    public Integer start(String desEla) {
        this.desEla = desEla;
        AmmEla ammEla = new AmmEla();
        ammEla.setDesela(desEla);
        ammEla.setStaela("A");
        ammEla.setDtaini(new Date());
        utilService.setInfoInsertRow(ammEla);
        AmmEla ammElaSaved = elaborazioneRepository.saveAndFlush(ammEla);
        return ammElaSaved.getId();
    }

    public void stop(Integer idElda) {
        Optional<AmmEla> ammEla = elaborazioneRepository.findById(idElda);
        if (!ammEla.isEmpty()) {
            ammEla.get().setStaela("T");
            ammEla.get().setDtafin(new Date());
            utilService.setInfoUpdateRow(ammEla.get());
            elaborazioneRepository.saveAndFlush(ammEla.get());
        }
    }
/*
    public Integer getId() {
        if (ammEla != null)
            return ammEla.getId();
        else
            return null;
    }
    */
}
