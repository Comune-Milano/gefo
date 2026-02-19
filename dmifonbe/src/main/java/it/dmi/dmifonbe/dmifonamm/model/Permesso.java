package it.dmi.dmifonbe.dmifonamm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class Permesso implements Serializable {

    private Long idRuo;

    @HtmlEscape
    private String nome;

    public Permesso(Long idRuo, String nome) {
        this.idRuo = idRuo;
        this.nome = nome;
    }

    public Long getIdRuo() {
        return idRuo;
    }

    public void setIdRuo(Long idRuo) {
        this.idRuo = idRuo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        Permesso that = (Permesso) o;
        return that.idRuo.equals(this.idRuo) && that.nome.equals(this.nome);
    }
}
