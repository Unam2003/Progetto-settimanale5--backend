package emanuelepiemonte.Progetto_settimanale5__backend.controllers;

import emanuelepiemonte.Progetto_settimanale5__backend.entities.Viaggio;
import emanuelepiemonte.Progetto_settimanale5__backend.exceptions.ValidationException;
import emanuelepiemonte.Progetto_settimanale5__backend.payloads.NewViaggioRespDTO;
import emanuelepiemonte.Progetto_settimanale5__backend.payloads.ViaggioDTO;
import emanuelepiemonte.Progetto_settimanale5__backend.services.ViaggioService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/viaggi")
public class ViaggiController {
    private final ViaggioService viaggioService;

    public ViaggiController(ViaggioService viaggioService) {
        this.viaggioService = viaggioService;
    }

    @GetMapping
    public Page<Viaggio> getViaggi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "stato") String sortBy
    ) {
        return this.viaggioService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewViaggioRespDTO createViaggio(@RequestBody @Validated ViaggioDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            validationResult.getFieldErrors().forEach(fieldError -> System.out.println(fieldError.getDefaultMessage()));

            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();

            throw new ValidationException(errors);
        }
        Viaggio newViaggio = this.viaggioService.saveViaggio(body);
        return new NewViaggioRespDTO(newViaggio.getViaggioId());
    }

    @GetMapping("/{viaggioId}")
    public Viaggio getViaggioById(@PathVariable UUID viaggioId) {
        return this.viaggioService.findById(viaggioId);
    }

    @PutMapping("/{viaggioId}")
    public Viaggio getViaggioByIdAndUpdate(@PathVariable UUID viaggioId, @RequestBody ViaggioDTO body) {
        return this.viaggioService.findByIdAndUpdate(viaggioId, body);
    }

    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getViaggioByIdAndDelete(@PathVariable UUID viaggioId) {
        this.viaggioService.findByIdAndDelete(viaggioId);
    }

}
