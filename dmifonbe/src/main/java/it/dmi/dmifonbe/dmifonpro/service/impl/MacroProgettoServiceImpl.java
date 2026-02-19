package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProMacpro;
import it.dmi.dmifonbe.dmifonpro.model.MacProResponse;
import it.dmi.dmifonbe.dmifonpro.model.MacroProgettoResponse;
import it.dmi.dmifonbe.dmifonpro.model.MacroProgettoRicerca;
import it.dmi.dmifonbe.dmifonpro.repository.MacroProgettoRepository;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.MacroProgettoService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class MacroProgettoServiceImpl implements MacroProgettoService {

    @Autowired
    MacroProgettoRepository macroProgettoRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    EntityManager em;

    @Autowired
    CalcolaTotaliService calcolaTotali;

    @Override
    @Transactional
    public ProMacpro getMacroProgetto(Integer id) throws MicroServicesException {
        Optional<ProMacpro> macpro = macroProgettoRepository.findById(id);
        if (macpro.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            Hibernate.initialize(macpro.get().getProTipfinByIdTipfina());
            Hibernate.initialize(macpro.get().getProTipfinByIdTipfinda());
            return macpro.get();
        }
    }

    @Override
    public ProMacpro inserisciMacroProgetto(ProMacpro macProDaInserire) throws MicroServicesException {
        if (
            macProDaInserire.getCodmacpro() == null ||
            macProDaInserire.getCodmacpro().isBlank() ||
            macProDaInserire.getDesmacpro() == null ||
            macProDaInserire.getDesmacpro().isBlank()
        ) throw new MicroServicesException(ErrorMessages.MAC_BLACK_OR_NULL.getUserMessage(), ErrorMessages.MAC_BLACK_OR_NULL.getCode());

        if (macProDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ID_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.ID_NOT_ALLOWED.getCode()
        );
        if (
            macroProgettoRepository
                .findProMacproByCodmacproIgnoreCaseAndIdTipfinda(macProDaInserire.getCodmacpro(), macProDaInserire.getIdTipfinda())
                .isPresent()
        ) throw new MicroServicesException(
            ErrorMessages.COD_IDTIPFINDA_ALREADY_EXIST.getUserMessage(),
            ErrorMessages.COD_IDTIPFINDA_ALREADY_EXIST.getCode()
        );
        utilService.setInfoInsertRow(macProDaInserire);
        return macroProgettoRepository.saveAndFlush(macProDaInserire);
    }

    @Override
    @Transactional
    public ProMacpro modificaMacroProgetto(ProMacpro macProDaModificare) throws MicroServicesException {
        if (macProDaModificare.getId() == 0) throw new MicroServicesException(
            ErrorMessages.NO_ID_PROVIDED.getUserMessage(),
            ErrorMessages.NO_ID_PROVIDED.getCode()
        );
        ProMacpro macpro = this.getMacroProgetto(macProDaModificare.getId());
        if (
            !macpro.getCodmacpro().equals(macProDaModificare.getCodmacpro()) ||
            (macpro.getIdTipfinda() != null && !macpro.getIdTipfinda().equals(macProDaModificare.getIdTipfinda()))
        ) {
            if (
                macroProgettoRepository
                    .findProMacproByCodmacproIgnoreCaseAndIdTipfinda(macProDaModificare.getCodmacpro(), macProDaModificare.getIdTipfinda())
                    .isPresent()
            ) throw new MicroServicesException(
                ErrorMessages.COD_IDTIPFINDA_ALREADY_EXIST.getUserMessage(),
                ErrorMessages.COD_IDTIPFINDA_ALREADY_EXIST.getCode()
            );
        }
        macProDaModificare.setUsrCreate(macpro.getUsrCreate());
        macProDaModificare.setDtCreate(macpro.getDtCreate());
        utilService.setInfoUpdateRow(macProDaModificare);
        return macroProgettoRepository.saveAndFlush(macProDaModificare);
    }

    @Override
    @Transactional
    public void cancellaMacroProgetto(Integer id) throws MicroServicesException {
        ProMacpro macroProgettoToDelete = this.getMacroProgetto(id);
        macroProgettoToDelete.getProProsById().size();
        if (!macroProgettoToDelete.getProProsById().isEmpty()) {
            throw new MicroServicesException(
                ErrorMessages.MACROPROGETTI_NOT_DELETABLE.getUserMessage(),
                ErrorMessages.MACROPROGETTI_NOT_DELETABLE.getCode()
            );
        }
        macroProgettoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MacroProgettoResponse ricercaMacroProgetto(MacroProgettoRicerca macProRic) {
        MacroProgettoResponse response = new MacroProgettoResponse();

        if (macProRic.checkNull()) {
            checkMaxRecords(macroProgettoRepository.count(), response);
            Pageable limit = PageRequest.of(0, this.getMaxRecords());
            Page<ProMacpro> results = macroProgettoRepository.findAllByOrderByCodmacproAsc(limit);
            this.valorizzaResultRicerca(macProRic.getCalcolaTotali(), results.getContent(), response);
        } else {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProMacpro> query = cb.createQuery(ProMacpro.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
            Root<ProMacpro> root = query.from(ProMacpro.class);
            Root<ProMacpro> rootCount = queryCount.from(ProMacpro.class);
            List<Predicate> predicates = this.getPredicates(root, cb, macProRic);
            List<Predicate> predicatesCount = this.getPredicates(rootCount, cb, macProRic);
            queryCount.select(cb.count(rootCount)).where(predicatesCount.toArray(new Predicate[0]));
            query.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(root.get("codmacpro")));
            Long countResult = em.createQuery(queryCount).getSingleResult();
            checkMaxRecords(countResult, response);
            List<ProMacpro> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
            this.valorizzaResultRicerca(macProRic.getCalcolaTotali(), results, response);
        }
        return response;
    }

    @Override
    public List<ProMacpro> ricercaMacroProgettiAutocomplete(String autocomplete) throws MicroServicesException {
        if (autocomplete != null && !autocomplete.isBlank()) {
            return macroProgettoRepository.findAutocomplete(autocomplete.toUpperCase());
        } else {
            return macroProgettoRepository.findAllByOrderByCodmacproAsc();
            /* throw  new MicroServicesException(
            ErrorMessages.AUTOCOMPLETE_BLANK.getUserMessage(),
            ErrorMessages.AUTOCOMPLETE_BLANK.getCode());
            */
        }
    }

    private List<Predicate> getPredicates(Root root, CriteriaBuilder cb, MacroProgettoRicerca macProRic) {
        List<Predicate> predicates = new ArrayList<>();
        if (macProRic.getAutocomplete() != null && !macProRic.getAutocomplete().isEmpty()) predicates.add(
            cb.or(
                cb.like(cb.upper(root.get("codmacpro")), "%" + macProRic.getAutocomplete().toUpperCase() + "%"),
                cb.like(cb.upper(root.get("desmacpro")), "%" + macProRic.getAutocomplete().toUpperCase() + "%")
            )
        );
        if (macProRic.getTipFinDa() != 0) {
            predicates.add(cb.equal(root.get("idTipfinda"), macProRic.getTipFinDa()));
        }
        if (macProRic.getTipFinA() != 0) {
            predicates.add(cb.equal(root.get("idTipfina"), macProRic.getTipFinA()));
        }

        return predicates;
    }

    private void checkMaxRecords(Long countResult, MacroProgettoResponse response) {
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

    private void valorizzaResultRicerca(boolean calcTotali, List<ProMacpro> results, MacroProgettoResponse response) {
        for (ProMacpro proMacpro : results) {
            MacProResponse proMacProElement = new MacProResponse();
            Hibernate.initialize(proMacpro.getProTipfinByIdTipfina());
            Hibernate.initialize(proMacpro.getProTipfinByIdTipfinda());
            proMacProElement.setMacroProgetto(proMacpro);
            if (calcTotali) {
                Totali totali = calcolaTotali.getTotaliFondi(EntityType.MACPRO.getValue(), proMacpro.getId());
                proMacProElement.setTotali(totali);
            }
            response.addMacroProgetto(proMacProElement);
        }
    }
}
