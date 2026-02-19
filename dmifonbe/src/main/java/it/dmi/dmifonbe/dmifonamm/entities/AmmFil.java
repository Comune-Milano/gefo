package it.dmi.dmifonbe.dmifonamm.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "amm_fil", schema = "dmifonamm", catalog = "dmifon")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class AmmFil extends EntitySuperClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "data_type", nullable = true, length = 100)
    @HtmlEscape
    private String dataType;

    @Column(name = "file", nullable = true)
    private byte[] file;

    @Valid
    @OneToMany(mappedBy = "ammFilByIdFil", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<AmmAll> ammAllsById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmmFil ammFil = (AmmFil) o;

        if (id != null ? !id.equals(ammFil.id) : ammFil.id != null) return false;
        if (!Arrays.equals(file, ammFil.file)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }

    public Collection<AmmAll> getAmmAllsById() {
        return ammAllsById;
    }

    public void setAmmAllsById(Collection<AmmAll> ammAllsById) {
        this.ammAllsById = ammAllsById;
    }
}
