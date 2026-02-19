package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProMun;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.MunicipioRepository;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.MunicipioService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MunicipioServiceImpl implements MunicipioService {

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    EntityManager em;

    @Autowired
    CalcolaTotaliService calcolaTotali;

    private Logger log = LoggerFactory.getLogger(MunicipioServiceImpl.class);

    @Override
    @Transactional
    public List<ProMun> getAllMunicipi() {
        return municipioRepository.findAll();
    }

    @Override
    public ProMun inserisciMunicipio(ProMun proMunDaInserire) throws MicroServicesException {
        if (proMunDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ID_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.ID_NOT_ALLOWED.getCode()
        );
        checkMunicipio(proMunDaInserire, false, null);
        utilService.setInfoInsertRow(proMunDaInserire);
        return municipioRepository.saveAndFlush(proMunDaInserire);
    }

    private void checkMunicipio(ProMun proMunDaInserire, boolean edit, ProMun proMun) throws MicroServicesException {
        if (proMunDaInserire.getDesmun().isBlank() || proMunDaInserire.getDesmun() == null) throw new MicroServicesException(
            ErrorMessages.MUN_DESC_BLANK.getUserMessage(),
            ErrorMessages.MUN_DESC_BLANK.getCode()
        );
        if (!edit && municipioRepository.existsByDesmunEqualsIgnoreCase(proMunDaInserire.getDesmun())) throw new MicroServicesException(
            ErrorMessages.MUN_ALREADY_EXISTS.getUserMessage(),
            ErrorMessages.MUN_ALREADY_EXISTS.getCode()
        );
        if (
            edit &&
            !proMun.getDesmun().equalsIgnoreCase(proMunDaInserire.getDesmun()) &&
            municipioRepository.existsByDesmunEqualsIgnoreCase(proMunDaInserire.getDesmun())
        ) throw new MicroServicesException(ErrorMessages.MUN_ALREADY_EXISTS.getUserMessage(), ErrorMessages.MUN_ALREADY_EXISTS.getCode());
    }

    @Override
    @Transactional
    public ProMun modificaMunicipio(ProMun proMunDaModificare) throws MicroServicesException {
        log.info("modifico il municipio");
        ProMun proMun = this.getMunicipio(proMunDaModificare.getId());
        checkMunicipio(proMunDaModificare, true, proMun);

        proMunDaModificare.setUsrCreate(proMun.getUsrCreate());
        proMunDaModificare.setDtCreate(proMun.getDtCreate());
        utilService.setInfoUpdateRow(proMunDaModificare);
        return municipioRepository.saveAndFlush(proMunDaModificare);
    }

    @Override
    @Transactional
    public ProMun getMunicipio(Integer id) throws MicroServicesException {
        Optional<ProMun> proMun = municipioRepository.findById(id);
        if (proMun.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            log.debug("municipio: {}", proMun.get());
            return proMun.get();
        }
    }

    @Override
    @Transactional
    public void cancellaMunicipio(Integer id) throws MicroServicesException {
        ProMun municipioToDelete = this.getMunicipio(id);
        log.debug("municipioToDelete: {}", municipioToDelete);
        municipioToDelete.getProProsById().size();
        if (!municipioToDelete.getProProsById().isEmpty()) {
            throw new MicroServicesException(
                ErrorMessages.MUNICIPIO_NOT_DELETABLE.getUserMessage(),
                ErrorMessages.MUNICIPIO_NOT_DELETABLE.getCode()
            );
        }
        municipioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MunicipioResponse ricercaMunicipio(MunicipioRicerca munProRic) {
        MunicipioResponse response = new MunicipioResponse();
        log.info("Controllo se ci sono parametri di ricerca");
        if (munProRic.checkNull()) {
            log.info("Non ci sono parametri di ricerca, procedo coi controlli");
            checkMaxRecords(municipioRepository.count(), response);
            Pageable limit = PageRequest.of(0, this.getMaxRecords());
            log.info("Ricerco tutti i valori basati sulla descrizione in ordine ascendente");
            Page<ProMun> results = municipioRepository.findAllByOrderByDesmunAsc(limit);
            this.valorizzaResultRicerca(munProRic.getCalcolaTotali(), results.getContent(), response);
            log.info("municipi valorizzati");
        } else {
            log.info("sono presenti parametri di ricerca.");
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProMun> query = cb.createQuery(ProMun.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
            Root<ProMun> root = query.from(ProMun.class);
            Root<ProMun> rootCount = queryCount.from(ProMun.class);
            List<Predicate> predicates = this.getPredicates(root, cb, munProRic);
            log.debug(
                "parametri di ricerca: autocomplete: {}, calcolaTotali: {}",
                munProRic.getAutocomplete(),
                munProRic.getCalcolaTotali()
            );
            List<Predicate> predicatesCount = this.getPredicates(rootCount, cb, munProRic);
            queryCount.select(cb.count(rootCount)).where(predicatesCount.toArray(new Predicate[0]));
            query.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(root.get("desmun")));
            Long countResult = em.createQuery(queryCount).getSingleResult();
            checkMaxRecords(countResult, response);
            List<ProMun> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
            this.valorizzaResultRicerca(munProRic.getCalcolaTotali(), results, response);
        }
        return response;
    }

    @Override
    public List<ProMun> ricercaMunicipiAutocomplete(String autocomplete) throws MicroServicesException {
        if (autocomplete != null && !autocomplete.isBlank()) {
            log.debug("c'è l'autocomplete: {} ", autocomplete);
            return municipioRepository.findAutocomplete(autocomplete.toUpperCase());
        } else {
            log.info("Non è presente l'auto complete. Ricerco tutto ordinando per descrizione");
            return municipioRepository.findAllByOrderByDesmunAsc();
            /* throw  new MicroServicesException(
            ErrorMessages.AUTOCOMPLETE_BLANK.getUserMessage(),
            ErrorMessages.AUTOCOMPLETE_BLANK.getCode());
            */
        }
    }

    private List<Predicate> getPredicates(Root root, CriteriaBuilder cb, MunicipioRicerca munProRic) {
        List<Predicate> predicates = new ArrayList<>();
        if (munProRic.getAutocomplete() != null && !munProRic.getAutocomplete().isEmpty()) predicates.add(
            cb.or(cb.like(cb.upper(root.get("desmun")), "%" + munProRic.getAutocomplete().toUpperCase() + "%"))
        );
        return predicates;
    }

    private void checkMaxRecords(Long countResult, MunicipioResponse response) {
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

    private void valorizzaResultRicerca(boolean calcTotali, List<ProMun> results, MunicipioResponse response) {
        log.info("Valorizzo i risultati nella ricerca");
        for (ProMun proMunpro : results) {
            log.debug("municipio id:{}", proMunpro.getId());
            MunProResponse proMunProElement = new MunProResponse();
            proMunProElement.setProMun(proMunpro);
            if (calcTotali) {
                log.info("calcola totali è a true, svolgo get totali fondi");
                Totali totali = calcolaTotali.getTotaliFondi(EntityType.MUN.getValue(), proMunpro.getId());
                log.debug("totali imrpisfon: {}, nprofon: {}", totali.getImprisfon(), totali.getNroprofon());
                proMunProElement.setTotali(totali);
            }
            response.addMunicipio(proMunProElement);
        }
    }
}
