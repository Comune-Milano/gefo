package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.repository.ProgettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AggiornaErediProgetto {

    @Autowired
    EntityManager em;

    //@Autowired
    //ProgettoService progettoService;

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    UtilService utilService;

    ProPro proNew;
    ProPro proOld;

    boolean aggProgettoPadre;
    boolean aggTipFin;
    boolean aggMacPro;
    boolean aggDir;
    boolean aggTem;
    boolean aggMun;
    boolean aggNil;
    boolean aggSet;
    boolean aggAss;


    public void setVecchioNuovo(ProPro vecchio, ProPro nuovo) {
        aggTipFin = false;
        aggMacPro = false;
        aggDir = false;
        aggTem = false;
        aggMun = false;
        aggNil = false;
        aggSet = false;
        aggAss = false;
        aggProgettoPadre = false;
        proOld = vecchio;
        proNew = nuovo;
        if (proOld == null || proNew == null)
            return;
        if ((proOld.getIdTipfin() == null && proNew.getIdTipfin() != null)
            || (proOld.getIdTipfin() != null && proNew.getIdTipfin() == null)
            || ((proOld.getIdTipfin() != null && proNew.getIdTipfin() != null) && (proOld.getIdTipfin().longValue() != proNew.getIdTipfin().longValue())))
            aggTipFin = true;
        if ((proOld.getIdMacpro() == null && proNew.getIdMacpro() != null)
            || (proOld.getIdMacpro() != null && proNew.getIdMacpro() == null)
            || ((proOld.getIdMacpro() != null && proNew.getIdMacpro() != null) && (proOld.getIdMacpro().longValue() != proNew.getIdMacpro().longValue())))
            aggMacPro = true;
        if ((proOld.getIdDir() == null && proNew.getIdDir() != null)
            || (proOld.getIdDir() != null && proNew.getIdDir() == null)
            || ((proOld.getIdDir() != null && proNew.getIdDir() != null) && (proOld.getIdDir().longValue() != proNew.getIdDir().longValue())))
            aggDir = true;
        if ((proOld.getIdTem() == null && proNew.getIdTem() != null)
            || (proOld.getIdTem() != null && proNew.getIdTem() == null)
            || ((proOld.getIdTem() != null && proNew.getIdTem() != null) && (proOld.getIdTem().longValue() != proNew.getIdTem().longValue())))
            aggTem = true;
        if ((proOld.getIdMun() == null && proNew.getIdMun() != null)
            || (proOld.getIdMun() != null && proNew.getIdMun() == null)
            || ((proOld.getIdMun() != null && proNew.getIdMun() != null) && (proOld.getIdMun().longValue() != proNew.getIdMun().longValue())))
            aggMun = true;
        if ((proOld.getIdNil() == null && proNew.getIdNil() != null)
            || (proOld.getIdNil() != null && proNew.getIdNil() == null)
            || ((proOld.getIdNil() != null && proNew.getIdNil() != null) && (proOld.getIdNil().longValue() != proNew.getIdNil().longValue())))
            aggNil = true;
        if ((proOld.getCodset() == null && proNew.getCodset() != null)
            || (proOld.getCodset() != null && proNew.getCodset() == null)
            || ((proOld.getCodset() != null && proNew.getCodset() != null) && (!proOld.getCodset().equals(proNew.getCodset()))))
            aggSet = true;
        if ((proOld.getCodass() == null && proNew.getCodass() != null)
            || (proOld.getCodass() != null && proNew.getCodass() == null)
            || ((proOld.getCodass() != null && proNew.getCodass() != null) && (!proOld.getCodass().equals(proNew.getCodass()))))
            aggAss = true;
        if ((proOld.getIdPropad() == null && proNew.getIdPropad() != null)
            || (proOld.getIdPropad() != null && proNew.getIdPropad() == null)
            || ((proOld.getIdPropad() != null && proNew.getIdPropad() != null) && (proOld.getIdPropad().longValue() != proNew.getIdPropad().longValue())))
            aggProgettoPadre = true;
    }

    public boolean isAggEredi() {
        if (isAggTipFin() || isAggMacPro() || isAggDir() || isAggTem() || isAggMun() || isAggNil() || isAggSet() || isAggAss())
            return true;
        else
            return false;
    }

    public void aggiornaEredi() {
        if (!isAggEredi())
            return;
        //estraggo i figli del progetto
        Optional<ProPro> proPadre = progettoRepository.findById(proOld.getId());
        if (proPadre.isEmpty())
            return;
        List<ProPro> erediList = getEredi(proPadre.get());
        List<Long> idErediList = new ArrayList<>();
        for (ProPro proProElement : erediList) {
            utilService.setInfoUpdateRow(proProElement);
            if (isAggTipFin()) proProElement.setIdTipfin(proNew.getIdTipfin());
            if (isAggMacPro()) proProElement.setIdMacpro(proNew.getIdMacpro());
            if (isAggDir()) proProElement.setIdDir(proNew.getIdDir());
            if (isAggTem()) proProElement.setIdTem(proNew.getIdTem());
            if (isAggMun()) proProElement.setIdMun(proNew.getIdMun());
            if (isAggNil()) proProElement.setIdNil(proNew.getIdNil());
            if (isAggSet()) proProElement.setCodset(proNew.getCodset());
            if (isAggAss()) proProElement.setCodass(proNew.getCodass());
            ProPro progettoModificato = progettoRepository.saveAndFlush(proProElement);
        }
    }

    public List<ProPro> getEredi(ProPro padre) {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProPro> query = cb.createQuery(ProPro.class);
        Root<ProPro> root = query.from(ProPro.class);
        predicates.add(cb.notEqual(root.get("id"), padre.getId()));
        predicates.add(cb.like(root.get("iddecpro"), padre.getIddecpro() + "%"));
        query.select(root).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(query).getResultList();
    }

    public boolean isAggProgettoPadre() {
        return aggProgettoPadre;
    }

    public void setAggProgettoPadre(boolean aggProgettoPadre) {
        this.aggProgettoPadre = aggProgettoPadre;
    }

    public boolean isAggTipFin() {
        return aggTipFin;
    }

    public void setAggTipFin(boolean aggTipFin) {
        this.aggTipFin = aggTipFin;
    }

    public boolean isAggMacPro() {
        return aggMacPro;
    }

    public void setAggMacPro(boolean aggMacPro) {
        this.aggMacPro = aggMacPro;
    }

    public boolean isAggDir() {
        return aggDir;
    }

    public void setAggDir(boolean aggDir) {
        this.aggDir = aggDir;
    }

    public boolean isAggTem() {
        return aggTem;
    }

    public void setAggTem(boolean aggTem) {
        this.aggTem = aggTem;
    }

    public boolean isAggMun() {
        return aggMun;
    }

    public void setAggMun(boolean aggMun) {
        this.aggMun = aggMun;
    }

    public boolean isAggNil() {
        return aggNil;
    }

    public void setAggNil(boolean aggNil) {
        this.aggNil = aggNil;
    }

    public boolean isAggSet() {
        return aggSet;
    }

    public void setAggSet(boolean aggSet) {
        this.aggSet = aggSet;
    }

    public boolean isAggAss() {
        return aggAss;
    }

    public void setAggAss(boolean aggAss) {
        this.aggAss = aggAss;
    }
}
