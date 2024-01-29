package antoniogiovanni.marchese.TEAM4BW5.payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NewFatturaDTO(
        @NotNull(message ="Data obbligatorio" )
        LocalDate data,
        @NotNull(message ="Importo fattura obbligatorio" )
        Double importo,
        @NotNull(message ="Id obbligatorio" )
        Long id
){
}