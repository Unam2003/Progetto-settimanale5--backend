package emanuelepiemonte.Progetto_settimanale5__backend.services;

import com.cloudinary.Cloudinary;
import emanuelepiemonte.Progetto_settimanale5__backend.entities.Viaggio;
import emanuelepiemonte.Progetto_settimanale5__backend.exceptions.NotFoundException;
import emanuelepiemonte.Progetto_settimanale5__backend.payloads.ViaggioDTO;
import emanuelepiemonte.Progetto_settimanale5__backend.repositories.ViaggioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ViaggioService {
    private final ViaggioRepository viaggioRepository;
    private final Cloudinary cloudinaryUploader;

    public ViaggioService(ViaggioRepository viaggioRepository, Cloudinary cloudinaryUploader) {
        this.viaggioRepository = viaggioRepository;
        this.cloudinaryUploader = cloudinaryUploader;
    }

    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.viaggioRepository.findAll(pageable);
    }

    public Viaggio saveViaggio(ViaggioDTO body) {
        Viaggio newViaggio = new Viaggio(body.destinazione(), body.data(), body.stato());
        Viaggio saveViaggio = this.viaggioRepository.save(newViaggio);
        log.info("Il viaggio con id " + saveViaggio.getViaggioId() + " è stato creato correttamente");
        return saveViaggio;
    }

    public Viaggio findById(UUID viaggioId) {
        return this.viaggioRepository.findById(viaggioId).orElseThrow(() -> new NotFoundException(viaggioId));
    }

    public Viaggio findByIdAndUpdate(UUID viaggioId, ViaggioDTO body) {
        Viaggio found = this.findById(viaggioId);


        found.setDestinazione(body.destinazione());
        found.setData(body.data());
        found.setStato(body.stato());

        Viaggio updateDipendente = this.viaggioRepository.save(found);
        log.info("Il viaggio con id " + updateDipendente.getViaggioId() + " è stato modificato correttamente");
        return updateDipendente;
    }

    public void findByIdAndDelete(UUID viaggioId) {
        Viaggio found = this.findById(viaggioId);
        this.viaggioRepository.delete(found);
    }

}
