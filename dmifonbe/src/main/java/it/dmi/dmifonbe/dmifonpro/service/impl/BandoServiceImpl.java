package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProBan;
import it.dmi.dmifonbe.dmifonpro.entities.ProMacpro;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfin;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.BandoRepository;
import it.dmi.dmifonbe.dmifonpro.repository.TipoFinanziamentoRepository;
import it.dmi.dmifonbe.dmifonpro.service.BandoService;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.TipoFinanziamentoService;
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
import org.checkerframework.checker.units.qual.A;
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
public class BandoServiceImpl implements BandoService {

    @Autowired
    BandoRepository bandoRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    EntityManager em;

    @Autowired
    CalcolaTotaliService calcolaTotali;

    @Autowired
    TipoFinanziamentoRepository tipoFinanziamentoRepository;

    @Autowired
    TipoFinanziamentoService tipoFinanziamentoService;

    @Override
    @Transactional
    public ProBan getBando(Integer id) throws MicroServicesException {
        Optional<ProBan> ban = bandoRepository.findById(id);
        if (ban.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            Hibernate.initialize(ban.get().getProStabanByIdStaban());
            Hibernate.initialize(ban.get().getProTemByIdTem());
            Hibernate.initialize(ban.get().getProTipfinByIdTipfin());
            return ban.get();
        }
    }

    @Override
    public ProBan inserisciBando(ProBan banDaInserire) throws MicroServicesException {
        if (banDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ID_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.ID_NOT_ALLOWED.getCode()
        );
        if (bandoRepository.findProBanByCodbanIgnoreCase(banDaInserire.getCodban()).isPresent()) throw new MicroServicesException(
            ErrorMessages.COD_IDTIPFINDA_ALREADY_EXIST.getUserMessage(),
            ErrorMessages.COD_IDTIPFINDA_ALREADY_EXIST.getCode()
        );
        utilService.setInfoInsertRow(banDaInserire);
        return bandoRepository.saveAndFlush(banDaInserire);
    }

    @Override
    @Transactional
    public ProBan modificaBando(ProBan banDaModificare) throws MicroServicesException {
        if (banDaModificare.getId() == 0) throw new MicroServicesException(
            ErrorMessages.NO_ID_PROVIDED.getUserMessage(),
            ErrorMessages.NO_ID_PROVIDED.getCode()
        );
        ProBan ban = this.getBando(banDaModificare.getId());
        if (!ban.getCodban().equals(banDaModificare.getCodban())) {
            if (bandoRepository.findProBanByCodbanIgnoreCase(banDaModificare.getCodban()).isPresent()) throw new MicroServicesException(
                ErrorMessages.COD_IDTIPFINDA_ALREADY_EXIST.getUserMessage(),
                ErrorMessages.COD_IDTIPFINDA_ALREADY_EXIST.getCode()
            );
        }
        banDaModificare.setUsrCreate(ban.getUsrCreate());
        banDaModificare.setDtCreate(ban.getDtCreate());
        utilService.setInfoUpdateRow(banDaModificare);
        return bandoRepository.saveAndFlush(banDaModificare);
    }

    @Override
    @Transactional
    public void cancellaBando(Integer id) throws MicroServicesException {
        ProBan bandoToDelete = this.getBando(id);
        bandoToDelete.getProProsById().size();
        if (!bandoToDelete.getProProsById().isEmpty()) {
            throw new MicroServicesException(
                ErrorMessages.BANDO_NOT_DELETABLE.getUserMessage(),
                ErrorMessages.BANDO_NOT_DELETABLE.getCode()
            );
        }
        bandoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BandoResponse ricercaBando(BandoRicerca banRic) throws MicroServicesException {
        BandoResponse response = new BandoResponse();

        if (banRic.checkNull()) {
            checkMaxRecords(bandoRepository.count(), response);
            Pageable limit = PageRequest.of(0, this.getMaxRecords());
            Page<ProBan> results = bandoRepository.findAllByOrderByCodbanAsc(limit);
            this.valorizzaResultRicerca(banRic.getCalcolaTotali(), results.getContent(), response);
        } else {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProBan> query = cb.createQuery(ProBan.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
            Root<ProBan> root = query.from(ProBan.class);
            Root<ProBan> rootCount = queryCount.from(ProBan.class);
            List<Predicate> predicates = this.getPredicates(root, cb, banRic);
            List<Predicate> predicatesCount = this.getPredicates(rootCount, cb, banRic);
            queryCount.select(cb.count(rootCount)).where(predicatesCount.toArray(new Predicate[0]));
            query.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(root.get("codban")));
            Long countResult = em.createQuery(queryCount).getSingleResult();
            checkMaxRecords(countResult, response);
            List<ProBan> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
            this.valorizzaResultRicerca(banRic.getCalcolaTotali(), results, response);
        }
        return response;
    }

    @Override
    public List<ProBan> ricercaBandoAutocomplete(String autocomplete) throws MicroServicesException {
        if (autocomplete != null && !autocomplete.isBlank()) {
            return bandoRepository.findAutocomplete(autocomplete.toUpperCase());
        } else throw new MicroServicesException(
            ErrorMessages.AUTOCOMPLETE_BLANK.getUserMessage(),
            ErrorMessages.AUTOCOMPLETE_BLANK.getCode()
        );
    }

    private List<Predicate> getPredicates(Root root, CriteriaBuilder cb, BandoRicerca banRic) throws MicroServicesException {
        List<Predicate> predicates = new ArrayList<>();
        if (banRic.getAutocomplete() != null && !banRic.getAutocomplete().isEmpty()) predicates.add(
            cb.or(
                cb.like(cb.upper(root.get("codban")), "%" + banRic.getAutocomplete().toUpperCase() + "%"),
                cb.like(cb.upper(root.get("desban")), "%" + banRic.getAutocomplete().toUpperCase() + "%")
            )
        );
        if (banRic.getTipFin() != 0) {
            Optional<ProTipfin> tipfinOpt = tipoFinanziamentoRepository.findById(banRic.getTipFin());
            if (tipfinOpt.isPresent()) {
                ProTipfin tipfinSearch = tipfinOpt.get();
                List<ProTipfin> erediList = tipoFinanziamentoService.getEredi(tipfinSearch);
                List<Long> idErediList = new ArrayList<>();
                for (ProTipfin tipfinElement : erediList) {
                    idErediList.add(Long.valueOf(tipfinElement.getId()));
                }
                predicates.add(root.get("idTipfin").in(idErediList));
            } else throw new MicroServicesException(ErrorMessages.TIPFINREF.getUserMessage(), ErrorMessages.TIPPROREF.getCode());
        }
        if (banRic.getStaBan() != 0) {
            predicates.add(cb.equal(root.get("idStaban"), banRic.getStaBan()));
        }
        if (banRic.getTem() != 0) {
            predicates.add(cb.equal(root.get("idTem"), banRic.getTem()));
        }

        return predicates;
    }

    private void checkMaxRecords(Long countResult, BandoResponse response) {
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

    private void valorizzaResultRicerca(boolean calcTotali, List<ProBan> results, BandoResponse response) {
        for (ProBan proBan : results) {
            BanResponse proBanElement = new BanResponse();
            Hibernate.initialize(proBan.getProStabanByIdStaban());
            Hibernate.initialize(proBan.getProTemByIdTem());
            Hibernate.initialize(proBan.getProTipfinByIdTipfin());
            proBanElement.setBando(proBan);
            if (calcTotali) {
                Totali totali = calcolaTotali.getTotaliFondi(EntityType.BAN.getValue(), proBan.getId());
                proBanElement.setTotali(totali);
            }
            response.addBando(proBanElement);
        }
    }
}
