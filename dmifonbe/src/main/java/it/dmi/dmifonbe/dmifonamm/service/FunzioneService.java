package it.dmi.dmifonbe.dmifonamm.service;

import it.dmi.dmifonbe.dmifonamm.entities.AmmFun;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;

public interface FunzioneService {
    AmmFun getFunzione(Integer id) throws MicroServicesException;

    AmmFun inserisciFunzione(AmmFun funzioneDaInserire);

    void cancellaFunzione(Integer idfunzione);

    AmmFun modificaFunzione(AmmFun funzioneDaModificare);

    List<AmmFun> getFunzioni();
}
