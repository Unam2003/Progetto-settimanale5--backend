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
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "prenotazione_id")
    private UUID prenotazioneId;
    @Column(name = "data_di_richiesta")
    private LocalDate dataDiRichiesta;
    @Column(nullable = false)
    private String note;

    @ManyToOne
    @JoinColumn(name = "dipendente_id", nullable = false)
    private Dipendente dipendente;

    @ManyToOne
    @JoinColumn(name = "viaggio_id", nullable = false)
    private Viaggio viaggio;

    public Prenotazione(LocalDate dataDiRichiesta, String note, Dipendente dipendente) {
        this.dataDiRichiesta = LocalDate.now();
        this.note = note;
        this.dipendente = dipendente;
    }
}
