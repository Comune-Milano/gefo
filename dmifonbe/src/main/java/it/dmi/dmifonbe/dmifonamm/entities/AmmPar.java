package it.dmi.dmifonbe.dmifonamm.entities;

import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "amm_par", schema = "dmifonamm", catalog = "dmifon")
public class AmmPar extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @HtmlEscape
    @Column(name = "codice", nullable = false, length = 100)
    private String codice;

    @Basic
    @HtmlEscape
    @Column(name = "valore", nullable = false, length = 250)
    private String valore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmmPar ammPar = (AmmPar) o;

        if (id != ammPar.id) return false;
        if (codice != null ? !codice.equals(ammPar.codice) : ammPar.codice != null) return false;
        if (valore != null ? !valore.equals(ammPar.valore) : ammPar.valore != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (codice != null ? codice.hashCode() : 0);
        result = 31 * result + (valore != null ? valore.hashCode() : 0);
        return result;
    }
}
