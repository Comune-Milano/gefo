package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipimp;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.model.TipoImportoResponse;
import it.dmi.dmifonbe.dmifonpro.repository.ImportoRepository;
import it.dmi.dmifonbe.dmifonpro.repository.TipoImportoRepository;
import it.dmi.dmifonbe.dmifonpro.service.TipoImportoService;
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
public class TipoImportoServiceImpl implements TipoImportoService {

    @Autowired
    TipoImportoRepository tipoImportoRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    EntityManager em;

    @Autowired
    ImportoRepository importoRepository;

    private Logger log = LoggerFactory.getLogger(TipoImportoServiceImpl.class);

    @Override
    public ProTipimp inserisciTipoImporto(ProTipimp proTipimpDaInserire) throws MicroServicesException {
        if (proTipimpDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ID_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.ID_NOT_ALLOWED.getCode()
        );
        proTipimpDaInserire.setFlgdicui("S");
        proTipimpDaInserire.setFlgtipimp("N");
        log.info("Controllo il tipo importo inserito");
        checkTipoImporto(proTipimpDaInserire, false, null);
        utilService.setInfoInsertRow(proTipimpDaInserire);
        return tipoImportoRepository.saveAndFlush(proTipimpDaInserire);
    }

    private void checkTipoImporto(ProTipimp proTipimp, boolean edit, ProTipimp proTipImpModifica) throws MicroServicesException {
        log.debug("destipimp: {}", proTipimp.getDestipimp());
        if (proTipimp.getDestipimp().isBlank() || proTipimp.getDestipimp() == null) throw new MicroServicesException(
            ErrorMessages.DESC_BLANK.getUserMessage(),
            ErrorMessages.DESC_BLANK.getCode()
        );
        if (edit && proTipimp.getDestipimp().equalsIgnoreCase(proTipImpModifica.getDestipimp())) throw new MicroServicesException(
            ErrorMessages.DESC_EQUALS.getUserMessage(),
            ErrorMessages.DESC_EQUALS.getCode()
        );
    }

    private void checkEliminazioneTipoImporto(Integer idProTipImp) throws MicroServicesException {
        if (importoRepository.existsByProTipimpByIdTipimpId(idProTipImp)) throw new MicroServicesException(
            ErrorMessages.TIPIMP_NOT_DELETABLE.getUserMessage(),
            ErrorMessages.TIPLIS_NOT_EDITABLE.getCode()
        );
    }

    @Override
    @Transactional
    public void cancellaTipoImporto(Integer id) throws MicroServicesException {
        ProTipimp tipImpToDelete = this.getTipoImporto(id);
        log.debug("tipImpToDelete: {}", tipImpToDelete);
        checkEliminazioneTipoImporto(tipImpToDelete.getId());
        tipoImportoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProTipimp getTipoImporto(Integer id) throws MicroServicesException {
        Optional<ProTipimp> proTipimp = tipoImportoRepository.findById(id);
        if (proTipimp.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            log.debug("tipFas: {}", proTipimp.get());
            return proTipimp.get();
        }
    }

    @Override
    @Transactional
    public ProTipimp modificaTipoImporto(ProTipimp proTipImpDaModificare) throws MicroServicesException {
        ProTipimp proTipimp = this.getTipoImporto(proTipImpDaModificare.getId());
        proTipImpDaModificare.setFlgdicui("S");
        proTipImpDaModificare.setFlgtipimp("N");
        checkTipoImporto(proTipImpDaModificare, true, proTipimp);
        proTipImpDaModificare.setUsrCreate(proTipimp.getUsrCreate());
        proTipImpDaModificare.setDtCreate(proTipimp.getDtCreate());
        utilService.setInfoUpdateRow(proTipImpDaModificare);
        return tipoImportoRepository.saveAndFlush(proTipImpDaModificare);
    }

    @Override
    @Transactional
    public TipoImportoResponse ricercaTipoImporto(AutocompleteRicerca tipImpRic) {
        TipoImportoResponse response = new TipoImportoResponse();

        log.info("Controllo se ci sono parametri di ricerca");
        if (tipImpRic.getAutocomplete() == null || tipImpRic.getAutocomplete().isBlank()) {
            log.info("Non ci sono parametri di ricerca, procedo coi controlli");
            checkMaxRecords(tipoImportoRepository.count(), response);
            Sort sort = Sort.by(Sort.Direction.ASC, "ordtipimp");
            Pageable limit = PageRequest.of(0, this.getMaxRecords(), sort);
            log.info("Ricerco tutti i valori in base a ordtipimp in ordine ascendente");
            Page<ProTipimp> results = tipoImportoRepository.findAllFlgdicuiEqualSPaginato(limit);
            this.valorizzaResultRicerca(results.getContent(), response);
            log.info("tipiImporto valorizzati");
        } else {
            log.info("sono presenti parametri di ricerca.");
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProTipimp> query = cb.createQuery(ProTipimp.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
            Root<ProTipimp> root = query.from(ProTipimp.class);
            Root<ProTipimp> rootCount = queryCount.from(ProTipimp.class);
            List<Predicate> predicates = this.getPredicates(root, cb, tipImpRic);
            log.debug("parametri di ricerca: autocomplete: {}", tipImpRic.getAutocomplete());
            List<Predicate> predicatesCount = this.getPredicates(rootCount, cb, tipImpRic);
            queryCount.select(cb.count(rootCount)).where(predicatesCount.toArray(new Predicate[0]));
            query.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(root.get("ordtipimp")));
            Long countResult = em.createQuery(queryCount).getSingleResult();
            checkMaxRecords(countResult, response);
            List<ProTipimp> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
            this.valorizzaResultRicerca(results, response);
        }
        return response;
    }

    private List<Predicate> getPredicates(Root root, CriteriaBuilder cb, AutocompleteRicerca tipImpRic) {
        List<Predicate> predicates = new ArrayList<>();
        if (tipImpRic.getAutocomplete() != null && !tipImpRic.getAutocomplete().isEmpty()) predicates.add(
            cb.and(
                cb.like(cb.upper(root.get("destipimp")), "%" + tipImpRic.getAutocomplete().toUpperCase() + "%"),
                cb.equal(root.get("flgdicui"), "S")
            )
        );
        return predicates;
    }

    private void checkMaxRecords(Long countResult, TipoImportoResponse response) {
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

    private void valorizzaResultRicerca(List<ProTipimp> results, TipoImportoResponse response) {
        log.info("Valorizzo i risultati nella ricerca");
        response.setProTipimp(results);
    }
}
