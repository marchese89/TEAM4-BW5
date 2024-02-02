package antoniogiovanni.marchese.TEAM4BW5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String sigla;
    private String provincia;
    private String regione;
    @OneToMany(mappedBy = "provincia")
    @JsonIgnore
    private List<Comune> comuneList;
}
