package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProPre;
import it.dmi.dmifonbe.dmifonpro.model.FiltroProgetti;
import it.dmi.dmifonbe.dmifonpro.model.PrevisionePeriodo;
import it.dmi.dmifonbe.dmifonpro.model.PrevisioneRicerca;
import it.dmi.dmifonbe.dmifonpro.repository.PrevisioneRepository;
import it.dmi.dmifonbe.dmifonpro.repository.ProgettoRepository;
import it.dmi.dmifonbe.dmifonpro.service.PrevisioneService;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PrevisioneServiceImpl implements PrevisioneService {

    @Autowired
    PrevisioneRepository previsioneRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    EntityManager em;

    @Autowired
    FiltroProgetti filtroProgetti;

    @Value("${ricercaPreBase}")
    String ricercaPreBase;

    @Value("${ricercaPreCondIdPro}")
    String ricercaPreCondIdPro;

    @Value("${ricercaPreCondLivPro1}")
    String ricercaPreCondLivPro1;

    @Value("${ricercaPreCondLivPro2}")
    String ricercaPreCondLivPro2;

    @Value("${ricercaPreCondLivPro3}")
    String ricercaPreCondLivPro3;

    @Value("${ricercaPreCondTipFin}")
    String ricercaPreCondTipFin;

    @Value("${ricercaPreDtaPreDa}")
    String ricercaPreDtaPreDa;

    @Value("${ricercaPreDtaPreA}")
    String ricercaPreDtaPreA;

    @Value("${ricercaPreFine}")
    String ricercaPreFine;

    @Value("${ricercaPreDirezione}")
    String ricercaPreDirezione;

    @Value("${imppreperiodo}")
    String imppreperiodo;

    @Override
    @Transactional
    public ProPre getPrevisione(Integer id) throws MicroServicesException {
        Optional<ProPre> proPreOptional = previsioneRepository.findById(id);
        if (proPreOptional.isPresent()) {
            ProPre proPre = proPreOptional.get();
            Hibernate.initialize(proPre.getProProByIdPro());
            return proPre;
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }

    @Override
    public ProPre inserisciPrevisione(ProPre previsioneDaInserire) throws MicroServicesException {
        if (!this.getBloccaPrevisioni()) {
            this.checkPrevisione(previsioneDaInserire, false);
            utilService.setInfoInsertRow(previsioneDaInserire);
            return previsioneRepository.saveAndFlush(previsioneDaInserire);
        } else throw new MicroServicesException(
            ErrorMessages.PREVISION_BLOCKED.getUserMessage(),
            ErrorMessages.PREVISION_BLOCKED.getCode()
        );
    }

    private void checkPrevisione(ProPre previsioneDaInserire, boolean edit) throws MicroServicesException {
        if (!edit && previsioneDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.PROPRE_INSERT_WITH_ID.getUserMessage(),
            ErrorMessages.PROPRE_INSERT_WITH_ID.getCode()
        ); else if (edit && previsioneDaInserire.getId() == 0) throw new MicroServicesException(
            ErrorMessages.PROPRE_EDIT_NO_ID.getUserMessage(),
            ErrorMessages.PROPRE_EDIT_NO_ID.getCode()
        );
        if (previsioneDaInserire.getIdPro() == null || previsioneDaInserire.getIdPro().equals(0L)) throw new MicroServicesException(
            ErrorMessages.MANDATORY_FIELD.getUserMessage(),
            ErrorMessages.MANDATORY_FIELD.getCode()
        );
        if (!progettoRepository.existsById(previsioneDaInserire.getIdPro().intValue())) throw new MicroServicesException(
            ErrorMessages.PROJECT_NOT_VALID.getUserMessage(),
            ErrorMessages.PROJECT_NOT_VALID.getCode()
        );
        if (previsioneDaInserire.getDtapre() == null || previsioneDaInserire.getImppre() == null) throw new MicroServicesException(
            ErrorMessages.MANDATORY_FIELD.getUserMessage(),
            ErrorMessages.MANDATORY_FIELD.getCode()
        );
        //in inserimento controllo che il codice non esista già
        if (!edit && previsioneRepository.findProPreByIdProAndDtapre(previsioneDaInserire.getIdPro(),previsioneDaInserire.getDtapre()).isPresent())
            throw new MicroServicesException(ErrorMessages.PREV_PRO_DATA_AlREADY_EXIST.getUserMessage(), ErrorMessages.PREV_PRO_DATA_AlREADY_EXIST.getCode());
    }

    @Override
    public void modificaPrevisione(ProPre previsioneDaModificare) throws MicroServicesException {
        if (!this.getBloccaPrevisioni()) {
            this.checkPrevisione(previsioneDaModificare, true);
            Optional<ProPre> previsioneOriginaleOpt = previsioneRepository.findById(previsioneDaModificare.getId());
            if (previsioneOriginaleOpt.isPresent()) {
                previsioneDaModificare.setUsrCreate(previsioneOriginaleOpt.get().getUsrCreate());
                previsioneDaModificare.setDtCreate(previsioneOriginaleOpt.get().getDtCreate());
                utilService.setInfoUpdateRow(previsioneDaModificare);
                previsioneRepository.saveAndFlush(previsioneDaModificare);
            } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
        } else throw new MicroServicesException(
            ErrorMessages.PREVISION_BLOCKED.getUserMessage(),
            ErrorMessages.PREVISION_BLOCKED.getCode()
        );
    }

    @Override
    public void cancellaPrevisione(Integer idPre) throws MicroServicesException {
        if (!this.getBloccaPrevisioni()) {
            if (previsioneRepository.existsById(idPre)) previsioneRepository.deleteById(idPre); else throw new MicroServicesException(
                ErrorMessages.NO_DATA_FOUND.getUserMessage(),
                ErrorMessages.NO_DATA_FOUND.getCode()
            );
        } else throw new MicroServicesException(
            ErrorMessages.PREVISION_BLOCKED.getUserMessage(),
            ErrorMessages.PREVISION_BLOCKED.getCode()
        );
    }

    @Override
    public boolean getBloccaPrevisioni() throws MicroServicesException {
        Optional<AmmPar> optParPrevisioneBloccata = parametriRepository.getAmmParByCodiceIgnoreCase(
            Parameters.PREVISIONEBLOCCATA.getValue()
        );
        if (optParPrevisioneBloccata.isPresent()) return Boolean.parseBoolean(
            optParPrevisioneBloccata.get().getValore()
        ); else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }

    @Override
    public void bloccaSbloccaPrevisioni(Boolean param) throws MicroServicesException {
        Optional<AmmPar> optParPrevisioneBloccata = parametriRepository.getAmmParByCodiceIgnoreCase(
            Parameters.PREVISIONEBLOCCATA.getValue()
        );
        if (optParPrevisioneBloccata.isPresent()) {
            optParPrevisioneBloccata.get().setValore(param.toString());
            utilService.setInfoUpdateRow(optParPrevisioneBloccata.get());
            parametriRepository.saveAndFlush(optParPrevisioneBloccata.get());
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }

    @Override
    @Transactional
    public List<ProPre> ricercaPrevisione(PrevisioneRicerca preRic, Integer idUteRuo) {
        filtroProgetti.generateFiltroProgetti(idUteRuo);
        String queryStr = ricercaPreBase;
        if (preRic.getIdProgetto() > 0) queryStr += " " + ricercaPreCondIdPro;
        if (filtroProgetti.isFiltered() || preRic.getDirezione() != 0) queryStr += " " + ricercaPreDirezione;
        if (preRic.getTipLiv() != null && !preRic.getTipLiv().isBlank()) if (
            preRic.getTipLiv().equals("01") || preRic.getTipLiv().equals("02") || preRic.getTipLiv().equals("03")
        ) queryStr += " " + ricercaPreCondLivPro1; else if (preRic.getTipLiv().equals("BA")) queryStr +=
            " " + ricercaPreCondLivPro2; else if (preRic.getTipLiv().equals("PB")) queryStr += " " + ricercaPreCondLivPro3;
        if (preRic.getTipoFinanziamento() > 0) queryStr += " " + ricercaPreCondTipFin;
        if (preRic.getDataPreDa() != null) queryStr += " " + ricercaPreDtaPreDa;
        if (preRic.getDataPreA() != null) queryStr += " " + ricercaPreDtaPreA;
        queryStr += " " + ricercaPreFine;

        Query query = em.createQuery(queryStr);
        if (preRic.getIdProgetto() > 0) query.setParameter("ID_PRO", preRic.getIdProgetto());
        if (filtroProgetti.isFiltered()) query.setParameter("DIR", filtroProgetti.getIdDirezione()); else if (
            preRic.getDirezione() != 0
        ) query.setParameter("DIR", preRic.getDirezione());
        if (preRic.getTipLiv() != null && !preRic.getTipLiv().isBlank()) if (
            preRic.getTipLiv().equals("01") || preRic.getTipLiv().equals("02") || preRic.getTipLiv().equals("03")
        ) query.setParameter("TIPLIV", Integer.parseInt(preRic.getTipLiv()));
        if (preRic.getTipoFinanziamento() > 0) query.setParameter("ID_TIPFIN", preRic.getTipoFinanziamento());
        if (preRic.getDataPreDa() != null) query.setParameter("DATA_DA", preRic.getDataPreDa());
        if (preRic.getDataPreA() != null) query.setParameter("DATA_A", preRic.getDataPreA());

        List<ProPre> results = query.getResultList();
        if (!results.isEmpty()) for (ProPre proPre : results) {
            Hibernate.initialize(proPre.getProProByIdPro());
        }
        return results;
    }

    public List<PrevisionePeriodo> getPeriodiDate(int annPreDa, int annPreA, String tipPer) {
        List<PrevisionePeriodo> results = new ArrayList<PrevisionePeriodo>();
        if (annPreA < annPreDa) return results;
        if (!(tipPer.equals("T") || tipPer.equals("S") || tipPer.equals("T"))) return results;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        PrevisionePeriodo previsionePeriodo;
        //TODO verificare cosa mettere al posto dalla try
        try {
            for (int i = annPreDa; i <= annPreA; i++) {
                if (tipPer.equals("T")) {
                    previsionePeriodo = new PrevisionePeriodo();
                    previsionePeriodo.setDataInizio(dateFormat.parse("01/01/" + i));
                    previsionePeriodo.setDataFine(dateFormat.parse("31/03/" + i));
                    previsionePeriodo.setImporto(new BigDecimal("0"));
                    results.add(previsionePeriodo);
                    previsionePeriodo = new PrevisionePeriodo();
                    previsionePeriodo.setDataInizio(dateFormat.parse("01/04/" + i));
                    previsionePeriodo.setDataFine(dateFormat.parse("30/06/" + i));
                    previsionePeriodo.setImporto(new BigDecimal("0"));
                    results.add(previsionePeriodo);
                    previsionePeriodo = new PrevisionePeriodo();
                    previsionePeriodo.setDataInizio(dateFormat.parse("01/07/" + i));
                    previsionePeriodo.setDataFine(dateFormat.parse("30/09/" + i));
                    previsionePeriodo.setImporto(new BigDecimal("0"));
                    results.add(previsionePeriodo);
                    previsionePeriodo = new PrevisionePeriodo();
                    previsionePeriodo.setDataInizio(dateFormat.parse("01/10/" + i));
                    previsionePeriodo.setDataFine(dateFormat.parse("31/12/" + i));
                    previsionePeriodo.setImporto(new BigDecimal("0"));
                    results.add(previsionePeriodo);
                }
                if (tipPer.equals("S")) {
                    previsionePeriodo = new PrevisionePeriodo();
                    previsionePeriodo.setDataInizio(dateFormat.parse("01/01/" + i));
                    previsionePeriodo.setDataFine(dateFormat.parse("30/06/" + i));
                    previsionePeriodo.setImporto(new BigDecimal("0"));
                    results.add(previsionePeriodo);
                    previsionePeriodo = new PrevisionePeriodo();
                    previsionePeriodo.setDataInizio(dateFormat.parse("01/07/" + i));
                    previsionePeriodo.setDataFine(dateFormat.parse("31/12/" + i));
                    previsionePeriodo.setImporto(new BigDecimal("0"));
                    results.add(previsionePeriodo);
                }
                if (tipPer.equals("A")) {
                    previsionePeriodo = new PrevisionePeriodo();
                    previsionePeriodo.setDataInizio(dateFormat.parse("01/01/" + i));
                    previsionePeriodo.setDataFine(dateFormat.parse("31/12/" + i));
                    previsionePeriodo.setImporto(new BigDecimal("0"));
                    results.add(previsionePeriodo);
                }
            }
        } catch (Exception e) {}
        return results;
    }

    public List<PrevisionePeriodo> getPeriodiImporti(int annPreDa, int annPreA, String tipPer, int idPro) {
        List<PrevisionePeriodo> results = this.getPeriodiDate(annPreDa, annPreA, tipPer);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
        for (PrevisionePeriodo previsionePeriodo : results) {
            Query res = em.createNativeQuery(imppreperiodo);
            res.setParameter(1, idPro);
            res.setParameter(2, dateFormat.format(previsionePeriodo.getDataInizio()));
            res.setParameter(3, dateFormat.format(previsionePeriodo.getDataFine()));
            BigDecimal imppreperiodo = (BigDecimal) res.getSingleResult();
            previsionePeriodo.setImporto(imppreperiodo == null ? BigDecimal.ZERO : imppreperiodo);
        }
        return results;
    }
}
