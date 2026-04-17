package emanuelepiemonte.Progetto_settimanale5__backend.controllers;

import emanuelepiemonte.Progetto_settimanale5__backend.entities.Dipendente;
import emanuelepiemonte.Progetto_settimanale5__backend.exceptions.ValidationException;
import emanuelepiemonte.Progetto_settimanale5__backend.payloads.DipendenteDTO;
import emanuelepiemonte.Progetto_settimanale5__backend.payloads.NewDipendenteResp;
import emanuelepiemonte.Progetto_settimanale5__backend.services.DipendenteService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    private final DipendenteService dipendenteService;

    public DipendenteController(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }

    @GetMapping
    public Page<Dipendente> getDipendenti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sortBy
    ) {
        return this.dipendenteService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteResp createDipendente(@RequestBody @Validated DipendenteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            validationResult.getFieldErrors().forEach(fieldError -> System.out.println(fieldError.getDefaultMessage()));

            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();

            throw new ValidationException(errors);
        }
        Dipendente newDipendente = this.dipendenteService.saveDipendente(body);
        return new NewDipendenteResp(newDipendente.getDipendenteId());
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente getDipendenteById(@PathVariable UUID dipendenteId) {
        return this.dipendenteService.findById(dipendenteId);
    }

    @PutMapping("/{dipendenteId}")
    public Dipendente getDipendenteByIdAndUpdate(@PathVariable UUID dipendenteId, @RequestBody DipendenteDTO body) {
        return this.dipendenteService.findByIdAndUpdate(dipendenteId, body);
    }

    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getDipendenteByIdAndDelete(@PathVariable UUID dipendenteId) {
        this.dipendenteService.findByIdAndDelete(dipendenteId);
    }

    @PatchMapping("/{dipendenteId}/avatar")
    public void uploadAvatar(@RequestParam("profile_picture") MultipartFile file, @PathVariable UUID dipendenteId) {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        System.out.println(file.getContentType());

        this.dipendenteService.avatarUpload(file, dipendenteId);

    }
}
