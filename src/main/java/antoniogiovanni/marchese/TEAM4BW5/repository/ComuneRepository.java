package antoniogiovanni.marchese.TEAM4BW5.repository;

import antoniogiovanni.marchese.TEAM4BW5.model.Comune;
import antoniogiovanni.marchese.TEAM4BW5.model.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;
import java.util.List;

@Repository
public interface ComuneRepository extends JpaRepository <Comune, Long> {
    @Query("SELECT c FROM Comune c WHERE c.provincia.id = :idProvincia")
    List<Comune> getComuniByProvincia(@Param("idProvincia") Long idProvincia);
}
