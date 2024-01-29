package repository;

import model.Fattura;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FatturaDAO extends JpaRepository<Fattura, Long> {
    Optional<Fattura> findById(Long id);
}