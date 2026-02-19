package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmDir;
import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.DirezioneRepository;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.*;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.*;
import it.dmi.dmifonbe.dmifonpro.service.*;
import it.dmi.dmifonbe.model.EntityType;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.Totali;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class ProgettoServiceImpl implements ProgettoService {

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    MacroProgettoRepository macroProgettoRepository;

    @Autowired
    StatoProgettoRepository statoProgettoRepository;

    @Autowired
    EntConRepository entConRepository;

    @Autowired
    InfoAggiuntiveRepository infoAggiuntiveRepository;

    @Autowired
    ImportoRepository importoRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    NilRepository nilRepository;

    @Autowired
    TematicaRepository tematicaRepository;

    @Autowired
    StatoFinanziamentoRepository statoFinanziamentoRepository;

    @Autowired
    DirezioneRepository direzioneRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    TipoInfoAggiuntiveRepository tipoInfoAggiuntiveRepository;

    @Autowired
    TipoImportoRepository tipoImportoRepository;

    @Autowired
    TipoFinanziamentoService tipoFinanziamentoService;

    @Autowired
    BandoRepository bandoRepository;

    @Autowired
    TipoFinanziamentoRepository tipoFinanziamentoRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    EntityManager em;

    @Autowired
    AggiornaLivelliProgetto aggiornaLivelliProgetto;

    @Autowired
    AggiornaErediProgetto aggiornaErediProgetto;

    @Autowired
    ProgettoDeleteServiceImpl progettoDeleteServiceImpl;

    @Autowired
    CalcolaTotaliService calcolaTotali;

    @Autowired
    TematicaService tematicaService;

    @Autowired
    FiltroProgetti filtroProgetti;

    @Value("${risorse}")
    String risorse;

    @Value("${risorsedicui}")
    String risorsedicui;

    @Value("${infagg}")
    String infagg;

    @Autowired
    ContabilitaService contabilitaService;

    @Override
    @Transactional
    public ProgettoDetail getProgetto(Integer id) throws MicroServicesException {
        ProgettoDetail response = new ProgettoDetail();
        Optional<ProPro> pro = progettoRepository.findById(id);
        if (pro.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            Hibernate.initialize(pro.get().getProBanByIdBan());
            Hibernate.initialize(pro.get().getProMacproByIdMacpro());
            Hibernate.initialize(pro.get().getProStaproByIdStapro());
            Hibernate.initialize(pro.get().getProStafinByIdStafin());
            Hibernate.initialize(pro.get().getProTipfinByIdTipfin());
            Hibernate.initialize(pro.get().getProTemByIdTem());
            Hibernate.initialize(pro.get().getProMunByIdMun());
            Hibernate.initialize(pro.get().getProNilByIdNil());
            response.setProgetto(pro.get());
            if (pro.get().getIdPropad() != null) {
                response.setProgettoPadre(progettoRepository.findById(Math.toIntExact(pro.get().getIdPropad())).get());
            }
            if (pro.get().getIdDir() != null) {
                Optional<AmmDir> direzioneOpt = direzioneRepository.findById(pro.get().getIdDir());
                if (direzioneOpt.isPresent()) {
                    response.setProgettoDirezione(direzioneOpt.get());
                }
            }
            if (pro.get().getCodset() != null) {
                Optional<ProEntcon> proEntconOpt = entConRepository.findByCodentconAndTipent(pro.get().getCodset(), "SETT");
                if (proEntconOpt.isPresent()) {
                    response.setProEntconSettore(proEntconOpt.get());
                } else {
                    ProEntcon proEntcon = new ProEntcon();
                    proEntcon.setCodentcon(pro.get().getCodset());
                    proEntcon.setDesimp("-");
                    response.setProEntconSettore(proEntcon);
                }
            }
            if (pro.get().getCodass() != null) {
                Optional<ProEntcon> proEntconOpt = entConRepository.findByCodentconAndTipent(pro.get().getCodass(), "ASSE");
                if (proEntconOpt.isPresent()) {
                    response.setProEntconAssessorato(proEntconOpt.get());
                } else {
                    ProEntcon proEntcon = new ProEntcon();
                    proEntcon.setCodentcon(pro.get().getCodass());
                    proEntcon.setDesimp("-");
                    response.setProEntconAssessorato(proEntcon);
                }
            }
            response.setRisorseLivelloBasso(calcolaTotali.getTotaliRisorse(EntityType.PRO.getValue(), id).getRisorse());
            response.setTotaliFondi(calcolaTotali.getTotaliFondi(EntityType.PRO.getValue(), id));
            response.setRisorse(this.obtainRisorseFromProgetto(pro.get().getId(), false));
            response.setRisorseDiCui(this.obtainRisorseFromProgetto(pro.get().getId(), true));
            response.setInfoAggiuntive(this.obtainInfoAggiuntiveFromProgetto(pro.get().getId()));
            return response;
        }
    }

    @Override
    public ProPro inserisciProgetto(ProPro proDaInserire) throws MicroServicesException {
        this.checksInserisciProgetto(proDaInserire);
        this.setDefaultValuesForProPro(proDaInserire);
        utilService.setInfoInsertRow(proDaInserire);
        ProPro progettoInserito = progettoRepository.saveAndFlush(proDaInserire);
        aggiornaLivelliProgetto.aggiornaCntlivinf();
        aggiornaLivelliProgetto.aggiornaLivpro();
        return progettoInserito;
    }

    @Override
    @Transactional
    public ProPro inserisciProgettoFiglio(Integer idPro) throws MicroServicesException, CloneNotSupportedException {
        Optional<ProPro> progettoPadreOpt = progettoRepository.findById(idPro);
        if (progettoPadreOpt.isPresent()) {
            ProPro progettoPadre, progettoFiglio;
            progettoPadre = progettoPadreOpt.get();
            progettoFiglio = ProPro.generateCopy(progettoPadre);
            progettoFiglio.setIdPropad(idPro.longValue());
            progettoFiglio.setId(0);
            progettoFiglio.setNotimp(null);
            if (
                progettoRepository.findProProByCodproIgnoreCase(progettoPadre.getCodpro() + "-xx").isPresent()
            ) throw new MicroServicesException(ErrorMessages.COD_ALREADY_EXIST.getUserMessage(), ErrorMessages.COD_ALREADY_EXIST.getCode());
            progettoFiglio.setCodpro(progettoPadre.getCodpro() + "-xx");
            return this.inserisciProgetto(progettoFiglio);
        } else throw new MicroServicesException(ErrorMessages.PROPADREF.getUserMessage(), ErrorMessages.PROPADREF.getCode());
    }

    @Override
    @Transactional
    public ProPro modificaImportoRisorseFiglio(Integer idPro) throws MicroServicesException, CloneNotSupportedException {
        Optional<ProPro> progettoFiglioOpt = progettoRepository.findById(idPro);
        if (progettoFiglioOpt.isPresent() && progettoFiglioOpt.get().getIdPropad() != null) {
            ProPro progettoFiglio = progettoFiglioOpt.get();
            Totali totaliFondi = calcolaTotali.getTotaliFondi(EntityType.PRO.getValue(), progettoFiglio.getIdPropad().intValue());
            //confronto le risorse dei fondi dei figli con quelle del progetto
            if (!(totaliFondi.getImprisfon().compareTo(totaliFondi.getImprisfonpro()) == 0)) {
                BigDecimal differnza = totaliFondi.getImprisfonpro().subtract(totaliFondi.getImprisfon());
                List<ProImp> risorseList = this.obtainRisorseFromProgetto(idPro.intValue(), false);
                if (!risorseList.isEmpty()) {
                    for (ProImp resource : risorseList) {
                        if (resource.getId() == 0) utilService.setInfoInsertRow(resource); else {
                            Optional<ProImp> importoOriginaleOpt = importoRepository.findById(resource.getId());
                            if (importoOriginaleOpt.isPresent()) {
                                resource.setUsrCreate(importoOriginaleOpt.get().getUsrCreate());
                                resource.setDtCreate(importoOriginaleOpt.get().getDtCreate());
                            }
                            utilService.setInfoUpdateRow(resource);
                        }
                        if (resource.getProTipimpByIdTipimp().getFlgtipimp().equals("F")) {
                            BigDecimal nuovoValore = new BigDecimal(resource.getImppro()).add(differnza);
                            if (!(nuovoValore.compareTo(BigDecimal.ZERO) == -1)) {
                                resource.setImppro(nuovoValore.doubleValue());
                                importoRepository.saveAndFlush(resource);
                            }
                        }
                    }
                }
            }
            return progettoFiglio;
        } else throw new MicroServicesException(ErrorMessages.PROPADREF.getUserMessage(), ErrorMessages.PROPADREF.getCode());
    }

    @Override
    public void cancellaProgetto(Integer id) throws MicroServicesException {
        progettoDeleteServiceImpl.cancellaProgettoTransactional(id);
        aggiornaLivelliProgetto.aggiornaCntlivinf();
        aggiornaLivelliProgetto.aggiornaLivpro();
    }

    @Override
    public ProPro modificaProgetto(ProgettoDetail proDaModificare) throws MicroServicesException {
        this.checksModificaProgettoDetail(proDaModificare);
        Optional<ProPro> progettoOriginaleOpt = progettoRepository.findById(proDaModificare.getProgetto().getId());
        if (progettoOriginaleOpt.isPresent()) {
            proDaModificare.getProgetto().setUsrCreate(progettoOriginaleOpt.get().getUsrCreate());
            proDaModificare.getProgetto().setDtCreate(progettoOriginaleOpt.get().getDtCreate());
        }
        utilService.setInfoUpdateRow(proDaModificare.getProgetto());
        ProPro progettoModificato = progettoRepository.saveAndFlush(proDaModificare.getProgetto());
        this.aggiornaInserisciInfoRigheDB(proDaModificare);
        if (!proDaModificare.getRisorse().isEmpty()) importoRepository.saveAllAndFlush(proDaModificare.getRisorse());
        if (!proDaModificare.getRisorseDiCui().isEmpty()) importoRepository.saveAllAndFlush(proDaModificare.getRisorseDiCui());
        if (!proDaModificare.getInfoAggiuntive().isEmpty()) infoAggiuntiveRepository.saveAllAndFlush(proDaModificare.getInfoAggiuntive());
        //verifico se ho modificato informazioni da aggiornare sui figli
        aggiornaErediProgetto.setVecchioNuovo(progettoOriginaleOpt.get(), proDaModificare.getProgetto());
        //solo se ho cambiato il progetto padre
        if (aggiornaErediProgetto.isAggProgettoPadre()) {
            aggiornaLivelliProgetto.aggiornaCntlivinf();
            aggiornaLivelliProgetto.aggiornaLivpro();
        }
        //se ho modificato il tipo finanziamento, macro,.... lo aggiorno sugli eredi
        if (aggiornaErediProgetto.isAggEredi()) aggiornaErediProgetto.aggiornaEredi();
        return progettoModificato;
    }

    @Override
    public ProPro modificaProgetto(ProPro proDaModificare) throws MicroServicesException {
        this.checksModificaProgetto(proDaModificare);
        Optional<ProPro> progettoOriginaleOpt = progettoRepository.findById(proDaModificare.getId());
        if (progettoOriginaleOpt.isPresent()) {
            proDaModificare.setUsrCreate(progettoOriginaleOpt.get().getUsrCreate());
            proDaModificare.setDtCreate(progettoOriginaleOpt.get().getDtCreate());
        }
        utilService.setInfoUpdateRow(proDaModificare);
        ProPro progettoModificato = progettoRepository.saveAndFlush(proDaModificare);
        //se ho modificato i valori degli impegni non è necessario aggiornare i livelli
        //aggiornaLivelliProgetto.aggiornaCntlivinf();
        //aggiornaLivelliProgetto.aggiornaLivpro();
        return progettoModificato;
    }

    @Override
    public List<Settore> getAllSettori() throws MicroServicesException {
        String uri = contabilitaService.getUri(Parameters.RICERCASETT.getValue()) + Parameters.SETTORI.getValue();
        ResponseEntity<BodyResponse> responseEntity = this.callContabilita(uri);
        if (responseEntity.getBody().getSettori() != null) return responseEntity
            .getBody()
            .getSettori(); else throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
    }

    @Override
    public List<Assessorato> getAllAssessorati() throws MicroServicesException {
        String uri = contabilitaService.getUri(Parameters.RICERCAASSE.getValue()) + Parameters.ASSESSORATI.getValue();
        ResponseEntity<BodyResponse> responseEntity = this.callContabilita(uri);
        if (responseEntity.getBody().getAssessorati() != null) return responseEntity
            .getBody()
            .getAssessorati(); else throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
    }

    private ResponseEntity<BodyResponse> callContabilita(String uri) throws MicroServicesException {
        Optional<AmmPar> ricercaImpAcceOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCAIMPEACCE.getValue());
        if (ricercaImpAcceOpt.isPresent()) {
            if (ricercaImpAcceOpt.get().getValore().equalsIgnoreCase(Parameters.WS.getValue())) {
                return contabilitaService.clientApiContabilita(uri, null, null, HttpMethod.POST);
            } else {
                return contabilitaService.clientExternalApiContabilita(uri, null, null, HttpMethod.GET);
            }
        } else throw new MicroServicesException(
            ErrorMessages.PARAMETERS_API_NOT_FOUND.getUserMessage(),
            ErrorMessages.PARAMETERS_API_NOT_FOUND.getCode()
        );
    }

    @Override
    @Transactional
    public ProgettoResponse ricercaProgetto(ProgettoRicerca proRic, Integer idUteRuo) throws MicroServicesException {
        ProgettoResponse response = new ProgettoResponse();

        if (proRic.checkNull() && !filtroProgetti.isFiltered()) {
            checkMaxRecords(progettoRepository.count(), response);
            Pageable limit = PageRequest.of(0, this.getMaxRecords());
            Page<ProPro> results = progettoRepository.findAllByOrderByCodproAsc(limit);
            this.valorizzaResultRicerca(proRic.getCalcolaTotali(), results.getContent(), response);
        } else {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProPro> query = cb.createQuery(ProPro.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
            Root<ProPro> root = query.from(ProPro.class);
            Root<ProPro> rootCount = queryCount.from(ProPro.class);
            List<Predicate> predicates = this.getPredicates(root, cb, proRic, idUteRuo);
            List<Predicate> predicatesCount = this.getPredicates(rootCount, cb, proRic, idUteRuo);
            queryCount.select(cb.count(rootCount)).where(predicatesCount.toArray(new Predicate[0]));
            query.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(root.get("codpro")));
            Long countResult = em.createQuery(queryCount).getSingleResult();
            checkMaxRecords(countResult, response);
            List<ProPro> results = em.createQuery(query).setMaxResults(this.getMaxRecords()).getResultList();
            this.valorizzaResultRicerca(proRic.getCalcolaTotali(), results, response);
        }
        return response;
    }

    @Override
    public List<ProPro> ricercaProgetto(String autocomplete, Integer idUteRuo) throws MicroServicesException {
        if (autocomplete != null && !autocomplete.isBlank()) {
            filtroProgetti.generateFiltroProgetti(idUteRuo);
            if (filtroProgetti.isFiltered()) return progettoRepository.findAutocompleteByDir(
                autocomplete.toUpperCase(),
                filtroProgetti.getIdDirezione()
            ); else return progettoRepository.findAutocomplete(autocomplete.toUpperCase());
        } else {
            if (filtroProgetti.isFiltered()) return progettoRepository.findByDir(
                filtroProgetti.getIdDirezione()
            ); else return progettoRepository.findAllByOrderByCodproAsc();
            //throw new MicroServicesException(ErrorMessages.AUTOCOMPLETE_BLANK.getUserMessage(),
            //   ErrorMessages.AUTOCOMPLETE_BLANK.getCode());
        }
    }

    private List<Predicate> getPredicates(Root root, CriteriaBuilder cb, ProgettoRicerca proRic, Integer idUteRuo)
        throws MicroServicesException {
        filtroProgetti.generateFiltroProgetti(idUteRuo);
        List<Predicate> predicates = new ArrayList<>();
        if (proRic.getAutocomplete() != null && !proRic.getAutocomplete().isEmpty()) predicates.add(
            cb.or(
                cb.like(cb.upper(root.get("codpro")), "%" + proRic.getAutocomplete().toUpperCase() + "%"),
                cb.like(cb.upper(root.get("despro")), "%" + proRic.getAutocomplete().toUpperCase() + "%"),
                cb.like(cb.upper(root.get("codcup")), "%" + proRic.getAutocomplete().toUpperCase() + "%")
            )
        );
        if (proRic.getTipoFinanziamento() != 0) {
            Optional<ProTipfin> tipfinOpt = tipoFinanziamentoRepository.findById(proRic.getTipoFinanziamento());
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
        if (proRic.getMacroProgetto() != 0) {
            predicates.add(cb.equal(root.get("idMacpro"), proRic.getMacroProgetto()));
        }
        if (proRic.getBando() != 0) {
            predicates.add(cb.equal(root.get("idBan"), proRic.getBando()));
        }
        if (proRic.getTematica() != 0) {
            Optional<ProTem> temOpt = tematicaRepository.findById(proRic.getTematica());
            if (temOpt.isPresent()) {
                ProTem temSearch = temOpt.get();
                List<ProTem> erediList = tematicaService.getEredi(temSearch);
                List<Long> idErediList = new ArrayList<>();
                for (ProTem temElement : erediList) {
                    idErediList.add(Long.valueOf(temElement.getId()));
                }
                predicates.add(root.get("idTem").in(idErediList));
            } else throw new MicroServicesException(ErrorMessages.TEMREF.getUserMessage(), ErrorMessages.TEMREF.getCode());
        }
        if (filtroProgetti.isFiltered()) predicates.add(cb.equal(root.get("idDir"), filtroProgetti.getIdDirezione())); else if (
            proRic.getDirezione() != 0
        ) predicates.add(cb.equal(root.get("idDir"), proRic.getDirezione()));

        if (proRic.getStatoProgetto() != 0) {
            predicates.add(cb.equal(root.get("idStapro"), proRic.getStatoProgetto()));
        }
        if (proRic.getStatoFinanziario() != 0) {
            predicates.add(cb.equal(root.get("idStafin"), proRic.getStatoFinanziario()));
        }
        if (proRic.getTipLiv() != null) {
            if (proRic.getTipLiv().equals("01") || proRic.getTipLiv().equals("02") || proRic.getTipLiv().equals("03")) {
                predicates.add(cb.equal(root.get("livpro"), Integer.parseInt(proRic.getTipLiv())));
            } else if (proRic.getTipLiv().equals("BA")) {
                predicates.add(cb.equal(root.get("cntlivinf"), 0));
            } else if (proRic.getTipLiv().equals("PB")) {
                predicates.add(
                    cb.or(cb.equal(root.get("cntlivinf"), 1), cb.and(cb.equal(root.get("cntlivinf"), 0), cb.isNull(root.get("idPropad"))))
                );
            }
        }
        if (proRic.getMunicipio() != 0) {
            predicates.add(cb.equal(root.get("idMun"), proRic.getMunicipio()));
        }
        if (proRic.getNil() != 0) {
            predicates.add(cb.equal(root.get("idNil"), proRic.getNil()));
        }
        return predicates;
    }

    private void checkMaxRecords(Long countResult, ProgettoResponse response) {
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

    private void valorizzaResultRicerca(boolean calcTotali, List<ProPro> results, ProgettoResponse response) {
        for (ProPro propro : results) {
            ProResponse proProElement = new ProResponse();
            Hibernate.initialize(propro.getProStaproByIdStapro());
            Hibernate.initialize(propro.getProStafinByIdStafin());
            Hibernate.initialize(propro.getProTipfinByIdTipfin());
            proProElement.setProgetto(propro);
            if (calcTotali) {
                Totali totali = calcolaTotali.getTotaliFondi(EntityType.PRO.getValue(), propro.getId());
                proProElement.setTotali(totali);
            }
            response.addProgetto(proProElement);
        }
    }

    private List<ProInfagg> obtainInfoAggiuntiveFromProgetto(int idProgetto) {
        List<ProInfagg> response = new ArrayList<>();
        Query res = em.createNativeQuery(infagg);
        res.setParameter(1, idProgetto);
        List<Object[]> infAggObjects = (List<Object[]>) res.getResultList();
        for (Object[] infAgg : infAggObjects) {
            ProInfagg proInfaggObj = new ProInfagg();
            proInfaggObj.setId((Integer) infAgg[0]);
            proInfaggObj.setIdPro((long) idProgetto);
            Integer idTipinfagg = (Integer) infAgg[1];
            proInfaggObj.setIdTipinfagg(idTipinfagg.longValue());
            proInfaggObj.setInfagg((String) infAgg[3]);
            proInfaggObj.setProTipinfaggByIdTipinfagg(tipoInfoAggiuntiveRepository.findById(idTipinfagg).get());
            response.add(proInfaggObj);
        }
        return response;
    }

    private List<ProImp> obtainRisorseFromProgetto(int idProgetto, boolean isRisorseDiCui) {
        List<ProImp> response = new ArrayList<>();
        String queryString;
        if (isRisorseDiCui) queryString = risorsedicui; else queryString = risorse;
        Query res = em.createNativeQuery(queryString);
        res.setParameter(1, idProgetto);
        List<Object[]> risorseObjects = (List<Object[]>) res.getResultList();
        for (Object[] risorsa : risorseObjects) {
            ProImp proImpElement = new ProImp();
            proImpElement.setId((Integer) risorsa[0]);
            proImpElement.setIdPro((long) idProgetto);
            BigInteger idTipImp = (BigInteger) risorsa[1];
            proImpElement.setIdTipimp(idTipImp.longValue());
            BigDecimal impPro = (BigDecimal) risorsa[3];
            proImpElement.setImppro(impPro.doubleValue());
            proImpElement.setNotimp(risorsa[5] == null ? "" : (String) risorsa[5]);
            proImpElement.setProTipimpByIdTipimp(tipoImportoRepository.findById(idTipImp.intValue()).get());
            response.add(proImpElement);
        }
        return response;
    }

    private void setDefaultValuesForProPro(ProPro proDaInserire) {
        proDaInserire.setCntlivinf(0);
        proDaInserire.setLivpro(1);
        proDaInserire.setIddecpro("000000000");
        proDaInserire.setImpimppre(BigDecimal.ZERO);
        proDaInserire.setImpaccpre(BigDecimal.ZERO);
        proDaInserire.setImpmanpre(BigDecimal.ZERO);
        proDaInserire.setImprevpre(BigDecimal.ZERO);
        proDaInserire.setImpimpric(BigDecimal.ZERO);
    }

    private void checksInserisciProgetto(ProPro proDaInserire) throws MicroServicesException {
        this.checkMandatoryFieldsProgetto(proDaInserire);
        this.checkProgettofieldsReferences(proDaInserire);
        if (proDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ID_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.ID_NOT_ALLOWED.getCode()
        );
        if (progettoRepository.findProProByCodproIgnoreCase(proDaInserire.getCodpro()).isPresent()) throw new MicroServicesException(
            ErrorMessages.COD_ALREADY_EXIST.getUserMessage(),
            ErrorMessages.COD_ALREADY_EXIST.getCode()
        );
    }

    private void checksModificaProgettoDetail(ProgettoDetail proDaModificare) throws MicroServicesException {
        this.checkMandatoryFieldsProgetto(proDaModificare.getProgetto());
        this.checkProgettofieldsReferences(proDaModificare.getProgetto());
        Optional<ProPro> proOpt = this.progettoRepository.findById(proDaModificare.getProgetto().getId());
        if (proOpt.isPresent()) {
            ProPro pro = proOpt.get();
            if (!pro.getCodpro().equals(proDaModificare.getProgetto().getCodpro())) {
                if (
                    progettoRepository.findProProByCodproIgnoreCase(proDaModificare.getProgetto().getCodpro()).isPresent()
                ) throw new MicroServicesException(
                    ErrorMessages.COD_ALREADY_EXIST.getUserMessage(),
                    ErrorMessages.COD_ALREADY_EXIST.getCode()
                );
            }
        }
    }

    private void checksModificaProgetto(ProPro proDaModificare) throws MicroServicesException {
        this.checkMandatoryFieldsProgetto(proDaModificare);
        this.checkProgettofieldsReferences(proDaModificare);
        Optional<ProPro> proOpt = this.progettoRepository.findById(proDaModificare.getId());
        if (proOpt.isPresent()) {
            ProPro pro = proOpt.get();
            if (!pro.getCodpro().equals(proDaModificare.getCodpro())) {
                if (
                    progettoRepository.findProProByCodproIgnoreCase(proDaModificare.getCodpro()).isPresent()
                ) throw new MicroServicesException(
                    ErrorMessages.COD_ALREADY_EXIST.getUserMessage(),
                    ErrorMessages.COD_ALREADY_EXIST.getCode()
                );
            }
        }
    }

    private void checkProgettofieldsReferences(ProPro pro) throws MicroServicesException {
        if (!tipoFinanziamentoRepository.findById(pro.getIdTipfin().intValue()).isPresent()) throw new MicroServicesException(
            ErrorMessages.TIPFINREF.getUserMessage(),
            ErrorMessages.TIPFINREF.getCode()
        );
        if (!macroProgettoRepository.findById(pro.getIdMacpro().intValue()).isPresent()) throw new MicroServicesException(
            ErrorMessages.MACPROREF.getUserMessage(),
            ErrorMessages.MACPROREF.getCode()
        );
        if (!statoProgettoRepository.findById(pro.getIdStapro().intValue()).isPresent()) throw new MicroServicesException(
            ErrorMessages.STAPROREF.getUserMessage(),
            ErrorMessages.STAPROREF.getCode()
        );
        if (!statoFinanziamentoRepository.findById(pro.getIdStafin().intValue()).isPresent()) throw new MicroServicesException(
            ErrorMessages.STAFINREF.getUserMessage(),
            ErrorMessages.STAFINREF.getCode()
        );
        if (!direzioneRepository.findById(pro.getIdDir()).isPresent()) throw new MicroServicesException(
            ErrorMessages.DIRREF.getUserMessage(),
            ErrorMessages.DIRREF.getCode()
        );
        if (pro.getIdBan() != null && pro.getIdBan() != 0L) {
            if (!bandoRepository.findById(pro.getIdBan().intValue()).isPresent()) throw new MicroServicesException(
                ErrorMessages.BANREF.getUserMessage(),
                ErrorMessages.BANREF.getCode()
            );
        }
        if (pro.getIdMun() != null && pro.getIdMun() != 0L) {
            if (!municipioRepository.findById(pro.getIdMun().intValue()).isPresent()) throw new MicroServicesException(
                ErrorMessages.MUNREF.getUserMessage(),
                ErrorMessages.MUNREF.getCode()
            );
        }
        if (pro.getIdNil() != null && pro.getIdNil() != 0L) {
            if (!nilRepository.findById(pro.getIdNil().intValue()).isPresent()) throw new MicroServicesException(
                ErrorMessages.MUNREF.getUserMessage(),
                ErrorMessages.MUNREF.getCode()
            );
        }
        if (pro.getIdPropad() != null && pro.getIdPropad() != 0L) {
            if (!progettoRepository.findById(pro.getIdPropad().intValue()).isPresent()) throw new MicroServicesException(
                ErrorMessages.PROPADREF.getUserMessage(),
                ErrorMessages.PROPADREF.getCode()
            );
        }
        if (pro.getIdTem() != null && pro.getIdTem() != 0L) {
            if (!tematicaRepository.findById(pro.getIdTem().intValue()).isPresent()) throw new MicroServicesException(
                ErrorMessages.TEMREF.getUserMessage(),
                ErrorMessages.TEMREF.getCode()
            );
        }
        if (pro.getTippro() != null && !pro.getTippro().trim().equals("")) {
            if (!pro.getTippro().equals("PTO") && !pro.getTippro().equals("PBA")) {
                throw new MicroServicesException(ErrorMessages.TIPPROREF.getUserMessage(), ErrorMessages.TIPPROREF.getCode());
            }
        }
        if (pro.getFlgopeavv() != null && !pro.getFlgopeavv().trim().equals("")) {
            if (!pro.getFlgopeavv().equals("S") && !pro.getFlgopeavv().equals("N")) {
                throw new MicroServicesException(ErrorMessages.FLGOPEAVVREF.getUserMessage(), ErrorMessages.FLGOPEAVVREF.getCode());
            }
        }
    }

    private void checkMandatoryFieldsProgetto(ProPro pro) throws MicroServicesException {
        if (pro.getCodpro() == null || pro.getCodpro().trim().equals("")) throw new MicroServicesException(
            ErrorMessages.MANDATORY_CODPRO.getUserMessage(),
            ErrorMessages.MANDATORY_CODPRO.getCode()
        );
        if (pro.getDespro() == null || pro.getDespro().trim().equals("")) throw new MicroServicesException(
            ErrorMessages.MANDATORY_DESPRO.getUserMessage(),
            ErrorMessages.MANDATORY_DESPRO.getCode()
        );
        if (pro.getIdTipfin() == null || pro.getIdTipfin() == 0L) throw new MicroServicesException(
            ErrorMessages.MANDATORY_TIPFIN.getUserMessage(),
            ErrorMessages.MANDATORY_TIPFIN.getCode()
        );
        if (pro.getIdMacpro() == null || pro.getIdMacpro() == 0L) throw new MicroServicesException(
            ErrorMessages.MANDATORY_MACPRO.getUserMessage(),
            ErrorMessages.MANDATORY_MACPRO.getCode()
        );
        if (pro.getIdStapro() == null || pro.getIdStapro() == 0L) throw new MicroServicesException(
            ErrorMessages.MANDATORY_STAPRO.getUserMessage(),
            ErrorMessages.MANDATORY_STAPRO.getCode()
        );
        if (pro.getIdStafin() == null || pro.getIdStafin() == 0L) throw new MicroServicesException(
            ErrorMessages.MANDATORY_STAFIN.getUserMessage(),
            ErrorMessages.MANDATORY_STAFIN.getCode()
        );
        if (pro.getIdDir() == null || pro.getIdDir() == 0L) throw new MicroServicesException(
            ErrorMessages.MANDATORY_DIR.getUserMessage(),
            ErrorMessages.MANDATORY_DIR.getCode()
        );
    }

    private void aggiornaInserisciInfoRigheDB(ProgettoDetail proDaModificare) {
        if (!proDaModificare.getRisorse().isEmpty()) {
            for (ProImp resource : proDaModificare.getRisorse()) {
                if (resource.getId() == 0) utilService.setInfoInsertRow(resource); else {
                    Optional<ProImp> importoOriginaleOpt = importoRepository.findById(resource.getId());
                    if (importoOriginaleOpt.isPresent()) {
                        resource.setUsrCreate(importoOriginaleOpt.get().getUsrCreate());
                        resource.setDtCreate(importoOriginaleOpt.get().getDtCreate());
                    }
                    utilService.setInfoUpdateRow(resource);
                }
            }
        }
        if (!proDaModificare.getRisorseDiCui().isEmpty()) {
            for (ProImp resource : proDaModificare.getRisorseDiCui()) {
                if (resource.getId() == 0) utilService.setInfoInsertRow(resource); else {
                    Optional<ProImp> importoOriginaleOpt = importoRepository.findById(resource.getId());
                    if (importoOriginaleOpt.isPresent()) {
                        resource.setUsrCreate(importoOriginaleOpt.get().getUsrCreate());
                        resource.setDtCreate(importoOriginaleOpt.get().getDtCreate());
                    }
                    utilService.setInfoUpdateRow(resource);
                }
            }
        }
        if (!proDaModificare.getInfoAggiuntive().isEmpty()) {
            for (ProInfagg infagg : proDaModificare.getInfoAggiuntive()) {
                if (infagg.getId() == 0) utilService.setInfoInsertRow(infagg); else {
                    Optional<ProInfagg> infaggOpt = infoAggiuntiveRepository.findById(infagg.getId());
                    if (infaggOpt.isPresent()) {
                        infagg.setUsrCreate(infaggOpt.get().getUsrCreate());
                        infagg.setDtCreate(infaggOpt.get().getDtCreate());
                    }
                    utilService.setInfoUpdateRow(infagg);
                }
            }
        }
    }

    @Override
    public List<ProEntcon> ricercaSettoriAutocomplete(String autocomplete) throws MicroServicesException {
        if (autocomplete != null && !autocomplete.isBlank()) {
            return entConRepository.findAutocomplete("SETT", autocomplete.toUpperCase());
        } else {
            return entConRepository.findAllByTipent("SETT");
        }
    }

    @Override
    public List<ProEntcon> ricercaAssessoratiAutocomplete(String autocomplete) throws MicroServicesException {
        if (autocomplete != null && !autocomplete.isBlank()) {
            return entConRepository.findAutocomplete("ASSE", autocomplete.toUpperCase());
        } else {
            return entConRepository.findAllByTipent("ASSE");
        }
    }
}
