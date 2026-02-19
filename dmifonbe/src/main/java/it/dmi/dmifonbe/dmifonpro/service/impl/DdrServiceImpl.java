package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProDdr;
import it.dmi.dmifonbe.dmifonpro.model.DdrRicerca;
import it.dmi.dmifonbe.dmifonpro.model.FiltroProgetti;
import it.dmi.dmifonbe.dmifonpro.repository.DdrRepository;
import it.dmi.dmifonbe.dmifonpro.repository.DdraRepository;
import it.dmi.dmifonbe.dmifonpro.repository.ProgettoRepository;
import it.dmi.dmifonbe.dmifonpro.service.DdrService;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class DdrServiceImpl implements DdrService {

    @Autowired
    DdrRepository ddrRepository;

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    DdraRepository ddraRepository;

    @Autowired
    EntityManager em;

    @Autowired
    FiltroProgetti filtroProgetti;
    @Autowired
    UtilService utilService;

    @Value("${ricercaDdrBase}")
    String ricercaDdrBase;

    @Value("${ricercaDdrDDra}")
    String ricercaDdrDDra;

    @Value("${ricercaDdrAutocomplete}")
    String ricercaDdrAutocomplete;

    @Value("${ricercaDdrCondIdPro}")
    String ricercaDdrCondIdPro;

    @Value("${ricercaDdrCondLivPro1}")
    String ricercaDdrCondLivPro1;

    @Value("${ricercaDdrCondLivPro2}")
    String ricercaDdrCondLivPro2;

    @Value("${ricercaDdrCondLivPro3}")
    String ricercaDdrCondLivPro3;

    @Value("${ricercaDdrCondTipFin}")
    String ricercaDdrCondTipFin;

    @Value("${ricercaDdrFlgNoDdra}")
    String ricercaDdrFlgNoDdra;

    @Value("${ricercaDdrDirezione}")
    String ricercaDdrDirezione;

    @Value("${ricercaDdrFine}")
    String ricercaDdrFine;

    @Override
    @Transactional
    public ProDdr getDdr(Integer id) throws MicroServicesException {
        Optional<ProDdr> proDdrOptional = ddrRepository.findById(id);
        if (proDdrOptional.isPresent()) {
            ProDdr proDdr = proDdrOptional.get();
            Hibernate.initialize(proDdr.getProProByIdPro());
            Hibernate.initialize(proDdr.getProDdraByIdDdra());
            return proDdr;
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }

    @Override
    public ProDdr inserisciDdr(ProDdr ddrDaInserire) throws MicroServicesException {
        this.checkDdr(ddrDaInserire, false);
        utilService.setInfoInsertRow(ddrDaInserire);
        return ddrRepository.saveAndFlush(ddrDaInserire);
    }

    @Override
    public void modificaDdr(ProDdr ddrDaModificare) throws MicroServicesException {
        this.checkDdr(ddrDaModificare, true);
        utilService.setInfoUpdateRow(ddrDaModificare);
        ddrRepository.saveAndFlush(ddrDaModificare);
    }

    private void checkDdr(ProDdr ddr, boolean edit) throws MicroServicesException {
        if (!edit && ddr.getId() != 0) throw new MicroServicesException(
            ErrorMessages.PRODDR_INSERT_WITH_ID.getUserMessage(),
            ErrorMessages.PRODDR_INSERT_WITH_ID.getCode()
        ); else if (edit && ddr.getId() == 0) throw new MicroServicesException(
            ErrorMessages.PRODDR_EDIT_NO_ID.getUserMessage(),
            ErrorMessages.PRODDR_EDIT_NO_ID.getCode()
        );
        if (ddr.getIdPro() == null || ddr.getIdPro().equals(0L)) throw new MicroServicesException(
            ErrorMessages.MANDATORY_FIELD.getUserMessage(),
            ErrorMessages.MANDATORY_FIELD.getCode()
        );
        if (!progettoRepository.existsById(ddr.getIdPro().intValue())) throw new MicroServicesException(
            ErrorMessages.PROJECT_NOT_VALID.getUserMessage(),
            ErrorMessages.PROJECT_NOT_VALID.getCode()
        );
        if (
            (ddr.getIdDdra() != null && !ddr.getIdDdra().equals(0L)) && !ddraRepository.existsById(ddr.getIdDdra().intValue())
        ) throw new MicroServicesException(
            ErrorMessages.PRODDR_DDRA_NOT_VALID.getUserMessage(),
            ErrorMessages.PRODDR_DDRA_NOT_VALID.getCode()
        );
        if (
            ddr.getCodddr() == null ||
            ddr.getCodddr().isBlank() ||
            ddr.getDesddr() == null ||
            ddr.getDesddr().isBlank() ||
            ddr.getDtaddr() == null ||
            ddr.getImpddr() == null ||
            ddr.getImpamm() == null ||
            ddr.getImptra() == null ||
            ddr.getImpinc() == null
        ) throw new MicroServicesException(ErrorMessages.MANDATORY_FIELD.getUserMessage(), ErrorMessages.MANDATORY_FIELD.getCode());
        //in inserimento controllo che il codice non esista già
        if (!edit && ddrRepository.findProDdrByCodddrIgnoreCase(ddr.getCodddr()).isPresent())
            throw new MicroServicesException(ErrorMessages.COD_ALREADY_EXIST.getUserMessage(), ErrorMessages.COD_ALREADY_EXIST.getCode());
    }

    @Override
    public void cancellaDdr(Integer idDdr) throws MicroServicesException {
        if (ddrRepository.existsById(idDdr)) ddrRepository.deleteById(idDdr); else throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
    }

    @Override
    public List<ProDdr> ricercaDdrAutocomplete(String autocomplete) throws MicroServicesException {
        if (autocomplete != null && !autocomplete.isBlank()) {
            return ddrRepository.findAutocomplete(autocomplete.toUpperCase());
        } else throw new MicroServicesException(
            ErrorMessages.AUTOCOMPLETE_BLANK.getUserMessage(),
            ErrorMessages.AUTOCOMPLETE_BLANK.getCode()
        );
    }

    @Override
    @Transactional
    public List<ProDdr> ricercaDdr(DdrRicerca ddrRic, Integer idUteRuo) {
        filtroProgetti.generateFiltroProgetti(idUteRuo);
        String queryStr = ricercaDdrBase;
        if (ddrRic.getAutocomplete() != null && !ddrRic.getAutocomplete().isEmpty()) queryStr += " " + ricercaDdrAutocomplete;
        if (ddrRic.getDdra() != null && ddrRic.getDdra() != 0) queryStr += " " + ricercaDdrDDra;
        if (ddrRic.getIdProgetto() > 0) queryStr += " " + ricercaDdrCondIdPro;
        if (filtroProgetti.isFiltered() || ddrRic.getDirezione() != 0) queryStr += " " + ricercaDdrDirezione;
        if (ddrRic.getTipLiv() != null && !ddrRic.getTipLiv().isBlank()) if (
            ddrRic.getTipLiv().equals("01") || ddrRic.getTipLiv().equals("02") || ddrRic.getTipLiv().equals("03")
        ) queryStr += " " + ricercaDdrCondLivPro1; else if (ddrRic.getTipLiv().equals("BA")) queryStr +=
            " " + ricercaDdrCondLivPro2; else if (ddrRic.getTipLiv().equals("PB")) queryStr += " " + ricercaDdrCondLivPro3;
        if (ddrRic.getTipoFinanziamento() > 0) queryStr += " " + ricercaDdrCondTipFin;
        if (ddrRic.getFlgNoDdra() != null && ddrRic.getFlgNoDdra().equals("S")) queryStr += " " + ricercaDdrFlgNoDdra;
        queryStr += " " + ricercaDdrFine;

        Query query = em.createQuery(queryStr);
        if (ddrRic.getAutocomplete() != null && !ddrRic.getAutocomplete().isEmpty()) query.setParameter(
            "AUTOCOMPLETE",
            "%" + ddrRic.getAutocomplete().toUpperCase() + "%"
        );
        if (ddrRic.getDdra() != null && ddrRic.getDdra() > 0) query.setParameter("DDRA", ddrRic.getDdra());
        if (ddrRic.getIdProgetto() > 0) query.setParameter("ID_PRO", ddrRic.getIdProgetto());
        if (filtroProgetti.isFiltered()) query.setParameter("DIR", filtroProgetti.getIdDirezione()); else if (
            ddrRic.getDirezione() != 0
        ) query.setParameter("DIR", ddrRic.getDirezione());
        if (ddrRic.getTipLiv() != null && !ddrRic.getTipLiv().isBlank()) if (
            ddrRic.getTipLiv().equals("01") || ddrRic.getTipLiv().equals("02") || ddrRic.getTipLiv().equals("03")
        ) query.setParameter("TIPLIV", Integer.parseInt(ddrRic.getTipLiv()));
        if (ddrRic.getTipoFinanziamento() > 0) query.setParameter("ID_TIPFIN", ddrRic.getTipoFinanziamento());

        List<ProDdr> results = query.getResultList();
        if (!results.isEmpty()) for (ProDdr proDdr : results) {
            Hibernate.initialize(proDdr.getProProByIdPro());
            Hibernate.initialize(proDdr.getProDdraByIdDdra());
        }
        return results;
    }
}
