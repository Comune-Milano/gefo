package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProLisval;
import it.dmi.dmifonbe.dmifonpro.model.AutocompleteRicerca;
import it.dmi.dmifonbe.dmifonpro.model.ListaValoriResponse;
import it.dmi.dmifonbe.dmifonpro.repository.AvanzamentoRepository;
import it.dmi.dmifonbe.dmifonpro.repository.ProLisValRepository;
import it.dmi.dmifonbe.dmifonpro.service.LisvalService;
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
public class LisvalServiceImpl implements LisvalService {

    @Autowired
    ProLisValRepository proLisValRepository;

    @Autowired
    EntityManager em;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    AvanzamentoRepository avanzamentoRepository;

    @Autowired
    UtilService utilService;

    private Logger log = LoggerFactory.getLogger(LisvalServiceImpl.class);

    @Override
    public List<ProLisval> getListaValori(String tipologia) {
        switch (tipologia) {
            case "AVAFASINT":
                return proLisValRepository.findByTiplis(Parameters.AVAFASINT.getValue());
            case "AVACRI":
                return proLisValRepository.findByTiplis(Parameters.AVACRI.getValue());
            case "AVATIPAPP":
                return proLisValRepository.findByTiplis(Parameters.AVATIPAPP.getValue());
            case "AVASTAANT":
                return proLisValRepository.findByTiplis(Parameters.AVASTAANT.getValue());
            case "TIPOLFAS":
                return proLisValRepository.findByTiplis(Parameters.TIPOLFAS.getValue());
            default:
                return new ArrayList<>();
        }
    }

    //Mi è stato chiesto di fare result.add sulle altre;
    //Leggi i commits per capire cosa ho cambiato.
    //Mi è stato chiesto inoltre di cambiare la chiamata.
    @Override
    public List<ProLisval> getListaValoriTipoLista() {
        List<ProLisval> result = new ArrayList<>();
        ProLisval proLisval;
        proLisval = new ProLisval();
        proLisval.setTiplis(Parameters.AVAFASINT.getValue());
        proLisval.setVallis("Fase intervento");
        result.add(proLisval);
        proLisval = new ProLisval();
        proLisval.setTiplis(Parameters.AVACRI.getValue());
        proLisval.setVallis("Livello criticità");
        result.add(proLisval);
        proLisval = new ProLisval();
        proLisval.setTiplis(Parameters.AVATIPAPP.getValue());
        proLisval.setVallis("Tipo appalto");
        result.add(proLisval);
        proLisval = new ProLisval();
        proLisval.setTiplis(Parameters.AVASTAANT.getValue());
        proLisval.setVallis("Stato anticipazione");
        result.add(proLisval);
        proLisval = new ProLisval();
        proLisval.setTiplis(Parameters.TIPOLFAS.getValue());
        proLisval.setVallis("Tipologia di fase");
        result.add(proLisval);
        return result;
    }

    @Override
    public ProLisval inserisciListaValore(ProLisval proLisValDaInserire) throws MicroServicesException {
        if (proLisValDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ID_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.ID_NOT_ALLOWED.getCode()
        );
        checkListaValore(proLisValDaInserire, false, null);
        utilService.setInfoInsertRow(proLisValDaInserire);
        log.info("Salvo la lista valore");
        return proLisValRepository.saveAndFlush(proLisValDaInserire);
    }

    private void checkListaValore(ProLisval proLisval, boolean edit, ProLisval proLisvalModifica) throws MicroServicesException {
        log.debug("TipLis: {}, Vallis: {}", proLisval.getTiplis(), proLisval.getVallis());
        if (proLisval.getVallis().isBlank() || proLisval.getVallis() == null) throw new MicroServicesException(
            ErrorMessages.DESC_BLANK.getUserMessage(),
            ErrorMessages.DESC_BLANK.getCode()
        );
        if (proLisval.getTiplis().isBlank() || proLisval.getTiplis() == null) throw new MicroServicesException(
            ErrorMessages.DESC_BLANK.getUserMessage(),
            ErrorMessages.DESC_BLANK.getCode()
        );
        if (!edit && !checkEnum(proLisval.getTiplis())) throw new MicroServicesException(
            ErrorMessages.INVALID_TIPLIS.getUserMessage(),
            ErrorMessages.INVALID_TIPLIS.getCode()
        );
        if (edit && !proLisval.getTiplis().equals(proLisvalModifica.getTiplis())) throw new MicroServicesException(
            ErrorMessages.TIPLIS_NOT_EDITABLE.getUserMessage(),
            ErrorMessages.TIPLIS_NOT_EDITABLE.getCode()
        );
    }

    private void checkEliminazioneListaValore(ProLisval proLisval) throws MicroServicesException {
        log.debug("TipLis: {}, ListaValore id: {}", proLisval.getTiplis(), proLisval.getId());
        switch (proLisval.getTiplis()) {
            case "AVAFASINT":
                if (avanzamentoRepository.existsByProLisvalByIdLisvalfasintId(proLisval.getId())) throw new MicroServicesException(
                    ErrorMessages.TIPLIS_NOT_DELETABLE.getUserMessage(),
                    ErrorMessages.TIPLIS_NOT_DELETABLE.getCode()
                );
                break;
            case "AVACRI":
                if (avanzamentoRepository.existsByProLisvalByIdLisvallivcriId(proLisval.getId())) throw new MicroServicesException(
                    ErrorMessages.TIPLIS_NOT_DELETABLE.getUserMessage(),
                    ErrorMessages.TIPLIS_NOT_DELETABLE.getCode()
                );
                break;
            case "AVASTAANT":
                if (avanzamentoRepository.existsByProLisvalByIdLisvalstaantId(proLisval.getId())) throw new MicroServicesException(
                    ErrorMessages.TIPLIS_NOT_DELETABLE.getUserMessage(),
                    ErrorMessages.TIPLIS_NOT_DELETABLE.getCode()
                );
                break;
            case "AVATIPAPP":
                if (avanzamentoRepository.existsByProLisvalByIdLisvaltipappId(proLisval.getId())) throw new MicroServicesException(
                    ErrorMessages.TIPLIS_NOT_DELETABLE.getUserMessage(),
                    ErrorMessages.TIPLIS_NOT_DELETABLE.getCode()
                );
                break;
            case "TIPOLFAS":
                if (avanzamentoRepository.existsByProLisvalByIdLisvaltipolfasId(proLisval.getId())) throw new MicroServicesException(
                    ErrorMessages.TIPLIS_NOT_DELETABLE.getUserMessage(),
                    ErrorMessages.TIPLIS_NOT_DELETABLE.getCode()
                );
                break;
        }
    }

    private boolean checkEnum(String tipLis) {
        return (
            tipLis.equals(Parameters.AVAFASINT.getValue()) ||
            tipLis.equals(Parameters.AVACRI.getValue()) ||
            tipLis.equals(Parameters.AVASTAANT.getValue()) ||
            tipLis.equals(Parameters.AVATIPAPP.getValue()) ||
            tipLis.equals(Parameters.TIPOLFAS.getValue())
        );
    }

    @Override
    @Transactional
    public ProLisval modificaListaValore(ProLisval proLisValDaModificare) throws MicroServicesException {
        ProLisval proLisval = this.getListaValore(proLisValDaModificare.getId());
        checkListaValore(proLisValDaModificare, true, proLisval);

        proLisValDaModificare.setUsrCreate(proLisval.getUsrCreate());
        proLisValDaModificare.setDtCreate(proLisval.getDtCreate());
        utilService.setInfoUpdateRow(proLisValDaModificare);
        return proLisValRepository.saveAndFlush(proLisValDaModificare);
    }

    @Override
    @Transactional
    public void cancellaListaValore(Integer id) throws MicroServicesException {
        ProLisval lisvalToDelete = this.getListaValore(id);
        checkEliminazioneListaValore(lisvalToDelete);
        proLisValRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProLisval getListaValore(Integer id) throws MicroServicesException {
        Optional<ProLisval> proLisval = proLisValRepository.findById(id);
        if (proLisval.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            log.debug("proLisval: {}", proLisval.get());
            return proLisval.get();
        }
    }

    @Override
    @Transactional
    public ListaValoriResponse ricercaListaValori(AutocompleteRicerca lisValRic) {
        ListaValoriResponse response = new ListaValoriResponse();
        log.info("Controllo se ci sono parametri di ricerca");
        if (lisValRic.getAutocomplete() == null || lisValRic.getAutocomplete().isBlank()) {
            log.info("Non ci sono parametri di ricerca, procedo coi controlli");
            checkMaxRecords(proLisValRepository.count(), response);
            Sort sort = Sort.by(Sort.Direction.ASC, "vallis", "tiplis");

            Pageable limit = PageRequest.of(0, this.getMaxRecords(), sort);
            log.info("Ricerco tutti i valori");
            Page<ProLisval> results = proLisValRepository.findAll(limit);
            this.valorizzaResultRicerca(results.getContent(), response);
            log.info("lista valore valorizzata");
        } else {
            log.info("sono presenti parametri di ricerca.");
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProLisval> query = cb.createQuery(ProLisval.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
            Root<ProLisval> root = query.from(ProLisval.class);
            Root<ProLisval> rootCount = queryCount.from(ProLisval.class);
            List<Predicate> predicates = this.getPredicates(root, cb, lisValRic);
            log.debug("parametri di ricerca: autocomplete: {}", lisValRic.getAutocomplete());
            List<Predicate> predicatesCount = this.getPredicates(rootCount, cb, lisValRic);
            queryCount.select(cb.count(rootCount)).where(predicatesCount.toArray(new Predicate[0]));
            query
                .select(root)
                .where(predicates.toArray(new Predicate[0]))
                .orderBy(cb.asc(root.get("vallis")))
                .orderBy(cb.asc(root.get("tiplis")));
            Long countResult = em.createQuery(queryCount).getSingleResult();
            checkMaxRecords(countResult, response);
            List<ProLisval> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
            this.valorizzaResultRicerca(results, response);
        }
        return response;
    }

    private List<Predicate> getPredicates(Root root, CriteriaBuilder cb, AutocompleteRicerca lisValRic) {
        List<Predicate> predicates = new ArrayList<>();
        if (lisValRic.getAutocomplete() != null && !lisValRic.getAutocomplete().isEmpty()) predicates.add(
            cb.or(cb.like(cb.upper(root.get("vallis")), "%" + lisValRic.getAutocomplete().toUpperCase() + "%"))
        );
        return predicates;
    }

    private void checkMaxRecords(Long countResult, ListaValoriResponse response) {
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

    private void valorizzaResultRicerca(List<ProLisval> results, ListaValoriResponse response) {
        log.info("Valorizzo i risultati nella ricerca");
        response.setProLisval(results);
    }
}
