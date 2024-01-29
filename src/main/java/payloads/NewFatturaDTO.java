package payloads;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NewFatturaDTO(
LocalDate data, Double importo, Long numero, Long id
){
}
