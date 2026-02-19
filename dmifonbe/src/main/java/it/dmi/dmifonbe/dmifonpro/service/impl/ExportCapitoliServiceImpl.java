package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonamm.service.impl.OutputServiceImpl;
import it.dmi.dmifonbe.dmifonpro.entities.ProDetcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.DetConRepository;
import it.dmi.dmifonbe.dmifonpro.repository.EntitaContabileRepository;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.ExportCapitoliService;
import it.dmi.dmifonbe.model.EntityType;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.Totali;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Calendar;
import java.util.List;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class ExportCapitoliServiceImpl implements ExportCapitoliService {

    EntitaContabileRepository entitaContabileRepository;

    //miei
    @Autowired
    EntityManager em;

    @Autowired
    ProgettoServiceImpl progettoServiceImpl;

    @Autowired
    ResponsabileServiceImpl responsabileServiceImpl;

    @Autowired
    CalcolaTotaliService calcolaTotali;

    @Autowired
    CapitoliProgettoServiceImpl capitoliProgettoServiceImpl;


    @Autowired
    UtilService utilService;

    @Autowired
    DetConRepository detConRepository;

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

    @Value("${impimpegni1}")
    String impimpegni1;

    @Value("${impimpegni2}")
    String impimpegni2;

    @Value("${impimpegni3}")
    String impimpegni3;

    @Value("${impimpegni4}")
    String impimpegni4;
    @Value("${filterPro}")
    String filterPro;
    @Autowired
    private OutputServiceImpl outputServiceImpl;

    @Override
    @Transactional
    public ExportCapitoliResponse exportCapitoli(ExportCapitoliRicerca parametri, Integer idUteRuo) throws MicroServicesException {
        filtroProgetti.generateFiltroProgetti(idUteRuo);
        ExportCapitoliResponse response = new ExportCapitoliResponse();
        outputServiceImpl.start(0, "Export capitoli", "csv");
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
        List<ProEntcon> resultEntCon;
        ProgettoAndProgettoPadre progettoAndProgettoPadre;
        ProgettoDetail progettoDetail;
        ResponsabileResponse responsabileResponse;
        Totali totFondi;
        Totali totaliImpegniAccertamenti;
        Totali totaliImpegniAndAccertamentiPregressi;
        ExportCapitoliDettaglio expCapDet;
        DatiCapitolo datiCapitolo;
        BigDecimal totaleImpegniCapitoli;
        BigDecimal totaleAccertamentiCapitoli;
        BigDecimal impegnatoConImpegni;
        ProDetcon proDetconPre;
        if (!results.isEmpty())
            for (ProPro propro : results) {
                progettoAndProgettoPadre = utilService.getProgettoAndProgettoPadre(Integer.valueOf(propro.getId()));
                progettoDetail = progettoServiceImpl.getProgetto(propro.getId());
                responsabileResponse = responsabileServiceImpl.getResponsabiliProgetto(propro.getId());
                System.out.println("calcolaTotali" + propro.getId());
                totFondi = calcolaTotali.getTotaliFondi(EntityType.PRO.getValue(), propro.getId());
                totaliImpegniAccertamenti = calcolaTotali.getTotaliImpegniAndAccertamenti(EntityType.PRO.getValue(), propro.getId());
                totaliImpegniAndAccertamentiPregressi = calcolaTotali.getTotaliImpegniAndAccertamentiPregressi(EntityType.PRO.getValue(), propro.getId());
                totaleImpegniCapitoli = BigDecimal.ZERO;
                totaleAccertamentiCapitoli = BigDecimal.ZERO;
                System.out.println("propro " + propro.getId());
                Hibernate.initialize(propro.getProDetconsById());
                System.out.println("propro fine" + propro.getId());
                //recupero tutti i dettagli contabili del progetto
                for (ProDetcon proDetcon : propro.getProDetconsById()) {
                    if (proDetcon.getTipent().equals(Parameters.CAPU.getValue()) ||
                        (parametri.isFlgCapEnt() && proDetcon.getTipent().equals(Parameters.CAPE.getValue()))) {
                        expCapDet = new ExportCapitoliDettaglio();
                        expCapDet.setProPro(propro);
                        expCapDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                        expCapDet.setProgettoDetail(progettoDetail);
                        expCapDet.setResponsabileResponse(responsabileResponse);
                        expCapDet.setTotFondi(totFondi);
                        expCapDet.setProDetcon(proDetcon);
                        System.out.println("capitoliProgettoServiceImpl " + proDetcon.getCodentcon() + " - " + proDetcon.getTipent());
                        try {
                            datiCapitolo = capitoliProgettoServiceImpl.getCapitoli(proDetcon.getCodentcon(), proDetcon.getTipent());
                        } catch (Exception e) {
                            datiCapitolo = getDatiCapitoloVuoto();
                            datiCapitolo.setId(proDetcon.getCodentcon());
                        }
                        expCapDet.setDatiCapitoloUscita(datiCapitolo);
                        calcolaImpegniAccetamentiCapitolo(propro, expCapDet);
                        if (proDetcon.getTipent().equals(Parameters.CAPU.getValue()) &&
                            proDetcon.getCodentcol() != null &&
                            !proDetcon.getCodentcol().isEmpty()) {
                            try {
                                datiCapitolo = capitoliProgettoServiceImpl.getCapitoli(proDetcon.getCodentcol(), Parameters.CAPE.getValue());
                            } catch (Exception e) {
                                datiCapitolo = getDatiCapitoloVuoto();
                                datiCapitolo.setId(proDetcon.getCodentcol());
                            }
                            expCapDet.setDatiCapitoloEntrata(datiCapitolo);
                        }
                        dettaglio(parametri, expCapDet);
                        //aggiorni il totale degli impegni ed accertamenti dei capitoli del progetto
                        if (proDetcon.getTipent().equals(Parameters.CAPE.getValue()))
                            totaleAccertamentiCapitoli = totaleAccertamentiCapitoli.add(expCapDet.getTotImpegniCapitoloProgetto());
                        else
                            totaleImpegniCapitoli = totaleImpegniCapitoli.add(expCapDet.getTotImpegniCapitoloProgetto());
                    }

                }
                try {
                    //controllo se esistono gli impegni pregressi
                    if (!(totaliImpegniAndAccertamentiPregressi.getImpimp().compareTo(BigDecimal.ZERO) == 0)) {
                        expCapDet = new ExportCapitoliDettaglio();
                        expCapDet.setProPro(propro);
                        expCapDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                        expCapDet.setProgettoDetail(progettoDetail);
                        expCapDet.setResponsabileResponse(responsabileResponse);
                        expCapDet.setTotFondi(totFondi);
                        proDetconPre = new ProDetcon();
                        proDetconPre.setTipent(Parameters.CAPU.getValue());
                        proDetconPre.setCodentcon("Impegni pregressi");
                        expCapDet.setProDetcon(proDetconPre);
                        datiCapitolo = getDatiCapitoloVuoto();
                        datiCapitolo.setId("");
                        expCapDet.setTotImpegniCapitoloPregressi(totaliImpegniAndAccertamentiPregressi.getImpimp());
                        expCapDet.setTotImpegniCapitoloProgetto(totaliImpegniAndAccertamentiPregressi.getImpimp());
                        expCapDet.setDatiCapitoloEntrata(datiCapitolo);
                    }
                } catch (Exception e) {
                    System.out.println("controllo se esistono gli impegni pregressi" + propro.getId() + "" + propro.getCodpro());
                }
                try {
                    //controllo se esiste una quota di impegnato su capitoli diversi da quelli indicati
                    impegnatoConImpegni = totaliImpegniAccertamenti.getImpimp().subtract(totaliImpegniAndAccertamentiPregressi.getImpimp());
                    if (!(impegnatoConImpegni.compareTo(totaleImpegniCapitoli) == 0)) {
                        expCapDet = new ExportCapitoliDettaglio();
                        expCapDet.setProPro(propro);
                        expCapDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                        expCapDet.setProgettoDetail(progettoDetail);
                        expCapDet.setResponsabileResponse(responsabileResponse);
                        expCapDet.setTotFondi(totFondi);
                        proDetconPre = new ProDetcon();
                        proDetconPre.setTipent(Parameters.CAPU.getValue());
                        proDetconPre.setCodentcon("Impegni senza capitolo indicato");
                        expCapDet.setProDetcon(proDetconPre);
                        datiCapitolo = getDatiCapitoloVuoto();
                        datiCapitolo.setId("");
                        expCapDet.setTotImpegniCapitoloPregressi(impegnatoConImpegni.subtract(totaleImpegniCapitoli));
                        expCapDet.setTotImpegniCapitoloProgetto(impegnatoConImpegni.subtract(totaleImpegniCapitoli));
                        expCapDet.setDatiCapitoloEntrata(datiCapitolo);
                    }
                } catch (Exception e) {
                    System.out.println("controllo se esiste una quota di impegnato su capitoli diversi da quelli indicati" + propro.getId() + "" + propro.getCodpro());
                }
                try {
                    //controllo se esistono gli accertamenti pregressi
                    if (!(totaliImpegniAndAccertamentiPregressi.getImpacc().compareTo(BigDecimal.ZERO) == 0)) {
                        expCapDet = new ExportCapitoliDettaglio();
                        expCapDet.setProPro(propro);
                        expCapDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                        expCapDet.setProgettoDetail(progettoDetail);
                        expCapDet.setResponsabileResponse(responsabileResponse);
                        expCapDet.setTotFondi(totFondi);
                        proDetconPre = new ProDetcon();
                        proDetconPre.setTipent(Parameters.CAPU.getValue());
                        proDetconPre.setCodentcon("Accertamenti pregressi");
                        expCapDet.setProDetcon(proDetconPre);
                        datiCapitolo = getDatiCapitoloVuoto();
                        datiCapitolo.setId("");
                        expCapDet.setTotImpegniCapitoloPregressi(totaliImpegniAndAccertamentiPregressi.getImpimp());
                        expCapDet.setTotImpegniCapitoloProgetto(totaliImpegniAndAccertamentiPregressi.getImpimp());
                        expCapDet.setDatiCapitoloEntrata(datiCapitolo);
                    }
                } catch (Exception e) {
                    System.out.println("controllo se esistono gli accertamenti pregressi" + propro.getId() + "" + propro.getCodpro());
                }
                try {
                    //controllo se esiste una quota di accertamenti su capitoli diversi da quelli indicati
                    impegnatoConImpegni = totaliImpegniAccertamenti.getImpacc().subtract(totaliImpegniAndAccertamentiPregressi.getImpacc());
                    if (!(impegnatoConImpegni.compareTo(totaleAccertamentiCapitoli) == 0)) {
                        expCapDet = new ExportCapitoliDettaglio();
                        expCapDet.setProPro(propro);
                        expCapDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                        expCapDet.setProgettoDetail(progettoDetail);
                        expCapDet.setResponsabileResponse(responsabileResponse);
                        expCapDet.setTotFondi(totFondi);
                        proDetconPre = new ProDetcon();
                        proDetconPre.setTipent(Parameters.CAPU.getValue());
                        proDetconPre.setCodentcon("Accertamenti senza capitolo indicato");
                        expCapDet.setProDetcon(proDetconPre);
                        datiCapitolo = getDatiCapitoloVuoto();
                        datiCapitolo.setId("");
                        expCapDet.setTotImpegniCapitoloPregressi(impegnatoConImpegni.subtract(totaleAccertamentiCapitoli));
                        expCapDet.setTotImpegniCapitoloProgetto(impegnatoConImpegni.subtract(totaleAccertamentiCapitoli));
                        expCapDet.setDatiCapitoloEntrata(datiCapitolo);
                    }
                } catch (Exception e) {
                    System.out.println("controllo se esiste una quota di accertamenti su capitoli diversi da quelli indicati" + propro.getId() + "" + propro.getCodpro());
                }
            }
        response.setFileName(outputServiceImpl.getFileName());
        response.setData(outputServiceImpl.getBytes());
        return response;
    }


    private DatiCapitolo getDatiCapitoloVuoto() {
        DatiCapitolo datiCapitolo = new DatiCapitolo();
        datiCapitolo.setDescrizione("NON TROVATO");
        datiCapitolo.setTitolo("");
        datiCapitolo.setMacro("");
        datiCapitolo.setMissione("");
        datiCapitolo.setProgramma("");
        datiCapitolo.setPrevisione("0");
        datiCapitolo.setPrevisione_1("0");
        datiCapitolo.setPrevisione_2("0");
        datiCapitolo.setassestato("0");
        datiCapitolo.setassestato_1("0");
        datiCapitolo.setassestato_2("0");
        datiCapitolo.setImpegnato("0");
        datiCapitolo.setImpegnato_1("0");
        datiCapitolo.setImpegnato_2("0");
        return datiCapitolo;
    }


    private void calcolaImpegniAccetamentiCapitolo(ProPro propro, ExportCapitoliDettaglio expCapDet) throws MicroServicesException {
        Query res = em.createNativeQuery(impimpegni1 + " " + filterPro + " " + impimpegni2 + " " + impimpegni3 + " " + impimpegni4);
        res.setParameter(1, propro.getId());
        if (expCapDet.getProDetcon().getTipent().equals(Parameters.CAPE.getValue()))
            res.setParameter(2, Parameters.CALCACCE.getValue());
        else
            res.setParameter(2, Parameters.CALCIMP.getValue());
        res.setParameter(3, expCapDet.getDatiCapitoloUscita().getId());
        Calendar now = Calendar.getInstance();
        int intCurrentYear = now.get(Calendar.YEAR);
        res.setParameter(4, String.valueOf(intCurrentYear));
        Object[] impegniObjects = (Object[]) res.getSingleResult();
        expCapDet.setTotImpegniCapitoloPregressi(impegniObjects[0] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[0]);
        res = em.createNativeQuery(impimpegni1 + " " + filterPro + " " + impimpegni2 + " " + impimpegni3);
        res.setParameter(1, propro.getId());
        if (expCapDet.getProDetcon().getTipent().equals(Parameters.CAPE.getValue()))
            res.setParameter(2, Parameters.CALCACCE.getValue());
        else
            res.setParameter(2, Parameters.CALCIMP.getValue());
        res.setParameter(3, expCapDet.getDatiCapitoloUscita().getId());
        impegniObjects = (Object[]) res.getSingleResult();
        expCapDet.setTotImpegniCapitoloProgetto(impegniObjects[0] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[0]);
    }


    private void dettaglio(ExportCapitoliRicerca parametri, ExportCapitoliDettaglio expCapDet) throws MicroServicesException {
        outputServiceImpl.newRow();
        outputServiceImpl.add(expCapDet.getProPro().getCodpro());
        outputServiceImpl.add(expCapDet.getProPro().getDespro());
        outputServiceImpl.add(expCapDet.getProgettoAndProgettoPadre().getProgettoPadreLabel());
        if (expCapDet.getProgettoDetail().getProgetto().getProMacproByIdMacpro() != null) {
            outputServiceImpl.add(expCapDet.getProgettoDetail().getProgetto().getProMacproByIdMacpro().getCodmacpro());
            outputServiceImpl.add(expCapDet.getProgettoDetail().getProgetto().getProMacproByIdMacpro().getDesmacpro());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        if (expCapDet.getProgettoDetail().getProgettoDirezione() != null) {
            outputServiceImpl.add(expCapDet.getProgettoDetail().getProgettoDirezione().getCoddir() + " - " + expCapDet.getProgettoDetail().getProgettoDirezione().getDesdir());
        } else {
            outputServiceImpl.add("");
        }
        if (expCapDet.getResponsabileResponse() != null) {
            List<DettaglioResponsabile> dettaglioResponsabileList = expCapDet.getResponsabileResponse().getResponsabili();
            if (!dettaglioResponsabileList.isEmpty() && dettaglioResponsabileList.get(0).getUtente() != null) {
                outputServiceImpl.add(dettaglioResponsabileList.get(0).getUtente().getCognome() + " " + dettaglioResponsabileList.get(0).getUtente().getNome());
            } else {
                outputServiceImpl.add("");
            }
        } else {
            outputServiceImpl.add("");
        }
        outputServiceImpl.add(expCapDet.getTotFondi().getImprisfon());
        if (expCapDet.getProDetcon().getTipent().equals(Parameters.CAPE.getValue()))
            outputServiceImpl.add("entrata");
        else
            outputServiceImpl.add("uscita");
        outputServiceImpl.add("Cap. " + expCapDet.getProDetcon().getCodentcon());
        outputServiceImpl.add(expCapDet.getDatiCapitoloUscita().getDescrizione());
        outputServiceImpl.add(expCapDet.getDatiCapitoloUscita().getTitolo());
        outputServiceImpl.add(expCapDet.getDatiCapitoloUscita().getMacro());
        outputServiceImpl.add(expCapDet.getDatiCapitoloUscita().getMissione());
        outputServiceImpl.add(expCapDet.getDatiCapitoloUscita().getProgramma());
        if (expCapDet.getDatiCapitoloEntrata() != null) {
            outputServiceImpl.add("Cap. " + expCapDet.getDatiCapitoloEntrata().getId());
            outputServiceImpl.add(expCapDet.getDatiCapitoloEntrata().getDescrizione());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        outputServiceImpl.add(expCapDet.getTotImpegniCapitoloPregressi());
        BigDecimal importo;
        importo = new BigDecimal(expCapDet.getDatiCapitoloUscita().getAssestato());
        outputServiceImpl.add(importo);
        importo = new BigDecimal(expCapDet.getDatiCapitoloUscita().getImpegnato());
        outputServiceImpl.add(importo);
        importo = new BigDecimal(expCapDet.getDatiCapitoloUscita().getassestato_1());
        outputServiceImpl.add(importo);
        importo = new BigDecimal(expCapDet.getDatiCapitoloUscita().getImpegnato_1());
        outputServiceImpl.add(importo);
        importo = new BigDecimal(expCapDet.getDatiCapitoloUscita().getassestato_2());
        outputServiceImpl.add(importo);
        importo = new BigDecimal(expCapDet.getDatiCapitoloUscita().getImpegnato_2());
        outputServiceImpl.add(importo);
        BigDecimal totAssestato = BigDecimal.ZERO;
        if (expCapDet.getDatiCapitoloUscita().getAssestato() != null)
            totAssestato = totAssestato.add(new BigDecimal(expCapDet.getDatiCapitoloUscita().getAssestato()));
        if (expCapDet.getDatiCapitoloUscita().getassestato_1() != null)
            totAssestato = totAssestato.add(new BigDecimal(expCapDet.getDatiCapitoloUscita().getassestato_1()));
        if (expCapDet.getDatiCapitoloUscita().getassestato_2() != null)
            totAssestato = totAssestato.add(new BigDecimal(expCapDet.getDatiCapitoloUscita().getassestato_2()));
        outputServiceImpl.add(totAssestato);
        outputServiceImpl.add(expCapDet.getTotImpegniCapitoloProgetto());
        outputServiceImpl.add(expCapDet.getProPro().getNotimp());
        outputServiceImpl.addRow();
    }

    private void intestazione(ExportCapitoliRicerca parametri) throws MicroServicesException {
        Year thisYear = Year.now();
        outputServiceImpl.newRow();
        outputServiceImpl.add("Codice progetto");
        outputServiceImpl.add("Descrizione progetto");
        outputServiceImpl.add("Progetto padre");
        outputServiceImpl.add("Codice Macro prog.");
        outputServiceImpl.add("Descr. Macro prog.");
        outputServiceImpl.add("Direzione");
        outputServiceImpl.add("RUP/RIO");
        outputServiceImpl.add("Totale risorse fondo");
        outputServiceImpl.add("Tipo uscita/entrata");
        outputServiceImpl.add("Capitolo");
        outputServiceImpl.add("Descrizione capitolo");
        outputServiceImpl.add("Titolo");
        outputServiceImpl.add("Macro/tipologia");
        outputServiceImpl.add("Missione");
        outputServiceImpl.add("Programma");
        outputServiceImpl.add("Capitolo di entrata collegato");
        outputServiceImpl.add("Descrizione capitolo di entrata collegato");
        outputServiceImpl.add("Totale impegni/accert. progetto/capitolo anni precedenti");
        outputServiceImpl.add("Assestato " + thisYear.toString());
        outputServiceImpl.add("Impegnato/accert. " + thisYear.toString());
        int anno = thisYear.getValue() + 1;
        outputServiceImpl.add("Assestato " + anno);
        outputServiceImpl.add("Impegnato/accert. " + anno);
        anno = thisYear.getValue() + 2;
        outputServiceImpl.add("Assestato " + anno);
        outputServiceImpl.add("Impegnato/accert. " + anno);
        outputServiceImpl.add("Totale assestato");
        outputServiceImpl.add("Totale impegni/accert. progetto/capitolo");
        outputServiceImpl.add("Note impegni");
        outputServiceImpl.addRow();
    }
}
