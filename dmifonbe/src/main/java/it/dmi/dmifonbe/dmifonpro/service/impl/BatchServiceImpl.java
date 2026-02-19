package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmRig;
import it.dmi.dmifonbe.dmifonamm.service.LogBatchService;
import it.dmi.dmifonbe.dmifonpro.entities.ProDetcon;
import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import it.dmi.dmifonbe.dmifonpro.model.Assessorato;
import it.dmi.dmifonbe.dmifonpro.model.BatchLogReturn;
import it.dmi.dmifonbe.dmifonpro.model.RicercaEntitaContabile;
import it.dmi.dmifonbe.dmifonpro.model.Settore;
import it.dmi.dmifonbe.dmifonpro.repository.DettaglioContabileRepository;
import it.dmi.dmifonbe.dmifonpro.repository.EntitaContabileRepository;
import it.dmi.dmifonbe.dmifonpro.service.BatchService;
import it.dmi.dmifonbe.dmifonpro.service.ImpegniProgettoService;
import it.dmi.dmifonbe.dmifonpro.service.ProgettoService;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    DettaglioContabileRepository dettaglioContabileRepository;

    @Autowired
    ImpegniProgettoService impegniProgettoService;

    @Autowired
    ProgettoService progettoService;

    @Autowired
    EntitaContabileRepository entitaContabileRepository;

    @Autowired
    LogBatchService logBatchService;

    @Override
    @Scheduled(cron = "0 00 2 * * *")
    public void aggiornamentoEntitaContabili() throws MicroServicesException {
        BatchLogReturn batchLogReturn = logBatchService.startLog();
        List<ProEntcon> listToSave = new ArrayList<>();
        List<ProEntcon> listCrono = new ArrayList<>();
        int numAcc = 0;
        int numImp = 0;
        int numCro = 0;
        int numSet = 0;
        int numAss = 0;
        Long numRiga = Long.valueOf(0);
        List<ProDetcon> proDetcons = dettaglioContabileRepository.findAll();
        for (ProDetcon proDetcon : proDetcons) {
            if (proDetcon.getTipent().equals(Parameters.TIPENTIMPE.getValue())) {
                try {
                    ProEntcon impegno = impegniProgettoService.getImpegno(proDetcon.getCodentcon());
                    this.checkProEntcon(impegno);
                    if (!listToSave.contains(impegno))
                        listToSave.add(impegno);
                    else {
                        AmmRig ammRig = new AmmRig();
                        ammRig.setIdOut(batchLogReturn.getAmmOut().getId().longValue());
                        ammRig.setNrorig(numRiga++);
                        ammRig.setPrgrig(1);
                        ammRig.setTesrig("Già presente nella lista impegno " + proDetcon.getCodentcon());
                        logBatchService.insertAmmRiga(ammRig);
                    }
                    numImp++;
                } catch (Exception e) {
                    if (e instanceof MicroServicesException) {
                        if (((MicroServicesException) e).getCode() == 570) {
                            AmmRig ammRig = new AmmRig();
                            ammRig.setIdOut(batchLogReturn.getAmmOut().getId().longValue());
                            ammRig.setNrorig(numRiga++);
                            ammRig.setPrgrig(1);
                            ammRig.setTesrig("Errore. L'impegno " + proDetcon.getCodentcon() + " non esiste in contabilità");
                            logBatchService.insertAmmRiga(ammRig);
                        }
                    } else {
                        AmmRig ammRig = new AmmRig();
                        ammRig.setIdOut(batchLogReturn.getAmmOut().getId().longValue());
                        ammRig.setNrorig(numRiga++);
                        ammRig.setPrgrig(1);
                        ammRig.setTesrig("Errore generico. L'impegno " + proDetcon.getCodentcon() + " ha l'errore generico:" + e.getMessage().substring(0, 150));
                        logBatchService.insertAmmRiga(ammRig);
                    }
                }
            } else if (proDetcon.getTipent().equals(Parameters.TIPENTCRON.getValue())) {
                try {
                    ProEntcon crono = impegniProgettoService.getCrono(proDetcon.getCodentcon());
                    this.checkProEntcon(crono);
                    if (!listToSave.contains(crono)) listToSave.add(crono);
                    listCrono.add(crono);
                    numAcc++;
                } catch (Exception e) {
                    if (e instanceof MicroServicesException) {
                        if (((MicroServicesException) e).getCode() == 570) {
                            AmmRig ammRig = new AmmRig();
                            ammRig.setIdOut(batchLogReturn.getAmmOut().getId().longValue());
                            ammRig.setNrorig(numRiga++);
                            ammRig.setPrgrig(1);
                            ammRig.setTesrig("Errore. Il cronoprogramma " + proDetcon.getCodentcon() + " non esiste in contabilità");
                            logBatchService.insertAmmRiga(ammRig);
                        }
                    }
                }
            } else if (proDetcon.getTipent().equals(Parameters.TIPENTACCE.getValue())) {
                try {
                    ProEntcon accertamento = impegniProgettoService.getAccertamento(proDetcon.getCodentcon());
                    this.checkProEntcon(accertamento);
                    if (!listToSave.contains(accertamento)) listToSave.add(accertamento);
                    numAcc++;
                } catch (Exception e) {
                    if (e instanceof MicroServicesException) {
                        if (((MicroServicesException) e).getCode() == 570) {
                            AmmRig ammRig = new AmmRig();
                            ammRig.setIdOut(batchLogReturn.getAmmOut().getId().longValue());
                            ammRig.setNrorig(numRiga++);
                            ammRig.setPrgrig(1);
                            ammRig.setTesrig("Errore. L'accertamento " + proDetcon.getCodentcon() + " non esiste in contabilità");
                            logBatchService.insertAmmRiga(ammRig);
                        }
                    }
                }
            }
        }
        for (ProEntcon crono : listCrono) {
            RicercaEntitaContabile ricercaEntitaContabile = new RicercaEntitaContabile();
            List<ProEntcon> listaAcceImpeCrono = new ArrayList<>();
            ricercaEntitaContabile.setId_crono(crono.getCodentcon());
            List<ProEntcon> proEntconAcceList = new ArrayList<>();
            List<ProEntcon> proEntconImpeList = new ArrayList<>();
            //getEntitaContabile scatena un'eccezione se non trova nulla e manda in errore tutto
            //se non trova accertamenti o impegni legati a crono non è un problema
            try {
                proEntconAcceList = impegniProgettoService.getEntitaContabile(Parameters.TIPENTACCE.getValue(), ricercaEntitaContabile);
            } catch (Exception e) {
            }
            try {
                proEntconImpeList = impegniProgettoService.getEntitaContabile(Parameters.TIPENTIMPE.getValue(), ricercaEntitaContabile);
            } catch (Exception e) {
            }
            listaAcceImpeCrono.addAll(proEntconAcceList);
            listaAcceImpeCrono.addAll(proEntconImpeList);
            //listaAcceImpeCrono.addAll(impegniProgettoService.getEntitaContabile(Parameters.TIPENTACCE.getValue(), ricercaEntitaContabile));
            //listaAcceImpeCrono.addAll(impegniProgettoService.getEntitaContabile(Parameters.TIPENTIMPE.getValue(), ricercaEntitaContabile));
            numCro++;
            for (ProEntcon ec : listaAcceImpeCrono) {
                if (ec.getTipent().equals(Parameters.TIPENTIMPE.getValue())) {
                    try {
                        ProEntcon impegno = impegniProgettoService.getImpegno(ec.getCodentcon());
                        this.checkProEntcon(impegno);
                        if (!listToSave.contains(impegno)) listToSave.add(impegno);
                        numImp++;
                    } catch (Exception e) {
                        if (e instanceof MicroServicesException) {
                            if (((MicroServicesException) e).getCode() == 570) {
                                AmmRig ammRig = new AmmRig();
                                ammRig.setIdOut(batchLogReturn.getAmmOut().getId().longValue());
                                ammRig.setNrorig(numRiga++);
                                ammRig.setPrgrig(1);
                                ammRig.setTesrig("Errore. L'impegno " + ec.getCodentcon() + " non esiste in contabilità");
                                logBatchService.insertAmmRiga(ammRig);
                            }
                        }
                    }
                } else if (ec.getTipent().equals(Parameters.TIPENTACCE.getValue())) {
                    try {
                        ProEntcon accertamento = impegniProgettoService.getAccertamento(ec.getCodentcon());
                        this.checkProEntcon(accertamento);
                        if (!listToSave.contains(accertamento)) listToSave.add(accertamento);
                        numAcc++;
                    } catch (Exception e) {
                        if (e instanceof MicroServicesException) {
                            if (((MicroServicesException) e).getCode() == 570) {
                                AmmRig ammRig = new AmmRig();
                                ammRig.setIdOut(batchLogReturn.getAmmOut().getId().longValue());
                                ammRig.setNrorig(numRiga++);
                                ammRig.setPrgrig(1);
                                ammRig.setTesrig("Errore. L'accertamento " + ec.getCodentcon() + " non esiste in contabilità");
                                logBatchService.insertAmmRiga(ammRig);
                            }
                        }
                    }
                }
            }
        }
        //recupero i settori
        List<Settore> settoreList = new ArrayList<>();
        //getAllSettori scatena un'eccezione se non trova nulla e manda in errore tutto
        //se non trova accertamenti o impegni legati a crono non è un problema
        try {
            settoreList = progettoService.getAllSettori();
        } catch (Exception e) {
        }
        ProEntcon proEntcon;
        for (Settore settore : settoreList) {
            proEntcon = new ProEntcon();
            proEntcon.setTipent("SETT");
            proEntcon.setCodentcon(settore.getId());
            proEntcon.setDesimp(settore.getDescrizione());
            proEntcon.setAnncmp(0);
            proEntcon.setImpass(BigDecimal.ZERO);
            proEntcon.setImpliq(BigDecimal.ZERO);
            proEntcon.setImpman(BigDecimal.ZERO);
            proEntcon.setImpmaneme(BigDecimal.ZERO);
            proEntcon.setImpeco(BigDecimal.ZERO);
            proEntcon.setNroconapp(Long.valueOf("0"));
            this.checkProEntcon(proEntcon);
            listToSave.add(proEntcon);
            numSet++;
        }
        //recupero gli assessorati
        List<Assessorato> assessoratoList = new ArrayList<>();
        //getAllSettori scatena un'eccezione se non trova nulla e manda in errore tutto
        //se non trova accertamenti o impegni legati a crono non è un problema
        try {
            assessoratoList = progettoService.getAllAssessorati();
        } catch (Exception e) {
        }
        for (Assessorato assessorato : assessoratoList) {
            proEntcon = new ProEntcon();
            proEntcon.setTipent("ASSE");
            proEntcon.setCodentcon(assessorato.getId());
            proEntcon.setDesimp(assessorato.getDescrizione());
            proEntcon.setAnncmp(0);
            proEntcon.setImpass(BigDecimal.ZERO);
            proEntcon.setImpliq(BigDecimal.ZERO);
            proEntcon.setImpman(BigDecimal.ZERO);
            proEntcon.setImpmaneme(BigDecimal.ZERO);
            proEntcon.setImpeco(BigDecimal.ZERO);
            proEntcon.setNroconapp(Long.valueOf("0"));
            this.checkProEntcon(proEntcon);
            listToSave.add(proEntcon);
            numAss++;
        }
        entitaContabileRepository.saveAllAndFlush(listToSave);
        //
        AmmRig ammRig = new AmmRig();
        ammRig.setIdOut(batchLogReturn.getAmmOut().getId().longValue());
        ammRig.setNrorig(numRiga++);
        ammRig.setPrgrig(1);
        ammRig.setTesrig("Numero settori Aggiornati: " + String.valueOf(numSet));
        logBatchService.insertAmmRiga(ammRig);
        //
        ammRig = new AmmRig();
        ammRig.setIdOut(batchLogReturn.getAmmOut().getId().longValue());
        ammRig.setNrorig(numRiga++);
        ammRig.setPrgrig(1);
        ammRig.setTesrig("Numero assessorati Aggiornati: " + String.valueOf(numAss));
        logBatchService.insertAmmRiga(ammRig);
        //
        logBatchService.endLog(batchLogReturn.getAmmEla(), batchLogReturn.getAmmOut().getId().longValue(), numRiga, numImp, numAcc, numCro);
    }

    private void checkProEntcon(ProEntcon entita) {
        Optional<ProEntcon> proEntconOriginale = entitaContabileRepository.findByTipentAndCodentcon(
            entita.getTipent(),
            entita.getCodentcon()
        );
        if (proEntconOriginale.isPresent()) {
            entita.setId(proEntconOriginale.get().getId());
            entita.setUsrCreate(proEntconOriginale.get().getUsrCreate());
            entita.setDtCreate(proEntconOriginale.get().getDtCreate());
            entita.setUsrLstupd("Cron");
            entita.setDtLstupd(new Date());
        } else {
            entita.setUsrCreate("Cron");
            entita.setDtCreate(new Date());
            entita.setUsrLstupd("Cron");
            entita.setDtLstupd(new Date());
        }
    }
}
