package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProDetcon;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.DetConRepository;
import it.dmi.dmifonbe.dmifonpro.repository.EntConRepository;
import it.dmi.dmifonbe.dmifonpro.repository.ProgettoRepository;
import it.dmi.dmifonbe.dmifonpro.service.CapitoliService;
import it.dmi.dmifonbe.dmifonpro.service.ContabilitaService;
import it.dmi.dmifonbe.dmifonpro.service.ProgettoService;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CapitoliProgettoServiceImpl implements CapitoliService {

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
    EntityManager em;

    @Autowired
    ContabilitaService contabilitaService;

    @Autowired
    ImpegniProgettoServiceImpl impegniProgettoServiceImpl;

    @Value("${ricercaImpeAcce}")
    String ricercaImpeAcce;

    @Override
    @Transactional
    public CapitoliProgettoResponse getCapitoliProgetto(Integer idPro) throws MicroServicesException {
        CapitoliProgettoResponse result = new CapitoliProgettoResponse();
        ProgettoAndProgettoPadre progettoAndProgettoPadre = utilService.getProgettoAndProgettoPadre(idPro);
        result.setProgetto(progettoAndProgettoPadre.getProgetto());
        result.setProgettoPadreLabel(progettoAndProgettoPadre.getProgettoPadreLabel());
        Hibernate.initialize(result.getProgetto().getProDetconsById());
        List<ProDetcon> capitoliEntrataList = new ArrayList<>();
        List<ProDetcon> capitoliUscitaList = new ArrayList<>();
        List<DettaglioCapitolo> detCapEntrata = new ArrayList<>();
        List<DettaglioCapitolo> detCapUscita = new ArrayList<>();
        for (ProDetcon capitolo : result.getProgetto().getProDetconsById()) {
            if (capitolo.getTipent().equals(Parameters.CAPE.getValue())) capitoliEntrataList.add(capitolo); else if (
                capitolo.getTipent().equals(Parameters.CAPU.getValue())
            ) capitoliUscitaList.add(capitolo);
        }
        String uriBase = contabilitaService.getUri(Parameters.RICERCACAPECAPU.getValue());
        String uriCapitoliEntrata = uriBase + Parameters.URI_FINAL_STRING_CAPE.getValue();
        String uriCapitoliUscita = uriBase + Parameters.URI_FINAL_STRING_CAPU.getValue();
        this.ottieniCapitoli(capitoliEntrataList, uriCapitoliEntrata, detCapEntrata);
        this.ottieniCapitoli(capitoliUscitaList, uriCapitoliUscita, detCapUscita);
        result.setCapitoliEntrata(detCapEntrata);
        result.setCapitoliUscita(detCapUscita);
        return result;
    }

    @Override
    public void modificaCapitoliProgetto(CapitoliProgettoResponse capitoliDaModificare) throws MicroServicesException {
        if (!progettoRepository.existsById(capitoliDaModificare.getProgetto().getId())) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND_PRO.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND_PRO.getCode()
        );
        List<ProDetcon> proDetconsToSave = new ArrayList<>();
        if (
            capitoliDaModificare.getCapitoliEntrata() != null &&
            !capitoliDaModificare.getCapitoliEntrata().isEmpty() &&
            capitoliDaModificare.getCapitoliUscita() != null &&
            !capitoliDaModificare.getCapitoliUscita().isEmpty()
        ) {
            this.checkDuplicateProDetCon(capitoliDaModificare.getCapitoliEntrata(), capitoliDaModificare.getCapitoliUscita());
        }
        if (capitoliDaModificare.getCapitoliEntrata() != null && !capitoliDaModificare.getCapitoliEntrata().isEmpty()) {
            this.createProDetConsToSave(proDetconsToSave, capitoliDaModificare.getCapitoliEntrata(), Parameters.CAPE.getValue());
        }
        if (capitoliDaModificare.getCapitoliUscita() != null && !capitoliDaModificare.getCapitoliUscita().isEmpty()) {
            this.createProDetConsToSave(proDetconsToSave, capitoliDaModificare.getCapitoliUscita(), Parameters.CAPU.getValue());
        }
        detConRepository.saveAllAndFlush(proDetconsToSave);
    }

    private void createProDetConsToSave(List<ProDetcon> proDetconsToSave, List<DettaglioCapitolo> capitoli, String parameter)
        throws MicroServicesException {
        for (DettaglioCapitolo dettaglioCapitolo : capitoli) {
            this.checkProDetCon(dettaglioCapitolo.getProDetcon());
            dettaglioCapitolo.getProDetcon().setTipent(parameter);
            if (dettaglioCapitolo.getProDetcon().getId() == 0) {
                utilService.setInfoInsertRow(dettaglioCapitolo.getProDetcon());
            } else {
                Optional<ProDetcon> detconOriginale = detConRepository.findById(dettaglioCapitolo.getProDetcon().getId());
                if (detconOriginale.isPresent()) {
                    dettaglioCapitolo.getProDetcon().setUsrCreate(detconOriginale.get().getUsrCreate());
                    dettaglioCapitolo.getProDetcon().setDtCreate(detconOriginale.get().getDtCreate());
                }
                utilService.setInfoUpdateRow(dettaglioCapitolo.getProDetcon());
            }
            proDetconsToSave.add(dettaglioCapitolo.getProDetcon());
        }
    }

    private void checkDuplicateProDetCon(List<DettaglioCapitolo> capitoliEntrata, List<DettaglioCapitolo> capitoliUscita)
        throws MicroServicesException {
        List<Integer> idList = new ArrayList<>();
        this.createidList(idList, capitoliEntrata);
        this.createidList(idList, capitoliUscita);
        this.findDuplicate(capitoliEntrata, idList);
        this.findDuplicate(capitoliUscita, idList);
    }

    private void createidList(List<Integer> idList, List<DettaglioCapitolo> capitoli) {
        for (DettaglioCapitolo dettCap : capitoli) {
            if (dettCap.getProDetcon().getId() != 0) idList.add(dettCap.getProDetcon().getId());
        }
    }

    private void findDuplicate(List<DettaglioCapitolo> capitoli, List<Integer> idList) throws MicroServicesException {
        for (DettaglioCapitolo dettaglioCapitolo : capitoli) {
            if (dettaglioCapitolo.getProDetcon().getId() != 0) {
                int frequency = Collections.frequency(idList, dettaglioCapitolo.getProDetcon().getId());
                if (frequency > 1) throw new MicroServicesException(
                    ErrorMessages.DUPLICATE_CAP_INPUT.getUserMessage(),
                    ErrorMessages.DUPLICATE_CAP_INPUT.getCode()
                );
            }
        }
    }

    private void checkProDetCon(ProDetcon proDetcon) throws MicroServicesException {
        if (proDetcon.getCodentcon() == null || proDetcon.getCodentcon().isBlank()) throw new MicroServicesException(
            ErrorMessages.MANDATORY_CODENTCON.getUserMessage(),
            ErrorMessages.MANDATORY_CODENTCON.getCode()
        );
        if (
            proDetcon.getId() == 0 &&
            !detConRepository
                .findProDetconsByIdProAndTipentIgnoreCaseAndCodentcon(proDetcon.getIdPro(), proDetcon.getTipent(), proDetcon.getCodentcon())
                .isEmpty()
        ) throw new MicroServicesException(ErrorMessages.DUPLICATE_DETCONT.getUserMessage(), ErrorMessages.DUPLICATE_DETCONT.getCode());
    }

    @Override
    public void cancellaCapitoliProgetto(Integer idDetCon) throws MicroServicesException {
        impegniProgettoServiceImpl.checkDeleteDettaglioContabile(idDetCon);
        detConRepository.deleteById(idDetCon);
    }

    private void checkCodEntCon(String codEntCon) throws MicroServicesException {
        if (codEntCon == null || codEntCon.isBlank()) throw new MicroServicesException(
            ErrorMessages.MANDATORY_CODENTCON.getUserMessage(),
            ErrorMessages.MANDATORY_CODENTCON.getCode()
        );
    }

    @Override
    public DatiCapitolo getCapitoli(String codEntCon, String parameter) throws MicroServicesException {
        DatiCapitolo result = new DatiCapitolo();
        this.checkCodEntCon(codEntCon);
        String uri = contabilitaService.getUri(Parameters.RICERCACAPECAPU.getValue());
        if (parameter.equals(Parameters.CAPE.getValue())) uri += Parameters.URI_FINAL_STRING_CAPE.getValue(); else uri +=
            Parameters.URI_FINAL_STRING_CAPU.getValue();
        Optional<AmmPar> ricercaCapeCapuOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCACAPECAPU.getValue());
        ResponseEntity<BodyResponse> responseEntity = null;
        if (ricercaCapeCapuOpt.isPresent()) {
            if (ricercaCapeCapuOpt.get().getValore().equalsIgnoreCase(Parameters.WS.getValue())) {
                responseEntity = contabilitaService.clientApiContabilita(uri, codEntCon, null, HttpMethod.POST);
            } else {
                uri += "/";
                responseEntity = contabilitaService.clientExternalApiContabilita(uri, codEntCon, null, HttpMethod.GET);
            }
        }
        if (responseEntity.getBody() != null) {
            result = responseEntity.getBody().getCapitoli().get(0);
        }
        return result;
    }

    @Override
    public List<DatiCapitolo> ricercaCapitoli(FiltriCapitoli filtriCapitoli, String parameter) throws MicroServicesException {
        List<DatiCapitolo> result = new ArrayList<>();
        String uri = contabilitaService.getUri(Parameters.RICERCACAPECAPU.getValue());
        if (parameter.equals(Parameters.CAPE.getValue())) uri += Parameters.URI_FINAL_STRING_CAPE.getValue(); else uri +=
            Parameters.URI_FINAL_STRING_CAPU.getValue();
        Optional<AmmPar> ricercaCapeCapuOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCACAPECAPU.getValue());
        ResponseEntity<BodyResponse> responseEntity = null;
        if (ricercaCapeCapuOpt.isPresent()) {
            if (ricercaCapeCapuOpt.get().getValore().equalsIgnoreCase(Parameters.WS.getValue())) {
                responseEntity = contabilitaService.clientApiContabilita(uri, null, filtriCapitoli, HttpMethod.POST);
            } else {
                responseEntity = contabilitaService.clientExternalApiContabilita(uri, null, filtriCapitoli, HttpMethod.GET);
            }
        }
        if (responseEntity.getBody() != null) {
            result = responseEntity.getBody().getCapitoli();
        }
        return result;
    }

    private void ottieniCapitoli(List<ProDetcon> listaCapitoli, String uri, List<DettaglioCapitolo> listaRisultante)
        throws MicroServicesException {
        for (ProDetcon proDetcon : listaCapitoli) {
            DettaglioCapitolo dettaglioCapitolo = new DettaglioCapitolo();
            dettaglioCapitolo.setProDetcon(proDetcon);
            try {
                Optional<AmmPar> ricercaCapeCapuOpt = parametriRepository.getAmmParByCodiceIgnoreCase(
                    Parameters.RICERCACAPECAPU.getValue()
                );
                ResponseEntity<BodyResponse> responseEntity = null;
                if (ricercaCapeCapuOpt.isPresent()) {
                    if (ricercaCapeCapuOpt.get().getValore().equalsIgnoreCase(Parameters.WS.getValue())) {
                        responseEntity = contabilitaService.clientApiContabilita(uri, proDetcon.getCodentcon(), null, HttpMethod.POST);
                    } else {
                        uri += "/";
                        responseEntity =
                            contabilitaService.clientExternalApiContabilita(uri, proDetcon.getCodentcon(), null, HttpMethod.GET);
                    }
                }
                if (responseEntity.getBody() != null) {
                    dettaglioCapitolo.setDatiCapitolo(responseEntity.getBody().getCapitoli().get(0));
                    listaRisultante.add(dettaglioCapitolo);
                }
            } catch (MicroServicesException e) {
                if (e.getCode() == 570) {
                    listaRisultante.add(dettaglioCapitolo);
                } else throw new MicroServicesException(e.getUserMessage(), e.getCode());
            }
        }
    }
}
