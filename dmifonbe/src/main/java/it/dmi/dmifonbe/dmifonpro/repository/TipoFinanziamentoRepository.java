package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipfin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoFinanziamentoRepository extends JpaRepository<ProTipfin, Integer> {
    Optional<ProTipfin> findProTipfinByCodtipfinIgnoreCase(String codTipFin);
    Page<ProTipfin> findAllByOrderByLivunoAscLivdueAscLivtreAscLivquaAscLivcinAscLivseiAsc(Pageable page);

    @Query(
        value = "SELECT tf FROM ProTipfin tf WHERE upper(tf.codtipfin) LIKE %:autocomplete% OR upper(tf.destipfin) LIKE %:autocomplete% ORDER BY tf.codtipfin"
    )
    List<ProTipfin> findAutocomplete(@Param(value = "autocomplete") String autocomplete);

    List<ProTipfin> findAllByOrderByCodtipfinAsc();

    Optional<ProTipfin> findProTipfinByLivunoAndLivdueAndLivtreAndLivquaAndLivcinAndLivsei(Long livuno, Long livdue, Long livtre, Long livqua, Long livcin, Long livsei);
}
