package antoniogiovanni.marchese.TEAM4BW5.payloads;

import lombok.Getter;

import java.util.UUID;

public record ProvinciaDTO (
    String sigla,
    String provincia,
    String regione
) {

}
