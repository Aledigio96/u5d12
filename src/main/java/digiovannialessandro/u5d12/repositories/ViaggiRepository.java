package digiovannialessandro.u5d12.repositories;

import digiovannialessandro.u5d12.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ViaggiRepository extends JpaRepository<Viaggio,Integer> {

}
