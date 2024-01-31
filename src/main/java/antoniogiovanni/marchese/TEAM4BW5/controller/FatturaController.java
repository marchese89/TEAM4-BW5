package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.enums.StatoFattura;
import antoniogiovanni.marchese.TEAM4BW5.exceptions.BadRequestException;
import antoniogiovanni.marchese.TEAM4BW5.model.Cliente;
import antoniogiovanni.marchese.TEAM4BW5.model.Fattura;
import antoniogiovanni.marchese.TEAM4BW5.payloads.NewFatturaDTO;
import antoniogiovanni.marchese.TEAM4BW5.service.FatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import antoniogiovanni.marchese.TEAM4BW5.payloads.NewFatturaResponseDTO;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/fatture")
public class FatturaController {
    @Autowired
    private FatturaService fatturaService;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/{fatturaId}")
    public Fattura getFatturaById(@PathVariable Long fatturaId) {
        return fatturaService.findById(fatturaId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping
    public Page<Fattura> getFattura(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String orderBy) {
        return fatturaService.getFattura(page, size, orderBy);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewFatturaResponseDTO createFattura(@RequestBody @Validated NewFatturaDTO newFatturaPayload, BindingResult validation)
    {
        System.out.println(validation);
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList().toString());
        }
        Fattura newFattura = fatturaService.save(newFatturaPayload);
        return new NewFatturaResponseDTO(newFattura.getId());
    }

    @PutMapping("/{fatturaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Fattura getFatturaByIdAndUpdate(@PathVariable Long fatturaId, @RequestBody Fattura modifiedFatturaPayload) {
        return fatturaService.findByIdAndUpdate(fatturaId, modifiedFatturaPayload);
    }

    @DeleteMapping("/{fatturaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getFatturaByIdAndDelete(@PathVariable Long fatturaId) {
        fatturaService.findByIdAndDelete(fatturaId);
    }



    @GetMapping("/annuali/{clienteId}/{anno}")
    public ResponseEntity<List<Fattura>> getFattureAnnuali(@PathVariable Long clienteId, @PathVariable int anno) {
        List<Fattura> fattureAnnuali = fatturaService.trovaFattureAnnuali(clienteId, anno);
        return new ResponseEntity<>(fattureAnnuali, HttpStatus.OK);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Fattura>> getFatturePerCliente(@PathVariable Long clienteId) {
        List<Fattura> fatturePerCliente = fatturaService.trovaFatturePerCliente(clienteId);
        return new ResponseEntity<>(fatturePerCliente, HttpStatus.OK);
    }

    @GetMapping("/stato/{statoFattura}")
    public ResponseEntity<List<Fattura>> getFatturePerStato(@PathVariable StatoFattura statoFattura) {
        List<Fattura> fatturePerStato = fatturaService.trovaFatturePerStato(statoFattura);
        return new ResponseEntity<>(fatturePerStato, HttpStatus.OK);
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<Fattura>> getFatturePerData(@PathVariable LocalDate data) {
        List<Fattura> fatturePerData = fatturaService.trovaFatturePerData(data);
        return new ResponseEntity<>(fatturePerData, HttpStatus.OK);
    }

    @GetMapping("/intervallo-data/{inizio}/{fine}")
    public ResponseEntity<List<Fattura>> getFatturePerIntervalloData(@PathVariable LocalDate inizio, @PathVariable LocalDate fine) {
        List<Fattura> fatturePerIntervalloData = fatturaService.trovaFatturePerIntervalloData(inizio, fine);
        return new ResponseEntity<>(fatturePerIntervalloData, HttpStatus.OK);
    }

    @GetMapping("/intervallo-data-importo/{inizio}/{fine}/{importoMin}/{importoMax}")
    public ResponseEntity<List<Fattura>> getFatturePerIntervalloDataEImporto(@PathVariable LocalDate inizio, @PathVariable LocalDate fine, @PathVariable Double importoMin, @PathVariable Double importoMax) {
        List<Fattura> fatturePerIntervalloDataEImporto = fatturaService.trovaFatturePerIntervalloDataEImporto(inizio, fine, importoMin, importoMax);
        return new ResponseEntity<>(fatturePerIntervalloDataEImporto, HttpStatus.OK);
    }

}
