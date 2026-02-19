package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfin;
import it.dmi.dmifonbe.dmifonpro.model.ProTipFinResponse;
import it.dmi.dmifonbe.dmifonpro.model.TipoFinanziamentoResponse;
import it.dmi.dmifonbe.dmifonpro.model.TipoFinanziamentoRicerca;
import it.dmi.dmifonbe.dmifonpro.repository.TipoFinanziamentoRepository;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.TipoFinanziamentoService;
import it.dmi.dmifonbe.model.EntityType;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.Totali;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class TipoFinanziamentoServiceImpl implements TipoFinanziamentoService {

    @Autowired
    TipoFinanziamentoRepository tipoFinanziamentoRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    EntityManager em;

    @Autowired
    CalcolaTotaliService calcolaTotali;

    @Override
    public ProTipfin getTipoFinanziamento(Integer id) throws MicroServicesException {
        Optional<ProTipfin> tipfin = tipoFinanziamentoRepository.findById(id);
        if (tipfin.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else return tipfin.get();
    }

    @Override
    public ProTipfin inserisciTipoFinanziamento(ProTipfin tipFinDaInserire) throws MicroServicesException {
        this.checkInserisciTipoFinanziamento(tipFinDaInserire);
        utilService.setInfoInsertRow(tipFinDaInserire);
        return tipoFinanziamentoRepository.saveAndFlush(tipFinDaInserire);
    }

    private void checkInserisciTipoFinanziamento(ProTipfin tipFinDaInserire) throws MicroServicesException {
        if (tipFinDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ID_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.ID_NOT_ALLOWED.getCode()
        );
        if (
            tipoFinanziamentoRepository.findProTipfinByCodtipfinIgnoreCase(tipFinDaInserire.getCodtipfin()).isPresent()
        ) throw new MicroServicesException(ErrorMessages.COD_ALREADY_EXIST.getUserMessage(), ErrorMessages.COD_ALREADY_EXIST.getCode());
        //controllo anche l'altra ak per livuno,.....
        if (tipoFinanziamentoRepository.findProTipfinByLivunoAndLivdueAndLivtreAndLivquaAndLivcinAndLivsei(tipFinDaInserire.getLivuno(), tipFinDaInserire.getLivdue(), tipFinDaInserire.getLivtre(), tipFinDaInserire.getLivqua(), tipFinDaInserire.getLivcin(), tipFinDaInserire.getLivsei()).isPresent())
            throw new MicroServicesException(ErrorMessages.LIVPROTIPFIN_AlREADY_EXIST.getUserMessage(),ErrorMessages.LIVPROTIPFIN_AlREADY_EXIST.getCode());
    }

    @Override
    public ProTipfin modificaTipoFinanziamento(ProTipfin tipFinDaModificare) throws MicroServicesException {
        ProTipfin tipfin = this.getTipoFinanziamento(tipFinDaModificare.getId());
        if (!tipfin.getCodtipfin().equals(tipFinDaModificare.getCodtipfin())) {
            if (
                tipoFinanziamentoRepository.findProTipfinByCodtipfinIgnoreCase(tipFinDaModificare.getCodtipfin()).isPresent()
            ) throw new MicroServicesException(ErrorMessages.COD_ALREADY_EXIST.getUserMessage(), ErrorMessages.COD_ALREADY_EXIST.getCode());
        }
        tipFinDaModificare.setUsrCreate(tipfin.getUsrCreate());
        tipFinDaModificare.setDtCreate(tipfin.getDtCreate());
        utilService.setInfoUpdateRow(tipFinDaModificare);
        return tipoFinanziamentoRepository.saveAndFlush(tipFinDaModificare);
    }

    @Override
    @Transactional
    public void cancellaTipoFinanziamento(Integer id) throws MicroServicesException {
        this.checkDeleteTipoFinanziamento(id);
        tipoFinanziamentoRepository.deleteById(id);
    }

    private void checkDeleteTipoFinanziamento(Integer id) throws MicroServicesException {
        Optional<ProTipfin> tipfinToDeleteOpt = tipoFinanziamentoRepository.findById(id);
        if (tipfinToDeleteOpt.isPresent()) {
            ProTipfin tipfinToDelete = tipfinToDeleteOpt.get();
            Hibernate.initialize(tipfinToDelete.getProProsById());
            Hibernate.initialize(tipfinToDelete.getProMacprosById());
            Hibernate.initialize(tipfinToDelete.getProMacprosById_0());
            Hibernate.initialize(tipfinToDelete.getProBansById());
            if (this.tipoFinanziamentoHasChild(tipfinToDelete)) throw new MicroServicesException(
                ErrorMessages.TIPFIN_DELETE_WITH_SONS_NOT_ALLOWED.getUserMessage(),
                ErrorMessages.TIPFIN_DELETE_WITH_SONS_NOT_ALLOWED.getCode()
            );
            if (!tipfinToDelete.getProProsById().isEmpty()) throw new MicroServicesException(
                ErrorMessages.TIPFIN_DELETE_NOT_ALLOWED.getUserMessage() + "Progetto",
                ErrorMessages.TIPFIN_DELETE_NOT_ALLOWED.getCode()
            );
            if (!tipfinToDelete.getProMacprosById().isEmpty()) throw new MicroServicesException(
                ErrorMessages.TIPFIN_DELETE_NOT_ALLOWED.getUserMessage() + "Macro Progetto",
                ErrorMessages.TIPFIN_DELETE_NOT_ALLOWED.getCode()
            );
            if (!tipfinToDelete.getProMacprosById_0().isEmpty()) throw new MicroServicesException(
                ErrorMessages.TIPFIN_DELETE_NOT_ALLOWED.getUserMessage() + "Macro Progetto",
                ErrorMessages.TIPFIN_DELETE_NOT_ALLOWED.getCode()
            );
            if (!tipfinToDelete.getProBansById().isEmpty()) throw new MicroServicesException(
                ErrorMessages.TIPFIN_DELETE_NOT_ALLOWED.getUserMessage() + "Bando",
                ErrorMessages.TIPFIN_DELETE_NOT_ALLOWED.getCode()
            );
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }

    private boolean tipoFinanziamentoHasChild(ProTipfin tipfinToDelete) {
        List<ProTipfin> erediList = this.getEredi(tipfinToDelete);
        if (erediList.contains(tipfinToDelete)) erediList.remove(tipfinToDelete);
        return !erediList.isEmpty();
    }

    @Override
    public TipoFinanziamentoResponse ricercaTipoFinanziamento(TipoFinanziamentoRicerca tipFinric) throws MicroServicesException {
        TipoFinanziamentoResponse response = new TipoFinanziamentoResponse();

        if (tipFinric.checkNull()) {
            checkMaxRecords(tipoFinanziamentoRepository.count(), response);
            Pageable limit = PageRequest.of(0, this.getMaxRecords());
            Page<ProTipfin> results = tipoFinanziamentoRepository.findAllByOrderByLivunoAscLivdueAscLivtreAscLivquaAscLivcinAscLivseiAsc(
                limit
            );
            this.valorizzaResultRicerca(tipFinric.getCalcolaTotali(), results.getContent(), response);
        } else {
            tipFinric.validate();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProTipfin> query = cb.createQuery(ProTipfin.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
            Root<ProTipfin> root = query.from(ProTipfin.class);
            Root<ProTipfin> rootCount = queryCount.from(ProTipfin.class);
            List<Predicate> predicates = this.getPredicates(root, cb, tipFinric);
            List<Predicate> predicatesCount = this.getPredicates(rootCount, cb, tipFinric);
            queryCount.select(cb.count(rootCount)).where(predicatesCount.toArray(new Predicate[0]));
            query
                .select(root)
                .where(predicates.toArray(new Predicate[0]))
                .orderBy(
                    cb.asc(root.get("livuno")),
                    cb.asc(root.get("livdue")),
                    cb.asc(root.get("livtre")),
                    cb.asc(root.get("livqua")),
                    cb.asc(root.get("livcin")),
                    cb.asc(root.get("livsei"))
                );
            Long countResult = em.createQuery(queryCount).getSingleResult();
            checkMaxRecords(countResult, response);
            List<ProTipfin> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
            this.valorizzaResultRicerca(tipFinric.getCalcolaTotali(), results, response);
        }
        return response;
    }

    @Override
    public List<ProTipfin> ricercaTipoFinanziamentoAutocomplete(String autocomplete) throws MicroServicesException {
        if (autocomplete != null && !autocomplete.isBlank()) {
            return tipoFinanziamentoRepository.findAutocomplete(autocomplete.toUpperCase());
        } else {
            return tipoFinanziamentoRepository.findAllByOrderByCodtipfinAsc();
            /* throw new MicroServicesException(
            ErrorMessages.AUTOCOMPLETE_BLANK.getUserMessage(),
            ErrorMessages.AUTOCOMPLETE_BLANK.getCode());
           */
        }
    }

    private List<Predicate> getPredicates(Root root, CriteriaBuilder cb, TipoFinanziamentoRicerca tipFinric) {
        List<Predicate> predicates = new ArrayList<>();
        if (tipFinric.getAutocomplete() != null && !tipFinric.getAutocomplete().isEmpty()) predicates.add(
            cb.or(
                cb.like(cb.upper(root.get("codtipfin")), "%" + tipFinric.getAutocomplete().toUpperCase() + "%"),
                cb.like(cb.upper(root.get("destipfin")), "%" + tipFinric.getAutocomplete().toUpperCase() + "%")
            )
        );
        if (tipFinric.getTipLiv() != null && !tipFinric.getTipLiv().isEmpty()) {
            if (tipFinric.getTipLiv().equals("01")) {
                predicates.add(cb.equal(root.get("livdue"), 0));
            } else if (tipFinric.getTipLiv().equals("02")) {
                predicates.add(cb.and(cb.notEqual(root.get("livdue"), 0), cb.equal(root.get("livtre"), 0)));
            } else if (tipFinric.getTipLiv().equals("03")) {
                predicates.add(cb.and(cb.notEqual(root.get("livtre"), 0), cb.equal(root.get("livqua"), 0)));
            }  else if (tipFinric.getTipLiv().equals("04")) {
                predicates.add(cb.and(cb.notEqual(root.get("livqua"), 0), cb.equal(root.get("livcin"), 0)));
            }  else if (tipFinric.getTipLiv().equals("05")) {
                predicates.add(cb.and(cb.notEqual(root.get("livcin"), 0), cb.equal(root.get("livsei"), 0)));
            } else if (tipFinric.getTipLiv().equals("06")) {
                predicates.add(cb.notEqual(root.get("livsei"), 0));
            }
        }
        if (tipFinric.getLiv1() != null && !tipFinric.getLiv1().equals(0)) {
            predicates.add(cb.equal(root.get("livuno"), tipFinric.getLiv1()));
        }
        if (tipFinric.getLiv2() != null && !tipFinric.getLiv2().equals(0)) {
            predicates.add(cb.equal(root.get("livdue"), tipFinric.getLiv2()));
        }
        if (tipFinric.getLiv3() != null && !tipFinric.getLiv3().equals(0)) {
            predicates.add(cb.equal(root.get("livtre"), tipFinric.getLiv3()));
        }
        if (tipFinric.getLiv4() != null && !tipFinric.getLiv4().equals(0)) {
            predicates.add(cb.equal(root.get("livqua"), tipFinric.getLiv4()));
        }
        if (tipFinric.getLiv5() != null && !tipFinric.getLiv5().equals(0)) {
            predicates.add(cb.equal(root.get("livcin"), tipFinric.getLiv5()));
        }
        return predicates;
    }

    private void checkMaxRecords(Long countResult, TipoFinanziamentoResponse response) {
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

    private void valorizzaResultRicerca(boolean calcTotali, List<ProTipfin> results, TipoFinanziamentoResponse response) {
        for (ProTipfin proTipfin : results) {
            ProTipFinResponse proTipFinElement = new ProTipFinResponse();
            proTipFinElement.setTipoFinanziamento(proTipfin);
            if (calcTotali) {
                Totali totali = calcolaTotali.getTotaliFondi(EntityType.PROTIPFIN.getValue(), proTipfin.getId());
                proTipFinElement.setTotali(totali);
                Totali totaliImpegni = calcolaTotali.getTotaliImpegniAndAccertamenti(EntityType.PROTIPFIN.getValue(), proTipfin.getId());
                proTipFinElement.setTotaliImpegni(totaliImpegni);
                Totali totaliDdr = calcolaTotali.getTotaliDdr(EntityType.PROTIPFIN.getValue(), proTipfin.getId());
                proTipFinElement.setTotaliDdr(totaliDdr);
                //ritorno un booleano per sapere se ha figli
                proTipFinElement.setHasChild(tipoFinanziamentoHasChild(proTipfin));
            }
            response.addTipoFinanziamento(proTipFinElement);
        }
    }

    @Override
    public List<ProTipfin> getEredi(ProTipfin padre) {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProTipfin> query = cb.createQuery(ProTipfin.class);
        Root<ProTipfin> root = query.from(ProTipfin.class);
        predicates.add(cb.equal(root.get("livuno"), padre.getLivuno()));
        if (padre.getLivdue() != 0) {
            predicates.add(cb.equal(root.get("livdue"), padre.getLivdue()));
            if (padre.getLivtre() != 0) {
                predicates.add(cb.equal(root.get("livtre"), padre.getLivtre()));
                if (padre.getLivqua() != 0) {
                    predicates.add(cb.equal(root.get("livqua"), padre.getLivqua()));
                    if (padre.getLivcin() != 0) {
                        predicates.add(cb.equal(root.get("livcin"), padre.getLivcin()));
                    }
                }
            }
        }
        query.select(root).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(query).getResultList();
    }
}
