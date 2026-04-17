package emanuelepiemonte.Progetto_settimanale5__backend.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsDTO(String message, LocalDateTime timestamp) {
}
