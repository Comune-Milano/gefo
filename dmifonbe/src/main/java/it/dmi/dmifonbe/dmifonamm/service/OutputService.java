package it.dmi.dmifonbe.dmifonamm.service;

public interface OutputService {


    public void newRow();

    public void add(Object o);

    public void addRow();

    public byte[] getBytes();

    public String getFileName();

    public void start(int idEla, String desOut, String tipOut);

    public void salvaOutputDB(Integer idEla);

}
