package it.dmi.dmifonbe.dmifonamm.service;

import it.dmi.dmifonbe.dmifonamm.entities.EntitySuperClass;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.model.ProgettoAndProgettoPadre;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public interface UtilService {
    String getUsernameAuthenticated();
    void setInfoInsertRow(EntitySuperClass entity);
    void setInfoUpdateRow(EntitySuperClass entity);
    ProgettoAndProgettoPadre getProgettoAndProgettoPadre(Integer idPro) throws MicroServicesException;
}
