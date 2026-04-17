package emanuelepiemonte.Progetto_settimanale5__backend.payloads;

import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PrenotazioneDTO(
        @Size(min = 2, max = 100, message = "La nota deve essere essere compresa tra i 2 e i 100 caratteri")
        String note,
        UUID dipendenteId,
        UUID viaggioId
) {
}
