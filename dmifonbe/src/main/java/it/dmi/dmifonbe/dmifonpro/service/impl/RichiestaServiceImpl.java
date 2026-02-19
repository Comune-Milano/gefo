package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmUte;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.repository.UtenteRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProRic;
import it.dmi.dmifonbe.dmifonpro.entities.ProRicute;
import it.dmi.dmifonbe.dmifonpro.model.*;
import it.dmi.dmifonbe.dmifonpro.repository.ProgettoRepository;
import it.dmi.dmifonbe.dmifonpro.repository.RicRepository;
import it.dmi.dmifonbe.dmifonpro.repository.RicUteRepository;
import it.dmi.dmifonbe.dmifonpro.service.EmailService;
import it.dmi.dmifonbe.dmifonpro.service.RichiestaService;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RichiestaServiceImpl implements RichiestaService {

    @Autowired
    RicUteRepository ricUteRepository;

    @Autowired
    RicRepository ricRepository;

    @Autowired
    FiltroProgetti filtroProgetti;

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    EntityManager em;

    @Autowired
    UtilService utilService;

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    EmailService emailService;

    @Value("${ricercaRichiestaBase}")
    String ricercaRichiestaBase;

    @Value("${ricercaRichiestaAutocomplete}")
    String ricercaRichiestaAutocomplete;

    @Value("${ricercaRichiestaCondIdPro}")
    String ricercaRichiestaCondIdPro;

    @Value("${ricercaRichiestaDirezione}")
    String ricercaRichiestaDirezione;

    @Value("${ricercaRichiestaCondTipFin}")
    String ricercaRichiestaCondTipFin;

    @Value("${ricercaRichiestaCondUtente}")
    String ricercaRichiestaCondUtente;

    @Value("${ricercaRichiestaCondStato}")
    String ricercaRichiestaCondStato;

    @Value("${ricercaRichiestaCondTipo}")
    String ricercaRichiestaCondTipo;

    @Value("${ricercaRichiestaCondRicPad}")
    String ricercaRichiestaCondRicPad;

    @Value("${ricercaRichiestaFine}")
    String ricercaRichiestaFine;

    @Override
    @Transactional
    public ResponseRichiesta getRichiesta(Integer id) throws MicroServicesException {
        Optional<ProRic> ric = ricRepository.findById(id);
        if (ric.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            ResponseRichiesta response = new ResponseRichiesta();
            List<AmmUte> ammUtes = new ArrayList<>();
            Hibernate.initialize(ric.get().getProProByIdPro());
            Hibernate.initialize(ric.get().getProRicutesById());
            response.setProRic(ric.get());
            if (!ric.get().getProRicutesById().isEmpty()) {
                for (ProRicute proRicute : ric.get().getProRicutesById()) {
                    Optional<AmmUte> ammUteOptional = utenteRepository.findById(proRicute.getIdUte());
                    ammUteOptional.ifPresent(ammUtes::add);
                }
                response.setUsers(ammUtes);
            }
            //recupero l'eventuale richiesta padre
            if (ric.get().getIdRicpad() != null) {
                Optional<ProRic> ricPad = ricRepository.findById(Integer.valueOf(ric.get().getIdRicpad().intValue()));
                if (ricPad.isPresent()) {
                    response.setProRicPad(ricPad.get());
                }
            }
            return response;
        }
    }

    @Override
    public ProRic inserisciRichiesta(ProRic ricDaInserire) throws MicroServicesException {
        this.checkRichiesta(ricDaInserire, false);
        List<ProRicute> proRicutes = new ArrayList<>();
        if (!ricDaInserire.getProRicutesById().isEmpty()) {
            proRicutes.addAll(ricDaInserire.getProRicutesById());
            ricDaInserire.setProRicutesById(null);
        }
        utilService.setInfoInsertRow(ricDaInserire);
        ProRic ricInserito = ricRepository.saveAndFlush(ricDaInserire);
        if (!proRicutes.isEmpty()) {
            this.checkRichiestaUtente(proRicutes);
            for (ProRicute proRicute : proRicutes) {
                proRicute.setIdRic((long) ricInserito.getId());
                utilService.setInfoInsertRow(proRicute);
            }
            List<ProRicute> proRicuteInseriti = ricUteRepository.saveAllAndFlush(proRicutes);
            if (ricInserito.getTipric().equalsIgnoreCase("c")) this.inviaMailUtenti(proRicuteInseriti, ricInserito, "commento");
        }
        return ricInserito;
    }

    @Override
    public void modificaRichiesta(ProRic ricDaModificare) throws MicroServicesException {
        this.checkRichiesta(ricDaModificare, true);
        if (!ricDaModificare.getProRicutesById().isEmpty()) {
            this.checkRichiestaUtente(ricDaModificare.getProRicutesById());
            for (ProRicute proRicute : ricDaModificare.getProRicutesById()) {
                if (proRicute.getId() == 0) {
                    utilService.setInfoInsertRow(proRicute);
                } else {
                    Optional<ProRicute> proRicuteOptional = ricUteRepository.findById(proRicute.getId());
                    if (proRicuteOptional.isPresent()) {
                        proRicute.setUsrCreate(proRicuteOptional.get().getUsrCreate());
                        proRicute.setDtCreate(proRicuteOptional.get().getDtCreate());
                    }
                    utilService.setInfoUpdateRow(proRicute);
                }
                ricUteRepository.saveAndFlush(proRicute);
            }
        }
        Optional<ProRic> ricOptional = ricRepository.findById(ricDaModificare.getId());
        if (ricOptional.isPresent()) {
            ricDaModificare.setUsrCreate(ricOptional.get().getUsrCreate());
            ricDaModificare.setDtCreate(ricOptional.get().getDtCreate());
        }
        utilService.setInfoUpdateRow(ricDaModificare);
        ProRic ricModificato = ricRepository.saveAndFlush(ricDaModificare);
        if (ricModificato.getTipric().equalsIgnoreCase("r")) {
            if (ricOptional.isPresent()) {
                ProRic ricPrecedente = ricOptional.get();
                if (!ricPrecedente.getStaric().equalsIgnoreCase("INV") && ricModificato.getStaric().equalsIgnoreCase("INV")) {
                    this.inviaMailUtenti(ricDaModificare.getProRicutesById(), ricModificato, "richiesta");
                    ricModificato.setDtainv(new Date());
                    ricRepository.saveAndFlush(ricModificato);
                }
                if (
                    (!ricPrecedente.getStaric().equalsIgnoreCase("ESE") && ricModificato.getStaric().equalsIgnoreCase("ESE")) ||
                    (!ricPrecedente.getStaric().equalsIgnoreCase("RES") && ricModificato.getStaric().equalsIgnoreCase("RES"))
                ) {
                    this.inviaMailRichiedente(ricModificato);
                }
            }
        }
    }

    private void checkRichiesta(ProRic ric, Boolean isUpdate) throws MicroServicesException {
        if (Boolean.TRUE.equals(isUpdate)) {
            if (ric.getId() <= 0) throw new MicroServicesException(
                ErrorMessages.IDRICHIESTA_REQUIRED.getUserMessage(),
                ErrorMessages.IDRICHIESTA_REQUIRED.getCode()
            );
            if (!ricRepository.existsById(ric.getId())) throw new MicroServicesException(
                ErrorMessages.NO_DATA_FOUND.getUserMessage(),
                ErrorMessages.NO_DATA_FOUND.getCode()
            );
        } else if (ric.getId() != 0) {
            throw new MicroServicesException(
                ErrorMessages.IDRICHIESTA_NOT_ALLOWED.getUserMessage(),
                ErrorMessages.IDRICHIESTA_NOT_ALLOWED.getCode()
            );
        }
        if (ric.getIdPro() == null || ric.getIdPro().equals(0L)) throw new MicroServicesException(
            ErrorMessages.MANDATORY_IDPRO.getUserMessage(),
            ErrorMessages.MANDATORY_IDPRO.getCode()
        ); else if (!progettoRepository.existsById(ric.getIdPro().intValue())) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND_PRO.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND_PRO.getCode()
        );
        if ((ric.getTipric() == null || ric.getTipric().isBlank())) throw new MicroServicesException(
            ErrorMessages.MANDATORY_TIPRIC.getUserMessage(),
            ErrorMessages.MANDATORY_TIPRIC.getCode()
        );
        if (ric.getDesric() == null || ric.getDesric().isBlank()) throw new MicroServicesException(
            ErrorMessages.MANDATORY_DESRIC.getUserMessage(),
            ErrorMessages.MANDATORY_DESRIC.getCode()
        );
        if (!ric.getTipric().equalsIgnoreCase("c") && !ric.getTipric().equalsIgnoreCase("r")) throw new MicroServicesException(
            ErrorMessages.WRONG_TIPRIC.getUserMessage(),
            ErrorMessages.WRONG_TIPRIC.getCode()
        );
        if (ric.getTipric().equalsIgnoreCase("r")) {
            if (ric.getStaric() == null || ric.getTipric().isBlank()) throw new MicroServicesException(
                ErrorMessages.MANDATORY_STARIC.getUserMessage(),
                ErrorMessages.MANDATORY_STARIC.getCode()
            );
            if (
                !ric.getStaric().equalsIgnoreCase("INI") &&
                !ric.getStaric().equalsIgnoreCase("INV") &&
                !ric.getStaric().equalsIgnoreCase("ESE") &&
                !ric.getStaric().equalsIgnoreCase("RES") &&
                !ric.getStaric().equalsIgnoreCase("ANN")
            ) throw new MicroServicesException(ErrorMessages.WRONG_STARIC.getUserMessage(), ErrorMessages.WRONG_STARIC.getCode());
            if (ric.getDtasca() == null) throw new MicroServicesException(
                ErrorMessages.MANDATORY_DTASCA.getUserMessage(),
                ErrorMessages.MANDATORY_DTASCA.getCode()
            );
            if (
                (ric.getStaric().equalsIgnoreCase("ese") || ric.getStaric().equalsIgnoreCase("res")) &&
                (ric.getRisric() == null || ric.getRisric().isBlank())
            ) throw new MicroServicesException(ErrorMessages.MANDATORY_RISRIC.getUserMessage(), ErrorMessages.MANDATORY_RISRIC.getCode());
            if (Boolean.FALSE.equals(isUpdate)) {
                if (ric.getProRicutesById().isEmpty()) throw new MicroServicesException(
                    ErrorMessages.MANDATORY_RICUTE.getUserMessage(),
                    ErrorMessages.MANDATORY_RICUTE.getCode()
                ); else {
                    if (
                        ric.getProRicutesById().isEmpty() && ricUteRepository.findByIdRic((long) ric.getId()).isEmpty()
                    ) throw new MicroServicesException(
                        ErrorMessages.MANDATORY_RICUTE.getUserMessage(),
                        ErrorMessages.MANDATORY_RICUTE.getCode()
                    );
                }
            }
        }
    }

    private void checkRichiestaUtente(List<ProRicute> richiestaUtenteList) throws MicroServicesException {
        for (ProRicute proRicute : richiestaUtenteList) {
            if (!utenteRepository.existsById(proRicute.getIdUte())) throw new MicroServicesException(
                ErrorMessages.USER_NOT_PRESENT.getUserMessage(),
                ErrorMessages.USER_NOT_PRESENT.getCode()
            );
        }
    }

    @Override
    public void cancellaRichiesta(Integer idRic) throws MicroServicesException {
        this.checkDeleteRichiesta(idRic);
        ricRepository.deleteById(idRic);
    }

    @Override
    public void cancellaRichiestaUtente(Integer idRicUte) throws MicroServicesException {
        this.checkDeleteRichiesta(idRicUte);
        ricUteRepository.deleteById(idRicUte);
    }

    private void checkDeleteRichiesta(Integer id) throws MicroServicesException {
        if (id == null || id.equals(0)) throw new MicroServicesException(
            ErrorMessages.MANDATORY_IDRIC.getUserMessage(),
            ErrorMessages.MANDATORY_IDRIC.getCode()
        );
        if (!ricUteRepository.existsById(id) && !ricRepository.existsById(id)) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
    }

    @Override
    @Transactional
    public List<RichiestaDetail> ricercaRichiesta(RichiestaRicerca ricRic, Integer idUteRuo) {
        filtroProgetti.generateFiltroProgetti(idUteRuo);
        String queryStr = ricercaRichiestaBase;
        if (ricRic.getAutocomplete() != null && !ricRic.getAutocomplete().isEmpty()) queryStr += " " + ricercaRichiestaAutocomplete;
        if (ricRic.getIdProgetto() > 0) queryStr += " " + ricercaRichiestaCondIdPro;
        if (Boolean.TRUE.equals(filtroProgetti.isFiltered())) queryStr += " " + ricercaRichiestaDirezione;
        if (ricRic.getTipoFinanziamento() > 0) queryStr += " " + ricercaRichiestaCondTipFin;
        if (ricRic.getUtente() != null && ricRic.getUtente() > 0) queryStr += " " + ricercaRichiestaCondUtente;
        if (ricRic.getStato() != null && !ricRic.getStato().isBlank()) queryStr += " " + ricercaRichiestaCondStato;
        if (ricRic.getTipo() != null && !ricRic.getTipo().isBlank()) queryStr += " " + ricercaRichiestaCondTipo;
        if (ricRic.getProRicPad() != null && ricRic.getProRicPad() > 0) queryStr += " " + ricercaRichiestaCondRicPad;
        queryStr += " " + ricercaRichiestaFine;

        Query query = em.createQuery(queryStr);
        if (ricRic.getAutocomplete() != null && !ricRic.getAutocomplete().isEmpty()) query.setParameter(
            "AUTOCOMPLETE",
            "%" + ricRic.getAutocomplete().toUpperCase() + "%"
        );
        if (ricRic.getIdProgetto() > 0) query.setParameter("ID_PRO", ricRic.getIdProgetto());
        if (Boolean.TRUE.equals(filtroProgetti.isFiltered())) query.setParameter("DIR", filtroProgetti.getIdDirezione());
        if (ricRic.getTipoFinanziamento() > 0) query.setParameter("ID_TIPFIN", ricRic.getTipoFinanziamento());
        if (ricRic.getUtente() != null && ricRic.getUtente() > 0) query.setParameter("ID_UTE", ricRic.getUtente());
        if (ricRic.getStato() != null && !ricRic.getStato().isBlank()) query.setParameter("STATO", ricRic.getStato());
        if (ricRic.getTipo() != null && !ricRic.getTipo().isBlank()) query.setParameter("TIPO", ricRic.getTipo());
        if (ricRic.getProRicPad() != null && ricRic.getProRicPad() > 0) query.setParameter("RICPAD", ricRic.getProRicPad());
        List<ProRic> resultsProRic = query.getResultList();
        List<RichiestaDetail> results = new ArrayList<>();
        if (!resultsProRic.isEmpty()) for (ProRic proRic : resultsProRic) {
            Hibernate.initialize(proRic.getProProByIdPro());
            Hibernate.initialize(proRic.getProRicutesById());
            RichiestaDetail rd = new RichiestaDetail();
            String username = "";
            rd.setProRic(proRic);
            if (!proRic.getProRicutesById().isEmpty()) {
                Integer idUte = proRic.getProRicutesById().get(0).getIdUte();
                if (idUte != null && !idUte.equals(0)) {
                    Optional<AmmUte> ammUteOptional = utenteRepository.findById(idUte);
                    if (ammUteOptional.isPresent()) username = ammUteOptional.get().getUsername();
                }
            }
            rd.setUsername(username);
            results.add(rd);
        }
        return results;
    }

    @Override
    public List<ProRic> ricercaRichiesta(String autocomplete, Integer idUteRuo) throws MicroServicesException {
        if (autocomplete != null && !autocomplete.isBlank()) {
            filtroProgetti.generateFiltroProgetti(idUteRuo);
            if (filtroProgetti.isFiltered()) return ricRepository.findAutocompleteByDir(
                autocomplete.toUpperCase(),
                filtroProgetti.getIdDirezione()
            ); else return ricRepository.findAutocomplete(autocomplete.toUpperCase());
        } else throw new MicroServicesException(
            ErrorMessages.AUTOCOMPLETE_BLANK.getUserMessage(),
            ErrorMessages.AUTOCOMPLETE_BLANK.getCode()
        );
    }

    private void inviaMailUtenti(List<ProRicute> proRicutes, ProRic proRic, String tipoMail) throws MicroServicesException {
        for (ProRicute proRicute : proRicutes) {
            Optional<AmmUte> ammUteOptional = utenteRepository.findById(proRicute.getIdUte());
            if (ammUteOptional.isPresent()) {
                if (ammUteOptional.get().getEmail() != null && !ammUteOptional.get().getEmail().isBlank()) this.inviaMail(
                        proRic,
                        ammUteOptional.get().getEmail(),
                        tipoMail
                    ); else if (ammUteOptional.get().getEmailalt() != null && !ammUteOptional.get().getEmailalt().isBlank()) this.inviaMail(
                        proRic,
                        ammUteOptional.get().getEmailalt(),
                        tipoMail
                    );
            }
        }
    }

    private void inviaMailRichiedente(ProRic proRic) throws MicroServicesException {
        Optional<AmmUte> ammUteOptional = utenteRepository.findByUsername(proRic.getUsrCreate());
        if (ammUteOptional.isPresent()) {
            if (ammUteOptional.get().getEmail() != null && !ammUteOptional.get().getEmail().isBlank()) this.inviaMail(
                    proRic,
                    ammUteOptional.get().getEmail(),
                    "risposta"
                ); else if (ammUteOptional.get().getEmailalt() != null && !ammUteOptional.get().getEmailalt().isBlank()) this.inviaMail(
                    proRic,
                    ammUteOptional.get().getEmailalt(),
                    "risposta"
                );
        }
    }

    private void inviaMail(ProRic proRic, String mailDestinatario, String tipoMail) throws MicroServicesException {
        ProgettoAndProgettoPadre progettoAndProgettoPadre = utilService.getProgettoAndProgettoPadre(proRic.getIdPro().intValue());
        String tipRic = tipoMail.equalsIgnoreCase("risposta") ? proRic.getRisric() : proRic.getDesric();
        String mailSubj =
            tipoMail +
            " per il progetto:" +
            progettoAndProgettoPadre.getProgetto().getCodpro() +
            " " +
            progettoAndProgettoPadre.getProgetto().getDespro() +
            " - progetto padre: " +
            progettoAndProgettoPadre.getProgettoPadreLabel();
        String mailBody =
            "Per il progetto: " +
            progettoAndProgettoPadre.getProgetto().getCodpro() +
            " " +
            progettoAndProgettoPadre.getProgetto().getDespro() +
            " - progetto padre: " +
            progettoAndProgettoPadre.getProgettoPadreLabel() +
            " è stato inserito/a un/a " +
            tipoMail +
            ": " +
            tipRic;
        try {
            emailService.sendSimpleMessage(mailDestinatario, mailSubj, mailBody);
        } catch (Exception e) {
            throw new MicroServicesException(ErrorMessages.EMAIL_ERROR.getUserMessage(), ErrorMessages.EMAIL_ERROR.getCode());
        }
    }
}
