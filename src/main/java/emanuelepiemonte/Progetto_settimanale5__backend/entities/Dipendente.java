package emanuelepiemonte.Progetto_settimanale5__backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "dipendenti")
public class Dipendente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "dipendente_id")
    private UUID dipendenteId;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "avatar_url")
    private String avatarURL;

    public Dipendente(String nome, String cognome, String username, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.avatarURL = "https://ui-avatars.com/api/?name=" + nome + "+" + cognome;
    }
}
