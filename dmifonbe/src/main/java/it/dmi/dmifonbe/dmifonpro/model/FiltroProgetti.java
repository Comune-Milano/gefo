package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonamm.entities.AmmUteruo;
import it.dmi.dmifonbe.dmifonamm.repository.AmmUteRuoRepository;
import it.dmi.dmifonbe.dmifonamm.repository.DirezioneRepository;
import java.io.Serializable;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
@Component
public class FiltroProgetti implements Serializable {

    @Autowired
    AmmUteRuoRepository ammUteRuoRepository;

    @Autowired
    DirezioneRepository direzioneRepository;

    private Integer idDirezione;

    public void generateFiltroProgetti(Integer idUteRuo) {
        Optional<AmmUteruo> uteruoOptional = ammUteRuoRepository.findById(idUteRuo);
        if (uteruoOptional.isPresent()) {
            AmmUteruo uteruo = uteruoOptional.get();
            if (
                uteruo.getTipcondat() != null && !uteruo.getTipcondat().isBlank() && uteruo.getTipcondat().equalsIgnoreCase("c")
            ) this.idDirezione = (int) uteruo.getIdDir(); else this.idDirezione = 0;
        }
    }

    public Boolean isFiltered() {
        return !this.idDirezione.equals(0);
    }

    public Integer getIdDirezione() {
        return idDirezione;
    }

    public void setIdDirezione(Integer idDirezione) {
        this.idDirezione = idDirezione;
    }
}
