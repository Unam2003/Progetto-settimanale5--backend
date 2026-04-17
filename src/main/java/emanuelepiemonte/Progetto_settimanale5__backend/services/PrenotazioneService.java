package emanuelepiemonte.Progetto_settimanale5__backend.services;

import emanuelepiemonte.Progetto_settimanale5__backend.entities.Dipendente;
import emanuelepiemonte.Progetto_settimanale5__backend.entities.Prenotazione;
import emanuelepiemonte.Progetto_settimanale5__backend.entities.Viaggio;
import emanuelepiemonte.Progetto_settimanale5__backend.exceptions.BadRequestException;
import emanuelepiemonte.Progetto_settimanale5__backend.exceptions.NotFoundException;
import emanuelepiemonte.Progetto_settimanale5__backend.payloads.PrenotazioneDTO;
import emanuelepiemonte.Progetto_settimanale5__backend.repositories.DipendenteRepository;
import emanuelepiemonte.Progetto_settimanale5__backend.repositories.PrenotazioneRepository;
import emanuelepiemonte.Progetto_settimanale5__backend.repositories.ViaggioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;
    private final ViaggioRepository viaggioRepository;
    private final DipendenteRepository dipendenteRepository;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, ViaggioRepository viaggioRepository, DipendenteRepository dipendenteRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.viaggioRepository = viaggioRepository;
        this.dipendenteRepository = dipendenteRepository;
    }

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione savePrenotazione(PrenotazioneDTO body) {
        Dipendente dipendente = dipendenteRepository.findById(body.dipendenteId()).orElseThrow(() -> new NotFoundException(body.dipendenteId()));
        Viaggio viaggio = viaggioRepository.findById(body.viaggioId()).orElseThrow(() -> new NotFoundException(body.viaggioId()));

        if (prenotazioneRepository.existsByDipendenteAndViaggioData(dipendente, viaggio.getData())) {
            throw new BadRequestException("Dipendente già impegnato in un'altra prenotazione per la stessa data");
        }

        Prenotazione newPrenotazione = new Prenotazione();
        newPrenotazione.setDipendente(dipendente);
        Prenotazione savePrenotazione = this.prenotazioneRepository.save(newPrenotazione);
        savePrenotazione.setViaggio(viaggio);
        savePrenotazione.setDataDiRichiesta(LocalDate.now());
        savePrenotazione.setNote(body.note());
        log.info("La prenotazione con id " + savePrenotazione.getPrenotazioneId() + " è stata creata correttamente");
        return savePrenotazione;
    }


    public Prenotazione findById(UUID prenotazioneId) {
        return this.prenotazioneRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    public Prenotazione findByIdAndUpdate(UUID prenotazioneId, PrenotazioneDTO body) {
        Prenotazione found = this.findById(prenotazioneId);
        found.setNote(body.note());

        Prenotazione updatePrenotazione = this.prenotazioneRepository.save(found);
        log.info("La prenotazione con id + " + updatePrenotazione.getPrenotazioneId() + " è stata modificata correttamente");
        return updatePrenotazione;
    }

    public void findByIdAndDelete(UUID prenotazioneId) {
        Prenotazione found = this.findById(prenotazioneId);
        this.prenotazioneRepository.delete(found);
    }

}
