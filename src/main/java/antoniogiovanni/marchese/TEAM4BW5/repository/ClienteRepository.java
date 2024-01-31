package antoniogiovanni.marchese.TEAM4BW5.repository;

import antoniogiovanni.marchese.TEAM4BW5.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    //-----------------------ORDER
    Page<Cliente> findAllByOrderByNomeContattoAsc(Pageable pageable);

    Page<Cliente> findAllByOrderByNomeContattoDesc(Pageable pageable);

//    List<Cliente> findAllByOrderByNomeContattoAsc();

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

}
