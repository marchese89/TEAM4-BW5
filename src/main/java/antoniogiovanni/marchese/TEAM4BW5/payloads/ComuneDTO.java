package antoniogiovanni.marchese.TEAM4BW5.payloads;

import antoniogiovanni.marchese.TEAM4BW5.model.Provincia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;

public record ComuneDTO(
        @NotNull(message = "la denominazione non può essere null")
        String denominazione,
        @NotNull(message = "il progressivo del comune non può essere null")
        Long progressivoDelComune,
        @NotNull(message = "l'id provincia non può essere null")
        Long idProvincia
){
}
