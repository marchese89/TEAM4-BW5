package payloads;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NewFatturaDTO(
LocalDate data, Long importo, Long numero, Long id
){
}
