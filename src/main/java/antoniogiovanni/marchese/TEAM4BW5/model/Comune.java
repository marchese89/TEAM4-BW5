package antoniogiovanni.marchese.TEAM4BW5.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long progressivoDelComune;

    private String denominazione;

    @ManyToOne
    @JoinColumn(name = "provincia", nullable = false)
    private Provincia provincia;



}
