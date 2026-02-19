package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ExportProgettiResponse implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    private byte[] data;
    private String fileName;


    public void setData(byte[] data) {
        this.data = data;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getData() {
        return data;
    }

    public String getFileName() {
        return fileName;
    }



}
