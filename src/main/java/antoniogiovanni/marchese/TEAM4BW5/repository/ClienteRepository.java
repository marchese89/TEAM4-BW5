package antoniogiovanni.marchese.TEAM4BW5.repository;

import antoniogiovanni.marchese.TEAM4BW5.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    //-----------------------ORDER

    Page<Cliente> findAllByOrderByNomeContattoAsc(Pageable pageable);

    Page<Cliente> findAllByOrderByNomeContattoDesc(Pageable pageable);

    //    List<Cliente> findAllByOrderByNomeContattoAsc();
    Page<Cliente> findAllByOrderByFatturatoAnnuale(Pageable pageable);

    Page<Cliente> findAllByOrderByFatturatoAnnualeAsc(Pageable pageable);

    Page<Cliente> findAllByOrderByFatturatoAnnualeDesc(Pageable pageable);

    Page<Cliente> findAllByOrderByDataInserimentoAsc(Pageable pageable);

    Page<Cliente> findAllByOrderByDataInserimentoDesc(Pageable pageable);

    Page<Cliente> findAllByOrderByDataUltimoContattoAsc(Pageable pageable);

    Page<Cliente> findAllByOrderByDataUltimoContattoDesc(Pageable pageable);

    //-----------------------FILTER

    Page<Cliente> findByFatturatoAnnualeGreaterThanEqual(Double fatturato, Pageable pageable);

    Page<Cliente> findByFatturatoAnnualeLessThanEqual(Double fatturato, Pageable pageable);

    Page<Cliente> findByDataInserimentoAfter(LocalDate dataInserimento, Pageable pageable);

    Page<Cliente> findByDataUltimoContattoAfter(LocalDate dataUltimoContatto, Pageable pageable);

    Page<Cliente> findByNomeContattoContainingIgnoreCase(String parteDelNome, Pageable pageable);

    @Query("SELECT c FROM Cliente c JOIN c.indirizzi i JOIN i.comune comune JOIN comune.provincia provincia WHERE i.tipoIndirizzo = 'SEDE_LEGALE' AND UPPER(provincia.provincia) = UPPER(:provincia)")
    Page<Cliente> findByProvinciaSedeLegale(@Param("provincia") String provincia, Pageable pageable);

    @Query("SELECT c FROM Cliente c JOIN c.indirizzi i JOIN i.comune comune JOIN comune.provincia provincia WHERE i.tipoIndirizzo = 'SEDE_LEGALE' ORDER BY UPPER(provincia.provincia)")
    Page<Cliente> findAllOrderByProvincia(Pageable pageable);

}
