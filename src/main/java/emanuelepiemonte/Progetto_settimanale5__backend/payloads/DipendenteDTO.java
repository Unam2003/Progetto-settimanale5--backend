package emanuelepiemonte.Progetto_settimanale5__backend.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotBlank(message = "Il nome proprio è obbligatorio e non può essere una stringa vuota")
        @Size(min = 2, max = 30, message = "Il nome proprio deve essere compreso tra i 2 e i 30 caratteri")
        String nome,
        @NotBlank(message = "Il cognome è obbligatorio e non può essere una stringa vuota")
        @Size(min = 2, max = 30, message = "Il cognome deve essere compreso tra i 2 e i 30 caratteri")
        String cognome,
        @NotBlank(message = "L'username è obbligatorio e non può essere una stringa vuota")
        @Size(min = 2, max = 15, message = "L'username deve essere compreso tra i 2 e i 15 caratteri")
        String username,
        @NotBlank(message = "L'email è obbligatorio e non può essere una stringa vuota")
        @Email(message = "L'email inserita non è del formato corretto")
        String email,
        String avatar
) {

}
