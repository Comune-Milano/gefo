package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.model.Totali;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.text.ParseException;

public interface CalcolaTotaliService {
    Totali getTotali(String entityType, int id) throws ParseException, MicroServicesException;
    Totali getTotaliFondi(String entityType, int id);
    Totali getTotaliRisorse(String entityType, int id);
    Totali getTotaliRisorseDiCui(String entityType, int id);
    Totali getTotaliImpegniAndAccertamenti(String entityType, int id);
    Totali getTotaliImpegniAndAccertamentiPregressi(String entityType, int id);
    Totali getTotaliDdr(String entityType, int id);
    Totali getTotaliPrevisioni(String entityType, int id) throws ParseException;
}
