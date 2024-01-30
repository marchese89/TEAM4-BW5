package antoniogiovanni.marchese.TEAM4BW5.payloads;

import antoniogiovanni.marchese.TEAM4BW5.enums.TipoIndirizzo;
import antoniogiovanni.marchese.TEAM4BW5.model.Comune;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewIndirizzoDTO(
        @NotNull(message = "l'indirizzo deve avere una via")
        @NotEmpty(message = "la via non può essere vuota")
        String via,
        @NotNull(message = "l'indirizzo deve avere un numero civico")
        @NotEmpty(message = "il numero civico non può essere vuoto")
        String numeroCivico,//ad esempio accettiamo 1/A
        @NotNull(message = "l'indirizzo deve avere una località")
        @NotEmpty(message = "la località non può essere vuota")
        String localita,
        @NotNull(message = "l'indirizzo deve avere un cap")
        int cap,
        @NotNull(message = "l'indirizzo deve avere un comune")
        long idComune,
        @NotNull(message = "l'indirizzo deve avere un cliente")
        long idCliente,
        TipoIndirizzo tipoIndirizzo

) {
}
