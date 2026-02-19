package it.dmi.dmifonbe.dmifonpro.service;

import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;
import it.dmi.dmifonbe.dmifonpro.model.AvanzamentoDetail;
import it.dmi.dmifonbe.dmifonpro.model.AvanzamentoResponse;
import it.dmi.dmifonbe.dmifonpro.model.AvanzamentoRicerca;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
