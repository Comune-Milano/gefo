package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProMacpro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MacroProgettoRepository extends JpaRepository<ProMacpro, Integer> {
    Optional<ProMacpro> findProMacproByCodmacproIgnoreCaseAndIdTipfinda(String codMacPro, Long idTipfinda);
    Page<ProMacpro> findAllByOrderByCodmacproAsc(Pageable page);

    @Query(
        value = "SELECT mp FROM ProMacpro mp WHERE upper(mp.codmacpro) LIKE %:autocomplete% OR upper(mp.desmacpro) LIKE %:autocomplete% order by mp.codmacpro"
    )
    List<ProMacpro> findAutocomplete(@Param(value = "autocomplete") String autocomplete);

    List<ProMacpro> findAllByOrderByCodmacproAsc();
}
