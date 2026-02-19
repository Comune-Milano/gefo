package it.dmi.dmifonbe.dmifonpro.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AggiornaLivelliProgetto {

    @Autowired
    EntityManager em;

    @Value("${cntlivinf1}")
    String cntlivinf1;

    @Value("${cntlivinf2}")
    String cntlivinf2;

    @Value("${cntlivinf3}")
    String cntlivinf3;

    @Value("${livpro1}")
    String livpro1;

    @Value("${livpro2}")
    String livpro2;

    @Value("${livpro3}")
    String livpro3;

    @Transactional
    public void aggiornaCntlivinf() {
        this.executeUpdate(cntlivinf1);
        this.executeUpdate(cntlivinf2);
        for (int i = 1; i <= 5; i++) {
            this.executeUpdate(cntlivinf3, i, i - 1);
        }
    }

    @Transactional
    public void aggiornaLivpro() {
        this.executeUpdate(livpro1);
        this.executeUpdate(livpro2);
        for (int i = 1; i <= 5; i++) {
            this.executeUpdate(livpro3, i + 1, i);
        }
    }

    @Transactional
    void executeUpdate(String query, int param1, int param2) {
        Query res = em.createNativeQuery(query);
        res.setParameter(1, param1);
        res.setParameter(2, param2);
        res.executeUpdate();
    }

    @Transactional
    void executeUpdate(String query) {
        Query res = em.createNativeQuery(query);
        res.executeUpdate();
    }
}
