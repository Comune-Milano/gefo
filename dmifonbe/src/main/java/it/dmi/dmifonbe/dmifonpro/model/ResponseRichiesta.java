package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonamm.entities.AmmUte;
import it.dmi.dmifonbe.dmifonpro.entities.ProRic;

import java.io.Serializable;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class ResponseRichiesta implements Serializable {

    private ProRic proRic;
    private List<AmmUte> users;

    private ProRic proRicPad;

    public ProRic getProRicPad() {
        return proRicPad;
    }

    public void setProRicPad(ProRic proRicPad) {
        this.proRicPad = proRicPad;
    }

    public ProRic getProRic() {
        return proRic;
    }

    public void setProRic(ProRic proRic) {
        this.proRic = proRic;
    }

    public List<AmmUte> getUsers() {
        return users;
    }

    public void setUsers(List<AmmUte> users) {
        this.users = users;
    }
}
