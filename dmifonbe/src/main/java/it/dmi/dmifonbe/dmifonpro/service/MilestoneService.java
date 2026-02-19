package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProMil;
import it.dmi.dmifonbe.dmifonpro.model.MilestoneFaseDetail;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface MilestoneService {
    MilestoneFaseDetail getMilestoneFase(Integer idFase) throws MicroServicesException;

    void modificaMilestoneFase(List<ProMil> milsDaModificare) throws MicroServicesException;

    void cancellaMilestone(Integer id) throws MicroServicesException;
}
