package antoniogiovanni.marchese.TEAM4BW5.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comune {
    @Id
    private int progressivoDelComune;

    private int codiceProvincia;
    private String denominazione;

    @ManyToOne
    @JoinColumn(name = "provincia", nullable = false)
    private Provincia provincia;


}
