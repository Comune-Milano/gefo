package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmDir;
import it.dmi.dmifonbe.dmifonamm.repository.DirezioneRepository;
import it.dmi.dmifonbe.dmifonpro.entities.*;
import it.dmi.dmifonbe.dmifonpro.model.Risorse;
import it.dmi.dmifonbe.dmifonpro.repository.*;
import it.dmi.dmifonbe.dmifonpro.service.CalcolaTotaliService;
import it.dmi.dmifonbe.model.EntityType;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.Totali;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CalcolaTotaliImpl implements CalcolaTotaliService {

    @Autowired
    EntityManager em;

    @Autowired
    TipoFinanziamentoRepository tipoFinanziamentoRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    NilRepository nilRepository;

    @Autowired
    DirezioneRepository direzioneRepository;

    @Autowired
    MacroProgettoRepository macroProgettoRepository;

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    BandoRepository bandoRepository;

    @Value("${nroprofon1}")
    String nroprofon1;

    @Value("${nroprofon2}")
    String nroprofon2;

    @Value("${imprisfon1}")
    String imprisfon1;

    @Value("${imprisfon2}")
    String imprisfon2;

    @Value("${filterTipFin}")
    String filterTipFin;

    @Value("${filterDir}")
    String filterDir;

    @Value("${filterMacPro}")
    String filterMacPro;

    @Value("${filterBan}")
    String filterBan;

    @Value("${filterProTem}")
    String filterProTem;

    @Value("${filterProMun}")
    String filterProMun;

    @Value("${filterProNil}")
    String filterProNil;

    @Value("${filterPro}")
    String filterPro;

    @Value("${imppre1}")
    String imppre1;

    @Value("${imppre2}")
    String imppre2;

    @Value("${impddr1}")
    String impddr1;

    @Value("${impddr2}")
    String impddr2;

    @Value("${imprisorsedicui1}")
    String imprisorsedicui1;

    @Value("${imprisorsedicui2}")
    String imprisorsedicui2;

    @Value("${imprisorse1}")
    String imprisorse1;

    @Value("${imprisfonpro}")
    String imprisfonpro;

    @Value("${impristotpro}")
    String impristotpro;

    @Value("${imprisorse2}")
    String imprisorse2;

    @Value("${imprisorse3}")
    String imprisorse3;

    @Value("${imprisorse4}")
    String imprisorse4;

    @Value("${impimpegni1}")
    String impimpegni1;

    @Value("${impimpegni2}")
    String impimpegni2;

    @Value("${impimpegni21}")
    String impimpegni21;

    @Value("${impimpegni3}")
    String impimpegni3;

    @Value("${impimpegnipre1}")
    String impimpegnipre1;

    @Value("${impimpegnipre2}")
    String impimpegnipre2;

    public Totali getTotali(String entityType, int id) throws ParseException, MicroServicesException {
        Totali result = new Totali();
        this.checkEntityExist(entityType, id, result);
        Totali totaliFondi = this.getTotaliFondi(entityType, id);
        result.setImprisfon(totaliFondi.getImprisfon());
        result.setNroprofon(totaliFondi.getNroprofon());
        result.setImprisfonpro(totaliFondi.getImprisfonpro());
        result.setImpristotpro(totaliFondi.getImpristotpro());

        result.setRisorse(this.getTotaliRisorse(entityType, id).getRisorse());

        result.setRisorseDiCui(this.getTotaliRisorseDiCui(entityType, id).getRisorseDiCui());

        Totali totaliImpegniAccertamenti = this.getTotaliImpegniAndAccertamenti(entityType, id);
        result.setImpimp(totaliImpegniAccertamenti.getImpimp());
        result.setImpecoimp(totaliImpegniAccertamenti.getImpecoimp());
        result.setImpliq(totaliImpegniAccertamenti.getImpliq());
        result.setImpman(totaliImpegniAccertamenti.getImpman());
        result.setImpacc(totaliImpegniAccertamenti.getImpacc());
        result.setImpecoacc(totaliImpegniAccertamenti.getImpecoacc());
        result.setImprev(totaliImpegniAccertamenti.getImprev());
        result.setImpigv(totaliImpegniAccertamenti.getImpigv());
        result.setImpigvdel(totaliImpegniAccertamenti.getImpigvdel());

        result.setImpdaacc(result.getImprisfon().subtract(result.getImpacc()));
        result.setImpdaimp(result.getImprisfon().subtract(result.getImpimp()));

        Totali totaliDdr = this.getTotaliDdr(entityType, id);
        result.setImpddr(totaliDdr.getImpddr());
        result.setImpamm(totaliDdr.getImpamm());
        result.setImptra(totaliDdr.getImptra());
        result.setImpinc(totaliDdr.getImpinc());

        Totali totaliPrevisioni = this.getTotaliPrevisioni(entityType, id);
        result.setImpprvese(totaliPrevisioni.getImpprvese());
        result.setImpprvese1(totaliPrevisioni.getImpprvese1());
        result.setImpprvsuc(totaliPrevisioni.getImpprvsuc());

        return result;
    }

    private void checkEntityExist(String entityType, int id, Totali result) throws MicroServicesException {
        if (entityType.equals(EntityType.PROTIPFIN.getValue())) {
            Optional<ProTipfin> entity = tipoFinanziamentoRepository.findById(id);
            if (entity.isPresent()) {
                result.setDesTipEnt(EntityType.PROTIPFIN.getDesc());
                result.setDesEnt(entity.get().getDestipfin());
            } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
        } else if (entityType.equals(EntityType.AMMDIR.getValue())) {
            Optional<AmmDir> entity = direzioneRepository.findById(id);
            if (entity.isPresent()) {
                result.setDesTipEnt(EntityType.AMMDIR.getDesc());
                result.setDesEnt(entity.get().getDesdir());
            } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
        } else if (entityType.equals(EntityType.MACPRO.getValue())) {
            Optional<ProMacpro> entity = macroProgettoRepository.findById(id);
            if (entity.isPresent()) {
                result.setDesTipEnt(EntityType.MACPRO.getDesc());
                result.setDesEnt(entity.get().getDesmacpro());
            } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
        } else if (entityType.equals(EntityType.BAN.getValue())) {
            Optional<ProBan> entity = bandoRepository.findById(id);
            if (entity.isPresent()) {
                result.setDesTipEnt(EntityType.BAN.getDesc());
                result.setDesEnt(entity.get().getDesban());
            } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
        } else if (entityType.equals(EntityType.PRO.getValue())) {
            Optional<ProPro> entity = progettoRepository.findById(id);
            if (entity.isPresent()) {
                result.setDesTipEnt(EntityType.PRO.getDesc());
                result.setDesEnt(entity.get().getDespro());
            } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
        } else if (entityType.equals(EntityType.MUN.getValue())) {
            Optional<ProMun> entity = municipioRepository.findById(id);
            if (entity.isPresent()) {
                result.setDesTipEnt(EntityType.MUN.getDesc());
                result.setDesEnt(entity.get().getDesmun());
            } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
        } else if (entityType.equals(EntityType.NIL.getValue())) {
            Optional<ProNil> entity = nilRepository.findById(id);
            if (entity.isPresent()) {
                result.setDesTipEnt(EntityType.NIL.getDesc());
                result.setDesEnt(entity.get().getDesnil());
            } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
        }
    }

    public Totali getTotaliFondi(String entityType, int id) {
        Totali result = new Totali();
        this.calcolaNroProFon(entityType, id, result);
        //importo delle risorse del fondo dei progetti di livello basso
        this.calcolaImprisfon(entityType, id, result);
        //importo delle risorse del fondo del progetto
        this.calcolaImprisfonpro(entityType, id, result);
        //importo delle risorse totali (fondo+ente+...) del progetto
        this.calcolaImpristotpro(entityType, id, result);
        return result;
    }

    public Totali getTotaliRisorse(String entityType, int id) {
        Totali result = new Totali();
        this.calcolaTotaliRisorse(entityType, id, result);
        return result;
    }

    public Totali getTotaliRisorseDiCui(String entityType, int id) {
        Totali result = new Totali();
        this.calcolaTotaliRisorseDiCui(entityType, id, result);
        return result;
    }

    public Totali getTotaliImpegniAndAccertamenti(String entityType, int id) {
        Totali result = new Totali();
        this.calcolaTotaliImpegniAndAccertamenti(Parameters.CALCIMP.getValue(), entityType, id, result);
        this.calcolaTotaliImpegniAndAccertamenti(Parameters.CALCACCE.getValue(), entityType, id, result);
        this.calcolaTotaliImpegniPrecedenti(entityType, id, result);
        return result;
    }

    public Totali getTotaliImpegniAndAccertamentiPregressi(String entityType, int id) {
        Totali result = new Totali();
        result.setImpimp(BigDecimal.ZERO);
        result.setImpman(BigDecimal.ZERO);
        result.setImpacc(BigDecimal.ZERO);
        result.setImprev(BigDecimal.ZERO);
        result.setImpigv(BigDecimal.ZERO);
        result.setImpigvdel(BigDecimal.ZERO);
        this.calcolaTotaliImpegniPrecedenti(entityType, id, result);
        return result;
    }

    public Totali getTotaliDdr(String entityType, int id) {
        Totali result = new Totali();
        String filter = this.getFilter(entityType);
        Query res = em.createNativeQuery(impddr1 + " " + filter + " " + impddr2);
        res.setParameter(1, id);
        Object[] importiDdrObjects = (Object[]) res.getSingleResult();
        result.setImpddr(importiDdrObjects[0] == null ? BigDecimal.ZERO : (BigDecimal) importiDdrObjects[0]);
        result.setImpamm(importiDdrObjects[1] == null ? BigDecimal.ZERO : (BigDecimal) importiDdrObjects[1]);
        result.setImptra(importiDdrObjects[2] == null ? BigDecimal.ZERO : (BigDecimal) importiDdrObjects[2]);
        result.setImpinc(importiDdrObjects[3] == null ? BigDecimal.ZERO : (BigDecimal) importiDdrObjects[3]);
        return result;
    }

    public Totali getTotaliPrevisioni(String entityType, int id) throws ParseException {
        Totali result = new Totali();
        String dataDa = "01/01/", dataA = "31/12/", endYear = "9999";
        Calendar now = Calendar.getInstance();
        int intCurrentYear = now.get(Calendar.YEAR);
        int intNextYear = intCurrentYear + 1;
        int intYearAfterNextYear = intCurrentYear + 2;

        String currentYear = String.valueOf(intCurrentYear);
        String nextYear = String.valueOf(intNextYear);
        String yearAfterNextYear = String.valueOf(intYearAfterNextYear);

        BigDecimal impPrvEse = this.calcolaImportiPrevisioni(dataDa + currentYear, dataA + currentYear, entityType, id);
        BigDecimal impPrvEse1 = this.calcolaImportiPrevisioni(dataDa + nextYear, dataA + nextYear, entityType, id);
        BigDecimal impPrvSuc = this.calcolaImportiPrevisioni(dataDa + yearAfterNextYear, dataA + endYear, entityType, id);

        result.setImpprvese(impPrvEse == null ? BigDecimal.ZERO : impPrvEse);
        result.setImpprvese1(impPrvEse1 == null ? BigDecimal.ZERO : impPrvEse1);
        result.setImpprvsuc(impPrvSuc == null ? BigDecimal.ZERO : impPrvSuc);
        return result;
    }

    //importo delle risorse del fondo dei progetti di livello basso
    private void calcolaImprisfon(String entityType, int id, Totali result) {
        String filter = this.getFilter(entityType);
        Query res = em.createNativeQuery(imprisfon1 + " " + filter + " " + imprisfon2);
        res.setParameter(1, id);
        BigDecimal impRisFon = (BigDecimal) res.getSingleResult();
        result.setImprisfon(impRisFon == null ? BigDecimal.ZERO : impRisFon);
    }

    private void calcolaImprisfonpro(String entityType, int id, Totali result) {
        if (!entityType.equals(EntityType.PRO.getValue())) {
            result.setImprisfonpro(BigDecimal.ZERO);
        } else {
            Query res = em.createNativeQuery(imprisfonpro);
            res.setParameter(1, id);
            BigDecimal impRisFon = (BigDecimal) res.getSingleResult();
            result.setImprisfonpro(impRisFon == null ? BigDecimal.ZERO : impRisFon);
        }
    }

    private void calcolaImpristotpro(String entityType, int id, Totali result) {
        if (!entityType.equals(EntityType.PRO.getValue())) {
            result.setImprisfonpro(BigDecimal.ZERO);
        } else {
            Query res = em.createNativeQuery(impristotpro);
            res.setParameter(1, id);
            BigDecimal impRisFon = (BigDecimal) res.getSingleResult();
            result.setImpristotpro(impRisFon == null ? BigDecimal.ZERO : impRisFon);
        }
    }

    private void calcolaNroProFon(String entityType, int id, Totali result) {
        String filter = this.getFilter(entityType);
        Query res = em.createNativeQuery(nroprofon1 + " " + filter + " " + nroprofon2);
        res.setParameter(1, id);
        BigInteger nroProFon = (BigInteger) res.getSingleResult();
        result.setNroprofon(nroProFon == null ? BigInteger.ZERO : nroProFon);
    }

    private void calcolaTotaliRisorse(String entityType, int id, Totali result) {
        String filter = this.getFilter(entityType);
        Query res = em.createNativeQuery(
            imprisorse1 + " " + filter + " " + imprisorse2 + " " + imprisorse3 + " " + filter + " " + imprisorse4
        );
        res.setParameter(1, id);
        List<Object[]> risorseObjects = res.getResultList();
        List<Risorse> totaliRisorse = new ArrayList<>();
        Risorse risorse;
        BigDecimal imppro = BigDecimal.ZERO;
        BigDecimal impacc = BigDecimal.ZERO;
        BigDecimal imprev = BigDecimal.ZERO;
        BigDecimal impimp = BigDecimal.ZERO;
        BigDecimal impman = BigDecimal.ZERO;
        for (Object[] risorsaObject : risorseObjects) {
            risorse = Risorse.getRisorsaFromObject(risorsaObject);
            //calcolo anche l'impegnato, mandati, accertato e reversali per tipo di importo
            try {
                Query res1 = em.createNativeQuery(impimpegni1 + " " + filter + " " + impimpegni21);
                res1.setParameter(1, id);
                res1.setParameter(2, Parameters.CALCIMP.getValue());
                res1.setParameter(3, risorse.getIdTipImp().intValue());
                Object[] impegniObjects = (Object[]) res1.getSingleResult();
                risorse.setSumImpImp(impegniObjects[0] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[0]);
                risorse.setSumImpMan(impegniObjects[3] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[3]);
                res1 = em.createNativeQuery(impimpegni1 + " " + filter + " " + impimpegni21);
                res1.setParameter(1, id);
                res1.setParameter(2, Parameters.CALCACCE.getValue());
                res1.setParameter(3, risorse.getIdTipImp());
                impegniObjects = (Object[]) res1.getSingleResult();
                risorse.setSumImpAcc(impegniObjects[0] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[0]);
                risorse.setSumImpRev(impegniObjects[3] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[3]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }
            imppro = imppro.add(risorse.getSumImpPro());
            impacc = impacc.add(risorse.getSumImpAcc());
            imprev = imprev.add(risorse.getSumImpRev());
            impimp = impimp.add(risorse.getSumImpImp());
            impman = impman.add(risorse.getSumImpMan());
            totaliRisorse.add(risorse);
        }
        //aggiungo una riga con il totale
        Risorse risorseTotale = new Risorse();
        risorseTotale.setDesTipImp("TOTALI");
        risorseTotale.setIdTipImp(BigInteger.valueOf(0));
        risorseTotale.setOrdTipImp(9999);
        risorseTotale.setSumImpPro(imppro);
        risorseTotale.setSumImpAcc(impacc);
        risorseTotale.setSumImpRev(imprev);
        risorseTotale.setSumImpImp(impimp);
        risorseTotale.setSumImpMan(impman);
        totaliRisorse.add(risorseTotale);
        result.setRisorse(totaliRisorse);
    }

    private void calcolaTotaliRisorseDiCui(String entityType, int id, Totali result) {
        String filter = this.getFilter(entityType);
        Query res = em.createNativeQuery(imprisorsedicui1 + " " + filter + " " + imprisorsedicui2);
        res.setParameter(1, id);
        List<Object[]> risorseObjects = res.getResultList();
        List<Risorse> totaliRisorse = new ArrayList<>();
        for (Object[] risorsaObject : risorseObjects) {
            totaliRisorse.add(Risorse.getRisorsaFromObject(risorsaObject));
        }
        result.setRisorseDiCui(totaliRisorse);
    }

    private void calcolaTotaliImpegniAndAccertamenti(String paramImpeAcc, String entityType, int id, Totali result) {
        String filter = this.getFilter(entityType);
        Query res = em.createNativeQuery(impimpegni1 + " " + filter + " " + impimpegni2);
        res.setParameter(1, id);
        res.setParameter(2, paramImpeAcc);
        Object[] impegniObjects = (Object[]) res.getSingleResult();
        if (paramImpeAcc.equals(Parameters.CALCIMP.getValue())) {
            result.setImpimp(impegniObjects[0] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[0]);
            result.setImpecoimp(impegniObjects[1] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[1]);
            result.setImpliq(impegniObjects[2] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[2]);
            result.setImpman(impegniObjects[3] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[3]);
        } else if (paramImpeAcc.equals(Parameters.CALCACCE.getValue())) {
            result.setImpacc(impegniObjects[0] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[0]);
            result.setImpecoacc(impegniObjects[1] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[1]);
            result.setImprev(impegniObjects[3] == null ? BigDecimal.ZERO : (BigDecimal) impegniObjects[3]);
        }
    }

    private void calcolaTotaliImpegniPrecedenti(String entityType, int id, Totali result) {
        String filter = this.getFilter(entityType);
        Query res = em.createNativeQuery(impimpegnipre1 + " " + filter + " " + impimpegnipre2);
        res.setParameter(1, id);
        Object[] impegniPrecedentiObjects = (Object[]) res.getSingleResult();
        result.setImpacc(
            result.getImpacc().add(impegniPrecedentiObjects[1] == null ? BigDecimal.ZERO : (BigDecimal) impegniPrecedentiObjects[1])
        );
        result.setImpimp(
            result
                .getImpimp()
                .add(impegniPrecedentiObjects[0] == null ? BigDecimal.ZERO : (BigDecimal) impegniPrecedentiObjects[0])
                .add(impegniPrecedentiObjects[4] == null ? BigDecimal.ZERO : (BigDecimal) impegniPrecedentiObjects[4])
        );
        result.setImpman(
            result.getImpman().add(impegniPrecedentiObjects[2] == null ? BigDecimal.ZERO : (BigDecimal) impegniPrecedentiObjects[2])
        );
        result.setImprev(
            result.getImprev().add(impegniPrecedentiObjects[3] == null ? BigDecimal.ZERO : (BigDecimal) impegniPrecedentiObjects[3])
        );
        result.setImpigv(impegniPrecedentiObjects[5] == null ? BigDecimal.ZERO : (BigDecimal) impegniPrecedentiObjects[5]);
        result.setImpigvdel(impegniPrecedentiObjects[6] == null ? BigDecimal.ZERO : (BigDecimal) impegniPrecedentiObjects[6]);
    }

    private BigDecimal calcolaImportiPrevisioni(String dataDa, String dataA, String entityType, int id) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        Date dateDataDa = formatter.parse(dataDa);
        Date dateDataA = formatter.parse(dataA);

        String filter = this.getFilter(entityType);
        Query res = em.createNativeQuery(imppre1 + " " + filter + " " + imppre2);
        res.setParameter(1, id);
        res.setParameter(2, dateDataDa);
        res.setParameter(3, dateDataA);
        BigDecimal impegniObjects = (BigDecimal) res.getSingleResult();
        return impegniObjects;
    }

    private String getFilter(String entityType) {
        String filter = "";
        if (entityType.equals(EntityType.PROTIPFIN.getValue())) {
            filter = filterTipFin;
        } else if (entityType.equals(EntityType.AMMDIR.getValue())) {
            filter = filterDir;
        } else if (entityType.equals(EntityType.MACPRO.getValue())) {
            filter = filterMacPro;
        } else if (entityType.equals(EntityType.BAN.getValue())) {
            filter = filterBan;
        } else if (entityType.equals(EntityType.PRO.getValue())) {
            filter = filterPro;
        } else if (entityType.equals(EntityType.PROTEM.getValue())) {
            filter = filterProTem;
        } else if (entityType.equals(EntityType.MUN.getValue())) {
            filter = filterProMun;
        } else if (entityType.equals(EntityType.NIL.getValue())) {
            filter = filterProNil;
        }
        return filter;
    }
}
