package emanuelepiemonte.Progetto_settimanale5__backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import emanuelepiemonte.Progetto_settimanale5__backend.entities.Dipendente;
import emanuelepiemonte.Progetto_settimanale5__backend.exceptions.BadRequestException;
import emanuelepiemonte.Progetto_settimanale5__backend.exceptions.NotFoundException;
import emanuelepiemonte.Progetto_settimanale5__backend.payloads.DipendenteDTO;
import emanuelepiemonte.Progetto_settimanale5__backend.repositories.DipendenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class DipendenteService {
    private final DipendenteRepository dipendenteRepository;
    private final Cloudinary cloudinaryUploader;

    public DipendenteService(DipendenteRepository dipendenteRepository, Cloudinary cloudinaryUploader) {
        this.dipendenteRepository = dipendenteRepository;
        this.cloudinaryUploader = cloudinaryUploader;
    }

    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dipendenteRepository.findAll(pageable);
    }

    public Dipendente saveDipendente(DipendenteDTO body) {
        if (this.dipendenteRepository.existsByEmail(body.email()))
            throw new BadRequestException("L'indirizzo email " + body.email() + " è già in uso!");
        Dipendente newDipendente = new Dipendente(body.nome(), body.cognome(), body.username(), body.email());
        Dipendente saveDipendente = this.dipendenteRepository.save(newDipendente);
        log.info("Il dipendente con id " + saveDipendente.getDipendenteId() + " è stato creato correttamente");
        return saveDipendente;
    }

    public Dipendente findById(UUID dipendenteId) {
        return this.dipendenteRepository.findById(dipendenteId).orElseThrow(() -> new NotFoundException(dipendenteId));
    }

    public Dipendente findByIdAndUpdate(UUID dipendenteId, DipendenteDTO body) {
        Dipendente found = this.findById(dipendenteId);

        if (!found.getEmail().equals(body.email())) {
            if (this.dipendenteRepository.existsByEmail(body.email()))
                throw new BadRequestException("L'inditizzo email " + body.email() + " è già in uso!");
        }
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setUsername(body.username());
        found.setEmail(body.email());

        Dipendente updateDipendente = this.dipendenteRepository.save(found);
        log.info("Il dipendente con id " + updateDipendente.getDipendenteId() + " è stato modificato correttamente");
        return updateDipendente;
    }

    public void findByIdAndDelete(UUID dipendenteId) {
        Dipendente found = this.findById(dipendenteId);
        this.dipendenteRepository.delete(found);
    }

    public void avatarUpload(MultipartFile file, UUID dipendenteId) {
        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) result.get("secure_url");
            System.out.println(url);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
