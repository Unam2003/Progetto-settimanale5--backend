package emanuelepiemonte.Progetto_settimanale5__backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "viaggi")
public class Viaggio {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "viaggio_id")
    private UUID viaggioId;
    @Column(nullable = false)
    private String destinazione;
    @Column(nullable = false)
    private LocalDate data;
    private String stato;

    public Viaggio(String destinazione, LocalDate data, String stato) {
        this.destinazione = destinazione;
        this.data = data;
        this.stato = stato;
    }
}
