package antoniogiovanni.marchese.TEAM4BW5.model;

import antoniogiovanni.marchese.TEAM4BW5.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "utenti")
@NoArgsConstructor
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente")
    private long id;
    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private Role role;
}
