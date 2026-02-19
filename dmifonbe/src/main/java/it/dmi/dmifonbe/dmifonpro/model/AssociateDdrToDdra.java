package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class AssociateDdrToDdra implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @Valid
    private Integer ddra;

    @Valid
    private List<Integer> ddrs;

    public Integer getDdra() {
        return ddra;
    }

    public void setDdra(Integer ddra) {
        this.ddra = ddra;
    }

    public List<Integer> getDdrs() {
        return ddrs;
    }

    public void setDdrs(List<Integer> ddrs) {
        this.ddrs = ddrs;
    }
}
