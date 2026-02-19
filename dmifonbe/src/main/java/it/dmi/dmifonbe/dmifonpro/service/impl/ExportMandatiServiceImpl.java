package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonamm.service.impl.OutputServiceImpl;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.*;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.ExportMandatiService;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class ExportMandatiServiceImpl implements ExportMandatiService {

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
    FiltroProgetti filtroProgetti;

    @Autowired
    TipoInfoAggiuntiveRepository tipoInfoAggiuntiveRepository;

    @Autowired
    TipoResponsabileRepository tipoResponsabileRepository;

    @Autowired
    AvanzamentoRepository avanzamentoRepository;

    @Autowired
    EntitaContabileRepository entitaContabileRepository;

    @Autowired
    MandatiServiceImpl mandatiServiceImpl;

    //miei
    @Autowired
    EntityManager em;

    @Autowired
    ImpegniProgettoServiceImpl impegniProgettoServiceImpl;

    @Autowired
    UtilService utilService;

    @Autowired
    DetConRepository detConRepository;

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
    private OutputServiceImpl outputServiceImpl;

    @Override
    //@Transactional
    public ExportMandatiResponse exportMandati(ExportMandatiRicerca parametri, Integer idUteRuo) throws MicroServicesException {
        filtroProgetti.generateFiltroProgetti(idUteRuo);
        ExportMandatiResponse response = new ExportMandatiResponse();
        outputServiceImpl.start(0, "Export mandati", "csv");
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
        List<MandatoResponse> datiMandatoList;
        ExportMandatiDettaglio expManDet;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dataPagamento;
        if (!results.isEmpty()) for (ProPro propro : results) {
            progettoAndProgettoPadre = utilService.getProgettoAndProgettoPadre(Integer.valueOf(propro.getId()));
            //recupero gli impegni
            resultEntCon =
                impegniProgettoServiceImpl.ricercaAccertamentiProgettoBase(
                    Integer.valueOf(propro.getId()),
                    Parameters.TIPENTIMPE.getValue()
                );
            if (!resultEntCon.isEmpty()) for (ProEntcon proEntcon : resultEntCon) {
                //recupero i mandati
                MandatoRicerca mandatoRicerca = new MandatoRicerca();
                mandatoRicerca.setAnno_mandato(parametri.getAnnEse().toString());
                mandatoRicerca.setId_impegno(proEntcon.getCodentcon());
                //se non esisto mandati la ricercaMandato scatena un'eccesione
                //la intercetto
                try {
                    datiMandatoList = mandatiServiceImpl.ricercaMandato(mandatoRicerca);
                } catch (Exception e) {
                    datiMandatoList = new ArrayList<>();
                }
                boolean mandatoDaAggiungere = true;
                if (!datiMandatoList.isEmpty()) for (MandatoResponse datiMandato : datiMandatoList) {
                    mandatoDaAggiungere = true;
                    try {
                        dataPagamento = (datiMandato.getProEntconman().getDtapag());
                        if (parametri.getDtaPagDa() != null && dataPagamento.before(parametri.getDtaPagDa())) mandatoDaAggiungere = false;
                        if (parametri.getDtaPagA() != null && dataPagamento.after(parametri.getDtaPagA())) mandatoDaAggiungere = false;
                    } catch (Exception e) {}
                    if (mandatoDaAggiungere) {
                        expManDet = new ExportMandatiDettaglio();
                        expManDet.setProPro(propro);
                        expManDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                        expManDet.setProEntcon(proEntcon);
                        expManDet.setDatiMandato(datiMandato);
                        dettaglio(parametri, expManDet);
                    }
                }
            }
        }
        response.setFileName(outputServiceImpl.getFileName());
        response.setData(outputServiceImpl.getBytes());
        return response;
    }

    private void dettaglio(ExportMandatiRicerca parametri, ExportMandatiDettaglio expImpDet) throws MicroServicesException {
        outputServiceImpl.newRow();
        outputServiceImpl.add(expImpDet.getProPro().getCodpro());
        outputServiceImpl.add(expImpDet.getProPro().getDespro());
        outputServiceImpl.add(expImpDet.getProgettoAndProgettoPadre().getProgettoPadreLabel());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getDesfat());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getRagsoc());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getCodfis());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getCodiva());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getTipatt());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getIdatt());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getId());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getIdimp());
        outputServiceImpl.add(expImpDet.getProEntcon().getDesimp());
        outputServiceImpl.add(expImpDet.getProEntcon().getImpass());
        outputServiceImpl.add(expImpDet.getProEntcon().getImpman());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getIdman());
        BigDecimal importo = BigDecimal.ZERO;
        if (expImpDet.getDatiMandato().getProEntconman().getImpman() != null) importo =
            (expImpDet.getDatiMandato().getProEntconman().getImpman());
        outputServiceImpl.add(importo);
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getCodcup());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getCodcig());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getDtareg());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getDtapag());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getBenragsoc());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getBencodfis());
        outputServiceImpl.add(expImpDet.getDatiMandato().getProEntconman().getBencodiva());
        outputServiceImpl.addRow();
    }

    private void intestazione(ExportMandatiRicerca parametri) throws MicroServicesException {
        outputServiceImpl.newRow();
        outputServiceImpl.add("Codice progetto");
        outputServiceImpl.add("Descrizione progetto");
        outputServiceImpl.add("Progetto padre");
        outputServiceImpl.add("Estremi fattura");
        outputServiceImpl.add("Ragione sociale");
        outputServiceImpl.add("Codice fiscale");
        outputServiceImpl.add("Partita IVA");
        outputServiceImpl.add("Tipo atto");
        outputServiceImpl.add("Estremi atto di liquidazione");
        outputServiceImpl.add("Estremi liquidazione");
        outputServiceImpl.add("Estremi impegno");
        outputServiceImpl.add("Descrizione impegno");
        outputServiceImpl.add("Assestato impegno");
        outputServiceImpl.add("Mandati impegno");
        outputServiceImpl.add("Estremi mandato");
        outputServiceImpl.add("Importo mandato");
        outputServiceImpl.add("CUP");
        outputServiceImpl.add("CIG");
        outputServiceImpl.add("Data registrazione mandato");
        outputServiceImpl.add("Data pagamento");
        outputServiceImpl.add("Beneficiario ragione sociale");
        outputServiceImpl.add("Beneficiario codice fiscale");
        outputServiceImpl.add("Beneficiario partita iva");

        outputServiceImpl.addRow();
    }
}
