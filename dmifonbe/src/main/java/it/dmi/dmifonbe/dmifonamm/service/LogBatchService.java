package it.dmi.dmifonbe.dmifonamm.service;

import it.dmi.dmifonbe.dmifonamm.entities.AmmEla;
import it.dmi.dmifonbe.dmifonamm.entities.AmmRig;
import it.dmi.dmifonbe.dmifonpro.model.BatchLogReturn;

public interface LogBatchService {
    BatchLogReturn startLog();
    void endLog(AmmEla ammEla, Long idOutput, Long numRiga, int numImp, int numAcc, int numCro);
    void insertAmmRiga(AmmRig ammRig);
}
