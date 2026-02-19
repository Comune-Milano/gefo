package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.model.TipoFaseResponse;
import it.dmi.dmifonbe.dmifonpro.repository.FaseRepository;
import it.dmi.dmifonbe.dmifonpro.repository.TipFasRepository;
import it.dmi.dmifonbe.dmifonpro.service.TipoFaseService;
import it.dmi.dmifonbe.model.Parameters;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoFaseServiceImpl implements TipoFaseService {

    @Autowired
    TipFasRepository tipFasRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    EntityManager em;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    FaseRepository faseRepository;

    private Logger log = LoggerFactory.getLogger(TipoFaseServiceImpl.class);

    @Override
    @Transactional
    public ProTipfas getTipoFase(Integer id) throws MicroServicesException {
        Optional<ProTipfas> proTipfas = tipFasRepository.findById(id);
        if (proTipfas.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            log.debug("tipFas: {}", proTipfas.get());
            return proTipfas.get();
        }
    }

    @Override
    public ProTipfas inserisciTipoFase(ProTipfas proTipFasDaInserire) throws MicroServicesException {
        if (proTipFasDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ID_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.ID_NOT_ALLOWED.getCode()
        );

        proTipFasDaInserire.setTipcon("N");
        log.info("Controllo il tipo fase inserito");
        checkTipoFase(proTipFasDaInserire, false, null);
        utilService.setInfoInsertRow(proTipFasDaInserire);
        return tipFasRepository.saveAndFlush(proTipFasDaInserire);
    }

    @Override
    @Transactional
    public ProTipfas modificaTipoFase(ProTipfas proTipFasDaModificare) throws MicroServicesException {
        ProTipfas proTipfas = this.getTipoFase(proTipFasDaModificare.getId());
        proTipFasDaModificare.setTipcon("N");
        checkTipoFase(proTipfas, true, proTipFasDaModificare);

        proTipFasDaModificare.setUsrCreate(proTipfas.getUsrCreate());
        proTipFasDaModificare.setDtCreate(proTipfas.getDtCreate());
        utilService.setInfoUpdateRow(proTipFasDaModificare);
        return tipFasRepository.saveAndFlush(proTipFasDaModificare);
    }

    private void checkTipoFase(ProTipfas proTipfas, boolean edit, ProTipfas proTipFasModifica) throws MicroServicesException {
        log.debug("desfas: {}", proTipfas.getDesfas());
        if (proTipfas.getDesfas().isBlank() || proTipfas.getDesfas() == null) throw new MicroServicesException(
            ErrorMessages.DESC_BLANK.getUserMessage(),
            ErrorMessages.DESC_BLANK.getCode()
        );

        if (
            edit &&
            !proTipFasModifica.getDesfas().equalsIgnoreCase(proTipfas.getDesfas()) &&
            tipFasRepository.existsByDesfasEqualsIgnoreCase(proTipFasModifica.getDesfas())
        ) throw new MicroServicesException(
            ErrorMessages.TIPFAS_ALREADY_EXISTS.getUserMessage(),
            ErrorMessages.TIPFAS_ALREADY_EXISTS.getCode()
        );
    }

    @Override
    @Transactional
    public void cancellaTipoFase(Integer id) throws MicroServicesException {
        log.debug("idTipFaseToDelete: {}", id);
        if (!tipFasRepository.existsById(id)) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
        if (faseRepository.existsByProTipfasByIdTipfasId(id)) throw new MicroServicesException(
            ErrorMessages.TIPFAS_NOT_DELETABLE.getUserMessage(),
            ErrorMessages.TIPFAS_NOT_DELETABLE.getCode()
        );

        tipFasRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TipoFaseResponse ricercaTipoFase(AutocompleteRicerca tipFasRic) {
        TipoFaseResponse response = new TipoFaseResponse();

        log.info("Controllo se ci sono parametri di ricerca");
        if (tipFasRic.getAutocomplete() == null || tipFasRic.getAutocomplete().isBlank()) {
            log.info("Non ci sono parametri di ricerca, procedo coi controlli");
            checkMaxRecords(tipFasRepository.count(), response);
            Sort sort = Sort.by(Sort.Direction.ASC, "ordfas");
            Pageable limit = PageRequest.of(0, this.getMaxRecords(), sort);
            log.info("Ricerco tutti i valori in base a ordfas in ordine ascendente");
            Page<ProTipfas> results = tipFasRepository.findAll(limit);
            this.valorizzaResultRicerca(results.getContent(), response);
            log.info("tipiFase valorizzati");
        } else {
            log.info("sono presenti parametri di ricerca.");
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProTipfas> query = cb.createQuery(ProTipfas.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
            Root<ProTipfas> root = query.from(ProTipfas.class);
            Root<ProTipfas> rootCount = queryCount.from(ProTipfas.class);
            List<Predicate> predicates = this.getPredicates(root, cb, tipFasRic);
            log.debug("parametri di ricerca: autocomplete: {}", tipFasRic.getAutocomplete());
            List<Predicate> predicatesCount = this.getPredicates(rootCount, cb, tipFasRic);
            queryCount.select(cb.count(rootCount)).where(predicatesCount.toArray(new Predicate[0]));
            query.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(root.get("ordfas")));
            Long countResult = em.createQuery(queryCount).getSingleResult();
            checkMaxRecords(countResult, response);
            List<ProTipfas> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
            this.valorizzaResultRicerca(results, response);
        }
        return response;
    }

    private List<Predicate> getPredicates(Root root, CriteriaBuilder cb, AutocompleteRicerca lisValRic) {
        List<Predicate> predicates = new ArrayList<>();
        if (lisValRic.getAutocomplete() != null && !lisValRic.getAutocomplete().isEmpty()) predicates.add(
            cb.or(cb.like(cb.upper(root.get("desfas")), "%" + lisValRic.getAutocomplete().toUpperCase() + "%"))
        );
        return predicates;
    }

    private void checkMaxRecords(Long countResult, TipoFaseResponse response) {
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

    private void valorizzaResultRicerca(List<ProTipfas> results, TipoFaseResponse response) {
        log.info("Valorizzo i risultati nella ricerca");
        response.setProTipfas(results);
    }
}
