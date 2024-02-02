package antoniogiovanni.marchese.TEAM4BW5.payloads;

import antoniogiovanni.marchese.TEAM4BW5.enums.StatoFattura;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewFatturaDTO(
        @NotNull(message = "Data obbligatorio")
        LocalDate data,
        @NotNull(message = "Id cliente obbligatorio")
        long idCliente,
        @NotNull(message = "Importo fattura obbligatorio")
        Double importo,
//        @NotNull(message ="Numero obbligatorio" )
//        Long numero,
        @NotNull(message = "Stato fattura obbligatorio")
        StatoFattura statoFattura
) {
}