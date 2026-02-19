package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgettoRepository extends JpaRepository<ProPro, Integer> {
    Optional<ProPro> findProProByCodproIgnoreCase(String codPro);
    Page<ProPro> findAllByOrderByCodproAsc(Pageable page);

    List<ProPro> findAllByOrderByCodproAsc();
    List<ProPro> findAllByIdPropad(Long idPropad);

    boolean existsByProNilByIdNilId(Integer id);

    @Query(
        value = "SELECT p FROM ProPro p WHERE upper(p.codpro) LIKE %:autocomplete% OR upper(p.despro) LIKE %:autocomplete% OR upper(p.codcup) LIKE %:autocomplete%"
    )
    List<ProPro> findAutocomplete(@Param(value = "autocomplete") String autocomplete);

    @Query(
        value = "SELECT p FROM ProPro p WHERE (upper(p.codpro) LIKE %:autocomplete% OR upper(p.despro) LIKE %:autocomplete% OR upper(p.codcup) LIKE %:autocomplete%) and p.idDir = :idDir"
    )
    List<ProPro> findAutocompleteByDir(@Param(value = "autocomplete") String autocomplete, @Param(value = "idDir") Integer idDir);

    @Query(value = "SELECT p FROM ProPro p WHERE p.idDir = :idDir ORDER BY p.codpro")
    List<ProPro> findByDir(@Param(value = "idDir") Integer idDir);
}
