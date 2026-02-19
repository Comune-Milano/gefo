package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonamm.service.impl.OutputServiceImpl;
import it.dmi.dmifonbe.dmifonpro.entities.*;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.*;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.ExportProgettiService;
import it.dmi.dmifonbe.dmifonpro.service.SemaforoService;
import it.dmi.dmifonbe.dmifonpro.service.TipoFinanziamentoService;
import it.dmi.dmifonbe.model.EntityType;
import it.dmi.dmifonbe.model.Totali;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class ExportProgettiServiceImpl implements ExportProgettiService {

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    TipFasRepository tipFasRepository;

    @Autowired
    SemaforoService semaforoService;

    @Autowired
    EntityManager em;

    @Autowired
    FaseRepository faseRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    ProLisValRepository proLisValRepository;

    @Autowired
    DettippolfasRepository dettippolfasRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    TipoFinanziamentoRepository tipoFinanziamentoRepository;

    @Autowired
    TipoFinanziamentoService tipoFinanziamentoService;

    //miei
    @Autowired
    ProgettoServiceImpl progettoServiceImpl;

    @Autowired
    ResponsabileServiceImpl responsabileServiceImpl;

    @Autowired
    AvanzamentoServiceImpl avanzamentoServiceImpl;

    @Autowired
    SemaforoServiceImpl semaforoServiceImpl;

    @Autowired
    PrevisioneServiceImpl previsioneServiceImpl;

    @Autowired
    CalcolaTotaliService calcolaTotali;

    @Autowired
    TipoImportoRepository tipoImportoRepository;

    @Autowired
    TipoInfoAggiuntiveRepository tipoInfoAggiuntiveRepository;

    @Autowired
    TipoResponsabileRepository tipoResponsabileRepository;

    @Autowired
    AvanzamentoRepository avanzamentoRepository;

    @Autowired
    FiltroProgetti filtroProgetti;

    @Value("${ricercaExpProBase}")
    String ricercaExpProBase;

    @Value("${ricercaExpProCondIdPro}")
    String ricercaExpProCondIdPro;

    @Value("${ricercaExpProCondLivPro1}")
    String ricercaExpProCondLivPro1;

    @Value("${ricercaExpProCondLivPro2}")
    String ricercaExpProCondLivPro2;

    @Value("${ricercaExpProCondLivPro3}")
    String ricercaExpProCondLivPro3;

    @Value("${ricercaExpProCondTipFin}")
    String ricercaExpProCondTipFin;

    @Value("${ricercaExpProDirezione}")
    String ricercaExpProDirezione;

    @Value("${ricercaExpProMacPro}")
    String ricercaExpProMacPro;

    @Value("${ricercaExpProBan}")
    String ricercaExpProBan;

    @Value("${ricercaExpProTem}")
    String ricercaExpProTem;

    @Value("${ricercaExpProStaPro}")
    String ricercaExpProStaPro;

    @Value("${ricercaExpProStaFin}")
    String ricercaExpProStaFin;

    @Value("${ricercaExpProFine}")
    String ricercaExpProFine;
    @Autowired
    OutputServiceImpl outputServiceImpl;

    @Override
    //@Transactional
    public ExportProgettiResponse exportProgetti(ExportProgettiRicerca parametri, Integer idUteRuo) throws MicroServicesException {
        filtroProgetti.generateFiltroProgetti(idUteRuo);
        ExportProgettiResponse response = new ExportProgettiResponse();
        outputServiceImpl.start(0, "Export progetti", "csv");
        intestazione(parametri);
        String queryStr = ricercaExpProBase;
        if (parametri.getIdPro() > 0) queryStr += " " + ricercaExpProCondIdPro;
        if (parametri.getTipLiv() != null && !parametri.getTipLiv().isBlank()) if (
            parametri.getTipLiv().equals("01") || parametri.getTipLiv().equals("02") || parametri.getTipLiv().equals("03")
        ) queryStr += " " + ricercaExpProCondLivPro1; else if (parametri.getTipLiv().equals("BA")) queryStr +=
            " " + ricercaExpProCondLivPro2; else if (parametri.getTipLiv().equals("PB")) queryStr += " " + ricercaExpProCondLivPro3;
        if (parametri.getIdTipFin() > 0) queryStr += " " + ricercaExpProCondTipFin;
        if (filtroProgetti.isFiltered() || parametri.getIdDir() != 0) queryStr += " " + ricercaExpProDirezione;
        if (parametri.getIdMacPro() != 0) queryStr += " " + ricercaExpProMacPro;
        if (parametri.getIdBan() != 0) queryStr += " " + ricercaExpProBan;
        if (parametri.getIdTem() != 0) queryStr += " " + ricercaExpProTem;
        if (parametri.getIdStaPro() != 0) queryStr += " " + ricercaExpProStaPro;
        if (parametri.getIdStaFin() != 0) queryStr += " " + ricercaExpProStaFin;
        queryStr += " " + ricercaExpProFine;

        Query query = em.createQuery(queryStr);
        if (parametri.getIdPro() > 0) query.setParameter("ID_PRO", parametri.getIdPro());
        if (parametri.getTipLiv() != null && !parametri.getTipLiv().isBlank()) if (
            parametri.getTipLiv().equals("01") || parametri.getTipLiv().equals("02") || parametri.getTipLiv().equals("03")
        ) query.setParameter("TIPLIV", Integer.parseInt(parametri.getTipLiv()));
        if (parametri.getIdTipFin() > 0) query.setParameter("ID_TIPFIN", parametri.getIdTipFin());
        if (filtroProgetti.isFiltered()) query.setParameter("DIR", filtroProgetti.getIdDirezione()); else if (
            parametri.getIdDir() != 0
        ) query.setParameter("DIR", parametri.getIdDir());
        if (parametri.getIdMacPro() != 0) query.setParameter("MACPRO", Long.valueOf(parametri.getIdMacPro()));
        if (parametri.getIdBan() != 0) query.setParameter("BAN", Long.valueOf(parametri.getIdBan()));
        if (parametri.getIdTem() != 0) query.setParameter("TEM", Integer.valueOf(parametri.getIdTem()));
        if (parametri.getIdStaPro() != 0) query.setParameter("STAPRO", Long.valueOf(parametri.getIdStaPro()));
        if (parametri.getIdStaFin() != 0) query.setParameter("STAFIN", Long.valueOf(parametri.getIdStaFin()));
        List<ProPro> results = query.getResultList();
        ExportProgettiDettaglio expProDet;
        Totali totFondi;
        Totali totImpAcc;
        Totali totDdr;
        Totali totRisorse;
        ProgettoDetail progettoDetail;
        ResponsabileResponse responsabileResponse;
        Optional<ProAva> proAva;
        if (!results.isEmpty()) for (ProPro propro : results) {
            //recupero i dati del progetto
            expProDet = new ExportProgettiDettaglio();
            expProDet.setProPro(propro);
            progettoDetail = progettoServiceImpl.getProgetto(propro.getId());
            expProDet.setProgettoDetail(progettoDetail);
            if (parametri.isFlgRes()) {
                responsabileResponse = responsabileServiceImpl.getResponsabiliProgetto(propro.getId());
                expProDet.setResponsabileResponse(responsabileResponse);
            }
            if (parametri.isFlgAva()) {
                proAva = avanzamentoRepository.findByNroverAndIdPro(0L, Long.valueOf(propro.getId()));
                if (proAva.isPresent()) {
                    expProDet.setAvanzamentoDetail(avanzamentoServiceImpl.getAvanzamento(proAva.get().getId()));
                    expProDet.setSemaforo(semaforoServiceImpl.getSemaforoAvanzamento(proAva.get().getId()));
                } else {
                    expProDet.setAvanzamentoDetail(null);
                    expProDet.setSemaforo(null);
                }
            }
            if (parametri.isFlgPre()) {
                expProDet.setPrevisionePeriodoList(
                    previsioneServiceImpl.getPeriodiImporti(
                        parametri.getAnnPreDa(),
                        parametri.getAnnPreA(),
                        parametri.getTipPer(),
                        propro.getId()
                    )
                );
            }
            List<PrevisionePeriodo> result = previsioneServiceImpl.getPeriodiDate(
                parametri.getAnnPreDa(),
                parametri.getAnnPreA(),
                parametri.getTipPer()
            );
            totFondi = calcolaTotali.getTotaliFondi(EntityType.PRO.getValue(), propro.getId());
            expProDet.setTotFondi(totFondi);
            totImpAcc = calcolaTotali.getTotaliImpegniAndAccertamenti(EntityType.PRO.getValue(), propro.getId());
            expProDet.setTotImpAcc(totImpAcc);
            totDdr = calcolaTotali.getTotaliDdr(EntityType.PRO.getValue(), propro.getId());
            expProDet.setTotDdr(totDdr);
            totRisorse = calcolaTotali.getTotaliRisorse(EntityType.PRO.getValue(), propro.getId());
            expProDet.setTotRisorse(totRisorse);
            dettaglio(parametri, expProDet);
        }
        response.setFileName(outputServiceImpl.getFileName());
        response.setData(outputServiceImpl.getBytes());
        return response;
    }

    private void dettaglio(ExportProgettiRicerca parametri, ExportProgettiDettaglio expProDet) throws MicroServicesException {
        outputServiceImpl.newRow();
        dettaglioBase(parametri, expProDet);
        if (parametri.isFlgDatAnaPro()) dettaglioDatAnaPro(parametri, expProDet);
        if (parametri.isFlgIgv()) dettaglioDatIgv(parametri, expProDet);
        if (parametri.isFlgRis()) dettaglioRis(parametri, expProDet);
        if (parametri.isFlgRisDiCui()) dettaglioRisDiCui(parametri, expProDet);
        if (parametri.isFlgInfAgg()) dettaglioInfAgg(parametri, expProDet);
        if (parametri.isFlgRes()) dettaglioRes(parametri, expProDet);
        if (parametri.isFlgAva()) dettaglioAva(parametri, expProDet);
        if (parametri.isFlgPre()) dettaglioPre(parametri, expProDet);
        outputServiceImpl.addRow();
    }

    private void dettaglioBase(ExportProgettiRicerca parametri, ExportProgettiDettaglio expProDet) throws MicroServicesException {
        outputServiceImpl.add(expProDet.getProPro().getCodpro());
        outputServiceImpl.add(expProDet.getProPro().getDespro());
        if (expProDet.getProgettoDetail().getProgettoPadre() != null) {
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgettoPadre().getCodpro());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgettoPadre().getDespro());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        if (expProDet.getProgettoDetail().getProgetto().getProStaproByIdStapro() != null)
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProStaproByIdStapro().getDessta());
        else outputServiceImpl.add("");
        if (expProDet.getProgettoDetail().getProgetto().getProStafinByIdStafin() != null)
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProStafinByIdStafin().getDessta());
        else outputServiceImpl.add("");
        if (expProDet.getProgettoDetail().getProgetto().getFlgopeavv() != null && expProDet.getProgettoDetail().getProgetto().getFlgopeavv().equals("S"))
            outputServiceImpl.add("Si");
        else
            outputServiceImpl.add("No");
        outputServiceImpl.add(expProDet.getProPro().getCodcup());
        if (expProDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin() != null) {
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin().getCodtipfin());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin().getDestipfin());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin().getLivuno());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin().getLivdue());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin().getLivtre());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin().getLivqua());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin().getLivcin());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin().getLivsei());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        if (expProDet.getProgettoDetail().getProgetto().getProMacproByIdMacpro() != null) {
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProMacproByIdMacpro().getCodmacpro());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProMacproByIdMacpro().getDesmacpro());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        if (expProDet.getProgettoDetail().getProgettoDirezione() != null) {
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgettoDirezione().getCoddir());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgettoDirezione().getDesdir());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getCodset());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getCodass());
        outputServiceImpl.add(expProDet.getTotFondi().getImprisfonpro());
        outputServiceImpl.add(expProDet.getTotFondi().getImprisfon());
        outputServiceImpl.add(expProDet.getTotImpAcc().getImpacc());
        outputServiceImpl.add(expProDet.getTotImpAcc().getImprev());
        outputServiceImpl.add(expProDet.getTotImpAcc().getImpimp());
        outputServiceImpl.add(expProDet.getTotImpAcc().getImpliq());
        outputServiceImpl.add(expProDet.getTotImpAcc().getImpman());
        outputServiceImpl.add(expProDet.getTotDdr().getImpddr());
        outputServiceImpl.add(expProDet.getTotDdr().getImpinc());
    }

    private void dettaglioDatAnaPro(ExportProgettiRicerca parametri, ExportProgettiDettaglio expProDet) throws MicroServicesException {
        if (expProDet.getProgettoDetail().getProgetto().getProBanByIdBan() != null) {
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProBanByIdBan().getCodban());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProBanByIdBan().getDesban());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        if (expProDet.getProgettoDetail().getProgetto().getProTemByIdTem() != null) {
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProTemByIdTem().getCodtem());
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProTemByIdTem().getDestem());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getCodappren());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getCodcig());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getCodcui());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getTippro());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getCodtippro());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getCodgami());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getVeraff());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getEstaff());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getNotaff());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getDesaffhou());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getDelcan());
        if (expProDet.getProgettoDetail().getProgetto().getProMunByIdMun() != null) {
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProMunByIdMun().getDesmun());
        } else {
            outputServiceImpl.add("");
        }
        if (expProDet.getProgettoDetail().getProgetto().getProNilByIdNil() != null) {
            outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getProNilByIdNil().getId());
        } else {
            outputServiceImpl.add("");
        }
    }

    private void dettaglioDatIgv(ExportProgettiRicerca parametri, ExportProgettiDettaglio expProDet) throws MicroServicesException {
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getImpigv());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getRifigv());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getImpigvdel());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getNotigv());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getNotpro());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getNotpre());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getNotimp());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getImpimpric());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getImpimppre());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getImpmanpre());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getImpaccpre());
        outputServiceImpl.add(expProDet.getProgettoDetail().getProgetto().getImprevpre());
    }

    private void dettaglioRis(ExportProgettiRicerca parametri, ExportProgettiDettaglio expProDet) throws MicroServicesException {
        List<ProImp> result = expProDet.getProgettoDetail().getRisorse();
        if (!result.isEmpty()) for (ProImp proImp : result) {
            outputServiceImpl.add(proImp.getImppro());
            //aggiungo anche le informazioni sugli accercataemnti, recersali,....
            boolean travato = false;
            for (Risorse risorse : expProDet.getTotRisorse().getRisorse()) {
                if (risorse.getIdTipImp().longValue() == proImp.getIdTipimp().longValue()) {
                    outputServiceImpl.add(risorse.getSumImpAcc());
                    outputServiceImpl.add(risorse.getSumImpRev());
                    outputServiceImpl.add(risorse.getSumImpImp());
                    outputServiceImpl.add(risorse.getSumImpMan());
                    travato = true;
                }
            }
            if (!travato) {
                outputServiceImpl.add(BigDecimal.ZERO);
                outputServiceImpl.add(BigDecimal.ZERO);
                outputServiceImpl.add(BigDecimal.ZERO);
                outputServiceImpl.add(BigDecimal.ZERO);
            }
        }
        //aggiundo ache i totale
        for (Risorse risorse : expProDet.getTotRisorse().getRisorse()) {
            //idtipimp a zero è il totale di tutto
            if (risorse.getIdTipImp().longValue() == 0) {
                outputServiceImpl.add(risorse.getSumImpPro());
                outputServiceImpl.add(risorse.getSumImpAcc());
                outputServiceImpl.add(risorse.getSumImpRev());
                outputServiceImpl.add(risorse.getSumImpImp());
                outputServiceImpl.add(risorse.getSumImpMan());
            }
        }
    }

    private void dettaglioRisDiCui(ExportProgettiRicerca parametri, ExportProgettiDettaglio expProDet) throws MicroServicesException {
        List<ProImp> result = expProDet.getProgettoDetail().getRisorseDiCui();
        if (!result.isEmpty()) for (ProImp proImp : result) {
            outputServiceImpl.add(proImp.getImppro());
        }
    }

    private void dettaglioInfAgg(ExportProgettiRicerca parametri, ExportProgettiDettaglio expProDet) throws MicroServicesException {
        List<ProInfagg> result = expProDet.getProgettoDetail().getInfoAggiuntive();
        if (!result.isEmpty()) for (ProInfagg proInfagg : result) {
            outputServiceImpl.add(proInfagg.getInfagg());
        }
    }

    private void dettaglioRes(ExportProgettiRicerca parametri, ExportProgettiDettaglio expProDet) throws MicroServicesException {
        List<DettaglioResponsabile> result = expProDet.getResponsabileResponse().getResponsabili();
        if (!result.isEmpty()) for (DettaglioResponsabile dettaglioResponsabile : result) {
            if (dettaglioResponsabile.getUtente() != null) outputServiceImpl.add(
                dettaglioResponsabile.getUtente().getUsername() +
                " - " +
                dettaglioResponsabile.getUtente().getCognome() +
                " " +
                dettaglioResponsabile.getUtente().getNome()
            ); else outputServiceImpl.add("");
        }
    }

    private void dettaglioAva(ExportProgettiRicerca parametri, ExportProgettiDettaglio expProDet) throws MicroServicesException {
        if (expProDet.getAvanzamentoDetail() != null) {
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getDtarilava());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getUsrLstupd());
            outputServiceImpl.add(expProDet.getSemaforo().getColore());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getDesstaava());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getPrcava());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getDatinilav());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getDatfinlav());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvalfasint().getVallis());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvallivcri().getVallis());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getNotcri());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvaltipapp().getVallis());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getDesapp());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvalstaant().getVallis());
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getImpant());
            //TODO manca getProLisvalByIdLisvaltipolfas() va aggiunto
            outputServiceImpl.add(expProDet.getAvanzamentoDetail().getAvanzamento().getIdlisvaltipolfas());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
    }

    private void dettaglioPre(ExportProgettiRicerca parametri, ExportProgettiDettaglio expProDet) throws MicroServicesException {
        List<PrevisionePeriodo> result = expProDet.getPrevisionePeriodoList();
        if (!result.isEmpty()) for (PrevisionePeriodo previsionePeriodo : result) {
            outputServiceImpl.add(previsionePeriodo.getImporto());
        }
    }

    private void intestazione(ExportProgettiRicerca parametri) throws MicroServicesException {
        outputServiceImpl.newRow();
        intestazioneBase(parametri);
        if (parametri.isFlgDatAnaPro()) intestazioneDatAnaPro(parametri);
        if (parametri.isFlgIgv()) intestazioneDatIgv(parametri);
        if (parametri.isFlgRis()) intestazioneRis(parametri);
        if (parametri.isFlgRisDiCui()) intestazioneRisDiCui(parametri);
        if (parametri.isFlgInfAgg()) intestazioneInfAgg(parametri);
        if (parametri.isFlgRes()) intestazioneRes(parametri);
        if (parametri.isFlgAva()) intestazioneAva(parametri);
        if (parametri.isFlgPre()) intestazionePre(parametri);
        outputServiceImpl.addRow();
    }

    private void intestazioneBase(ExportProgettiRicerca parametri) throws MicroServicesException {
        outputServiceImpl.add("Codice progetto");
        outputServiceImpl.add("Descrizione progetto");
        outputServiceImpl.add("Codice progetto padre");
        outputServiceImpl.add("Descrizione progetto padre");
        outputServiceImpl.add("Stato progetto");
        outputServiceImpl.add("Stato finanziamento");
        outputServiceImpl.add("Operazioni avviate");
        outputServiceImpl.add("CUP");
        outputServiceImpl.add("Codice tipo finanziamento");
        outputServiceImpl.add("Desc. tipo finanziamento");
        outputServiceImpl.add("Tip. fin. livello 1");
        outputServiceImpl.add("Tip. fin. livello 2");
        outputServiceImpl.add("Tip. fin. livello 3");
        outputServiceImpl.add("Tip. fin. livello 4");
        outputServiceImpl.add("Tip. fin. livello 5");
        outputServiceImpl.add("Tip. fin. livello 6");
        outputServiceImpl.add("Codice macroprogetto");
        outputServiceImpl.add("Desc. macroprogetto");
        outputServiceImpl.add("Codice direzione");
        outputServiceImpl.add("Desc. direzione");
        outputServiceImpl.add("Codice area/settore");
        outputServiceImpl.add("Codice assessorato");
        outputServiceImpl.add("Risorse del fordo del progetto");
        outputServiceImpl.add("Risorse approvate liv. basso");
        outputServiceImpl.add("Accertamenti");
        outputServiceImpl.add("Reversali");
        outputServiceImpl.add("Impegni");
        outputServiceImpl.add("Liquidazioni");
        outputServiceImpl.add("Mandati");
        outputServiceImpl.add("DDR domande di rimborso");
        outputServiceImpl.add("DDR incassate");
    }

    private void intestazioneDatAnaPro(ExportProgettiRicerca parametri) throws MicroServicesException {
        outputServiceImpl.add("Codice bando");
        outputServiceImpl.add("Des. bando");
        outputServiceImpl.add("Codice tamatica");
        outputServiceImpl.add("Des. tematica");
        outputServiceImpl.add("Codice applic. rendicontazione");
        outputServiceImpl.add("CIG");
        outputServiceImpl.add("CUI");
        outputServiceImpl.add("Tipo progetto");
        outputServiceImpl.add("Codice");
        outputServiceImpl.add("Codice Gami");
        outputServiceImpl.add("Data verbali ammissione al finanziamento");
        outputServiceImpl.add("Estremi e data ammissione al finanziamento");
        outputServiceImpl.add("Note ammissione al finanziamento");
        outputServiceImpl.add("Aff. In house");
        outputServiceImpl.add("Delibera di candidatura");
        outputServiceImpl.add("Municipio");
        outputServiceImpl.add("NIL");
    }

    private void intestazioneDatIgv(ExportProgettiRicerca parametri) throws MicroServicesException {
        outputServiceImpl.add("Importo IGV");
        outputServiceImpl.add("Riferimento IGV");
        outputServiceImpl.add("Importo IGV in Delfi");
        outputServiceImpl.add("Note IGV");
        outputServiceImpl.add("Note progetto");
        outputServiceImpl.add("Note previsioni");
        outputServiceImpl.add("Note impegni");
        outputServiceImpl.add("Imp. impegni richiesti");
        outputServiceImpl.add("Imp. impegni pegressi");
        outputServiceImpl.add("Imp. mandati pegressi");
        outputServiceImpl.add("Imp. accertamenti pegressi");
        outputServiceImpl.add("Imp. revesali pegressi");
    }

    private void intestazioneRis(ExportProgettiRicerca parametri) throws MicroServicesException {
        List<ProTipimp> result = tipoImportoRepository.findAllFlgdicuiNotEqualS();
        if (!result.isEmpty()) for (ProTipimp proTipimp : result) {
            outputServiceImpl.add(proTipimp.getDestipimp());
            outputServiceImpl.add("Accertamenti " + proTipimp.getDestipimp());
            outputServiceImpl.add("Reversali " + proTipimp.getDestipimp());
            outputServiceImpl.add("Impegni " + proTipimp.getDestipimp());
            outputServiceImpl.add("Mandati " + proTipimp.getDestipimp());
        }
        outputServiceImpl.add("Risorse Totali");
        outputServiceImpl.add("Accertamenti Totali");
        outputServiceImpl.add("Reversali Totali");
        outputServiceImpl.add("Impegni Totali");
        outputServiceImpl.add("Mandati Totali");
    }

    private void intestazioneRisDiCui(ExportProgettiRicerca parametri) throws MicroServicesException {
        List<ProTipimp> result = tipoImportoRepository.findAllFlgdicuiEqualS();
        if (!result.isEmpty()) for (ProTipimp proTipimp : result) {
            outputServiceImpl.add(proTipimp.getDestipimp());
        }
    }

    private void intestazioneInfAgg(ExportProgettiRicerca parametri) throws MicroServicesException {
        List<ProTipinfagg> result = tipoInfoAggiuntiveRepository.findAllByOrderByOrdtipinfaggAsc();
        if (!result.isEmpty()) for (ProTipinfagg proTipinfagg : result) {
            outputServiceImpl.add(proTipinfagg.getDestipinfagg());
        }
    }

    private void intestazioneRes(ExportProgettiRicerca parametri) throws MicroServicesException {
        List<ProTipres> result = tipoResponsabileRepository.findAllByOrderByOrdtipresAsc();
        if (!result.isEmpty()) for (ProTipres proTipres : result) {
            outputServiceImpl.add(proTipres.getDestipres());
        }
    }

    private void intestazioneAva(ExportProgettiRicerca parametri) throws MicroServicesException {
        outputServiceImpl.add("Date Rilev. Ava.");
        outputServiceImpl.add("Utente aggiornamento");
        outputServiceImpl.add("Semaforo");
        outputServiceImpl.add("Stato avanz. tecnico");
        outputServiceImpl.add("Perc. avanz.");
        outputServiceImpl.add("Data inizio lav.");
        outputServiceImpl.add("Data fine lav.");
        outputServiceImpl.add("Fase intervento");
        outputServiceImpl.add("Livello criticità");
        outputServiceImpl.add("Note criticità");
        outputServiceImpl.add("Tipo appalto");
        outputServiceImpl.add("N.appalto");
        outputServiceImpl.add("Stato anticipazione");
        outputServiceImpl.add("Importo anticipazione");
        outputServiceImpl.add("Tipologia di fase");
    }

    private void intestazionePre(ExportProgettiRicerca parametri) throws MicroServicesException {
        List<PrevisionePeriodo> result = previsioneServiceImpl.getPeriodiDate(
            parametri.getAnnPreDa(),
            parametri.getAnnPreA(),
            parametri.getTipPer()
        );
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        String intest;
        if (!result.isEmpty()) for (PrevisionePeriodo previsionePeriodo : result) {
            intest =
                "Prev. da " +
                dateFormat.format(previsionePeriodo.getDataInizio()) +
                " a " +
                dateFormat.format(previsionePeriodo.getDataFine());
            outputServiceImpl.add(intest);
        }
    }
}
