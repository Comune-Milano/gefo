package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProTem;
import it.dmi.dmifonbe.dmifonpro.model.ProTemResponse;
import it.dmi.dmifonbe.dmifonpro.model.TematicaResponse;
import it.dmi.dmifonbe.dmifonpro.model.TematicaRicerca;
import it.dmi.dmifonbe.dmifonpro.repository.TematicaRepository;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.TematicaService;
import it.dmi.dmifonbe.model.EntityType;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.Totali;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TematicaServiceImpl implements TematicaService {

    @Autowired
    TematicaRepository tematicaRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    EntityManager em;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    CalcolaTotaliService calcolaTotali;

    @Override
    public ProTem getTematica(Integer id) throws MicroServicesException {
        Optional<ProTem> tem = tematicaRepository.findById(id);
        if (tem.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else return tem.get();
    }

    @Override
    public ProTem inserisciTematica(ProTem temDaInserire) throws MicroServicesException {
        this.checkInserisciTematica(temDaInserire);
        utilService.setInfoInsertRow(temDaInserire);
        return tematicaRepository.saveAndFlush(temDaInserire);
    }

    private void checkInserisciTematica(ProTem temDaInserire) throws MicroServicesException {
        if (temDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ID_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.ID_NOT_ALLOWED.getCode()
        );
        if (tematicaRepository.findProTemByCodtemIgnoreCase(temDaInserire.getCodtem()).isPresent()) throw new MicroServicesException(
            ErrorMessages.PROTEM_AlREADY_EXIST.getUserMessage(),
            ErrorMessages.PROTEM_AlREADY_EXIST.getCode()
        );
        if (
            tematicaRepository.findProTemByLivunoAndLivdue(temDaInserire.getLivuno(), temDaInserire.getLivdue()).isPresent()
        ) throw new MicroServicesException(
            ErrorMessages.LIVPROTEM_AlREADY_EXIST.getUserMessage(),
            ErrorMessages.LIVPROTEM_AlREADY_EXIST.getCode()
        );
    }

    @Override
    public ProTem modificaTematica(ProTem temDaModificare) throws MicroServicesException {
        if (temDaModificare.getId() == 0) throw new MicroServicesException(
            ErrorMessages.NO_ID_PROVIDED.getUserMessage(),
            ErrorMessages.NO_ID_PROVIDED.getCode()
        );
        Optional<ProTem> tematicaOriginaleOpt = tematicaRepository.findById(temDaModificare.getId());
        if (tematicaOriginaleOpt.isPresent()) {
            temDaModificare.setUsrCreate(tematicaOriginaleOpt.get().getUsrCreate());
            temDaModificare.setDtCreate(tematicaOriginaleOpt.get().getDtCreate());
        }
        utilService.setInfoUpdateRow(temDaModificare);
        return tematicaRepository.saveAndFlush(temDaModificare);
    }

    @Override
    public void cancellaTematica(Integer id) {
        tematicaRepository.deleteById(id);
    }

    @Override
    public TematicaResponse ricercaTematica(TematicaRicerca temRic) {
        TematicaResponse response = new TematicaResponse();
        if (temRic.checkNull()) {
            checkMaxRecords(tematicaRepository.count(), response);
            Pageable limit = PageRequest.of(0, this.getMaxRecords());
            Page<ProTem> results = tematicaRepository.findAllByOrderByLivunoAscLivdueAsc(limit);
            this.valorizzaResultRicerca(temRic.getCalcolaTotali(), results.getContent(), response);
        } else {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProTem> query = cb.createQuery(ProTem.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
            Root<ProTem> root = query.from(ProTem.class);
            Root<ProTem> rootCount = queryCount.from(ProTem.class);
            List<Predicate> predicates = this.getPredicates(root, cb, temRic);
            List<Predicate> predicatesCount = this.getPredicates(rootCount, cb, temRic);
            queryCount.select(cb.count(rootCount)).where(predicatesCount.toArray(new Predicate[0]));
            query.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(root.get("livuno")), cb.asc(root.get("livdue")));
            Long countResult = em.createQuery(queryCount).getSingleResult();
            checkMaxRecords(countResult, response);
            List<ProTem> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
            this.valorizzaResultRicerca(temRic.getCalcolaTotali(), results, response);
        }
        return response;
    }

    private void valorizzaResultRicerca(boolean calcTotali, List<ProTem> results, TematicaResponse response) {
        for (ProTem proTem : results) {
            ProTemResponse proTemElement = new ProTemResponse();
            proTemElement.setTematica(proTem);
            if (calcTotali) {
                Totali totali = calcolaTotali.getTotaliFondi(EntityType.PROTEM.getValue(), proTem.getId());
                proTemElement.setTotali(totali);
            }
            response.addTematiche(proTemElement);
        }
    }

    private List<Predicate> getPredicates(Root root, CriteriaBuilder cb, TematicaRicerca temRic) {
        List<Predicate> predicates = new ArrayList<>();
        if (temRic.getAutocomplete() != null && !temRic.getAutocomplete().isEmpty()) predicates.add(
            cb.or(
                cb.like(cb.upper(root.get("codtem")), "%" + temRic.getAutocomplete().toUpperCase() + "%"),
                cb.like(cb.upper(root.get("destem")), "%" + temRic.getAutocomplete().toUpperCase() + "%")
            )
        );
        if (temRic.getTipLiv() != null && !temRic.getTipLiv().isEmpty()) {
            if (temRic.getTipLiv().equals("01")) {
                predicates.add(cb.equal(root.get("livdue"), 0));
            } else if (temRic.getTipLiv().equals("02")) {
                predicates.add(cb.and(cb.notEqual(root.get("livdue"), 0)));
            }
        }
        if (temRic.getLiv1() != null && !temRic.getLiv1().equals(0)) {
            predicates.add(cb.equal(root.get("livuno"), temRic.getLiv1()));
        }
        if (temRic.getLiv2() != null && !temRic.getLiv2().equals(0)) {
            predicates.add(cb.equal(root.get("livdue"), temRic.getLiv2()));
        }
        return predicates;
    }

    private void checkMaxRecords(Long countResult, TematicaResponse response) {
        if (countResult > this.getMaxRecords()) {
            response.setWarningMessage(ErrorMessages.QUERY_MAX_ROW_WARNING.getUserMessage());
        }
    }

    private int getMaxRecords() {
        int maxRecords = 0;
        Optional<AmmPar> optParMaxRecords = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCAROWSONLY.getValue());
        if (optParMaxRecords.isPresent()) maxRecords = Integer.parseInt(optParMaxRecords.get().getValore());
        return maxRecords;
    }

    @Override
    public List<ProTem> ricercaTematicaAutocomplete(String temRic) throws MicroServicesException {
        if (temRic != null && !temRic.isBlank()) {
            return tematicaRepository.findAutocomplete(temRic.toUpperCase());
        } else {
            return tematicaRepository.findAllByOrderByDestemAsc();
            /* throw new MicroServicesException(
               ErrorMessages.AUTOCOMPLETE_BLANK.getUserMessage(),
               ErrorMessages.AUTOCOMPLETE_BLANK.getCode());
             */
        }
    }

    @Override
    public List<ProTem> getEredi(ProTem padre) {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProTem> query = cb.createQuery(ProTem.class);
        Root<ProTem> root = query.from(ProTem.class);
        predicates.add(cb.equal(root.get("livuno"), padre.getLivuno()));
        if (padre.getLivdue() != 0) {
            predicates.add(cb.equal(root.get("livdue"), padre.getLivdue()));
        }
        query.select(root).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(query).getResultList();
    }
}
