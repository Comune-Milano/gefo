package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonamm.service.impl.OutputServiceImpl;
import it.dmi.dmifonbe.dmifonpro.entities.ProDetcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.*;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.ExportImpegniService;
import it.dmi.dmifonbe.model.EntityType;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.Totali;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class ExportImpegniServiceImpl implements ExportImpegniService {

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

    @Value("${ricercaDetcon}")
    String ricercaDetcon;


    @Autowired
    private OutputServiceImpl outputServiceImpl;

    @Override
    //@Transactional
    public ExportImpegniResponse exportImpegni(ExportImpegniRicerca parametri, Integer idUteRuo) throws MicroServicesException {
        filtroProgetti.generateFiltroProgetti(idUteRuo);
        ExportImpegniResponse response = new ExportImpegniResponse();
        outputServiceImpl.start(0, "Export impegni", "csv");
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
        ExportImpegniDettaglio expImpDet;
        BigDecimal impimppre = BigDecimal.ZERO;
        BigDecimal impaccpre = BigDecimal.ZERO;
        BigDecimal impmanpre = BigDecimal.ZERO;
        BigDecimal imprevpre = BigDecimal.ZERO;
        ProEntcon proEntconPre;
        if (!results.isEmpty()) for (ProPro propro : results) {
            progettoAndProgettoPadre = utilService.getProgettoAndProgettoPadre(Integer.valueOf(propro.getId()));
            //recupero gli accertamenti
            resultEntCon =
                impegniProgettoServiceImpl.ricercaAccertamentiProgettoBase(
                    Integer.valueOf(propro.getId()),
                    Parameters.TIPENTACCE.getValue(),
                    parametri.isFlgSolImpPro()
                );
            if (!resultEntCon.isEmpty()) for (ProEntcon proEntcon : resultEntCon) {
                expImpDet = new ExportImpegniDettaglio();
                expImpDet.setProPro(propro);
                expImpDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                expImpDet.setProEntcon(proEntcon);
                leggiInfo(expImpDet, parametri.isFlgSolImpPro());
                dettaglio(parametri, expImpDet);
            }
            //recupero i dati degli impegni ed accertamenti pregressi
            if (parametri.isFlgSolImpPro()) {
                impimppre = propro.getImpimppre();
                impaccpre = propro.getImpaccpre();
                impmanpre = propro.getImpmanpre();
                imprevpre = propro.getImprevpre();
            } else {
                Totali totali = calcolaTotali.getTotaliImpegniAndAccertamentiPregressi(EntityType.PRO.getValue(), propro.getId());
                impimppre = totali.getImpimp();
                impaccpre = totali.getImpacc();
                impmanpre = totali.getImpman();
                imprevpre = totali.getImprev();
            }
            //aggiungo una riga per gli eventuali accertamenti pregressi
            if (!(impaccpre.compareTo(BigDecimal.ZERO) == 0)) {
                expImpDet = new ExportImpegniDettaglio();
                expImpDet.setProPro(propro);
                expImpDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                proEntconPre = getProEntConPregressi(Parameters.TIPENTACCE.getValue());
                proEntconPre.setImpass(impaccpre);
                proEntconPre.setImpman(imprevpre);
                expImpDet.setProEntcon(proEntconPre);
                dettaglio(parametri, expImpDet);
            }
            //recupero gli impegni
            resultEntCon =
                impegniProgettoServiceImpl.ricercaAccertamentiProgettoBase(
                    Integer.valueOf(propro.getId()),
                    Parameters.TIPENTIMPE.getValue(),
                    parametri.isFlgSolImpPro()
                );
            if (!resultEntCon.isEmpty()) for (ProEntcon proEntcon : resultEntCon) {
                expImpDet = new ExportImpegniDettaglio();
                expImpDet.setProPro(propro);
                expImpDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                expImpDet.setProEntcon(proEntcon);
                leggiInfo(expImpDet, parametri.isFlgSolImpPro());
                dettaglio(parametri, expImpDet);
            }
            //aggiungo una riga per gli eventuali impegni pregressi
            if (!(impimppre.compareTo(BigDecimal.ZERO) == 0)) {
                expImpDet = new ExportImpegniDettaglio();
                expImpDet.setProPro(propro);
                expImpDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                proEntconPre = getProEntConPregressi(Parameters.TIPENTIMPE.getValue());
                proEntconPre.setImpass(impimppre);
                proEntconPre.setImpman(impmanpre);
                expImpDet.setProEntcon(proEntconPre);
                dettaglio(parametri, expImpDet);
            }
        }
        response.setFileName(outputServiceImpl.getFileName());
        response.setData(outputServiceImpl.getBytes());
        return response;
    }

    private ProEntcon getProEntConPregressi(String tipent) throws MicroServicesException {
        ProEntcon proEntcon = new ProEntcon();
        proEntcon.setTipent(tipent);
        proEntcon.setCodentcon("     ");
        if (tipent.equals(Parameters.TIPENTIMPE.getValue()))
            proEntcon.setDesimp(("Imppegni pregressi"));
        else
            proEntcon.setDesimp(("Accertamenti  pregressi"));
        proEntcon.setIdcro("");
        proEntcon.setDespdd("");
        proEntcon.setIdcap("");
        return proEntcon;
    }


    private void leggiInfo(ExportImpegniDettaglio expImpDet, boolean flgSolImpPro) throws MicroServicesException {
        List<ProDetcon> proDetconList;
        //cerco il dettaglio contabile tra il progetto ed i suoi figli
        Query res;
        res = em.createQuery(ricercaDetcon);
        res.setParameter(1, expImpDet.getProPro().getId());
        res.setParameter(2, expImpDet.getProEntcon().getTipent());
        res.setParameter(3, expImpDet.getProEntcon().getCodentcon());
        proDetconList = res.getResultList();
        /*
        //se ho ricliesto gli impegni del solo progetto recupero il dettaglio contabile
        proDetconList = detConRepository.findProDetconsByIdProAndTipentIgnoreCaseAndCodentcon(
            Long.valueOf(expImpDet.getProPro().getId()),
            expImpDet.getProEntcon().getTipent(),
            expImpDet.getProEntcon().getCodentcon()
        );
        */
        Optional<ProEntcon> proEntcon;
        if (!proDetconList.isEmpty()) {
            expImpDet.setProDetCon(proDetconList.get(0));
            if (expImpDet.getProDetCon() != null && expImpDet.getProDetCon().getIdTipimp() != null)
                expImpDet.setProTipImp(
                    tipoImportoRepository.findById(expImpDet.getProDetCon().getIdTipimp().intValue()).get()
                );
            else expImpDet.setProTipImp(null);
            if (expImpDet.getProDetCon().getTipent().equals(Parameters.TIPENTIMPE.getValue()) &&
                expImpDet.getProDetCon().getCodentcol() != null &&
                expImpDet.getProDetCon().getTipentcol() != null) {
                proEntcon = entitaContabileRepository.findByTipentAndCodentcon(
                    expImpDet.getProDetCon().getTipentcol(),
                    expImpDet.getProDetCon().getCodentcol());
                if (!proEntcon.isEmpty()) {
                    expImpDet.setProEntconCol(proEntcon.get());
                } else {
                    expImpDet.setProEntconCol(null);
                }
            } else {
                expImpDet.setProEntconCol(null);
            }
        } else {
            expImpDet.setProDetCon(null);
            expImpDet.setProEntconCol(null);
        }
    }

    private void dettaglio(ExportImpegniRicerca parametri, ExportImpegniDettaglio expImpDet) throws MicroServicesException {
        outputServiceImpl.newRow();
        outputServiceImpl.add(expImpDet.getProPro().getCodpro());
        outputServiceImpl.add(expImpDet.getProPro().getDespro());
        outputServiceImpl.add(expImpDet.getProgettoAndProgettoPadre().getProgettoPadreLabel());
        if (expImpDet.getProEntcon().getTipent().equals(Parameters.TIPENTACCE.getValue())) outputServiceImpl.add("Accertamento"); else outputServiceImpl.add(
            "Impegno"
        );
        String codice = expImpDet.getProEntcon().getCodentcon();
        outputServiceImpl.add(codice.substring(0, 4));
        outputServiceImpl.add(codice.substring(5));
        outputServiceImpl.add(expImpDet.getProEntcon().getDesimp());
        outputServiceImpl.add(expImpDet.getProEntcon().getIdcro());
        outputServiceImpl.add(expImpDet.getProEntcon().getDespdd());
        //metto cap altrimenti trasforma la stinga in data
        outputServiceImpl.add("Cap. " + expImpDet.getProEntcon().getIdcap());
        outputServiceImpl.add(expImpDet.getProEntcon().getImpass());
        outputServiceImpl.add(expImpDet.getProEntcon().getImpman());
        if (expImpDet.getProDetCon() != null && expImpDet.getProTipImp() != null) outputServiceImpl.add(
            expImpDet.getProTipImp().getDestipimp()
        ); else outputServiceImpl.add("");
        if (expImpDet.getProEntconCol() != null) {
            codice = expImpDet.getProEntconCol().getCodentcon();
            outputServiceImpl.add(codice.substring(0, 4));
            outputServiceImpl.add(codice.substring(5));
            outputServiceImpl.add(expImpDet.getProEntconCol().getImpass());
            outputServiceImpl.add(expImpDet.getProEntconCol().getImpman());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        if (expImpDet.getProDetCon() != null) outputServiceImpl.add(expImpDet.getProDetCon().getNotent()); else outputServiceImpl.add("");
        outputServiceImpl.addRow();
    }

    private void intestazione(ExportImpegniRicerca parametri) throws MicroServicesException {
        outputServiceImpl.newRow();
        outputServiceImpl.add("Codice progetto");
        outputServiceImpl.add("Descrizione progetto");
        outputServiceImpl.add("Progetto padre");
        outputServiceImpl.add("Tipo");
        outputServiceImpl.add("Anno");
        outputServiceImpl.add("Numero");
        outputServiceImpl.add("Descrizione");
        outputServiceImpl.add("Estremi crono");
        outputServiceImpl.add("Estremi PDD DD");
        outputServiceImpl.add("Estremi capitolo");
        outputServiceImpl.add("Importo accert./impegni");
        outputServiceImpl.add("Importo reversali/mandati");
        outputServiceImpl.add("Tipo importo accert./impegni");
        outputServiceImpl.add("Anno accert. collegato");
        outputServiceImpl.add("Num. accert. collegato");
        outputServiceImpl.add("Imp. accert. collegato");
        outputServiceImpl.add("Imp. rev. acc. collegato");
        outputServiceImpl.add("Note");
        outputServiceImpl.addRow();
    }
}
