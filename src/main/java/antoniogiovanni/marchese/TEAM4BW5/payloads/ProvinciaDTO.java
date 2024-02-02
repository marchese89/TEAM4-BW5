package antoniogiovanni.marchese.TEAM4BW5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

public record ProvinciaDTO (
        @NotEmpty(message = "La sigla non può essere vuota")
        @NotNull(message = "La sigla è un campo obbligatorio!")
        String sigla,
        @NotEmpty(message = "La provincia non può essere vuota")
        @NotNull(message = "La provincia è un campo obbligatorio!")
        String provincia,
        @NotEmpty(message = "La regione non può essere vuota")
        @NotNull(message = "La regione è un campo obbligatorio!")
        String regione
) {

}
