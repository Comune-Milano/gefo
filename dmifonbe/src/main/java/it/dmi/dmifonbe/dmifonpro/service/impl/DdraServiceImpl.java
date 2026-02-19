package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProDdr;
import it.dmi.dmifonbe.dmifonpro.entities.ProDdra;
import it.dmi.dmifonbe.dmifonpro.model.AssociateDdrToDdra;
import it.dmi.dmifonbe.dmifonpro.model.DdraDetail;
import it.dmi.dmifonbe.dmifonpro.model.DdraRicerca;
import it.dmi.dmifonbe.dmifonpro.repository.DdrRepository;
import it.dmi.dmifonbe.dmifonpro.repository.DdraRepository;
import it.dmi.dmifonbe.dmifonpro.service.DdraService;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DdraServiceImpl implements DdraService {

    @Autowired
    DdraRepository ddraRepository;

    @Autowired
    DdrRepository ddrRepository;

    @Autowired
    UtilService utilService;


    @Autowired
    EntityManager em;

    @Value("${ricercaDdraAutocomplete}")
    String ricercaDdraAutocomplete;

    @Value("${ricercaDdraBase}")
    String ricercaDdraBase;

    @Value("${ricercaDdraCondIdDdr}")
    String ricercaDdraCondIdDdr;

    @Value("${ricercaDdraFine}")
    String ricercaDdraFine;

    @Override
    @Transactional
    public DdraDetail getDdra(Integer id) throws MicroServicesException {
        Optional<ProDdra> proDdraOptional = ddraRepository.findById(id);
        if (proDdraOptional.isPresent()) {
            DdraDetail result = new DdraDetail();
            ProDdra proDdra = proDdraOptional.get();
            Hibernate.initialize(proDdra.getProDdrsById());
            result.setDdra(proDdra);
            result.setImportoDdra(this.getImportoDdra(proDdra));
            return result;
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }

    private BigDecimal getImportoDdra(ProDdra proDdra) {
        BigDecimal result = BigDecimal.ZERO;
        for (ProDdr proDdr : proDdra.getProDdrsById()) {
            result = result.add(proDdr.getImpddr());
        }
        return result;
    }

    @Override
    public ProDdra inserisciDdra(ProDdra ddraDaInserire) throws MicroServicesException {
        this.checkDdra(ddraDaInserire, false);
        utilService.setInfoInsertRow(ddraDaInserire);
        return ddraRepository.saveAndFlush(ddraDaInserire);
    }

    private void checkDdra(ProDdra ddra, boolean edit) throws MicroServicesException {
        if (!edit && ddra.getId() != 0) throw new MicroServicesException(
            ErrorMessages.PRODDRA_INSERT_WITH_ID.getUserMessage(),
            ErrorMessages.PRODDRA_INSERT_WITH_ID.getCode()
        ); else if (edit && ddra.getId() == 0) throw new MicroServicesException(
            ErrorMessages.PRODDRA_EDIT_NO_ID.getUserMessage(),
            ErrorMessages.PRODDRA_EDIT_NO_ID.getCode()
        );
        if (
            ddra.getCodddra() == null ||
            ddra.getCodddra().isBlank() ||
            ddra.getDesddra() == null ||
            ddra.getDesddra().isBlank() ||
            ddra.getDtaddra() == null
        ) throw new MicroServicesException(ErrorMessages.MANDATORY_FIELD.getUserMessage(), ErrorMessages.MANDATORY_FIELD.getCode());
        //in inserimento controllo che il codice non esista già
        if (!edit && ddraRepository.findProDdraByCodddraIgnoreCase(ddra.getCodddra()).isPresent())
            throw new MicroServicesException(ErrorMessages.COD_ALREADY_EXIST.getUserMessage(), ErrorMessages.COD_ALREADY_EXIST.getCode());
    }

    @Override
    public void modificaDdra(ProDdra ddraDaModificare) throws MicroServicesException {
        this.checkDdra(ddraDaModificare, true);
        utilService.setInfoUpdateRow(ddraDaModificare);
        ddraRepository.saveAndFlush(ddraDaModificare);
    }

    @Override
    @Transactional
    public void cancellaDdra(Integer idDdra) throws MicroServicesException {
        if (!ddraRepository.existsById(idDdra)) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
        Optional<ProDdra> proDdraOptional = ddraRepository.findById(idDdra);
        if (proDdraOptional.isPresent()) {
            ProDdra proDdra = proDdraOptional.get();
            Hibernate.initialize(proDdra.getProDdrsById());
            if (!proDdra.getProDdrsById().isEmpty()) throw new MicroServicesException(
                ErrorMessages.PRODDRA_DDR_PRESENT.getUserMessage(),
                ErrorMessages.PRODDRA_DDR_PRESENT.getCode()
            );
        }
        ddraRepository.deleteById(idDdra);
    }

    @Override
    public void associaDdrADdra(AssociateDdrToDdra associateDdrToDdra) throws MicroServicesException {
        if (!ddraRepository.existsById(associateDdrToDdra.getDdra())) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
        if (!associateDdrToDdra.getDdrs().isEmpty()) {
            List<ProDdr> ddrsToSave = new ArrayList<>();
            for (Integer idDdr : associateDdrToDdra.getDdrs()) {
                Optional<ProDdr> proDdrOptional = ddrRepository.findById(idDdr);
                if (proDdrOptional.isPresent()) {
                    ProDdr proDdr = proDdrOptional.get();
                    if (proDdr.getIdDdra() == null || proDdr.getIdDdra().equals(0L)) {
                        proDdr.setIdDdra(associateDdrToDdra.getDdra().longValue());
                        ddrsToSave.add(proDdr);
                    }
                }
            }
            if (!ddrsToSave.isEmpty()) ddrRepository.saveAllAndFlush(ddrsToSave);
        } else throw new MicroServicesException(ErrorMessages.BAD_REQUEST.getUserMessage(), ErrorMessages.BAD_REQUEST.getCode());
    }

    @Override
    @Transactional
    public List<DdraDetail> ricercaDdra(DdraRicerca ddraRic) {
        List<DdraDetail> response = new ArrayList<>();
        String queryStr = ricercaDdraBase;
        if (ddraRic.getAutocomplete() != null && !ddraRic.getAutocomplete().isEmpty()) queryStr += " " + ricercaDdraAutocomplete;
        if (ddraRic.getIdDdr() > 0) queryStr += " " + ricercaDdraCondIdDdr;
        queryStr += " " + ricercaDdraFine;
        Query query = em.createQuery(queryStr);
        if (ddraRic.getAutocomplete() != null && !ddraRic.getAutocomplete().isEmpty()) query.setParameter(
            "AUTOCOMPLETE",
            "%" + ddraRic.getAutocomplete().toUpperCase() + "%"
        );
        if (ddraRic.getIdDdr() > 0) query.setParameter("ID_DDR", ddraRic.getIdDdr());
        List<ProDdra> results = query.getResultList();
        if (!results.isEmpty()) for (ProDdra proDdra : results) {
            DdraDetail ddraDetail = new DdraDetail();
            ddraDetail.setDdra(proDdra);
            ddraDetail.setImportoDdra(this.getImportoDdra(proDdra));
            response.add(ddraDetail);
        }
        return response;
    }

    @Override
    public List<ProDdra> ricercaDdraAutocomplete(String autocomplete) throws MicroServicesException {
        if (autocomplete != null && !autocomplete.isBlank()) {
            List<ProDdra> results = ddraRepository.findAutocomplete(autocomplete.toUpperCase());
            return results;
        } else {
            return ddraRepository.findAllByOrderByCodddraAsc();
            /*throw new MicroServicesException(
                ErrorMessages.AUTOCOMPLETE_BLANK.getUserMessage(),
                ErrorMessages.AUTOCOMPLETE_BLANK.getCode()
            );*/
        }
    }
}
