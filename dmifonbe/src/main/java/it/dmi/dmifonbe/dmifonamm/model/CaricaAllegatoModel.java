package it.dmi.dmifonbe.dmifonamm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonamm.entities.AmmAll;
import it.dmi.dmifonbe.dmifonamm.entities.AmmFil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class CaricaAllegatoModel implements Serializable {

    @Valid
    private AmmFilBase64 file;

    @Valid
    private AmmAll allegato;

    @Valid
    public AmmFilBase64 getFile() {
        return file;
    }

    public void setFile(AmmFilBase64 file) {
        this.file = file;
    }

    public AmmAll getAllegato() {
        return allegato;
    }

    public void setAllegato(AmmAll allegato) {
        this.allegato = allegato;
    }
}
