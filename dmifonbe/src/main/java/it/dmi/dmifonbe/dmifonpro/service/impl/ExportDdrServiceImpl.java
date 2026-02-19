package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonamm.service.impl.ElaborazioneServiceImpl;
import it.dmi.dmifonbe.dmifonamm.service.impl.OutputServiceImpl;
import it.dmi.dmifonbe.dmifonpro.entities.ProDdr;
import it.dmi.dmifonbe.dmifonpro.entities.ProDdra;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.*;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.ExportDdrService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class ExportDdrServiceImpl implements ExportDdrService {

    @Autowired
    ResponsabileServiceImpl responsabileServiceImpl;
    @Autowired
    ProgettoServiceImpl progettoServiceImpl;

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
    FiltroProgetti filtroProgetti;

    @Autowired
    TipoInfoAggiuntiveRepository tipoInfoAggiuntiveRepository;

    @Autowired
    TipoResponsabileRepository tipoResponsabileRepository;

    @Autowired
    AvanzamentoRepository avanzamentoRepository;

    @Autowired
    EntitaContabileRepository entitaContabileRepository;

    //miei
    @Autowired
    EntityManager em;


    @Autowired
    UtilService utilService;

    @Autowired
    DetConRepository detConRepository;
    @Autowired
    DdraRepository ddraRepository;

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

    @Value("${ricercaDdrBase}")
    String ricercaDdrBase;


    @Value("${ricercaDdrCondIdPro}")
    String ricercaDdrCondIdPro;
    @Value("${ricercaDdrFine}")
    String ricercaDdrFine;

    @Autowired
    OutputServiceImpl outputServiceImpl;
    @Autowired
    ElaborazioneServiceImpl elaborazioneServiceImpl;

    @Override
    //@Transactional
    public ExportDdrResponse exportDdr(ExportDdrRicerca parametri, Integer idUteRuo) throws MicroServicesException {
        filtroProgetti.generateFiltroProgetti(idUteRuo);
        ExportDdrResponse response = new ExportDdrResponse();
        Integer idElda=elaborazioneServiceImpl.start("Export ddr");
        outputServiceImpl.start(0, "Export ddr", "csv");
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
        ExportDdrDettaglio expDdrDet;
        if (!results.isEmpty()) for (ProPro propro : results) {
            progettoAndProgettoPadre = utilService.getProgettoAndProgettoPadre(Integer.valueOf(propro.getId()));
            progettoDetail = progettoServiceImpl.getProgetto(propro.getId());
            //recupero le ddr
            String queryStrDdr = ricercaDdrBase;
            queryStrDdr += " " + ricercaDdrCondIdPro;
            queryStrDdr += " " + ricercaDdrFine;
            Query queryDdr = em.createQuery(queryStrDdr);
            queryDdr.setParameter("ID_PRO", propro.getId());
            List<ProDdr> resultsDdr = queryDdr.getResultList();
            if (!resultsDdr.isEmpty()) for (ProDdr proDdr : resultsDdr) {
                expDdrDet = new ExportDdrDettaglio();
                //Hibernate.initialize(proDdr.getProDdraByIdDdra());
                if (proDdr.getIdDdra() != null) {
                    Optional<ProDdra> proDdra = ddraRepository.findById(Integer.valueOf(proDdr.getIdDdra().intValue()));
                    if (!proDdra.isEmpty()) {
                        expDdrDet.setProDdra(proDdra.get());
                    } else
                        expDdrDet.setProDdra(null);
                } else
                    expDdrDet.setProDdra(null);
                expDdrDet.setProPro(propro);
                expDdrDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                expDdrDet.setProgettoDetail(progettoDetail);
                expDdrDet.setProDdr(proDdr);
                dettaglio(parametri, expDdrDet);
            }
        }
        response.setFileName(outputServiceImpl.getFileName());
        response.setData(outputServiceImpl.getBytes());
        //non va se vengono richiesti due export assieme
        //outputServiceImpl.salvaOutputDB(idElda);
        elaborazioneServiceImpl.stop(idElda);
        return response;
    }



    private void dettaglio(ExportDdrRicerca parametri, ExportDdrDettaglio expDdrDet) throws MicroServicesException {
        outputServiceImpl.newRow();
        outputServiceImpl.add(expDdrDet.getProgettoDetail().getProgetto().getCodpro());
        outputServiceImpl.add(expDdrDet.getProgettoDetail().getProgetto().getDespro());
        outputServiceImpl.add(expDdrDet.getProgettoAndProgettoPadre().getProgettoPadreLabel());
        if (expDdrDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin() != null) {
            outputServiceImpl.add(expDdrDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin().getCodtipfin());
            outputServiceImpl.add(expDdrDet.getProgettoDetail().getProgetto().getProTipfinByIdTipfin().getDestipfin());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        outputServiceImpl.add(expDdrDet.getProDdr().getCodddr());
        outputServiceImpl.add(expDdrDet.getProDdr().getDesddr());
        outputServiceImpl.add(expDdrDet.getProDdr().getDtaddr());
        outputServiceImpl.add(expDdrDet.getProDdr().getImpddr());
        outputServiceImpl.add(expDdrDet.getProDdr().getImpamm());
        outputServiceImpl.add(expDdrDet.getProDdr().getImptra());
        outputServiceImpl.add(expDdrDet.getProDdr().getImpinc());
        outputServiceImpl.add(expDdrDet.getProDdr().getPrgrev());
        outputServiceImpl.add(expDdrDet.getProDdr().getNotddr());
        if (expDdrDet.getProDdra()!=null){
            outputServiceImpl.add(expDdrDet.getProDdra().getCodddra());
            outputServiceImpl.add(expDdrDet.getProDdra().getDesddra());
            outputServiceImpl.add(expDdrDet.getProDdra().getDtaddra());
            outputServiceImpl.add(expDdrDet.getProDdra().getNotddra());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        outputServiceImpl.addRow();
    }

    private void intestazione(ExportDdrRicerca parametri) throws MicroServicesException {
        outputServiceImpl.newRow();
        outputServiceImpl.add("Codice progetto");
        outputServiceImpl.add("Descrizione progetto");
        outputServiceImpl.add("Progetto padre");
        outputServiceImpl.add("Codice tipo fin.");
        outputServiceImpl.add("Descr. Tipo fin.");
        outputServiceImpl.add("Codice DDR");
        outputServiceImpl.add("Descr. DDR");
        outputServiceImpl.add("Data DDR");
        outputServiceImpl.add("Importo DDR");
        outputServiceImpl.add("Importo ammissibile DDR");
        outputServiceImpl.add("Importo trasmesso DDR");
        outputServiceImpl.add("Importo incassato DDR");
        outputServiceImpl.add("Progressivo riscossione");
        outputServiceImpl.add("Note DDR");
        outputServiceImpl.add("Codice DDRA");
        outputServiceImpl.add("Descr. DDRA");
        outputServiceImpl.add("Data DDRA");
        outputServiceImpl.add("Note DDRA");
        outputServiceImpl.addRow();
    }
}
