package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProDetcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipimp;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.DetConRepository;
import it.dmi.dmifonbe.dmifonpro.repository.EntConRepository;
import it.dmi.dmifonbe.dmifonpro.repository.ProgettoRepository;
import it.dmi.dmifonbe.dmifonpro.repository.TipoImportoRepository;
import it.dmi.dmifonbe.dmifonpro.service.ContabilitaService;
import it.dmi.dmifonbe.dmifonpro.service.ImpegniProgettoService;
import it.dmi.dmifonbe.dmifonpro.service.ProgettoService;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ImpegniProgettoServiceImpl implements ImpegniProgettoService {

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    ProgettoService progettoService;

    @Autowired
    DetConRepository detConRepository;

    @Autowired
    EntConRepository entConRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    ContabilitaService contabilitaService;

    @Autowired
    EntityManager em;

    @Value("${ricercaImpeAcce}")
    String ricercaImpeAcce;

    @Value("${ricercaImpeAcceSoloProgetto}")
    String ricercaImpeAcceSoloProgetto;

    @Value("${ricercaImpeAcceOrder}")
    String ricercaImpeAcceOrder;

    @Autowired
    TipoImportoRepository tipoImportoRepository;

    @Override
    @Transactional
    public void modificaImpegniProgetto(ImpegniProgetto impegniProgettoDaModificare) throws MicroServicesException {
        progettoService.modificaProgetto(impegniProgettoDaModificare.getProgetto());
        if (!impegniProgettoDaModificare.getImpegni().isEmpty())
            this.salvaDettagliContabili(impegniProgettoDaModificare.getImpegni());
        if (!impegniProgettoDaModificare.getAccertamenti().isEmpty()) this.salvaDettagliContabili(
            impegniProgettoDaModificare.getAccertamenti()
        );
        if (!impegniProgettoDaModificare.getCrono().isEmpty())
            this.salvaDettagliContabiliCrono(impegniProgettoDaModificare.getCrono());
    }

    @Override
    public ImpegniProgetto getImpegniProgetto(Integer idPro) throws MicroServicesException {
        ImpegniProgetto result = new ImpegniProgetto();
        ProgettoAndProgettoPadre progettoAndProgettoPadre = utilService.getProgettoAndProgettoPadre(idPro);
        result.setProgetto(progettoAndProgettoPadre.getProgetto());
        result.setProgettoPadreLabel(progettoAndProgettoPadre.getProgettoPadreLabel());
        List<ProDetcon> impegniList = detConRepository.findProDetconsByIdProAndTipentIgnoreCaseOrderByCodentcon(
            Long.valueOf(idPro),
            Parameters.TIPENTIMPE.getValue()
        );
        result.setImpegni(this.ottieniDettagliContabili(impegniList, Parameters.TIPENTIMPE.getValue()));
        List<ProDetcon> accertamentiList = detConRepository.findProDetconsByIdProAndTipentIgnoreCaseOrderByCodentcon(
            Long.valueOf(idPro),
            Parameters.TIPENTACCE.getValue()
        );
        result.setAccertamenti(this.ottieniDettagliContabili(accertamentiList, Parameters.TIPENTACCE.getValue()));
        List<ProDetcon> cronoList = detConRepository.findProDetconsByIdProAndTipentIgnoreCaseOrderByCodentcon(
            Long.valueOf(idPro),
            Parameters.TIPENTCRON.getValue()
        );
        result.setCrono(this.ottieniDettagliContabili(cronoList, Parameters.TIPENTCRON.getValue()));
        return result;
    }

    private void checkCodEntCon(String codEntCon) throws MicroServicesException {
        if (codEntCon == null || codEntCon.isBlank()) throw new MicroServicesException(
            ErrorMessages.MANDATORY_CODENTCON.getUserMessage(),
            ErrorMessages.MANDATORY_CODENTCON.getCode()
        );
    }

    @Override
    public ProEntcon getAccertamento(String codEntCon) throws MicroServicesException {
        this.checkCodEntCon(codEntCon);
        String uri = contabilitaService.getUri(Parameters.RICERCAIMPEACCE.getValue());
        Optional<AmmPar> ricercaImpAcceOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCAIMPEACCE.getValue());
        ResponseEntity<BodyResponse> responseEntity = null;
        if (ricercaImpAcceOpt.isPresent()) {
            if (ricercaImpAcceOpt.get().getValore().equalsIgnoreCase(Parameters.WS.getValue())) {
                uri += Parameters.URI_FINAL_STRING_ACCE.getValue();
                responseEntity = contabilitaService.clientApiContabilita(uri, codEntCon, null, HttpMethod.POST);
            } else {
                uri += Parameters.URI_FINAL_STRING_ACCE.getValue() + "/";
                responseEntity = contabilitaService.clientExternalApiContabilita(uri, codEntCon, null, HttpMethod.GET);
            }
        }
        return this.convertToProEntcon(responseEntity.getBody().getAccertamenti().get(0), Parameters.TIPENTACCE.getValue());
    }

    @Override
    public ProEntcon getCrono(String codEntCon) throws MicroServicesException {
        this.checkCodEntCon(codEntCon);
        String uri = contabilitaService.getUri(Parameters.RICERCAIMPEACCE.getValue());
        Optional<AmmPar> ricercaImpAcceOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCAIMPEACCE.getValue());
        ResponseEntity<BodyResponse> responseEntity = null;
        if (ricercaImpAcceOpt.isPresent()) {
            if (ricercaImpAcceOpt.get().getValore().equalsIgnoreCase(Parameters.WS.getValue())) {
                uri += Parameters.URI_FINAL_STRING_CRONO.getValue();
                responseEntity = contabilitaService.clientApiContabilita(uri, codEntCon, null, HttpMethod.POST);
                return this.convertToProEntcon(responseEntity.getBody().getCrono().get(0));
            } else {
                uri += Parameters.URI_FINAL_STRING_CRONO.getValue() + "/";
                responseEntity = contabilitaService.clientExternalApiContabilita(uri, codEntCon, null, HttpMethod.GET);
                return this.convertToProEntcon(responseEntity.getBody().getCronoprogrammi().get(0));
            }
        } else throw new MicroServicesException(
            ErrorMessages.WSENDPOINT_NOT_FOUND.getUserMessage(),
            ErrorMessages.WSENDPOINT_NOT_FOUND.getCode()
        );
    }

    @Override
    public ProEntcon getImpegno(String codEntCon) throws MicroServicesException {
        this.checkCodEntCon(codEntCon);
        String uri = contabilitaService.getUri(Parameters.RICERCAIMPEACCE.getValue());
        Optional<AmmPar> ricercaImpAcceOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCAIMPEACCE.getValue());
        ResponseEntity<BodyResponse> responseEntity = null;
        if (ricercaImpAcceOpt.isPresent()) {
            if (ricercaImpAcceOpt.get().getValore().equalsIgnoreCase(Parameters.WS.getValue())) {
                uri += Parameters.URI_FINAL_STRING_IMPE.getValue();
                responseEntity = contabilitaService.clientApiContabilita(uri, codEntCon, null, HttpMethod.POST);
            } else {
                uri += Parameters.URI_FINAL_STRING_IMPE.getValue() + "/";
                responseEntity = contabilitaService.clientExternalApiContabilita(uri, codEntCon, null, HttpMethod.GET);
            }
        }
        return this.convertToProEntcon(responseEntity.getBody().getImpegni().get(0), Parameters.TIPENTIMPE.getValue());
    }

    @Override
    public List<ProEntcon> getEntitaContabile(String tipEntCon, RicercaEntitaContabile ricEntCon) throws MicroServicesException {
        List<ContentResponse> resultsOfContentResponse;
        List<ProEntcon> results = new ArrayList<>();
        String uri =
            contabilitaService.getUri(Parameters.RICERCAIMPEACCE.getValue()) +
            (
                tipEntCon.equals(Parameters.TIPENTACCE.getValue())
                    ? Parameters.URI_FINAL_STRING_ACCE.getValue()
                    : Parameters.URI_FINAL_STRING_IMPE.getValue()
            );
        Optional<AmmPar> ricercaImpAcceOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCAIMPEACCE.getValue());
        ResponseEntity<BodyResponse> responseEntity = null;
        if (ricercaImpAcceOpt.isPresent()) {
            if (ricercaImpAcceOpt.get().getValore().equalsIgnoreCase(Parameters.WS.getValue())) {
                responseEntity = contabilitaService.clientApiContabilita(uri, null, ricEntCon, HttpMethod.POST);
            } else {
                responseEntity = contabilitaService.clientExternalApiContabilita(uri, null, ricEntCon, HttpMethod.GET);
            }
        }
        if (tipEntCon.equals(Parameters.TIPENTACCE.getValue())) {
            resultsOfContentResponse = responseEntity.getBody().getAccertamenti();
        } else {
            resultsOfContentResponse = responseEntity.getBody().getImpegni();
        }
        for (ContentResponse elementContentResponse : resultsOfContentResponse) {
            results.add(this.convertToProEntcon(elementContentResponse, tipEntCon));
        }
        return results;
    }

    @Override
    public List<ProEntcon> ricercaAccertamentiProgetto(Integer id, String tipEntCon) throws MicroServicesException {
        this.checkRicercaAccertamentiProgettoParameters(id, tipEntCon);
        String queryStr = "";
        queryStr = ricercaImpeAcce + " " + ricercaImpeAcceOrder;
        Query res = em.createQuery(queryStr);
        res.setParameter(1, id.intValue());
        res.setParameter(2, tipEntCon);
        List<ProEntcon> accertamenti = res.getResultList();
        if (accertamenti.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
        return accertamenti;
    }

    public List<ProEntcon> ricercaAccertamentiProgettoBase(Integer id, String tipEntCon) throws MicroServicesException {
        return ricercaAccertamentiProgettoBase(id, tipEntCon, false);
    }

    public List<ProEntcon> ricercaAccertamentiProgettoBase(Integer id, String tipEntCon, boolean flgSolImpPro) throws MicroServicesException {
        Query res;
        String queryStr = "";
        if (flgSolImpPro)
            queryStr = ricercaImpeAcce + " " + ricercaImpeAcceSoloProgetto + " " + ricercaImpeAcceOrder;
        else
            queryStr = ricercaImpeAcce + " " + ricercaImpeAcceOrder;
        res = em.createQuery(queryStr);
        res.setParameter(1, id.intValue());
        res.setParameter(2, tipEntCon);
        List<ProEntcon> accertamenti = res.getResultList();
        return accertamenti;
    }

    @Override
    public List<ProTipimp> getTipoImportoRisorsa() throws MicroServicesException {
        List<ProTipimp> result = tipoImportoRepository.findAllFlgdicuiNotEqualS();
        if (result.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
        return result;
    }

    @Override
    public void cancellaDettaglioContabile(Integer id) throws MicroServicesException {
        this.checkDeleteDettaglioContabile(id);
        detConRepository.deleteById(id);
    }

    public void checkDeleteDettaglioContabile(Integer id) throws MicroServicesException {
        if (id == null || id.equals(0)) throw new MicroServicesException(
            ErrorMessages.DELETE_NO_ID_PROVIDED.getUserMessage(),
            ErrorMessages.DELETE_NO_ID_PROVIDED.getCode()
        );
        if (!detConRepository.findById(id).isPresent()) throw new MicroServicesException(
            ErrorMessages.DETCON_NOT_FOUND.getUserMessage(),
            ErrorMessages.DETCON_NOT_FOUND.getCode()
        );
    }

    private ProEntcon convertToProEntcon(ContentResponseCrono crono) {
        ProEntcon result = new ProEntcon();
        result.setTipent(Parameters.TIPENTCRON.getValue());
        result.setCodentcon(crono.getId() == null ? "" : crono.getId());
        result.setDesimp(crono.getDescrizione() == null ? "" : crono.getDescrizione());
        result.setIdcap("");
        result.setCodcig("");
        result.setCodgami("");
        result.setCodcup("");
        result.setDespdd("");
        result.setIdcro("");
        result.setImpass(BigDecimal.ZERO);
        result.setImpeco(BigDecimal.ZERO);
        result.setImpliq(BigDecimal.ZERO);
        result.setImpman(BigDecimal.ZERO);
        result.setImpmaneme(BigDecimal.ZERO);
        result.setNroconapp(0L);
        if (crono.getAnno() != null && !crono.getAnno().isBlank()) result.setAnncmp(Integer.parseInt(crono.getAnno()));
        return result;
    }

    private ProEntcon convertToProEntcon(ContentResponse impegni, String tipent) {
        ProEntcon result = new ProEntcon();
        result.setTipent(tipent);
        result.setCodentcon(impegni.getId() == null ? "" : impegni.getId());
        result.setDesimp(impegni.getDescrizione() == null ? "" : impegni.getDescrizione());
        result.setIdcap(impegni.getId_capitolo() == null ? "" : impegni.getId_capitolo());
        result.setCodcig(impegni.getCig() == null ? "" : impegni.getCig());
        result.setCodgami(impegni.getCodice_gami() == null ? "" : impegni.getCodice_gami());
        result.setCodcup(impegni.getCup() == null ? "" : impegni.getCup());
        result.setDespdd(impegni.getDescrizione_PDD() == null ? "" : impegni.getDescrizione_PDD());
        result.setIdcro(impegni.getId_crono() == null ? "" : impegni.getId_crono());
        if (impegni.getImporto_assestato() != null) result.setImpass(new BigDecimal(impegni.getImporto_assestato())); else result.setImpass(
            BigDecimal.ZERO
        );
        if (impegni.getImporto_economie() != null) result.setImpeco(new BigDecimal(impegni.getImporto_economie())); else result.setImpeco(
            BigDecimal.ZERO
        );
        if (impegni.getImporto_liquidato() != null) result.setImpliq(new BigDecimal(impegni.getImporto_liquidato())); else result.setImpliq(
            BigDecimal.ZERO
        );
        if (impegni.getImporto_mandati() != null) result.setImpman(new BigDecimal(impegni.getImporto_mandati())); else result.setImpman(
            BigDecimal.ZERO
        );
        if (impegni.getImporto_mandati_emessi() != null) result.setImpmaneme(
            new BigDecimal(impegni.getImporto_mandati_emessi())
        ); else result.setImpmaneme(BigDecimal.ZERO);
        if (impegni.getNumero_contratto_applicativo() != null) result.setNroconapp(
            Long.valueOf(impegni.getNumero_contratto_applicativo())
        ); else result.setNroconapp(0L);
        if (impegni.getAnno() != null && !impegni.getAnno().isBlank()) result.setAnncmp(
            Integer.parseInt(impegni.getAnno())
        ); else result.setAnncmp(0);
        return result;
    }

    private List<DettaglioContabile> ottieniDettagliContabili(List<ProDetcon> detconList, String tipent) {
        List<DettaglioContabile> listDettagliContabili = new ArrayList<>();
        for (ProDetcon impegno : detconList) {
            DettaglioContabile dettaglioContabile = new DettaglioContabile();
            Optional<ProEntcon> entconOpt = entConRepository.findByCodentconAndTipent(impegno.getCodentcon(), tipent);
            if (entconOpt.isPresent()) {
                dettaglioContabile.setProDetcon(impegno);
                dettaglioContabile.setProEntcon(entconOpt.get());
                listDettagliContabili.add(dettaglioContabile);
            }
        }
        return listDettagliContabili;
    }

    private void salvaDettagliContabili(List<DettaglioContabile> listDettagliContabili) throws MicroServicesException {
        this.checkDettagliContabili(listDettagliContabili);
        for (DettaglioContabile dettaglioContabile : listDettagliContabili) {
            if (dettaglioContabile.getProDetcon().getId() != 0) {
                Optional<ProDetcon> dettaglioContabileOriginaleOpt = detConRepository.findById(dettaglioContabile.getProDetcon().getId());
                if (dettaglioContabileOriginaleOpt.isPresent()) {
                    dettaglioContabile.getProDetcon().setUsrCreate(dettaglioContabileOriginaleOpt.get().getUsrCreate());
                    dettaglioContabile.getProDetcon().setDtCreate(dettaglioContabileOriginaleOpt.get().getDtCreate());
                }
                utilService.setInfoUpdateRow(dettaglioContabile.getProDetcon());
            } else utilService.setInfoInsertRow(dettaglioContabile.getProDetcon());
            detConRepository.saveAndFlush(dettaglioContabile.getProDetcon());
            if (!this.proEntconExist(dettaglioContabile.getProEntcon())) {
                utilService.setInfoInsertRow(dettaglioContabile.getProEntcon());
                entConRepository.saveAndFlush(dettaglioContabile.getProEntcon());
            }
        }
    }

    private void salvaDettagliContabiliCrono(List<DettaglioContabile> cronos) throws MicroServicesException {
        List<ProEntcon> accertamentiByCrono = new ArrayList<>();
        List<ProEntcon> impegniByCrono = new ArrayList<>();
        for (DettaglioContabile crono : cronos) {
            RicercaEntitaContabile ricEntCon = new RicercaEntitaContabile();
            ricEntCon.setId_crono(crono.getProDetcon().getCodentcon());
            List<ProEntcon> proEntconAcceList = new ArrayList<>();
            List<ProEntcon> proEntconImpeList = new ArrayList<>();
            //getEntitaContabile scatena un'eccezione se non trova nulla e manda in errore tutto
            //se non trova accertamenti o impegni legati a crono non è un problema
            try {
                proEntconAcceList = this.getEntitaContabile(Parameters.TIPENTACCE.getValue(), ricEntCon);
            } catch (Exception e) {
            }
            try {
                proEntconImpeList = this.getEntitaContabile(Parameters.TIPENTIMPE.getValue(), ricEntCon);
            } catch (Exception e) {
            }
            this.valueListOfEntCons(accertamentiByCrono, proEntconAcceList);
            this.valueListOfEntCons(impegniByCrono, proEntconImpeList);
        }
        entConRepository.saveAllAndFlush(accertamentiByCrono);
        entConRepository.saveAllAndFlush(impegniByCrono);
        this.salvaDettagliContabili(cronos);
    }

    private void valueListOfEntCons(List<ProEntcon> resultList, List<ProEntcon> startList) {
        for (ProEntcon proEntcon : startList) {
            if (!this.proEntconExist(proEntcon)) {
                utilService.setInfoInsertRow(proEntcon);
                resultList.add(proEntcon);
            }
        }
    }

    private boolean proEntconExist(ProEntcon proEntcon) {
        Optional<ProEntcon> proEntconExistentOpt = entConRepository.findByCodentconAndTipent(
            proEntcon.getCodentcon(),
            proEntcon.getTipent()
        );
        return !proEntconExistentOpt.isEmpty();
    }

    private void checkDettagliContabili(List<DettaglioContabile> listDettagliContabili) throws MicroServicesException {
        List<String> codentconList = new ArrayList<>();
        for (DettaglioContabile dettCont : listDettagliContabili) {
            codentconList.add(dettCont.getProDetcon().getCodentcon());
        }
        for (DettaglioContabile dettaglioContabile : listDettagliContabili) {
            if (dettaglioContabile.getProDetcon().getId() == 0) {
                if (
                    !detConRepository
                        .findProDetconsByIdProAndTipentIgnoreCaseAndCodentcon(
                            dettaglioContabile.getProDetcon().getIdPro(),
                            dettaglioContabile.getProDetcon().getTipent(),
                            dettaglioContabile.getProDetcon().getCodentcon()
                        )
                        .isEmpty()
                ) throw new MicroServicesException(
                    ErrorMessages.DETCON_AlREADY_EXIST.getUserMessage(),
                    ErrorMessages.DETCON_AlREADY_EXIST.getCode()
                );
            }
            int frequency = Collections.frequency(codentconList, dettaglioContabile.getProDetcon().getCodentcon());
            if (frequency > 1) throw new MicroServicesException(
                ErrorMessages.DUPLICATE_DETCONT.getUserMessage(),
                ErrorMessages.DUPLICATE_DETCONT.getCode()
            );
        }
    }

    private void checkRicercaAccertamentiProgettoParameters(Integer id, String tipEntCon) throws MicroServicesException {
        if (id.equals(0) || !progettoRepository.findById(id).isPresent()) throw new MicroServicesException(
            ErrorMessages.RICERCA_ID_NOT_VALID.getUserMessage(),
            ErrorMessages.RICERCA_ID_NOT_VALID.getCode()
        );
        if (
            !tipEntCon.equals(Parameters.TIPENTACCE.getValue()) && !tipEntCon.equals(Parameters.TIPENTIMPE.getValue())
        ) throw new MicroServicesException(
            ErrorMessages.RICERCA_TIPENTCON_NOT_VALID.getUserMessage(),
            ErrorMessages.RICERCA_TIPENTCON_NOT_VALID.getCode()
        );
    }

}
