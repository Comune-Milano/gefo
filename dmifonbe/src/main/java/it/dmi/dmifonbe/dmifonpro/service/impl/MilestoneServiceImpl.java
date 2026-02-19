package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProFas;
import it.dmi.dmifonbe.dmifonpro.entities.ProMil;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;
import it.dmi.dmifonbe.dmifonpro.model.MilestoneDetail;
import it.dmi.dmifonbe.dmifonpro.model.MilestoneFaseDetail;
import it.dmi.dmifonbe.dmifonpro.model.ProgettoAndProgettoPadre;
import it.dmi.dmifonbe.dmifonpro.repository.FaseRepository;
import it.dmi.dmifonbe.dmifonpro.repository.MilestoneRepository;
import it.dmi.dmifonbe.dmifonpro.repository.TipFasRepository;
import it.dmi.dmifonbe.dmifonpro.service.MilestoneService;
import it.dmi.dmifonbe.dmifonpro.service.SemaforoService;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    @Autowired
    FaseRepository faseRepository;

    @Autowired
    TipFasRepository tipFasRepository;

    @Autowired
    SemaforoService semaforoService;

    @Autowired
    UtilService utilService;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Override
    @Transactional
    public MilestoneFaseDetail getMilestoneFase(Integer idFase) throws MicroServicesException {
        MilestoneFaseDetail result = new MilestoneFaseDetail();
        Optional<ProFas> faseOpt = faseRepository.findById(idFase);
        if (faseOpt.isPresent()) {
            ProFas fase = faseOpt.get();
            result.setFase(fase);
            //aggiungo la descrizione del tipo fase
            Optional<ProTipfas> tipfasOptional = tipFasRepository.findById(fase.getIdTipfas().intValue());
            if (tipfasOptional.isPresent()) {
                result.setProTipfas(tipfasOptional.get());
            }
            Hibernate.initialize(fase.getProMilsById());
            if (!fase.getProMilsById().isEmpty()) {
                List<MilestoneDetail> milestoneToReturn = new ArrayList<>();
                for (ProMil milestone : fase.getProMilsById()) {
                    MilestoneDetail milestoneDetail = new MilestoneDetail();
                    milestoneDetail.setMilestone(milestone);
                    milestoneDetail.setSemaforoMilestone(semaforoService.getSemaforoMilestone(milestone.getId()));
                    milestoneToReturn.add(milestoneDetail);
                }
                result.setMilestone(milestoneToReturn);
            }
            Hibernate.initialize(fase.getProAvaByIdAva());
            ProgettoAndProgettoPadre progettoAndProgettoPadre = utilService.getProgettoAndProgettoPadre(
                fase.getProAvaByIdAva().getIdPro().intValue()
            );
            result.setProgetto(progettoAndProgettoPadre.getProgetto());
            result.setProgettoPadreLabel(progettoAndProgettoPadre.getProgettoPadreLabel());
            return result;
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }

    @Override
    public void modificaMilestoneFase(List<ProMil> milsDaModificare) throws MicroServicesException {
        this.checkMilestone(milsDaModificare);
        milestoneRepository.saveAllAndFlush(milsDaModificare);
    }

    private void checkMilestone(List<ProMil> milestones) throws MicroServicesException {
        List<Integer> idsToCheck = new ArrayList<>();
        for (ProMil milestone : milestones) {
            if (milestone.getDesmil().isBlank()) throw new MicroServicesException(
                ErrorMessages.MANDATORY_FIELD.getUserMessage(),
                ErrorMessages.MANDATORY_FIELD.getCode()
            );
            if (milestone.getDtaded() == null) throw new MicroServicesException(
                ErrorMessages.MANDATORY_FIELD.getUserMessage(),
                ErrorMessages.MANDATORY_FIELD.getCode()
            );
            if (milestone.getIdFas().equals(0L)) throw new MicroServicesException(
                ErrorMessages.MANDATORY_FIELD.getUserMessage(),
                ErrorMessages.MANDATORY_FIELD.getCode()
            );
            if (!faseRepository.existsById(milestone.getIdFas().intValue())) throw new MicroServicesException(
                ErrorMessages.NO_DATA_FOUND_PROFAS.getUserMessage(),
                ErrorMessages.NO_DATA_FOUND_PROFAS.getCode()
            );
            idsToCheck.add(milestone.getId());
        }
        for (Integer id : idsToCheck) {
            if (Collections.frequency(idsToCheck, id) > 1) throw new MicroServicesException(
                ErrorMessages.DUPLICATE_MILESTONE_INPUT.getUserMessage(),
                ErrorMessages.DUPLICATE_MILESTONE_INPUT.getCode()
            );
        }
    }

    @Override
    public void cancellaMilestone(Integer id) throws MicroServicesException {
        if (milestoneRepository.existsById(id)) milestoneRepository.deleteById(id); else throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
    }
}
