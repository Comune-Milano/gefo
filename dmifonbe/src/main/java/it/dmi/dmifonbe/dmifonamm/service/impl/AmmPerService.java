package it.dmi.dmifonbe.dmifonamm.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmFun;
import it.dmi.dmifonbe.dmifonamm.entities.AmmPer;
import it.dmi.dmifonbe.dmifonamm.entities.AmmRuo;
import it.dmi.dmifonbe.dmifonamm.model.Permesso;
import it.dmi.dmifonbe.dmifonamm.repository.AmmFunRepository;
import it.dmi.dmifonbe.dmifonamm.repository.AmmPerRepository;
import it.dmi.dmifonbe.dmifonamm.repository.AmmRuoRepository;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfin;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AmmPerService {

    @Autowired
    EntityManager em;

    @EventListener(ApplicationReadyEvent.class)
    @Cacheable("RuoloFunzioni")
    public List<Permesso> loadRuoloFunzioni() {
        List<Object[]> permessiObj = em
            .createQuery("select ap.idRuo , af.nome  from AmmPer ap inner join AmmFun af on ap.idFun = af.id", Object[].class)
            .getResultList();
        List<Permesso> permessi = new ArrayList<>();
        for (Object[] row : permessiObj) {
            Permesso permesso = new Permesso((Long) row[0], (String) row[1]);
            permessi.add(permesso);
        }
        return permessi;
    }
}
