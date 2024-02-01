package antoniogiovanni.marchese.TEAM4BW5.repository;

import antoniogiovanni.marchese.TEAM4BW5.enums.TipoIndirizzo;
import antoniogiovanni.marchese.TEAM4BW5.model.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndirizzoRepository extends JpaRepository<Indirizzo,Long> {
    @Query("SELECT COUNT(i) FROM Indirizzo i WHERE i.cliente.id = :userId AND i.tipoIndirizzo = :addressType")
    int countByUserIdAndType(@Param("userId") Long userId, @Param("addressType") TipoIndirizzo addressType);

    @Query("SELECT i FROM Indirizzo i WHERE i.cliente.id = :idCliente")
    List<Indirizzo> getByIdUtente(@Param("idCliente") Long idCliente);
}
