package antoniogiovanni.marchese.TEAM4BW5.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timestamp) {
}
