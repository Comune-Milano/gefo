package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.*;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.*;
import it.dmi.dmifonbe.dmifonpro.service.AvanzamentoService;
import it.dmi.dmifonbe.dmifonpro.service.SemaforoService;
import it.dmi.dmifonbe.dmifonpro.service.TipoFinanziamentoService;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class AvanzamentoServiceImpl implements AvanzamentoService {

    @Autowired
    AvanzamentoRepository avanzamentoRepository;

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

    @Autowired
    FiltroProgetti filtroProgetti;

    @Value("${dtInizioAva}")
    String dtInizioAva;

    @Value("${dtInizioAvaPre}")
    String dtInizioAvaPre;

    @Value("${dtFineAva}")
    String dtFineAva;

    @Value("${dtFineAvaPre}")
    String dtFineAvaPre;

    @Value("${ricercaAvanzamentiBase}")
    String ricercaAvanzamentiBase;

    @Value("${ricercaAvanzamentiCondIdPro}")
    String ricercaAvanzamentiCondIdPro;

    @Value("${ricercaAvanzamentiCondLivPro1}")
    String ricercaAvanzamentiCondLivPro1;

    @Value("${ricercaAvanzamentiCondLivPro2}")
    String ricercaAvanzamentiCondLivPro2;

    @Value("${ricercaAvanzamentiCondLivPro3}")
    String ricercaAvanzamentiCondLivPro3;

    @Value("${ricercaAvanzamentiCondTipFin}")
    String ricercaAvanzamentiCondTipFin;

    @Value("${ricercaAvanzamentiFine}")
    String ricercaAvanzamentiFine;

    @Value("${ricercaAvanzamentiCondTipAva1}")
    String ricercaAvanzamentiCondTipAva1;

    @Value("${ricercaAvanzamentiCondTipAva2}")
    String ricercaAvanzamentiCondTipAva2;

    @Value("${ricercaAvanzamentiDirezione}")
    String ricercaAvanzamentiDirezione;

    @Override
    @Transactional
    public AvanzamentoDetail getAvanzamento(Integer id) throws MicroServicesException {
        AvanzamentoDetail result = new AvanzamentoDetail();
        Optional<ProAva> ava = avanzamentoRepository.findById(id);
        if (ava.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            Hibernate.initialize(ava.get().getProProByIdPro());
            Hibernate.initialize(ava.get().getProLisvalByIdLisvalfasint());
            Hibernate.initialize(ava.get().getProLisvalByIdLisvallivcri());
            Hibernate.initialize(ava.get().getProLisvalByIdLisvaltipapp());
            Hibernate.initialize(ava.get().getProLisvalByIdLisvalstaant());
            Hibernate.initialize(ava.get().getProFasById());
            result.setAvanzamento(ava.get());
            if (ava.get().getProProByIdPro().getIdPropad() != null) {
                Optional<ProPro> progettoPadreOpt = progettoRepository.findById(ava.get().getProProByIdPro().getIdPropad().intValue());
                if (progettoPadreOpt.isPresent()) {
                    result.setProgettoPadreLabel(progettoPadreOpt.get().getCodpro() + " - " + progettoPadreOpt.get().getDespro());
                } else throw new MicroServicesException(
                    ErrorMessages.NO_DATA_FOUND_PARENT_PRO.getUserMessage(),
                    ErrorMessages.NO_DATA_FOUND_PARENT_PRO.getCode()
                );
            } else result.setProgettoPadreLabel(" - ");
            List<ProFas> fasi = ava.get().getProFasById();
            if (!fasi.isEmpty()) {
                List<FaseDetail> fasiDetailList = new ArrayList<>();
                for (ProFas fase : fasi) {
                    FaseDetail faseDetail = new FaseDetail();
                    faseDetail.setFase(fase);
                    Optional<ProTipfas> tipfasOptional = tipFasRepository.findById(fase.getIdTipfas().intValue());
                    tipfasOptional.ifPresent(faseDetail::setTipfas);
                    faseDetail.setSemaforoFase(semaforoService.getSemaforoFase(fase.getId()));
                    faseDetail.setSemaforoMilestone(semaforoService.getSemaforoMilestoneFase(fase.getId()));
                    fasiDetailList.add(faseDetail);
                }
                result.setFasi(fasiDetailList);
                result.setDatinilav(this.getDataInizioAvanzamento(id));
                result.setDatfinlav(this.getDataFineAvanzamento(id));
            }
            return result;
        }
    }

    @Override
    public ProAva inserisciAvanzamento(ProAva avaDaInserire) throws MicroServicesException {
        this.checksAvanzamento(avaDaInserire, false);
        this.setDefaultValuesForProAva(avaDaInserire);
        utilService.setInfoInsertRow(avaDaInserire);
        ProAva avanzamentoInserito = avanzamentoRepository.saveAndFlush(avaDaInserire);
        this.inserisciVersionePrecedente(avanzamentoInserito.getId());
        return avanzamentoInserito;
    }

    public void inserisciVersionePrecedente(Integer idStoricoDaInserire) {
        ProAva avaStorico = avanzamentoRepository.findById(idStoricoDaInserire).get();
        Long nroVersion = avanzamentoRepository.findMaxNroVersionByIdPro(avaStorico.getIdPro());
        ProAva storico = ProAva.generateCopy(avaStorico);
        storico.setNrover(nroVersion + 1);
        utilService.setInfoInsertRow(storico);
        ProAva storicoInserito = avanzamentoRepository.saveAndFlush(storico);
        List<ProFas> proFasList = faseRepository.findByIdAva(idStoricoDaInserire.longValue());
        List<ProFas> fasiStoricoDaSalvare = new ArrayList<>();
        if (!proFasList.isEmpty()) {
            for (ProFas fase : proFasList) {
                ProFas faseStorico = ProFas.generateCopy(fase);
                faseStorico.setIdAva((long) storicoInserito.getId());
                utilService.setInfoInsertRow(faseStorico);
                fasiStoricoDaSalvare.add(faseStorico);
            }
            faseRepository.saveAllAndFlush(fasiStoricoDaSalvare);
        }
    }

    @Override
    public void creaFasiAvanzamento(Integer idAva) throws MicroServicesException {
        Optional<ProAva> proAvaOptional = avanzamentoRepository.findById(idAva);
        if (proAvaOptional.isPresent()) {
            ProAva proAva = proAvaOptional.get();
            if (proAva.getNrover() != 0) throw new MicroServicesException(
                ErrorMessages.PROAVA_PREV_VERSION_EDIT.getUserMessage(),
                ErrorMessages.PROAVA_PREV_VERSION_EDIT.getCode()
            );
            List<ProDettipolfas> proDettipolfasList = dettippolfasRepository.findByIdLisvaltipolfas(proAva.getIdlisvaltipolfas());
            List<ProFas> proFasToSave = new ArrayList<>();
            for (ProDettipolfas proDettipolfas : proDettipolfasList) {
                if (
                    faseRepository.findByIdAvaAndIdTipfas(idAva.longValue(), proDettipolfas.getIdTipfas()).isPresent()
                ) throw new MicroServicesException(
                    ErrorMessages.FAS_ALREADY_EXIST.getUserMessage(),
                    ErrorMessages.FAS_ALREADY_EXIST.getCode()
                );
                ProFas proFas = new ProFas();
                proFas.setIdAva(idAva.longValue());
                proFas.setIdTipfas(proDettipolfas.getIdTipfas());
                try {
                    //imposto anche una data di origine inizio e fine
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar now = Calendar.getInstance();
                    int intCurrentYear = now.get(Calendar.YEAR);
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    //preferiscono impostare 31/12/3000
                    proFas.setDtainiori(dateFormat.parse("31/12/3000"));
                    proFas.setDtafinori(dateFormat.parse("31/12/3000"));
                    //proFas.setDtainiori(dateFormat.parse("31/12/" + intCurrentYear));
                    //proFas.setDtafinori(dateFormat.parse("31/12/" + intCurrentYear));
                } catch (Exception e) {}
                proFasToSave.add(proFas);
            }
            faseRepository.saveAllAndFlush(proFasToSave);
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }

    @Override
    public void modificaAvanzamento(AvanzamentoDetail avaDaModificare) throws MicroServicesException {
        this.checksAvanzamento(avaDaModificare.getAvanzamento(), true);
        Optional<ProAva> proAvaOptionalOriginale = avanzamentoRepository.findById(avaDaModificare.getAvanzamento().getId());
        if (proAvaOptionalOriginale.isPresent()) {
            avaDaModificare.getAvanzamento().setDtarilava(new Date());
            avaDaModificare.getAvanzamento().setUsrCreate(proAvaOptionalOriginale.get().getUsrCreate());
            avaDaModificare.getAvanzamento().setDtCreate(proAvaOptionalOriginale.get().getDtCreate());
            utilService.setInfoUpdateRow(avaDaModificare.getAvanzamento());
            this.avanzamentoRepository.saveAndFlush(avaDaModificare.getAvanzamento());
            if (!avaDaModificare.getFasi().isEmpty()) this.salvaFasi(avaDaModificare.getFasi());
            this.inserisciVersionePrecedente(avaDaModificare.getAvanzamento().getId());
        }
    }

    private void salvaFasi(List<FaseDetail> fasiDetail) throws MicroServicesException {
        List<ProFas> fasi = new ArrayList<>();
        for (FaseDetail faseDetail : fasiDetail) {
            this.checkSalvaFasi(faseDetail.getFase());
            fasi.add(faseDetail.getFase());
        }
        faseRepository.saveAllAndFlush(fasi);
    }

    private void checkSalvaFasi(ProFas fase) throws MicroServicesException {
        if (fase.getDtainiori() == null || fase.getDtafinori() == null) throw new MicroServicesException(
            ErrorMessages.MANDATORY_FIELD.getUserMessage() + "date origine",
            ErrorMessages.MANDATORY_FIELD.getCode()
        );
        if (!avanzamentoRepository.existsById(fase.getIdAva().intValue())) throw new MicroServicesException(
            ErrorMessages.MANDATORY_FIELD.getUserMessage() + "avanzamento",
            ErrorMessages.MANDATORY_FIELD.getCode()
        );
        if (!tipFasRepository.existsById(fase.getIdTipfas().intValue())) throw new MicroServicesException(
            ErrorMessages.MANDATORY_FIELD.getUserMessage() + "tipo fase",
            ErrorMessages.MANDATORY_FIELD.getCode()
        );
    }

    private void checksAvanzamento(ProAva ava, boolean isEdit) throws MicroServicesException {
        if (!isEdit) {
            Optional<ProAva> proAva = avanzamentoRepository.findByNroverAndIdPro(0L, ava.getIdPro());
            if (proAva.isPresent()) throw new MicroServicesException(
                ErrorMessages.PROAVA_AlREADY_EXIST.getUserMessage(),
                ErrorMessages.PROAVA_AlREADY_EXIST.getCode()
            );
        } else if (ava.getNrover() != 0) throw new MicroServicesException(
            ErrorMessages.PROAVA_PREV_VERSION_EDIT.getUserMessage(),
            ErrorMessages.PROAVA_PREV_VERSION_EDIT.getCode()
        );

        if (ava.getDesstaava() == null || ava.getDesstaava().isBlank()) throw new MicroServicesException(
            ErrorMessages.DESSTAAVA_BLANK.getUserMessage(),
            ErrorMessages.DESSTAAVA_BLANK.getCode()
        );

        Optional<ProPro> proPro = progettoRepository.findById(ava.getIdPro().intValue());
        if (!proPro.isPresent()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND_PRO.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND_PRO.getCode()
        );

        if (ava.getIdLisvalfasint() != null && ava.getIdLisvalfasint() != 0) {
            if (
                !this.getProLisValRecord(ava.getIdLisvalfasint().intValue()).getTiplis().equals("AVAFASINT")
            ) throw new MicroServicesException(ErrorMessages.MISMATCH_TIPLIS.getUserMessage(), ErrorMessages.MISMATCH_TIPLIS.getCode());
        } else throw new MicroServicesException(ErrorMessages.MANDATORY_FIELD.getUserMessage(), ErrorMessages.MANDATORY_FIELD.getCode());

        if (ava.getIdLisvallivcri() != null && ava.getIdLisvallivcri() != 0) {
            if (!this.getProLisValRecord(ava.getIdLisvallivcri().intValue()).getTiplis().equals("AVACRI")) throw new MicroServicesException(
                ErrorMessages.MISMATCH_TIPLIS.getUserMessage(),
                ErrorMessages.MISMATCH_TIPLIS.getCode()
            );
        } else throw new MicroServicesException(ErrorMessages.MANDATORY_FIELD.getUserMessage(), ErrorMessages.MANDATORY_FIELD.getCode());

        if (ava.getIdLisvaltipapp() != null && ava.getIdLisvaltipapp() != 0) {
            if (
                !this.getProLisValRecord(ava.getIdLisvaltipapp().intValue()).getTiplis().equals("AVATIPAPP")
            ) throw new MicroServicesException(ErrorMessages.MISMATCH_TIPLIS.getUserMessage(), ErrorMessages.MISMATCH_TIPLIS.getCode());
        } else throw new MicroServicesException(ErrorMessages.MANDATORY_FIELD.getUserMessage(), ErrorMessages.MANDATORY_FIELD.getCode());

        if (ava.getIdLisvalstaant() != null && ava.getIdLisvalstaant() != 0) {
            if (
                !this.getProLisValRecord(ava.getIdLisvalstaant().intValue()).getTiplis().equals("AVASTAANT")
            ) throw new MicroServicesException(ErrorMessages.MISMATCH_TIPLIS.getUserMessage(), ErrorMessages.MISMATCH_TIPLIS.getCode());
        } else throw new MicroServicesException(ErrorMessages.MANDATORY_FIELD.getUserMessage(), ErrorMessages.MANDATORY_FIELD.getCode());

        if (ava.getIdlisvaltipolfas() != null && ava.getIdlisvaltipolfas() != 0) {
            if (
                !this.getProLisValRecord(ava.getIdlisvaltipolfas().intValue()).getTiplis().equals("TIPOLFAS")
            ) throw new MicroServicesException(ErrorMessages.MISMATCH_TIPLIS.getUserMessage(), ErrorMessages.MISMATCH_TIPLIS.getCode());
        } else throw new MicroServicesException(ErrorMessages.MANDATORY_FIELD.getUserMessage(), ErrorMessages.MANDATORY_FIELD.getCode());
    }

    private ProLisval getProLisValRecord(int idLisvalfasint) throws MicroServicesException {
        Optional<ProLisval> proLisvalOpt = proLisValRepository.findById(idLisvalfasint);
        if (proLisvalOpt.isPresent()) {
            return proLisvalOpt.get();
        } else throw new MicroServicesException(
            ErrorMessages.LISVAL_NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.LISVAL_NO_DATA_FOUND.getCode()
        );
    }

    private void setDefaultValuesForProAva(ProAva avaDaInserire) {
        avaDaInserire.setNrover(0L);
        avaDaInserire.setDtarilava(new Date());
    }

    @Override
    @Transactional
    public void cancellaFase(Integer idFase) throws MicroServicesException {
        Optional<ProFas> proFasOpt = faseRepository.findById(idFase);
        if (proFasOpt.isPresent()) {
            ProFas proFas = proFasOpt.get();
            Optional<ProAva> proAvaOpt = avanzamentoRepository.findById(proFas.getIdAva().intValue());
            if (proAvaOpt.isPresent() && proAvaOpt.get().getNrover() != 0) throw new MicroServicesException(
                ErrorMessages.DELETE_NO_CORRECT_VERSION.getUserMessage(),
                ErrorMessages.DELETE_NO_CORRECT_VERSION.getCode()
            );
            Hibernate.initialize(proFas.getProMilsById());
            if (!proFas.getProMilsById().isEmpty()) throw new MicroServicesException(
                ErrorMessages.PROFAS_DELETE_NOT_ALLOWED.getUserMessage(),
                ErrorMessages.PROFAS_DELETE_NOT_ALLOWED.getCode()
            ); else faseRepository.deleteById(idFase);
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }

    @Override
    @Transactional
    public void cancellaAvanzamento(Integer idAva) throws MicroServicesException {
        Optional<ProAva> proAvaOpt = avanzamentoRepository.findById(idAva);
        if (proAvaOpt.isPresent()) {
            ProAva proAva = proAvaOpt.get();
            if (proAva.getNrover() != 0) throw new MicroServicesException(
                ErrorMessages.DELETE_NO_CORRECT_VERSION.getUserMessage(),
                ErrorMessages.DELETE_NO_CORRECT_VERSION.getCode()
            );
            Hibernate.initialize(proAva.getProFasById());
            if (!proAva.getProFasById().isEmpty()) throw new MicroServicesException(
                ErrorMessages.PROAVA_DELETE_NOT_ALLOWED.getUserMessage(),
                ErrorMessages.PROAVA_DELETE_NOT_ALLOWED.getCode()
            ); else avanzamentoRepository.deleteById(idAva);
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }

    @Override
    public AvanzamentoResponse ricercaAvanzamenti(AvanzamentoRicerca avaRic, Integer idUteRuo) throws MicroServicesException {
        filtroProgetti.generateFiltroProgetti(idUteRuo);
        AvanzamentoResponse response = new AvanzamentoResponse();
        if (avaRic.checkNull() && !filtroProgetti.isFiltered()) {
            checkMaxRecords(avanzamentoRepository.count(), response);
            Pageable limit = PageRequest.of(0, this.getMaxRecords());
            Page<ProAva> results = avanzamentoRepository.findAll(limit);
            this.valorizzaResultRicerca(results.getContent(), response);
        } else {
            String queryStr = ricercaAvanzamentiBase;
            if (avaRic.getIdProgetto() > 0) queryStr += " " + ricercaAvanzamentiCondIdPro;
            if (filtroProgetti.isFiltered() || avaRic.getDirezione() != 0) queryStr += " " + ricercaAvanzamentiDirezione;
            if (avaRic.getTipLiv() != null && !avaRic.getTipLiv().isBlank()) if (
                avaRic.getTipLiv().equals("01") || avaRic.getTipLiv().equals("02") || avaRic.getTipLiv().equals("03")
            ) queryStr += " " + ricercaAvanzamentiCondLivPro1; else if (avaRic.getTipLiv().equals("BA")) queryStr +=
                " " + ricercaAvanzamentiCondLivPro2; else if (avaRic.getTipLiv().equals("PB")) queryStr +=
                " " + ricercaAvanzamentiCondLivPro3;
            if (avaRic.getTipoFinanziamento() > 0) queryStr += " " + ricercaAvanzamentiCondTipFin;
            if (avaRic.getTipoAvanzamento() != null && !avaRic.getTipoAvanzamento().isBlank()) if (
                avaRic.getTipoAvanzamento().equals("V")
            ) queryStr += " " + ricercaAvanzamentiCondTipAva1; else queryStr += " " + ricercaAvanzamentiCondTipAva2;

            queryStr += " " + ricercaAvanzamentiFine;

            Query query = em.createQuery(queryStr);
            if (avaRic.getIdProgetto() > 0) query.setParameter("ID_PRO", avaRic.getIdProgetto());
            if (filtroProgetti.isFiltered()) query.setParameter("DIR", filtroProgetti.getIdDirezione()); else if (
                avaRic.getDirezione() != 0
            ) query.setParameter("DIR", avaRic.getDirezione());
            if (avaRic.getTipLiv() != null && !avaRic.getTipLiv().isBlank()) if (
                avaRic.getTipLiv().equals("01") || avaRic.getTipLiv().equals("02") || avaRic.getTipLiv().equals("03")
            ) query.setParameter("TIPLIV", Integer.parseInt(avaRic.getTipLiv()));
            if (avaRic.getTipoFinanziamento() > 0) query.setParameter("ID_TIPFIN", avaRic.getTipoFinanziamento());

            List<ProAva> results = query.getResultList();
            if (!results.isEmpty()) valorizzaResultRicerca(results, response);
        }
        return response;
    }

    @Override
    public List<ProTipfas> getTipoFase() throws MicroServicesException {
        List<ProTipfas> result = tipFasRepository.findAll();
        if (result.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
        return result;
    }

    private Date getDataInizioAvanzamento(Integer id) {
        Date data = this.getInizioFineAvanzamento(dtInizioAva, id);
        Date dataPre = this.getInizioFineAvanzamento(dtInizioAvaPre, id);
        if (dataPre != null && (data == null || dataPre.before(data))) return dataPre; else return data;
    }

    private Date getDataFineAvanzamento(Integer id) {
        Date data = this.getInizioFineAvanzamento(dtFineAva, id);
        Date dataPre = this.getInizioFineAvanzamento(dtFineAvaPre, id);
        if (dataPre != null && (data == null || dataPre.after(data))) return dataPre; else return data;
    }

    private Date getInizioFineAvanzamento(String query, Integer idAva) {
        Query res = em.createNativeQuery(query);
        res.setParameter(1, idAva);
        Date data = (Date) res.getSingleResult();
        return data;
    }

    private void checkMaxRecords(Long countResult, AvanzamentoResponse response) {
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

    private void valorizzaResultRicerca(List<ProAva> results, AvanzamentoResponse response) throws MicroServicesException {
        for (ProAva proava : results) {
            AvaResponse proAvaElement = new AvaResponse();
            proAvaElement.setAvanzamento(proava);
            proAvaElement.setLivelloCriticita(proLisValRepository.findById(proava.getIdLisvallivcri().intValue()).get());
            proAvaElement.setFaseIntervento(proLisValRepository.findById(proava.getIdLisvalfasint().intValue()).get());
            ProPro progetto = progettoRepository.findById(proava.getIdPro().intValue()).get();
            proAvaElement.setCodDesProgetto(progetto.getCodpro() + "-" + progetto.getDespro());
            proAvaElement.setSemaforoAvanzamento(semaforoService.getSemaforoAvanzamento(proava.getId()));
            response.addAvanzamento(proAvaElement);
        }
    }
}
