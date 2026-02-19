package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipinfagg;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.InfoAggiuntiveRepository;
import it.dmi.dmifonbe.dmifonpro.repository.TipoInformazioneRepository;
import it.dmi.dmifonbe.dmifonpro.service.TipoInformazioneService;
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
public class TipoInformazioneServiceImpl implements TipoInformazioneService {

    @Autowired
    TipoInformazioneRepository tipoInformazioneRepository;

    @Autowired
    InfoAggiuntiveRepository infoAggiuntiveRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    EntityManager em;

    private Logger log = LoggerFactory.getLogger(TipoInformazioneServiceImpl.class);

    @Override
    public ProTipinfagg inserisciTipoInformazione(ProTipinfagg proTipinfaggDaInserire) throws MicroServicesException {
        if (proTipinfaggDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ID_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.ID_NOT_ALLOWED.getCode()
        );
        log.info("Controllo il tipo informazione aggiuntiva inserito");
        checkTipoInformazione(proTipinfaggDaInserire, false, null);
        utilService.setInfoInsertRow(proTipinfaggDaInserire);
        return tipoInformazioneRepository.saveAndFlush(proTipinfaggDaInserire);
    }

    private void checkTipoInformazione(ProTipinfagg proTipimp, boolean edit, ProTipinfagg proTipImpModifica) throws MicroServicesException {
        log.debug("destipinfagg: {}", proTipimp.getDestipinfagg());
        if (proTipimp.getDestipinfagg().isBlank() || proTipimp.getDestipinfagg() == null) throw new MicroServicesException(
            ErrorMessages.DESC_BLANK.getUserMessage(),
            ErrorMessages.DESC_BLANK.getCode()
        );
        if (edit && proTipimp.getDestipinfagg().equalsIgnoreCase(proTipImpModifica.getDestipinfagg())) throw new MicroServicesException(
            ErrorMessages.DESC_EQUALS.getUserMessage(),
            ErrorMessages.DESC_EQUALS.getCode()
        );
    }

    private void checkEliminazioneTipoImporto(Integer idProTipImp) throws MicroServicesException {
        if (infoAggiuntiveRepository.existsByProTipinfaggByIdTipinfaggId(idProTipImp)) throw new MicroServicesException(
            ErrorMessages.TIPIMP_NOT_DELETABLE.getUserMessage(),
            ErrorMessages.TIPLIS_NOT_EDITABLE.getCode()
        );
    }

    @Override
    @Transactional
    public void cancellaTipoInformazione(Integer id) throws MicroServicesException {
        ProTipinfagg tipInToDelete = this.getTipoInformazione(id);
        checkEliminazioneTipoImporto(tipInToDelete.getId());
        log.debug("tipInToDelete: {}", tipInToDelete);
        tipoInformazioneRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProTipinfagg modificaTipoInformazione(ProTipinfagg proTipinfaggDaModificare) throws MicroServicesException {
        ProTipinfagg proTipinfagg = this.getTipoInformazione(proTipinfaggDaModificare.getId());
        checkTipoInformazione(proTipinfaggDaModificare, true, proTipinfagg);
        proTipinfaggDaModificare.setUsrCreate(proTipinfagg.getUsrCreate());
        proTipinfaggDaModificare.setDtCreate(proTipinfagg.getDtCreate());
        utilService.setInfoUpdateRow(proTipinfaggDaModificare);
        return tipoInformazioneRepository.saveAndFlush(proTipinfaggDaModificare);
    }

    @Override
    @Transactional
    public ProTipinfagg getTipoInformazione(Integer id) throws MicroServicesException {
        Optional<ProTipinfagg> proTipinfagg = tipoInformazioneRepository.findById(id);
        if (proTipinfagg.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            log.debug("tipInfAgg: {}", proTipinfagg.get());
            return proTipinfagg.get();
        }
    }

    @Override
    @Transactional
    public TipiInformazioneResponse ricercaTipoInformazione(AutocompleteRicerca tipInRic) {
        TipiInformazioneResponse response = new TipiInformazioneResponse();

        log.info("Controllo se ci sono parametri di ricerca");
        if (tipInRic.getAutocomplete() == null || tipInRic.getAutocomplete().isBlank()) {
            log.info("Non ci sono parametri di ricerca, procedo coi controlli");
            checkMaxRecords(tipoInformazioneRepository.count(), response);
            Sort sort = Sort.by(Sort.Direction.ASC, "ordtipinfagg");
            Pageable limit = PageRequest.of(0, this.getMaxRecords(), sort);
            log.info("Ricerco tutti i valori in base a ordtipinfagg in ordine ascendente");
            Page<ProTipinfagg> results = tipoInformazioneRepository.findAllByOrderByOrdtipinfagg(limit);
            this.valorizzaResultRicerca(results.getContent(), response);
            log.info("tipiInformazioneAggiuntiva valorizzati");
        } else {
            log.info("sono presenti parametri di ricerca.");
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProTipinfagg> query = cb.createQuery(ProTipinfagg.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
            Root<ProTipinfagg> root = query.from(ProTipinfagg.class);
            Root<ProTipinfagg> rootCount = queryCount.from(ProTipinfagg.class);
            List<Predicate> predicates = this.getPredicates(root, cb, tipInRic);
            log.debug("parametri di ricerca: autocomplete: {}", tipInRic.getAutocomplete());
            List<Predicate> predicatesCount = this.getPredicates(rootCount, cb, tipInRic);
            queryCount.select(cb.count(rootCount)).where(predicatesCount.toArray(new Predicate[0]));
            query.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(root.get("ordtipinfagg")));
            Long countResult = em.createQuery(queryCount).getSingleResult();
            checkMaxRecords(countResult, response);
            List<ProTipinfagg> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
            this.valorizzaResultRicerca(results, response);
        }
        return response;
    }

    private List<Predicate> getPredicates(Root root, CriteriaBuilder cb, AutocompleteRicerca tipInRic) {
        List<Predicate> predicates = new ArrayList<>();
        if (tipInRic.getAutocomplete() != null && !tipInRic.getAutocomplete().isEmpty()) predicates.add(
            cb.like(cb.upper(root.get("destipinfagg")), "%" + tipInRic.getAutocomplete().toUpperCase() + "%")
        );
        return predicates;
    }

    private void checkMaxRecords(Long countResult, TipiInformazioneResponse response) {
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

    private void valorizzaResultRicerca(List<ProTipinfagg> results, TipiInformazioneResponse response) {
        log.info("Valorizzo i risultati nella ricerca");
        response.setProTipInFagg(results);
    }
}
