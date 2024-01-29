package antoniogiovanni.marchese.TEAM4BW5.repository;

import antoniogiovanni.marchese.TEAM4BW5.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteDAO extends JpaRepository<Utente,Long> {
    Optional<Utente> findByEmail(String email);
}
