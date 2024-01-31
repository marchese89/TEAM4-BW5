package antoniogiovanni.marchese.TEAM4BW5.payloads;


import antoniogiovanni.marchese.TEAM4BW5.enums.TipoCliente;
import jakarta.validation.constraints.*;

public record ClienteDTO(@NotEmpty(message = "La ragione sociale è un campo obbligatorio!")
                         String ragioneSociale,
                         @NotEmpty(message = "La partita IVA è obbligatoria")
                         @Size(min = 13, max = 13, message = "La lunghezza deve essere di 11 caratteri")
                         String partitaIva,
                         @Email(message = "L'indirizzo inserito non è un indirizzo valido")
                         @NotEmpty(message = "La mail è un campo obbligatorio!")
                         String email,
                         @PositiveOrZero(message = "Il fatturato annuale deve essere un valore positivo o zero")
                         Double fatturatoAnnuale,
                         @Email(message = "L'indirizzo inserito non è un indirizzo valido")
                         @NotEmpty(message = "La pec è un campo obbligatorio!")
                         String pec,
                         @NotEmpty(message = "Il telefono è obbligatorio")
                         String telefono,
                         String emailContatto,
                         String nomeContatto,
                         String cognomeContatto,
                         String telefonoContatto,
                         @NotNull(message = "Il tipo cliente è un campo obbligatorio")
                         TipoCliente tipo
) {
}
