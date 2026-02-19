package it.dmi.dmifonbe.dmifonamm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize
public class AmmFilBase64 {

    private Integer id;

    @HtmlEscape
    private String file;

    @HtmlEscape
    private String dataType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
