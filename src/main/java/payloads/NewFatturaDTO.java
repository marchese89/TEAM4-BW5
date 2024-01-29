package payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NewFatturaDTO(
        @NotNull(message ="Campo obbligatorio" )
        LocalDate data,
        @NotNull(message ="Campo obbligatorio" )
        Double importo,
        @NotNull(message ="Campo obbligatorio" )
        Long numero,
        @NotNull(message ="Campo obbligatorio" )
        Long id
){
}
