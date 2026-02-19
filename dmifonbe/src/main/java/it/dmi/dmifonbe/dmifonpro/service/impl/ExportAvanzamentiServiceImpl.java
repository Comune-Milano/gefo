package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonamm.service.impl.OutputServiceImpl;
import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.*;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.dmifonpro.service.ExportAvanzamentiService;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class ExportAvanzamentiServiceImpl implements ExportAvanzamentiService {

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
    MilestoneServiceImpl milestoneServiceImpl;

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

    @Value("${ricercaAvanzamentiBase}")
    String ricercaAvanzamentiBase;


    @Value("${ricercaAvanzamentiCondIdPro}")
    String ricercaAvanzamentiCondIdPro;
    @Value("${ricercaAvanzamentiFine}")
    String ricercaAvanzamentiFine;

    @Autowired
    private OutputServiceImpl outputServiceImpl;

    @Override
    @Transactional
    public ExportAvanzamentiResponse exportAvanzamenti(ExportAvanzamentiRicerca parametri, Integer idUteRuo) throws MicroServicesException {
        filtroProgetti.generateFiltroProgetti(idUteRuo);
        ExportAvanzamentiResponse response = new ExportAvanzamentiResponse();
        outputServiceImpl.start(0, "Export avanzamenti", "csv");
        intestazione(parametri);
        String queryStr = ricercaExpProBase;
        if (parametri.getIdPro() > 0) queryStr += " " + ricercaExpProCondIdPro;
        if (parametri.getTipLiv() != null && !parametri.getTipLiv().isBlank()) if (
                parametri.getTipLiv().equals("01") || parametri.getTipLiv().equals("02") || parametri.getTipLiv().equals("03")
        ) queryStr += " " + ricercaExpProCondLivPro1;
        else if (parametri.getTipLiv().equals("BA")) queryStr +=
                " " + ricercaExpProCondLivPro2;
        else if (parametri.getTipLiv().equals("PB")) queryStr += " " + ricercaExpProCondLivPro3;
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
        if (filtroProgetti.isFiltered()) query.setParameter("DIR", filtroProgetti.getIdDirezione());
        else if (
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
        Optional<ProAva> proAva;
        AvanzamentoDetail avanzamentoDetail;
        ExportAvanzamentiDettaglio expAvanzamentiDet;
        Semaforo semaforo;
        MilestoneFaseDetail milestoneFaseDetail;
        if (!results.isEmpty()) for (ProPro propro : results) {
            progettoAndProgettoPadre = utilService.getProgettoAndProgettoPadre(Integer.valueOf(propro.getId()));
            //recupero l'avanzamento
            proAva = avanzamentoRepository.findByNroverAndIdPro(0L, Long.valueOf(propro.getId()));
            if (proAva.isPresent()) {
                avanzamentoDetail = avanzamentoServiceImpl.getAvanzamento(proAva.get().getId());
                semaforo = semaforoServiceImpl.getSemaforoAvanzamento(proAva.get().getId());
                if (avanzamentoDetail.getFasi() != null &&
                        !avanzamentoDetail.getFasi().isEmpty()) {
                    for (FaseDetail faseDetail : avanzamentoDetail.getFasi()) {
                        expAvanzamentiDet = new ExportAvanzamentiDettaglio();
                        expAvanzamentiDet.setProPro(propro);
                        expAvanzamentiDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                        expAvanzamentiDet.setAvanzamentoDetail(avanzamentoServiceImpl.getAvanzamento(proAva.get().getId()));
                        expAvanzamentiDet.setFaseDetail(faseDetail);
                        dettaglio(parametri, expAvanzamentiDet);
                        if (parametri.isFlgMil()) {
                            milestoneFaseDetail = milestoneServiceImpl.getMilestoneFase(faseDetail.getFase().getId());
                            if (milestoneFaseDetail != null && milestoneFaseDetail.getMilestone() != null) {
                                for (MilestoneDetail milestoneDetail : milestoneFaseDetail.getMilestone()) {
                                    expAvanzamentiDet = new ExportAvanzamentiDettaglio();
                                    expAvanzamentiDet.setProPro(propro);
                                    expAvanzamentiDet.setProgettoAndProgettoPadre(progettoAndProgettoPadre);
                                    expAvanzamentiDet.setAvanzamentoDetail(avanzamentoServiceImpl.getAvanzamento(proAva.get().getId()));
                                    expAvanzamentiDet.setFaseDetail(faseDetail);
                                    expAvanzamentiDet.setMilestoneDetail(milestoneDetail);
                                    dettaglio(parametri, expAvanzamentiDet);
                                }
                            }
                        }
                    }
                }
            }
        }
        response.setFileName(outputServiceImpl.getFileName());
        response.setData(outputServiceImpl.getBytes());
        return response;
    }


    private void dettaglio(ExportAvanzamentiRicerca parametri, ExportAvanzamentiDettaglio expAvanzamentiDet) throws MicroServicesException {
        outputServiceImpl.newRow();
        if (expAvanzamentiDet.getMilestoneDetail() == null)
            outputServiceImpl.add("Fase");
        else
            outputServiceImpl.add("Milestone");
        outputServiceImpl.add(expAvanzamentiDet.getProPro().getCodpro());
        outputServiceImpl.add(expAvanzamentiDet.getProPro().getDespro());
        outputServiceImpl.add(expAvanzamentiDet.getProgettoAndProgettoPadre().getProgettoPadreLabel());
        outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getDtarilava());
        outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getUsrLstupd());
        outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getDesstaava());
        outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getPrcava());
        outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getDatinilav());
        outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getDatfinlav());
        if (expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvalfasint() != null)
            outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvalfasint().getVallis());
        else
            outputServiceImpl.add("");
        if (expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvallivcri() != null)
            outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvallivcri().getVallis());
        else
            outputServiceImpl.add("");
        outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getNotcri());
        if (expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvaltipapp() != null)
            outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvaltipapp().getVallis());
        else
            outputServiceImpl.add("");
        outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getDesapp());
        if (expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvalstaant() != null)
            outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvalstaant().getVallis());
        else
            outputServiceImpl.add("");
        outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getImpant());
        if (expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvaltipolfas() != null)
            outputServiceImpl.add(expAvanzamentiDet.getAvanzamentoDetail().getAvanzamento().getProLisvalByIdLisvaltipolfas().getVallis());
        else
            outputServiceImpl.add("");
        if (expAvanzamentiDet.getFaseDetail().getFase().getProTipfasByIdTipfas()!=null)
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getFase().getProTipfasByIdTipfas().getDesfas());
        else
            outputServiceImpl.add("");
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getFase().getDtainiori());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getFase().getDtafinori());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getFase().getDtainipre());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getFase().getDtafinpre());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getFase().getDtainieff());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getFase().getDtafineff());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getSemaforoFase().getColore());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getSemaforoFase().getDescrizione());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getSemaforoFase().getPercentuale());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getSemaforoMilestone().getColore());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getSemaforoMilestone().getDescrizione());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getSemaforoMilestone().getPercentuale());
        outputServiceImpl.add(expAvanzamentiDet.getFaseDetail().getFase().getNotfas());
        if (parametri.isFlgMil() && expAvanzamentiDet.getMilestoneDetail() != null) {
            outputServiceImpl.add(expAvanzamentiDet.getMilestoneDetail().getMilestone().getDesmil());
            outputServiceImpl.add(expAvanzamentiDet.getMilestoneDetail().getMilestone().getDtaded());
            outputServiceImpl.add(expAvanzamentiDet.getMilestoneDetail().getMilestone().getDtaeff());
            if (expAvanzamentiDet.getMilestoneDetail().getSemaforoMilestone() != null) {
                outputServiceImpl.add(expAvanzamentiDet.getMilestoneDetail().getSemaforoMilestone().getColore());
                outputServiceImpl.add(expAvanzamentiDet.getMilestoneDetail().getSemaforoMilestone().getDescrizione());
            } else {
                outputServiceImpl.add("");
                outputServiceImpl.add("");
            }
            outputServiceImpl.add(expAvanzamentiDet.getMilestoneDetail().getMilestone().getNotmil());
        } else {
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
            outputServiceImpl.add("");
        }
        outputServiceImpl.addRow();
    }

    private void intestazione(ExportAvanzamentiRicerca parametri) throws MicroServicesException {
        outputServiceImpl.newRow();
        outputServiceImpl.add("Tipo riga");
        outputServiceImpl.add("Codice progetto");
        outputServiceImpl.add("Descrizione progetto");
        outputServiceImpl.add("Progetto padre");
        outputServiceImpl.add("Data rileva. avanzamento");
        outputServiceImpl.add("Utente aggiornamento");
        outputServiceImpl.add("Stato avanzamento tecnico");
        outputServiceImpl.add("Percentuale avanzamento");
        outputServiceImpl.add("Data inizio lavori");
        outputServiceImpl.add("Data fine lavori");
        outputServiceImpl.add("Fase Intervento");
        outputServiceImpl.add("Livello criticita'");
        outputServiceImpl.add("Note criticita'");
        outputServiceImpl.add("Tipo appalto");
        outputServiceImpl.add("N. appalto");
        outputServiceImpl.add("Stato anticipazione");
        outputServiceImpl.add("Importo anticip.");
        outputServiceImpl.add("Tipologia fasi");
        outputServiceImpl.add("Fase");
        outputServiceImpl.add("Data inizio origine");
        outputServiceImpl.add("Data fine origine");
        outputServiceImpl.add("Data inizio prevista");
        outputServiceImpl.add("Data fine prevista");
        outputServiceImpl.add("Data inizio effettiva");
        outputServiceImpl.add("Data fine effettiva");
        outputServiceImpl.add("Semaforo fase colore");
        outputServiceImpl.add("Semaforo fase descrizione");
        outputServiceImpl.add("Semaforo fase percentuale");
        outputServiceImpl.add("Semaforo milestones colore");
        outputServiceImpl.add("Semaforo milestones descrizione");
        outputServiceImpl.add("Semaforo milestones percentuale");
        outputServiceImpl.add("Responsabile/note");
        if (parametri.isFlgMil()) {
            outputServiceImpl.add("Milestone descr.");
            outputServiceImpl.add("Data Deadline");
            outputServiceImpl.add("Data effettiva");
            outputServiceImpl.add("Semaforo milestone colore");
            outputServiceImpl.add("Semaforo milestone descrizione");
            outputServiceImpl.add("Note milestone");
        }
        outputServiceImpl.addRow();
    }
}
