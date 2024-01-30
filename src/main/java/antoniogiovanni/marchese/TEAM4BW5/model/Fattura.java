package antoniogiovanni.marchese.TEAM4BW5.model;

import antoniogiovanni.marchese.TEAM4BW5.enums.StatoFattura;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "fatture")
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "data")
    private LocalDate data;
    @Column(name = "importo")
    private Double importo;
    @Column(name = "numero")
    private Long numero;
    @Column(name = "stato_fattura")
    private StatoFattura statoFattura;


    @ManyToOne
    @JoinColumn(name = "cliente_fattura")
    private Cliente cliente;
}