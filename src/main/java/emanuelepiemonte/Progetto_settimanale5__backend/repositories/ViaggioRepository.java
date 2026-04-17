package emanuelepiemonte.Progetto_settimanale5__backend.repositories;

import emanuelepiemonte.Progetto_settimanale5__backend.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, UUID> {
}
