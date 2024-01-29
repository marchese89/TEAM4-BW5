package antoniogiovanni.marchese.TEAM4BW5.repository;

import antoniogiovanni.marchese.TEAM4BW5.model.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndirizzoRepository extends JpaRepository<Indirizzo,Long> {
}
