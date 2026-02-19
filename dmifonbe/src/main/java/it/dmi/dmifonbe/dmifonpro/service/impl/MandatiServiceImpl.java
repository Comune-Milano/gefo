package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntconman;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.service.ContabilitaService;
import it.dmi.dmifonbe.dmifonpro.service.MandatiService;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:Queries/queries.properties")
public class MandatiServiceImpl implements MandatiService {

    @Autowired
    ContabilitaService contabilitaService;

    @Autowired
    ParametriRepository parametriRepository;

    @Override
    public MandatoResponse getMandato(String codEntCon) throws MicroServicesException, ParseException {
        MandatoResponse result = new MandatoResponse();
        this.checkCodEntCon(codEntCon);
        String uri = contabilitaService.getUri(Parameters.RICERCAMAND.getValue()) + Parameters.MANDATI.getValue();
        Optional<AmmPar> ricercaMandOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCAMAND.getValue());
        ResponseEntity<BodyResponse> responseEntity = null;
        if (ricercaMandOpt.isPresent()) {
            if (ricercaMandOpt.get().getValore().equalsIgnoreCase(Parameters.WS.getValue())) {
                responseEntity = contabilitaService.clientApiContabilita(uri, codEntCon, null, HttpMethod.POST);
            } else {
                uri += "/";
                responseEntity = contabilitaService.clientExternalApiContabilita(uri, codEntCon, null, HttpMethod.GET);
            }
        }
        if (responseEntity.getBody() != null) {
            result = valorizzaResultRicerca(responseEntity.getBody().getMandati().get(0));
        }
        return result;
    }

    @Override
    public List<MandatoResponse> ricercaMandato(MandatoRicerca mandatoRicerca) throws MicroServicesException, ParseException {
        List<MandatoResponse> result = new ArrayList<>();
        FiltriMandati filtriMandati = new FiltriMandati();
        filtriMandati.setId_mandato(mandatoRicerca.getId_mandato());
        filtriMandati.setAnno_mandato(mandatoRicerca.getAnno_mandato());
        filtriMandati.setId_impegno(mandatoRicerca.getId_impegno());
        filtriMandati.setCig(mandatoRicerca.getCig());
        String uri = contabilitaService.getUri(Parameters.RICERCAMAND.getValue()) + Parameters.MANDATI.getValue();
        Optional<AmmPar> ricercaMandOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.RICERCAMAND.getValue());
        ResponseEntity<BodyResponse> responseEntity = null;
        if (ricercaMandOpt.isPresent()) {
            if (ricercaMandOpt.get().getValore().equalsIgnoreCase(Parameters.WS.getValue())) {
                responseEntity = contabilitaService.clientApiContabilita(uri, null, filtriMandati, HttpMethod.POST);
            } else {
                responseEntity = contabilitaService.clientExternalApiContabilita(uri, null, filtriMandati, HttpMethod.GET);
            }
        }
        if (responseEntity.getBody() != null) {
            for (DatiMandato datiMandato : responseEntity.getBody().getMandati()) {
                result.add(valorizzaResultRicerca(datiMandato));
            }
        }
        return result;
    }

    private MandatoResponse valorizzaResultRicerca(DatiMandato datiMandato) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN);
        MandatoResponse res = new MandatoResponse();
        ProEntconman proEntconman = new ProEntconman();
        proEntconman.setCodentcon(datiMandato.getId());
        proEntconman.setTipent(Parameters.TIPENTMAN.getValue());
        proEntconman.setIdman(datiMandato.getId_mandato());
        proEntconman.setAnnman(Integer.parseInt(datiMandato.getAnno_mandato()));
        proEntconman.setRagsoc(datiMandato.getRagione_sociale());
        proEntconman.setBencodfis(datiMandato.getBeneficiario_codice_fiscale());
        proEntconman.setBencodiva(datiMandato.getBeneficiario_partita_iva());
        proEntconman.setBenragsoc(datiMandato.getBeneficiario_ragione_sociale());
        proEntconman.setCodcup(datiMandato.getCup());
        proEntconman.setCodiva(datiMandato.getPartita_iva());
        proEntconman.setCodfis(datiMandato.getCodice_fiscale());
        proEntconman.setDesfat(datiMandato.getDescrizione_fattura());
        proEntconman.setCodcig(datiMandato.getCig());
        proEntconman.setDtapag(formatter.parse(datiMandato.getData_pagamento()));
        proEntconman.setDtareg(formatter.parse(datiMandato.getData_registrazione()));
        proEntconman.setIdatt(datiMandato.getId_atto());
        proEntconman.setIdimp(datiMandato.getId_impegno());
        proEntconman.setNroipg(Long.parseLong(datiMandato.getNumero_impegno()));
        proEntconman.setNroman(Long.parseLong(datiMandato.getNumero_mandato()));
        proEntconman.setTipatt(datiMandato.getTipo_atto());
        proEntconman.setImpman(new BigDecimal(datiMandato.getImporto_mandato()));
        proEntconman.setTipele(datiMandato.getTipo());
        res.setProEntconman(proEntconman);
        return res;
    }

    private void checkCodEntCon(String codEntCon) throws MicroServicesException {
        if (codEntCon == null || codEntCon.isBlank()) throw new MicroServicesException(
            ErrorMessages.MANDATORY_CODENTCON.getUserMessage(),
            ErrorMessages.MANDATORY_CODENTCON.getCode()
        );
    }
}
