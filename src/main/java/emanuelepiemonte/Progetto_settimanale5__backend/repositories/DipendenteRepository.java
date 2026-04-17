package emanuelepiemonte.Progetto_settimanale5__backend.repositories;

import emanuelepiemonte.Progetto_settimanale5__backend.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {
    boolean existsByEmail(String email);
}
