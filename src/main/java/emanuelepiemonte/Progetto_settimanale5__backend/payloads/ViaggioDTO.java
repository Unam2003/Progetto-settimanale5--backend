package emanuelepiemonte.Progetto_settimanale5__backend.payloads;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ViaggioDTO(
        @NotBlank(message = "La destinazione è obbligatoria e non può essere una stringa vuota")
        @Size(min = 2, max = 30, message = "La destinazione deve essere compresa tra i 2 e i 30 caratteri")
        String destinazione,
        @FutureOrPresent(message = "La data deve essere oggi o una data futura")
        LocalDate data,
        @NotBlank(message = "Lo stato del viaggio è obbligatorio e non può essere una stringa vuota")
        @Size(min = 2, max = 12, message = "Lo stato del viaggio deve essere compreso tra i 2 e i 12 caratteri")
        String stato
) {
}
