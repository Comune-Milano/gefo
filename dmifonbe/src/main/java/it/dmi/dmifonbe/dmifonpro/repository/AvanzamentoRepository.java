package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AvanzamentoRepository extends JpaRepository<ProAva, Integer> {
    Optional<ProAva> findByNroverAndIdPro(Long nrover, Long idPro);

    @Query(value = "SELECT MAX(a.nrover) FROM ProAva a WHERE a.idPro = :idPro")
    Long findMaxNroVersionByIdPro(@Param(value = "idPro") Long idPro);

    boolean existsByProLisvalByIdLisvalfasintId(Integer id);

    boolean existsByProLisvalByIdLisvallivcriId(Integer id);

    boolean existsByProLisvalByIdLisvalstaantId(Integer id);

    boolean existsByProLisvalByIdLisvaltipappId(Integer id);

    boolean existsByProLisvalByIdLisvaltipolfasId(Integer id);
}
