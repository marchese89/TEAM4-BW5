package antoniogiovanni.marchese.TEAM4BW5.repository;

import antoniogiovanni.marchese.TEAM4BW5.model.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;

@Repository
public interface ComuneRepository extends JpaRepository <Comune, Long> {
}
