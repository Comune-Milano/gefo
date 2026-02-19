package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProTem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TematicaRepository extends JpaRepository<ProTem, Integer> {
    Optional<ProTem> findProTemByCodtemIgnoreCase(String codTem);
    Optional<ProTem> findProTemByLivunoAndLivdue(int livuno, int livdue);
    Page<ProTem> findAllByOrderByLivunoAscLivdueAsc(Pageable page);

    @Query(
        value = "SELECT tem FROM ProTem tem WHERE upper(tem.codtem) LIKE %:autocomplete% OR upper(tem.destem) LIKE %:autocomplete% order by tem.codtem"
    )
    List<ProTem> findAutocomplete(@Param(value = "autocomplete") String autocomplete);

    List<ProTem> findAllByOrderByDestemAsc();
}
