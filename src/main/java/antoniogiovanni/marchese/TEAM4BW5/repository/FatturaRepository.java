package antoniogiovanni.marchese.TEAM4BW5.repository;

import antoniogiovanni.marchese.TEAM4BW5.enums.StatoFattura;
import antoniogiovanni.marchese.TEAM4BW5.model.Cliente;
import antoniogiovanni.marchese.TEAM4BW5.model.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, Long> {

    List<Fattura> findByClienteAndDataBetweenOrderByDataAsc(Cliente cliente, LocalDate inizioAnno, LocalDate fineAnno);

    List<Fattura> findByClienteOrderByDataAsc(Cliente cliente);
    List<Fattura> findByStatoFatturaOrderByDataAsc(StatoFattura statoFattura);
    List<Fattura> findByDataOrderByDataAsc(LocalDate data);
    List<Fattura> findByDataBetweenOrderByDataAsc(LocalDate inizio, LocalDate fine);
    List<Fattura> findByDataBetweenAndImportoBetweenOrderByDataAsc(LocalDate inizio, LocalDate fine, Double importoMin, Double importoMax);

}
