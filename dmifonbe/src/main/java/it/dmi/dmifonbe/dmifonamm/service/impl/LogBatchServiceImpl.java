package it.dmi.dmifonbe.dmifonamm.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmEla;
import it.dmi.dmifonbe.dmifonamm.entities.AmmOut;
import it.dmi.dmifonbe.dmifonamm.entities.AmmRig;
import it.dmi.dmifonbe.dmifonamm.repository.ElaborazioneRepository;
import it.dmi.dmifonbe.dmifonamm.repository.OutputRepository;
import it.dmi.dmifonbe.dmifonamm.repository.RigaRepository;
import it.dmi.dmifonbe.dmifonamm.service.LogBatchService;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.model.BatchLogReturn;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogBatchServiceImpl implements LogBatchService {

    @Autowired
    ElaborazioneRepository elaborazioneRepository;

    @Autowired
    OutputRepository outputRepository;

    @Autowired
    RigaRepository rigaRepository;

    @Autowired
    UtilService utilService;

    @Override
    public BatchLogReturn startLog() {
        AmmEla ammEla = new AmmEla();
        AmmOut ammOut = new AmmOut();

        ammEla.setDesela("Elaborazione per aggiornare i dati degli impegni, accertamenti e crono");
        ammEla.setStaela("A");
        ammEla.setDtaini(new Date());
        ammEla.setUsrCreate("Cron");
        ammEla.setDtCreate(new Date());
        ammEla.setUsrLstupd("Cron");
        ammEla.setDtLstupd(new Date());
        AmmEla ammElaSaved = elaborazioneRepository.saveAndFlush(ammEla);

        ammOut.setIdEla(ammElaSaved.getId().longValue());
        ammOut.setDesout("Risultato eleborazione per aggiornare i dati degli impegni, accertamenti e crono");
        ammOut.setTipout("TXT");
        ammOut.setUsrCreate("Cron");
        ammOut.setDtCreate(new Date());
        ammOut.setUsrLstupd("Cron");
        ammOut.setDtLstupd(new Date());
        AmmOut ammOutSaved = outputRepository.saveAndFlush(ammOut);
        BatchLogReturn result = new BatchLogReturn();
        result.setAmmEla(ammElaSaved);
        result.setAmmOut(ammOutSaved);
        return result;
    }

    @Override
    public void endLog(AmmEla ammEla, Long idOutput, Long numRiga, int numImp, int numAcc, int numCro) {
        AmmRig ammRigImpe = new AmmRig();
        numRiga += 1;
        ammRigImpe.setIdOut(idOutput);
        ammRigImpe.setNrorig(numRiga);
        ammRigImpe.setPrgrig(1);
        ammRigImpe.setTesrig("Numero Impegni Aggiornati: " + numImp);
        rigaRepository.saveAndFlush(ammRigImpe);

        numRiga += 1;
        AmmRig ammRigAcce = new AmmRig();
        ammRigAcce.setIdOut(idOutput);
        ammRigAcce.setNrorig(numRiga);
        ammRigAcce.setPrgrig(1);
        ammRigAcce.setTesrig("Numero accertamenti Aggiornati: " + numAcc);
        rigaRepository.saveAndFlush(ammRigAcce);

        numRiga += 1;
        AmmRig ammRigCro = new AmmRig();
        ammRigCro.setIdOut(idOutput);
        ammRigCro.setNrorig(numRiga);
        ammRigCro.setPrgrig(1);
        ammRigCro.setTesrig("Numero crono Aggiornati: " + numCro);
        rigaRepository.saveAndFlush(ammRigCro);

        ammEla.setStaela("T");
        ammEla.setDtafin(new Date());
        elaborazioneRepository.saveAndFlush(ammEla);
    }

    @Override
    public void insertAmmRiga(AmmRig ammRig) {
        rigaRepository.saveAndFlush(ammRig);
    }
}
