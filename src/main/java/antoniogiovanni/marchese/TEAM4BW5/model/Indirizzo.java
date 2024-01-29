package antoniogiovanni.marchese.TEAM4BW5.model;

import antoniogiovanni.marchese.TEAM4BW5.enums.TipoIndirizzo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "indirizzi")
@Getter
@Setter
@NoArgsConstructor
public class Indirizzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    private  String via;
    private String numeroCivico;//ad esempio accettiamo 1/A
    private String localita;
    private int cap;
    @Enumerated(EnumType.STRING)
    private TipoIndirizzo tipoIndirizzo;
    @ManyToOne
    @JoinColumn(name = "comune_id")
    private Comune comune;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
