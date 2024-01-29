package antoniogiovanni.marchese.TEAM4BW5.payloads;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProvinciaDTO {
    String sigla; String provincia; String regione;

    public ProvinciaDTO(UUID id) {
    }
}
