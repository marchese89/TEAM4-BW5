package antoniogiovanni.marchese.TEAM4BW5.repository;

import antoniogiovanni.marchese.TEAM4BW5.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@R
public interface ProvinciaRepository extends JpaRepository <Provincia, UUID> {
    Optional<Object> findByName(String provincia);


}
