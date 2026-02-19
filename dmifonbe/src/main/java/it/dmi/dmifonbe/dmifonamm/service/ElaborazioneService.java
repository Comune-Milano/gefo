package it.dmi.dmifonbe.dmifonamm.service;

import it.dmi.dmifonbe.dmifonamm.entities.AmmEla;
import it.dmi.dmifonbe.dmifonamm.model.ElaborazioneRicerca;

import java.util.List;

public interface ElaborazioneService {

    List<AmmEla> ricercaElaborazione(ElaborazioneRicerca elaRic);

    public Integer start(String desEla) ;

    public void stop(Integer idElda) ;



}
