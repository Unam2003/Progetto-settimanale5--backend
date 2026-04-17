package emanuelepiemonte.Progetto_settimanale5__backend.repositories;

import emanuelepiemonte.Progetto_settimanale5__backend.entities.Dipendente;
import emanuelepiemonte.Progetto_settimanale5__backend.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
    boolean existsByDipendenteAndViaggioData(Dipendente dipendente, LocalDate data);
}
