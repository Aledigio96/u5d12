package digiovannialessandro.u5d11.repositories;

import digiovannialessandro.u5d11.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendentiRepository extends JpaRepository<Dipendente,Integer> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Dipendente> findByEmail(String email);
}
