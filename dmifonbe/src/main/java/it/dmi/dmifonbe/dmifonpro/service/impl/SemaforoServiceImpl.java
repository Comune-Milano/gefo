package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import it.dmi.dmifonbe.dmifonpro.entities.ProFas;
import it.dmi.dmifonbe.dmifonpro.entities.ProMil;
import it.dmi.dmifonbe.dmifonpro.model.Semaforo;
import it.dmi.dmifonbe.dmifonpro.repository.AvanzamentoRepository;
import it.dmi.dmifonbe.dmifonpro.repository.FaseRepository;
import it.dmi.dmifonbe.dmifonpro.repository.MilestoneRepository;
import it.dmi.dmifonbe.dmifonpro.service.SemaforoService;
import it.dmi.dmifonbe.model.GerarchiaSemafori;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class SemaforoServiceImpl implements SemaforoService {

    @Autowired
    FaseRepository faseRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    AvanzamentoRepository avanzamentoRepository;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Override
    public Semaforo getSemaforoFase(Integer id) {
        Date dataInizio = this.getDataInizioFase(id);
        Date dataFine = this.getDataFineFase(id);
        Semaforo result = new Semaforo();
        if (dataInizio == null || dataFine == null) this.setValoriSemaforo(
                "bianco",
                BigDecimal.ZERO,
                Parameters.SEMAFORO_NON_DEFINITO.getValue(),
                result
            ); else {
            Optional<ProFas> faseOpt = faseRepository.findById(id);
            if (faseOpt.isPresent()) {
                ProFas fase = faseOpt.get();
                if (fase.getDtafineff() != null) this.setValoriSemaforo(
                        "verde",
                        BigDecimal.valueOf(100),
                        Parameters.SEMAFORO_TERMINATO.getValue(),
                        result
                    ); else if (new Date().before(dataInizio)) this.setValoriSemaforo(
                        "verde",
                        BigDecimal.ZERO,
                        Parameters.SEMAFORO_DAIMPLEMENTARE.getValue(),
                        result
                    ); else if (
                    fase.getDtainieff() == null ||
                    ((new Date().after(dataFine) || new Date().equals(dataFine)) && fase.getDtafineff() == null)
                ) this.setValoriSemaforo("rosso", BigDecimal.ZERO, Parameters.SEMAFORO_DAIMPLEMENTARE.getValue(), result); else {
                    Integer giorniPrevisti = this.differenceDateInDays(dataFine, dataInizio);
                    Integer giorniAdOggi = this.differenceDateInDays(new Date(), dataInizio);
                    Float percentualeAvanzamento = (((float) giorniAdOggi / giorniPrevisti) * 100);
                    Optional<AmmPar> ammParSemaforoGialloOpt = parametriRepository.getAmmParByCodiceIgnoreCase(
                        Parameters.SEMAFOROGIALLOFASEPERCENTUALE.getValue()
                    );
                    if (ammParSemaforoGialloOpt.isPresent()) {
                        Integer semaforoGialloPercentuale = Integer.parseInt(ammParSemaforoGialloOpt.get().getValore());
                        if (percentualeAvanzamento < semaforoGialloPercentuale) this.setValoriSemaforo(
                                "verde",
                                BigDecimal.valueOf(percentualeAvanzamento).setScale(0, RoundingMode.HALF_EVEN),
                                Parameters.SEMAFORO_INCORSO.getValue(),
                                result
                            ); else this.setValoriSemaforo(
                                "giallo",
                                BigDecimal.valueOf(percentualeAvanzamento).setScale(0, RoundingMode.HALF_EVEN),
                                Parameters.SEMAFORO_INSCADENZA.getValue(),
                                result
                            );
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Semaforo getSemaforoMilestone(Integer idMilestone) {
        Semaforo result = new Semaforo();
        Optional<ProMil> milestoneOpt = milestoneRepository.findById(idMilestone);
        if (milestoneOpt.isPresent()) {
            if (milestoneOpt.get().getDtaded() == null) this.setValoriSemaforo(
                    "bianco",
                    BigDecimal.ZERO,
                    Parameters.SEMAFORO_NON_DEFINITO.getValue(),
                    result
                ); else if (milestoneOpt.get().getDtaeff() != null) this.setValoriSemaforo(
                    "verde",
                    BigDecimal.valueOf(100),
                    Parameters.SEMAFORO_TERMINATO.getValue(),
                    result
                ); else if (
                    new Date().after(milestoneOpt.get().getDtaded()) || new Date().equals(milestoneOpt.get().getDtaded())
            ) this.setValoriSemaforo("rosso", BigDecimal.ZERO, Parameters.SEMAFORO_DAIMPLEMENTARE.getValue(), result);
            else {
                Integer giorniAllaDeadline = this.differenceDateInDays(milestoneOpt.get().getDtaded(), new Date());
                Optional<AmmPar> ammParSemaforoGialloOpt = parametriRepository.getAmmParByCodiceIgnoreCase(
                        Parameters.SEMAFOROGIALLOMILESTONEGIORNI.getValue()
                );
                if (ammParSemaforoGialloOpt.isPresent()) {
                    Integer semaforoGialloGiorni = Integer.parseInt(ammParSemaforoGialloOpt.get().getValore());
                    if (giorniAllaDeadline > semaforoGialloGiorni) this.setValoriSemaforo(
                            "verde",
                            BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN),
                            Parameters.SEMAFORO_DAIMPLEMENTARE.getValue(),
                            result
                        ); else this.setValoriSemaforo(
                            "giallo",
                            BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN),
                            Parameters.SEMAFORO_INSCADENZA.getValue(),
                            result
                        );
                }
            }
        } else this.setValoriSemaforo("bianco", BigDecimal.ZERO, Parameters.SEMAFORO_NON_DEFINITO.getValue(), result);
        return result;
    }

    @Override
    public Semaforo getSemaforoMilestoneFase(Integer id) {
        Semaforo result = new Semaforo();
        Optional<ProFas> faseOpt = faseRepository.findById(id);
        List<Semaforo> semaforoListMilestone = new ArrayList<>();
        if (faseOpt.isPresent()) {
            List<ProMil> proMilList = milestoneRepository.findByIdFas(id.longValue());
            if (!proMilList.isEmpty()) {
                BigDecimal sommaPercentuali = BigDecimal.ZERO;
                for (ProMil milestone : proMilList) {
                    Semaforo semaforoMilestone = this.getSemaforoMilestone(milestone.getId());
                    sommaPercentuali =
                        sommaPercentuali.add(
                            semaforoMilestone.getPercentuale() == null ? BigDecimal.ZERO : semaforoMilestone.getPercentuale()
                        );
                    semaforoListMilestone.add(semaforoMilestone);
                }
                BigDecimal percentuale = sommaPercentuali.divide(BigDecimal.valueOf(proMilList.size()), RoundingMode.HALF_EVEN);
                result.setPercentuale(percentuale);
                this.calcolaColoreMilestone(semaforoListMilestone, result);
            } else this.setValoriSemaforo("bianco", BigDecimal.ZERO, Parameters.SEMAFORO_NON_DEFINITO.getValue(), result);
        } else this.setValoriSemaforo("bianco", BigDecimal.ZERO, Parameters.SEMAFORO_NON_DEFINITO.getValue(), result);
        return result;
    }

    @Override
    @Transactional
    public Semaforo getSemaforoAvanzamento(Integer id) throws MicroServicesException {
        Semaforo result = new Semaforo();
        Optional<ProAva> proAvaOpt = avanzamentoRepository.findById(id);
        if (proAvaOpt.isPresent()) {
            if (proAvaOpt.get().getNrover() != 0) this.setValoriSemaforo(
                    "bianco",
                    BigDecimal.ZERO,
                    Parameters.SEMAFORO_NON_DEFINITO.getValue(),
                    result
                ); else {
                Semaforo semaforoAvanzamento = null;
                BigDecimal sommaPercentuali = BigDecimal.ZERO;
                ProAva avanzamento = proAvaOpt.get();
                for (ProFas proFas : avanzamento.getProFasById()) {
                    Semaforo semaforoFase = this.getSemaforoFase(proFas.getId());
                    sommaPercentuali = sommaPercentuali.add(semaforoFase.getPercentuale());
                    if (semaforoAvanzamento == null || isColoreMaggiore(semaforoFase, semaforoAvanzamento)) semaforoAvanzamento =
                        semaforoFase;
                    Semaforo semaforoMilestone = this.getSemaforoMilestoneFase(proFas.getId());
                    if (
                        !semaforoMilestone.getColore().equals("bianco") && this.isColoreMaggiore(semaforoMilestone, semaforoAvanzamento)
                    ) semaforoAvanzamento = semaforoMilestone;
                }
                if (semaforoAvanzamento == null) this.setValoriSemaforo(
                        "bianco",
                        BigDecimal.ZERO,
                        Parameters.SEMAFORO_NON_DEFINITO.getValue(),
                        result
                    ); else this.setValoriSemaforo(
                        semaforoAvanzamento.getColore(),
                        sommaPercentuali.divide(BigDecimal.valueOf(avanzamento.getProFasById().size()), RoundingMode.HALF_EVEN),
                        semaforoAvanzamento.getDescrizione(),
                        result
                    );
            }
        } else this.setValoriSemaforo("bianco", BigDecimal.ZERO, Parameters.SEMAFORO_NON_DEFINITO.getValue(), result);
        return result;
    }

    private boolean isColoreMaggiore(Semaforo sem1, Semaforo sem2) throws MicroServicesException {
        Integer sem1Value = GerarchiaSemafori.assegnaValoreColore(sem1.getColore());
        Integer sem2Value = GerarchiaSemafori.assegnaValoreColore(sem2.getColore());
        return sem1Value >= sem2Value;
    }

    private void calcolaColoreMilestone(List<Semaforo> semaforoListMilestone, Semaforo result) {
        boolean isBianco = false;
        boolean isRosso = false;
        boolean isGiallo = false;
        boolean isInCorso = false;
        boolean isDaImpl = false;
        boolean isTerminato = false;
        for (Semaforo semaforo : semaforoListMilestone) {
            switch (semaforo.getColore()) {
                case "bianco":
                    isBianco = true;
                    break;
                case "rosso":
                    isRosso = true;
                    break;
                case "giallo":
                    isGiallo = true;
                    break;
                case "verde":
                    if (semaforo.getDescrizione().equals(Parameters.SEMAFORO_INCORSO.getValue())) isInCorso = true; else if (
                        semaforo.getDescrizione().equals(Parameters.SEMAFORO_DAIMPLEMENTARE.getValue())
                    ) isDaImpl = true; else isTerminato = true;
                    break;
            }
        }
        if (isBianco) this.setValoriSemaforo("bianco", BigDecimal.ZERO, Parameters.SEMAFORO_NON_DEFINITO.getValue(), result); else if (
            isRosso
        ) this.setValoriSemaforo("rosso", BigDecimal.ZERO, Parameters.SEMAFORO_DAIMPLEMENTARE.getValue(), result); else if (
            isGiallo
        ) this.setValoriSemaforo("giallo", BigDecimal.ZERO, Parameters.SEMAFORO_INSCADENZA.getValue(), result); else if (
            isInCorso
        ) this.setValoriSemaforo("verde", BigDecimal.ZERO, Parameters.SEMAFORO_INCORSO.getValue(), result); else if (
            isDaImpl
        ) this.setValoriSemaforo("verde", BigDecimal.ZERO, Parameters.SEMAFORO_DAIMPLEMENTARE.getValue(), result); else if (
            isTerminato
        ) this.setValoriSemaforo("verde", BigDecimal.ZERO, Parameters.SEMAFORO_TERMINATO.getValue(), result);
    }

    private void setValoriSemaforo(String colore, BigDecimal percentuale, String descrizione, Semaforo semaforo) {
        semaforo.setColore(colore);
        semaforo.setDescrizione(descrizione);
        semaforo.setPercentuale(percentuale);
    }

    private Integer differenceDateInDays(Date dataFine, Date dataInizio) {
        long diffInMillies = Math.abs(dataFine.getTime() - dataInizio.getTime());
        return Math.toIntExact(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS));
    }

    private Date getDataInizioFase(Integer id) {
        Optional<ProFas> faseOpt = faseRepository.findById(id);
        if (faseOpt.isPresent()) {
            ProFas fase = faseOpt.get();
            if (fase.getDtainipre() != null) return fase.getDtainipre(); else return fase.getDtainiori();
        } else return null;
    }

    private Date getDataFineFase(Integer id) {
        Optional<ProFas> faseOpt = faseRepository.findById(id);
        if (faseOpt.isPresent()) {
            ProFas fase = faseOpt.get();
            if (fase.getDtafinpre() != null) return fase.getDtafinpre(); else return fase.getDtafinori();
        } else return null;
    }
}
