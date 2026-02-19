package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import it.dmi.dmifonbe.dmifonpro.entities.ProDdra;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class DdraDetail implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @Valid
    private ProDdra Ddra;

    @Valid
    private BigDecimal importoDdra;

    public ProDdra getDdra() {
        return Ddra;
    }

    public void setDdra(ProDdra ddra) {
        Ddra = ddra;
    }

    public BigDecimal getImportoDdra() {
        return importoDdra;
    }

    public void setImportoDdra(BigDecimal importoDdra) {
        this.importoDdra = importoDdra;
    }
}
