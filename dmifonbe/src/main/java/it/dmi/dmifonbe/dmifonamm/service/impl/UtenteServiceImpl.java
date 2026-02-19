package it.dmi.dmifonbe.dmifonamm.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmDir;
import it.dmi.dmifonbe.dmifonamm.entities.AmmRuo;
import it.dmi.dmifonbe.dmifonamm.entities.AmmUte;
import it.dmi.dmifonbe.dmifonamm.entities.AmmUteruo;
import it.dmi.dmifonbe.dmifonamm.repository.AmmUteRuoRepository;
import it.dmi.dmifonbe.dmifonamm.repository.UtenteRepository;
import it.dmi.dmifonbe.dmifonamm.service.DirezioneService;
import it.dmi.dmifonbe.dmifonamm.service.RuoloService;
import it.dmi.dmifonbe.dmifonamm.service.UtenteService;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    RuoloService ruoloService;

    @Autowired
    DirezioneService direzioneService;

    @Autowired
    AmmUteRuoRepository ammUteRuoRepository;

    @Override
    @Transactional
    public AmmUte getUtente(Integer id, Integer idUteRuo) throws MicroServicesException {
        this.checkCallGetUtente(id, idUteRuo);
        return this.getUtente(id);
    }

    private void checkCallGetUtente(Integer id, Integer idUteRuo) throws MicroServicesException {
        Optional<AmmUteruo> ammUteruoOptional = ammUteRuoRepository.findById(idUteRuo);
        if (ammUteruoOptional.isPresent()) {
            Integer idUte = Math.toIntExact(ammUteruoOptional.get().getIdUte());
            Long idRuo = ammUteruoOptional.get().getIdRuo();
            if (idRuo != 2 && !Objects.equals(idUte, id)) {
                throw new MicroServicesException(
                    ErrorMessages.USER_NOT_ABILITATE.getUserMessage(),
                    ErrorMessages.USER_NOT_ABILITATE.getCode()
                );
            }
        } else throw new MicroServicesException(
            ErrorMessages.AMMUTERUO_NOT_PRESENT.getUserMessage(),
            ErrorMessages.AMMUTERUO_NOT_PRESENT.getCode()
        );
    }

    @Transactional
    public AmmUte getUtente(Integer id) throws MicroServicesException {
        Optional<AmmUte> utenteOpt = utenteRepository.findById(id);
        if (utenteOpt.isEmpty()) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        ); else {
            AmmUte utente = utenteOpt.get();
            Hibernate.initialize(utente.getAmmUteruosById());
            return utente;
        }
    }

    @Override
    @Transactional
    public AmmUte inserisciUtente(AmmUte utenteDaInserire) throws MicroServicesException {
        if (utenteDaInserire.getId() != 0) throw new MicroServicesException(
            ErrorMessages.IDUSER_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.IDUSER_NOT_ALLOWED.getCode()
        );
        if (!this.ricercaUtente(utenteDaInserire.getUsername(), null, null).isEmpty()) throw new MicroServicesException(
            ErrorMessages.USER_ALREADY_EXIST.getUserMessage(),
            ErrorMessages.USER_ALREADY_EXIST.getCode()
        );
        utilService.setInfoInsertRow(utenteDaInserire);
        return utenteRepository.saveAndFlush(utenteDaInserire);
    }

    @Override
    @Transactional
    public void abilitazioneUtente(Integer idUtente, boolean abilitato) throws MicroServicesException {
        AmmUte utente = this.getUtente(idUtente);
        if (!utente.getAbilitato().equals(abilitato)) utente.setAbilitato(abilitato); else throw new MicroServicesException(
            ErrorMessages.USER_DISABLE_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.USER_DISABLE_NOT_ALLOWED.getCode()
        );
        utilService.setInfoUpdateRow(utente);
        utenteRepository.saveAndFlush(utente);
    }

    @Override
    @Transactional
    public AmmUte modificaUtente(AmmUte utenteDaModificare) throws MicroServicesException {
        AmmUte user = this.getUtente(utenteDaModificare.getId());
        if (!user.getAbilitato().equals(utenteDaModificare.getAbilitato())) throw new MicroServicesException(
            ErrorMessages.MODIFY_USER_DISABLE_NOT_ALLOWED.getUserMessage(),
            ErrorMessages.MODIFY_USER_DISABLE_NOT_ALLOWED.getCode()
        );
        if (!user.getUsername().equals(utenteDaModificare.getUsername())) throw new MicroServicesException(
            ErrorMessages.USERNAME_NOT_EDITABLE.getUserMessage(),
            ErrorMessages.USERNAME_NOT_EDITABLE.getCode()
        );
        utenteDaModificare.setUsrCreate(user.getUsrCreate());
        utenteDaModificare.setDtCreate(user.getDtCreate());
        utilService.setInfoUpdateRow(utenteDaModificare);
        for (AmmUteruo ammUteruo : utenteDaModificare.getAmmUteruosById()) {
            if (ammUteruo.getId() == 0) {
                if (
                    ammUteRuoRepository.findByIdUteAndIdRuoAndIdDir(ammUteruo.getIdUte(), ammUteruo.getIdRuo(), ammUteruo.getIdDir()) !=
                    null
                ) {
                    throw new MicroServicesException(
                        ErrorMessages.AMMUTERUO_ALREADY_PRESENT.getUserMessage(),
                        ErrorMessages.AMMUTERUO_ALREADY_PRESENT.getCode()
                    );
                }
                utilService.setInfoInsertRow(ammUteruo);
            } else {
                utenteDaModificare.setUsrCreate(user.getUsrCreate());
                utenteDaModificare.setDtCreate(user.getDtCreate());
                utilService.setInfoUpdateRow(ammUteruo);
            }
        }
        return utenteRepository.saveAndFlush(utenteDaModificare);
    }

    @Override
    @Transactional
    public List<AmmUte> ricercaUtente(String username, String cognome, String autocomplete, Integer idUteRuo)
        throws MicroServicesException {
        Optional<AmmUteruo> ammUteruoOptional = ammUteRuoRepository.findById(idUteRuo);
        if (ammUteruoOptional.isPresent()) {
            Integer idUte = Math.toIntExact(ammUteruoOptional.get().getIdUte());
            Long idRuo = ammUteruoOptional.get().getIdRuo();
            if (idRuo != 2) {
                List<AmmUte> results = new ArrayList<>();
                AmmUte ammUte = this.getUtente(idUte);
                if (
                    (username == null && cognome == null && autocomplete.isBlank()) ||
                    ammUte.getUsername().equalsIgnoreCase(username) ||
                    ammUte.getCognome().equalsIgnoreCase(cognome) ||
                    (
                        autocomplete != null &&
                        !autocomplete.isBlank() &&
                        (
                            ammUte.getCognome().toLowerCase().startsWith(autocomplete.toLowerCase()) ||
                            ammUte.getUsername().toLowerCase().startsWith(autocomplete.toLowerCase())
                        )
                    )
                ) {
                    results.add(ammUte);
                }
                return results;
            } else return this.ricercaUtente(username, cognome, autocomplete);
        } else throw new MicroServicesException(
            ErrorMessages.AMMUTERUO_NOT_PRESENT.getUserMessage(),
            ErrorMessages.AMMUTERUO_NOT_PRESENT.getCode()
        );
    }

    private List<AmmUte> ricercaUtente(String username, String cognome, String autocomplete) {
        if (username != null) {
            List<AmmUte> results = new ArrayList<>();
            Optional<AmmUte> ammUteByUsername = utenteRepository.findByUsername(username);
            if (ammUteByUsername.isPresent()) {
                AmmUte ammUte = ammUteByUsername.get();
                results.add(ammUte);
            }
            return results;
        } else if (cognome != null) {
            return utenteRepository.findByCognome(cognome);
        } else if (autocomplete != null && !autocomplete.isBlank()) {
            return utenteRepository.findAutocomplete(autocomplete.toUpperCase());
        } else return utenteRepository.findAll();
    }

    @Override
    @Transactional
    public AmmUte ricercaUtenteRuolo(String username) throws MicroServicesException {
        if (!username.isEmpty()) {
            if (username.contains("@")) username = username.substring(0, username.indexOf("@"));
            Optional<AmmUte> ammUteByUsername = utenteRepository.findByUsername(username);
            if (ammUteByUsername.isPresent()) {
                if (!ammUteByUsername.get().getAbilitato()) throw new MicroServicesException(
                    ErrorMessages.USER_NOT_ABILITATE.getUserMessage(),
                    ErrorMessages.USER_NOT_ABILITATE.getCode()
                );
                Hibernate.initialize(ammUteByUsername.get().getAmmUteruosById());
                if (!ammUteByUsername.get().getAmmUteruosById().isEmpty()) {
                    for (AmmUteruo ammUteruo : ammUteByUsername.get().getAmmUteruosById()) {
                        Hibernate.initialize(ammUteruo.getAmmDirByIdDir());
                        Hibernate.initialize(ammUteruo.getAmmRuoByIdRuo());
                    }
                } else throw new MicroServicesException(
                    ErrorMessages.USER_WITHOUT_ROLES.getUserMessage(),
                    ErrorMessages.USER_WITHOUT_ROLES.getCode()
                );
                return ammUteByUsername.get();
            } else throw new MicroServicesException(
                ErrorMessages.USER_NOT_PRESENT.getUserMessage(),
                ErrorMessages.USER_NOT_PRESENT.getCode()
            );
        } else throw new MicroServicesException(ErrorMessages.BAD_REQUEST.getUserMessage(), ErrorMessages.BAD_REQUEST.getCode());
    }

    @Override
    @Transactional
    public AmmUteruo associaRuoloDirezione(Integer idUtente, Integer idRuolo, Integer idDirezione) throws MicroServicesException {
        AmmUte utente = this.getUtente(idUtente);
        AmmRuo ruolo = ruoloService.getRuolo(idRuolo);
        AmmDir direzione = direzioneService.getDirezione(idDirezione);
        AmmUteruo ammUteruo = new AmmUteruo();
        ammUteruo.setIdUte(utente.getId());
        ammUteruo.setIdRuo(ruolo.getId());
        ammUteruo.setIdDir(direzione.getId());
        ammUteruo.setFlgdef("N");
        ammUteruo.setTipcondat("C");
        utilService.setInfoInsertRow(ammUteruo);
        utente.getAmmUteruosById().add(ammUteruo);
        utenteRepository.saveAndFlush(utente);
        return ammUteruo;
    }

    @Override
    public void cancellaRuoloDirezione(Integer idUtente, Integer idAmmUteruo) throws MicroServicesException {
        Optional<AmmUteruo> ammUteruoToDeleteOptional = ammUteRuoRepository.findById(idAmmUteruo);
        if (ammUteruoToDeleteOptional.isPresent()) {
            if (ammUteruoToDeleteOptional.get().getIdUte() != Long.valueOf(idUtente)) {
                throw new MicroServicesException(
                    ErrorMessages.AMMUTERUO_NOT_PRESENT.getUserMessage(),
                    ErrorMessages.AMMUTERUO_NOT_PRESENT.getCode()
                );
            } else {
                ammUteRuoRepository.deleteById(idAmmUteruo);
            }
        } else throw new MicroServicesException(
            ErrorMessages.ID_AMMUTERUO_NOT_PRESENT.getUserMessage(),
            ErrorMessages.ID_AMMUTERUO_NOT_PRESENT.getCode()
        );
    }
}
