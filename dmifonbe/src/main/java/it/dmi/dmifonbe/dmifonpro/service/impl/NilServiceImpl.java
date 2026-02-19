package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProNil;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.MunicipioRepository;
import it.dmi.dmifonbe.dmifonpro.repository.NilRepository;
import it.dmi.dmifonbe.dmifonpro.repository.ProgettoRepository;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.NilService;
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
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NilServiceImpl implements NilService {

    @Autowired
    NilRepository nilRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    EntityManager em;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    CalcolaTotaliService calcolaTotali;

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    ProgettoRepository progettoRepository;

    private Logger log = LoggerFactory.getLogger(NilServiceImpl.class);

    @Override
    public List<ProNil> ricercaNilAutocomplete(String nilRic) throws MicroServicesException {
        if (nilRic != null && !nilRic.isBlank()) {
            return nilRepository.findAutocomplete(nilRic.toUpperCase());
        } else {
            return nilRepository.findAllByOrderByDesnilAsc();
        }
    }

    @Override
    public ProNil inserisciNil(ProNil proNilDaInserire) throws MicroServicesException {
        if (proNilDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ID_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.ID_NOT_ALLOWED.getCode()
        );
        log.info("Controllo il nil inserito");

        checkNil(proNilDaInserire, false, null);
        utilService.setInfoInsertRow(proNilDaInserire);
        return nilRepository.saveAndFlush(proNilDaInserire);
    }

    private void checkNil(ProNil proNil, boolean edit, ProNil proNilModifica) throws MicroServicesException {
        log.debug(
            "idnil:{}, desnil: {}, codnil: {} idMun: {}, ",
            proNil.getId(),
            proNil.getDesnil(),
            proNil.getCodnil(),
            proNil.getIdMun()
        );
        if (proNil.getDesnil().isBlank() || proNil.getDesnil() == null) throw new MicroServicesException(
            ErrorMessages.DESC_BLANK.getUserMessage(),
            ErrorMessages.DESC_BLANK.getCode()
        );
        if (proNil.getCodnil().isBlank() || proNil.getCodnil() == null) throw new MicroServicesException(
            ErrorMessages.COD_NIL_BLANK.getUserMessage(),
            ErrorMessages.COD_NIL_BLANK.getCode()
        );
        if (!edit && nilRepository.existsByCodnilEqualsIgnoreCase(proNil.getCodnil())) throw new MicroServicesException(
            ErrorMessages.COD_NIL_ALREADY_EXISTS.getUserMessage(),
            ErrorMessages.COD_NIL_ALREADY_EXISTS.getCode()
        );
        //nel caso in cui il codice esiste già all'interno del db
        if (
            edit &&
            nilRepository.existsByCodnilEqualsIgnoreCase(proNil.getCodnil()) &&
            !proNilModifica.getCodnil().equalsIgnoreCase(proNil.getCodnil())
        ) throw new MicroServicesException(
            ErrorMessages.COD_NIL_ALREADY_EXISTS.getUserMessage(),
            ErrorMessages.COD_NIL_ALREADY_EXISTS.getCode()
        );
        if (proNil.getIdMun() != null && !municipioRepository.existsById(proNil.getIdMun().intValue())) throw new MicroServicesException(
            ErrorMessages.MUNREF.getUserMessage(),
            ErrorMessages.MUNREF.getCode()
        );
    }

    @Override
    @Transactional
    public ProNil modificaNil(ProNil proNilDaModificare) throws MicroServicesException {
        ProNil proNil = this.getNil(proNilDaModificare.getId());
        log.info("Controllo il nil inserito");
        checkNil(proNilDaModificare, true, proNil);

        proNilDaModificare.setUsrCreate(proNil.getUsrCreate());
        proNilDaModificare.setDtCreate(proNil.getDtCreate());
        utilService.setInfoUpdateRow(proNilDaModificare);
        log.info("Salvo il nil.");
        return nilRepository.saveAndFlush(proNilDaModificare);
    }

    @Override
    @Transactional
    public ProNil getNil(Integer id) throws MicroServicesException {
        Optional<ProNil> proNil = nilRepository.findById(id);
        if (proNil.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            log.debug("nil: {}", proNil.get());
            Hibernate.initialize(proNil.get().getProMunByIdMun());
            return proNil.get();
        }
    }

    @Override
    @Transactional
    public void cancellaNil(Integer id) throws MicroServicesException {
        log.debug("idNilToDelete: {}", id);
        if (nilRepository.existsById(id) && progettoRepository.existsByProNilByIdNilId(id)) {
            throw new MicroServicesException(
                ErrorMessages.MACROPROGETTI_NOT_DELETABLE.getUserMessage(),
                ErrorMessages.MACROPROGETTI_NOT_DELETABLE.getCode()
            );
        }
        nilRepository.deleteById(id);
    }

    @Override
    @Transactional
    public NilResponse ricercaNil(NilRicerca proNilRic) {
        NilResponse response = new NilResponse();
        log.info("Controllo se ci sono parametri di ricerca");
        if (proNilRic.checkNull()) {
            log.info("Non ci sono parametri di ricerca, procedo coi controlli");
            checkMaxRecords(nilRepository.count(), response);
            Pageable limit = PageRequest.of(0, this.getMaxRecords());
            log.info("Ricerco tutti i valori basati sul codicenil in ordine ascendente");
            Page<ProNil> results = nilRepository.findAllByOrderByCodnilAsc(limit);
            this.valorizzaResultRicerca(proNilRic.getCalcolaTotali(), results.getContent(), response);
            log.info("nil valorizzati");
        } else {
            log.info("sono presenti parametri di ricerca.");
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProNil> query = cb.createQuery(ProNil.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
            Root<ProNil> root = query.from(ProNil.class);
            Root<ProNil> rootCount = queryCount.from(ProNil.class);
            List<Predicate> predicates = this.getPredicates(root, cb, proNilRic);
            log.debug(
                "parametri di ricerca: autocomplete: {}, calcolaTotali: {}",
                proNilRic.getAutocomplete(),
                proNilRic.getCalcolaTotali()
            );
            List<Predicate> predicatesCount = this.getPredicates(rootCount, cb, proNilRic);
            queryCount.select(cb.count(rootCount)).where(predicatesCount.toArray(new Predicate[0]));
            query.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(root.get("codnil")));
            Long countResult = em.createQuery(queryCount).getSingleResult();
            checkMaxRecords(countResult, response);
            List<ProNil> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
            this.valorizzaResultRicerca(proNilRic.getCalcolaTotali(), results, response);
        }
        return response;
    }

    private List<Predicate> getPredicates(Root root, CriteriaBuilder cb, NilRicerca proNilRic) {
        List<Predicate> predicates = new ArrayList<>();
        if (proNilRic.getAutocomplete() != null && !proNilRic.getAutocomplete().isEmpty()) predicates.add(
            cb.or(
                cb.like(cb.upper(root.get("codnil")), "%" + proNilRic.getAutocomplete().toUpperCase() + "%"),
                cb.like(cb.upper(root.get("desnil")), "%" + proNilRic.getAutocomplete().toUpperCase() + "%")
            )
        );
        return predicates;
    }

    private void checkMaxRecords(Long countResult, NilResponse response) {
        if (countResult > this.getMaxRecords()) {
            log.debug("countResult  {}", countResult);
            response.setWarningMessage(ErrorMessages.QUERY_MAX_ROW_WARNING.getUserMessage());
        }
    }

    private int getMaxRecords() {
        int maxRecords = 0;
        Optional<AmmPar> optParMaxRecords = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCAROWSONLY.getValue());
        if (optParMaxRecords.isPresent()) maxRecords = Integer.parseInt(optParMaxRecords.get().getValore());
        return maxRecords;
    }

    private void valorizzaResultRicerca(boolean calcTotali, List<ProNil> results, NilResponse response) {
        log.info("Valorizzo i risultati nella ricerca");
        for (ProNil proNilPro : results) {
            log.debug("nil id:{}", proNilPro.getId());
            ProNilResponse proNilProElement = new ProNilResponse();
            proNilProElement.setProNil(proNilPro);
            if (calcTotali) {
                Totali totali = calcolaTotali.getTotaliFondi(EntityType.NIL.getValue(), proNilPro.getId());
                log.debug("totali imrpisfon: {}, nprofon: {}", totali.getImprisfon(), totali.getNroprofon());
                proNilProElement.setTotali(totali);
            }
            response.addNil(proNilProElement);
        }
    }
}
