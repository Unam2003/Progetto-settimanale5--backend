package emanuelepiemonte.Progetto_settimanale5__backend.controllers;

import emanuelepiemonte.Progetto_settimanale5__backend.entities.Prenotazione;
import emanuelepiemonte.Progetto_settimanale5__backend.exceptions.ValidationException;
import emanuelepiemonte.Progetto_settimanale5__backend.payloads.NewPrenotazioneDTO;
import emanuelepiemonte.Progetto_settimanale5__backend.payloads.PrenotazioneDTO;
import emanuelepiemonte.Progetto_settimanale5__backend.services.PrenotazioneService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @GetMapping
    public Page<Prenotazione> getPrenotazione(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "note") String sortBy
    ) {
        return this.prenotazioneService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewPrenotazioneDTO createPrenotazione(@RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            validationResult.getFieldErrors().forEach(fieldError -> System.out.println(fieldError.getDefaultMessage()));

            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();

            throw new ValidationException(errors);
        }
        Prenotazione newPrenotazione = this.prenotazioneService.savePrenotazione(body);
        return new NewPrenotazioneDTO(newPrenotazione.getPrenotazioneId());
    }

    @GetMapping("/{prenotazioneId}")
    public Prenotazione getPrenotazioneById(@PathVariable UUID prenotazioneId) {
        return this.prenotazioneService.findById(prenotazioneId);
    }

    @PutMapping("/{prenotazioneId}")
    public Prenotazione getPrenotazioneByIdAndUpdate(@PathVariable UUID prenotazioneId, @RequestBody PrenotazioneDTO body) {
        return this.prenotazioneService.findByIdAndUpdate(prenotazioneId, body);
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getPrenotazioneByIdAndDelete(@PathVariable UUID prenotazioneId) {
        this.prenotazioneService.findByIdAndDelete(prenotazioneId);
    }

}
