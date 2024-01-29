package antoniogiovanni.marchese.TEAM4BW5.payloads;

import antoniogiovanni.marchese.TEAM4BW5.model.Provincia;
import lombok.Getter;

@Getter

public class ComuneDTO {
    int codiceProvincia; String denominazione; Provincia provincia;

    public ComuneDTO(long progressivoDelComune) {
    }
}
