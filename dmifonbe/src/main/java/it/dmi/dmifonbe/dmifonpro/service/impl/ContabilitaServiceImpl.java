package it.dmi.dmifonbe.dmifonpro.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.service.ContabilitaService;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ContabilitaServiceImpl implements ContabilitaService {

    @Autowired
    ParametriRepository parametriRepository;

    @Override
    public ResponseEntity<BodyResponse> clientExternalApiContabilita(String uri, String codEntCon, Object ricEntCon, HttpMethod httpMethod)
        throws MicroServicesException {
        if (codEntCon != null) uri += codEntCon; else if (ricEntCon != null) uri = this.externalUriBuilder(uri, ricEntCon);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(this.obtainToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<LinkedHashMap> responseEntity;
        ResponseContabilitaEsterna bodyResponse;
        ResponseContabilitaEsternaNoArray bodyResponseNoArray;
        ObjectMapper mapper = new ObjectMapper();
        try {
            responseEntity = restTemplate.exchange(uri, httpMethod, entity, LinkedHashMap.class);
        } catch (Exception e) {
            throw new MicroServicesException(
                ErrorMessages.ACCOUNTING_API_ERROR.getUserMessage(),
                ErrorMessages.ACCOUNTING_API_ERROR.getCode()
            );
        }
        try {
            bodyResponse = mapper.convertValue(responseEntity.getBody(), ResponseContabilitaEsterna.class);
        } catch (IllegalArgumentException ie) {
            //nel caso di errore verifico se mi ha restituito un elemento senza array
            try {
                //responseEntity = restTemplate.exchange(uri, httpMethod, entity, LinkedHashMap.class);
                bodyResponseNoArray = mapper.convertValue(responseEntity.getBody(), ResponseContabilitaEsternaNoArray.class);
                bodyResponse = new ResponseContabilitaEsterna();
                bodyResponse.setResult(new BodyResponse());
                //
                List<DatiCapitolo> capitoli = new ArrayList<>();
                capitoli.add(bodyResponseNoArray.getResult().getCapitoli());
                bodyResponse.getResult().setCapitoli(capitoli);
                //
                List<ContentResponse> impegni = new ArrayList<>();
                impegni.add(bodyResponseNoArray.getResult().getImpegni());
                bodyResponse.getResult().setImpegni(impegni);
                //
                List<ContentResponse> accertamenti = new ArrayList<>();
                accertamenti.add(bodyResponseNoArray.getResult().getAccertamenti());
                bodyResponse.getResult().setAccertamenti(accertamenti);
                //
                List<DatiMandato> mandati = new ArrayList<>();
                mandati.add(bodyResponseNoArray.getResult().getMandati());
                bodyResponse.getResult().setMandati(mandati);
                //
                List<ContentResponseCrono> crono = new ArrayList<>();
                crono.add(bodyResponseNoArray.getResult().getCrono());
                bodyResponse.getResult().setCrono(crono);
                //
                List<ContentResponseCrono> cronoprogrammi = new ArrayList<>();
                cronoprogrammi.add(bodyResponseNoArray.getResult().getCronoprogrammi());
                bodyResponse.getResult().setCronoprogrammi(cronoprogrammi);
            } catch (Exception ex) {
                throw new MicroServicesException(
                    ErrorMessages.ACCOUNTING_API_ERROR.getUserMessage(),
                    ErrorMessages.ACCOUNTING_API_ERROR.getCode()
                );
            }
        } catch (Exception e) {
            throw new MicroServicesException(
                ErrorMessages.ACCOUNTING_API_ERROR.getUserMessage(),
                ErrorMessages.ACCOUNTING_API_ERROR.getCode()
            );
        }
        if (responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT)) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND_CAP.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND_CAP.getCode()
        );
        return new ResponseEntity<>(bodyResponse.getResult(), HttpStatus.OK);
    }

    private String externalUriBuilder(String uri, Object ricEntConObj) {
        if (ricEntConObj instanceof RicercaEntitaContabile) {
            RicercaEntitaContabile ricEntCon = (RicercaEntitaContabile) ricEntConObj;
            return UriComponentsBuilder.fromUriString(uri).queryParams(ricEntCon.toMultiValueMap()).build().toString();
        } else if (ricEntConObj instanceof FiltriCapitoli) {
            FiltriCapitoli ricEntCon = (FiltriCapitoli) ricEntConObj;
            return UriComponentsBuilder.fromUriString(uri).queryParams(ricEntCon.toMultiValueMap()).build().toString();
        } else if (ricEntConObj instanceof FiltriMandati) {
            FiltriMandati ricEntCon = (FiltriMandati) ricEntConObj;
            return UriComponentsBuilder.fromUriString(uri).queryParams(ricEntCon.toMultiValueMap()).build().toString();
        }
        return null;
    }

    private String obtainToken() throws MicroServicesException {
        Optional<AmmPar> ammParClientID = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.APICLIENTID.getValue());
        Optional<AmmPar> ammParClientSecret = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.APICLIENTSECRET.getValue());
        Optional<AmmPar> ammParApiUriToken = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.APIURITOKEN.getValue());
        if (ammParClientID.isPresent() && ammParClientSecret.isPresent()) {
            String clientId = ammParClientID.get().getValore();
            String clientSecret = ammParClientSecret.get().getValore();
            String apiUriToken = ammParApiUriToken.get().getValore();
            String body = ("grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret + "&scope=openId");
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            try {
                ResponseEntity<HashMap> responseEntity = restTemplate.exchange(apiUriToken, HttpMethod.POST, entity, HashMap.class);
                if (responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT)) throw new MicroServicesException(
                    ErrorMessages.NO_DATA_FOUND.getUserMessage(),
                    ErrorMessages.NO_DATA_FOUND.getCode()
                );
                return (String) responseEntity.getBody().get(Parameters.ACCESSTOKENKEY.getValue());
            } catch (Exception e) {
                throw new MicroServicesException(
                    ErrorMessages.ACCOUNTING_API_ERROR.getUserMessage(),
                    ErrorMessages.ACCOUNTING_API_ERROR.getCode()
                );
            }
        } else throw new MicroServicesException(
            ErrorMessages.PARAMETERS_API_NOT_FOUND.getUserMessage(),
            ErrorMessages.PARAMETERS_API_NOT_FOUND.getCode()
        );
    }

    @Override
    public ResponseEntity<BodyResponse> clientApiContabilita(String uri, String codEntCon, Object ricEntCon, HttpMethod httpMethod)
        throws MicroServicesException {
        String body;
        if (codEntCon != null) body = this.getBody(codEntCon, null); else body = this.getBody(null, ricEntCon);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<LinkedHashMap> responseEntity;
        BodyResponse bodyResponse;
        try {
            responseEntity = restTemplate.exchange(uri, httpMethod, entity, LinkedHashMap.class);
            ObjectMapper mapper = new ObjectMapper();
            bodyResponse = mapper.convertValue(responseEntity.getBody(), BodyResponse.class);
        } catch (Exception e) {
            throw new MicroServicesException(
                ErrorMessages.ACCOUNTING_API_ERROR.getUserMessage(),
                ErrorMessages.ACCOUNTING_API_ERROR.getCode()
            );
        }
        if (responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT)) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND_CAP.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND_CAP.getCode()
        );
        return new ResponseEntity<>(bodyResponse, HttpStatus.OK);
    }

    @Override
    public String getUri(String parameter) throws MicroServicesException {
        String uri = "";
        Optional<AmmPar> ricercaImpAcceOpt = parametriRepository.getAmmParByCodiceIgnoreCase(parameter);
        if (ricercaImpAcceOpt.isPresent()) {
            if (ricercaImpAcceOpt.get().getValore().equalsIgnoreCase(Parameters.WS.getValue())) {
                Optional<AmmPar> wsEndpointOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.WSENDPOINT.getValue());
                if (wsEndpointOpt.isPresent()) uri = wsEndpointOpt.get().getValore(); else throw new MicroServicesException(
                    ErrorMessages.WSENDPOINT_NOT_FOUND.getUserMessage(),
                    ErrorMessages.WSENDPOINT_NOT_FOUND.getCode()
                );
            } else {
                Optional<AmmPar> apiEndpointOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.APIENDPOINT.getValue());
                if (apiEndpointOpt.isPresent()) uri = apiEndpointOpt.get().getValore(); else throw new MicroServicesException(
                    ErrorMessages.APIENDPOINT_NOT_FOUND.getUserMessage(),
                    ErrorMessages.APIENDPOINT_NOT_FOUND.getCode()
                );
            }
        } else throw new MicroServicesException(
            ErrorMessages.WSENDPOINT_NOT_FOUND.getUserMessage(),
            ErrorMessages.WSENDPOINT_NOT_FOUND.getCode()
        );
        return uri;
    }

    @Override
    public String getBody(String codEntCon, Object ricEntCon) throws MicroServicesException {
        String wsEnte = "";
        String codiceUtente = "";
        Optional<AmmPar> wsEnteOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.WSENTE.getValue());
        if (wsEnteOpt.isPresent()) wsEnte = wsEnteOpt.get().getValore(); else throw new MicroServicesException(
            ErrorMessages.WSENTE_NOT_FOUND.getUserMessage(),
            ErrorMessages.WSENTE_NOT_FOUND.getCode()
        );
        Optional<AmmPar> codiceUtenteOpt = parametriRepository.getAmmParByCodiceIgnoreCase(Parameters.WSCODICEUTENTE.getValue());
        if (codiceUtenteOpt.isPresent()) codiceUtente = codiceUtenteOpt.get().getValore(); else throw new MicroServicesException(
            ErrorMessages.WSCODICEUTENTE_NOT_FOUND.getUserMessage(),
            ErrorMessages.WSCODICEUTENTE_NOT_FOUND.getCode()
        );
        JSONObject bodyjson = new JSONObject();
        bodyjson.put("ente", wsEnte);
        bodyjson.put("codiceUtente", codiceUtente);
        if (codEntCon != null && !codEntCon.equals("")) bodyjson.put("id", codEntCon);
        if (ricEntCon != null) {
            if (ricEntCon instanceof RicercaEntitaContabile) {
                this.bodyRicercaEntitaContabile((RicercaEntitaContabile) ricEntCon, bodyjson);
            } else if (ricEntCon instanceof FiltriCapitoli) {
                this.bodyFiltriCapitoli((FiltriCapitoli) ricEntCon, bodyjson);
            } else if (ricEntCon instanceof FiltriMandati) {
                this.bodyFiltriMandati((FiltriMandati) ricEntCon, bodyjson);
            }
        }
        return bodyjson.toString();
    }

    private void bodyFiltriCapitoli(FiltriCapitoli ricEntCon, JSONObject bodyjson) {
        if (ricEntCon.getDescrizione() != null && !ricEntCon.getDescrizione().equals("")) bodyjson.put(
            "descrizione",
            ricEntCon.getDescrizione()
        );
        if (ricEntCon.getTitolo() != null && !ricEntCon.getTitolo().equals("")) bodyjson.put("titolo", ricEntCon.getTitolo());
        if (ricEntCon.getMacro() != null && !ricEntCon.getMacro().equals("")) bodyjson.put("macro", ricEntCon.getMacro());
        if (ricEntCon.getMissione() != null && !ricEntCon.getMissione().equals("")) bodyjson.put("missione", ricEntCon.getMissione());
        if (ricEntCon.getProgramma() != null && !ricEntCon.getProgramma().equals("")) bodyjson.put("programma", ricEntCon.getProgramma());
    }

    private void bodyRicercaEntitaContabile(RicercaEntitaContabile ricEntCon, JSONObject bodyjson) {
        if (ricEntCon.getId() != null && !ricEntCon.getId().equals("")) bodyjson.put("id", ricEntCon.getId());
        if (ricEntCon.getDescrizione() != null && !ricEntCon.getDescrizione().equals("")) bodyjson.put(
            "descrizione",
            ricEntCon.getDescrizione()
        );
        if (ricEntCon.getId_capitolo() != null && !ricEntCon.getId_capitolo().equals("")) bodyjson.put(
            "id_capitolo",
            ricEntCon.getId_capitolo()
        );
        if (ricEntCon.getId_crono() != null && !ricEntCon.getId_crono().equals("")) bodyjson.put("id_crono", ricEntCon.getId_crono());
        if (ricEntCon.getCup() != null && !ricEntCon.getCup().equals("")) bodyjson.put("cup", ricEntCon.getCup());
        if (ricEntCon.getCig() != null && !ricEntCon.getCig().equals("")) bodyjson.put("cig", ricEntCon.getCig());
        if (ricEntCon.getAnno() != null && !ricEntCon.getAnno().equals("")) bodyjson.put("anno", ricEntCon.getAnno());
    }

    private void bodyFiltriMandati(FiltriMandati ricEntCon, JSONObject bodyjson) {
        if (ricEntCon.getId_mandato() != null && !ricEntCon.getId_mandato().equals("")) bodyjson.put(
            "id_mandato",
            ricEntCon.getId_mandato()
        );
        if (ricEntCon.getId_impegno() != null && !ricEntCon.getId_impegno().equals("")) bodyjson.put(
            "id_impegno",
            ricEntCon.getId_impegno()
        );
        if (ricEntCon.getCig() != null && !ricEntCon.getCig().equals("")) bodyjson.put("cig", ricEntCon.getCig());
        if (ricEntCon.getAnno_mandato() != null && !ricEntCon.getAnno_mandato().equals("")) bodyjson.put(
            "anno_mandato",
            ricEntCon.getAnno_mandato()
        );
        if (ricEntCon.getId_pro() != null) bodyjson.put("id_pro", ricEntCon.getId_pro());
        if (ricEntCon.getFlgNoDdr() != null && !ricEntCon.getFlgNoDdr().equals("")) bodyjson.put("flgNoDdr", ricEntCon.getFlgNoDdr());
    }
}
