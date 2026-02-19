package it.dmi.dmifonbe.dmifonamm.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmOut;
import it.dmi.dmifonbe.dmifonamm.entities.AmmRig;
import it.dmi.dmifonbe.dmifonamm.repository.OutputRepository;
import it.dmi.dmifonbe.dmifonamm.repository.RigaRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Service
public class OutputServiceImpl {

    //private int idEla;
    //private int idOut;
    private String desOut;
    private String tipOut;
    private String riga;
    List<String> righe;
    //AmmOut ammOut;

    private int numRiga;
    private String sepCsv = ";";

    @Autowired
    OutputRepository outputRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    RigaRepository rigaRepository;



    public void start(int idEla, String desOut, String tipOut) {
        //this.idEla = idEla;
        this.desOut = desOut;
        this.tipOut = tipOut;
        righe = new ArrayList<>();
        riga = "";
        //idOut = 0;
        numRiga = 0;
        /*
        this.ammOut = null;
        if (idEla > 0) {
            //inserisco l'output
            this.ammOut = new AmmOut();
            this.ammOut.setIdEla(Long.valueOf(idEla));
            if (desOut.length() > 250)
                this.ammOut.setDesout(desOut.substring(0, 250));
            else
                this.ammOut.setDesout(desOut);
            this.ammOut.setTipout(tipOut);
            utilService.setInfoInsertRow(this.ammOut);
            AmmOut ammOutSaved = outputRepository.saveAndFlush(this.ammOut);
            this.ammOut=ammOutSaved;
        }
         */
    }

    public void newRow() {
        riga = "";
    }

    public void add(Object o) {
        String str = "";
        char doppiaApice = '"';
        if (o !=null) {
            if (o instanceof String)
                if (tipOut.equals("csv"))
                    str = doppiaApice + o.toString().replace(';', ',') + doppiaApice;
                else
                    str = o.toString();
            if (o instanceof Integer)
                str = o.toString();
            if (o instanceof Long)
                str=o.toString();
            if (o instanceof BigDecimal) {
                DecimalFormatSymbols localeSymbols = new DecimalFormatSymbols(Locale.ITALY);
                DecimalFormat df = new DecimalFormat();
                df.setDecimalFormatSymbols(localeSymbols);
                str=df.format(o);
            }
            if (o instanceof Double) {
                DecimalFormatSymbols localeSymbols = new DecimalFormatSymbols(Locale.ITALY);
                DecimalFormat df = new DecimalFormat();
                df.setDecimalFormatSymbols(localeSymbols);
                str=df.format(o);
            }
            if (o instanceof Date) {
                str= new SimpleDateFormat("dd/MM/yyyy").format(o);
            }
        }
        riga = riga + str;
        if (tipOut.equals("csv"))
            riga = riga + sepCsv;
    }

    public void addRow() {
        numRiga++;
        righe.add(riga);
        /*
        if (this.ammOut != null) {
            AmmRig ammRigImpe = new AmmRig();
            ammRigImpe.setIdOut(Long.valueOf(this.ammOut.getId()));
            ammRigImpe.setNrorig(Long.valueOf(this.numRiga));
            ammRigImpe.setPrgrig(1);
            if (riga.length() > 250)
                ammRigImpe.setTesrig(riga.substring(0, 250));
            else
                ammRigImpe.setTesrig(riga);
            if (this.ammOut != null) {
                System.out.println("IdEla " + this.ammOut.getIdEla());
                System.out.println("IdEla " + this.ammOut.getDesout());
            }
            System.out.println("IdOut " + ammRigImpe.getIdOut());
            System.out.println("Tesrig " + ammRigImpe.getTesrig());
            rigaRepository.saveAndFlush(ammRigImpe);
        }
         */
    }

    public void salvaOutputDB(Integer idEla) {
        if (idEla == null || idEla.intValue() == 0)
            return;
        AmmOut ammOut = new AmmOut();
        ammOut.setIdEla(Long.valueOf(idEla));
        if (desOut.length() > 250)
            ammOut.setDesout(desOut.substring(0, 250));
        else
            ammOut.setDesout(desOut);
        ammOut.setTipout(tipOut);
        utilService.setInfoInsertRow(ammOut);
        AmmOut ammOutSaved = outputRepository.saveAndFlush(ammOut);
        if (ammOutSaved != null && ammOutSaved.getId().intValue() > 0) {
            int numeroRiga = 0;
            System.out.println("righe " + righe.toString());
            for (String line : righe) {
                numeroRiga++;
                String[] righeList = line.split("(?<=\\G.{" + 250 + "})");
                int numeroPro = 0;
                System.out.println("righeList " + righeList.toString());
                for(String rigaEle: righeList){
                    AmmRig ammRig = new AmmRig();
                    ammRig.setIdOut(Long.valueOf(ammOutSaved.getId()));
                    ammRig.setNrorig(Long.valueOf(numeroRiga));
                    numeroPro++;
                    ammRig.setPrgrig(Integer.valueOf(numeroPro));
                    ammRig.setTesrig(rigaEle);
                    utilService.setInfoInsertRow(ammRig);
                    System.out.println("IdOut " + ammRig.getIdOut());
                    System.out.println("Tesrig " + ammRig.getTesrig());
                    rigaRepository.saveAndFlush(ammRig);
                }
                /*
                ammRig.setPrgrig(1);
                if (riga.length() > 250)

                    ammRig.setTesrig(riga.substring(0, 250));
                else
                    ammRig.setTesrig(riga);
                utilService.setInfoInsertRow(ammRig);
                System.out.println("IdOut " + ammRig.getIdOut());
                System.out.println("Tesrig " + ammRig.getTesrig());
                rigaRepository.saveAndFlush(ammRig);
                 */
            }
        }
    }

    public byte[] getBytes() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            for (String line : righe) {
                bos.write(line.getBytes());
                bos.write((char) 13);
                bos.write((char) 10);
            }
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    public String getFileName() {
        return desOut + "." + tipOut;
    }
}
