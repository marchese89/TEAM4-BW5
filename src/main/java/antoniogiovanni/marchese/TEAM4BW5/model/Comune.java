package antoniogiovanni.marchese.TEAM4BW5.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Comune {
    @Id
    private long progressivoDelComune;

    private int codiceProvincia;
    private String denominazione;

    @ManyToOne
    @JoinColumn(name = "provincia", nullable = false)
    private Provincia provincia;



}
