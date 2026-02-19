package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipimp;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoImportoRepository extends JpaRepository<ProTipimp, Integer> {
    @Query(value = "SELECT ti FROM ProTipimp ti WHERE NOT ti.flgdicui = 'S' order by ti.ordtipimp ")
    List<ProTipimp> findAllFlgdicuiNotEqualS();

    @Query(value = "SELECT ti FROM ProTipimp ti WHERE ti.flgdicui = 'S' order by ti.ordtipimp ")
    List<ProTipimp> findAllFlgdicuiEqualS();

    @Query(value = "SELECT ti FROM ProTipimp ti WHERE ti.flgdicui = 'S' order by ti.ordtipimp ")
    Page<ProTipimp> findAllFlgdicuiEqualSPaginato(Pageable pageable);
}
