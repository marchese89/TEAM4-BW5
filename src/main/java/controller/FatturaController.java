package controller;

import exceptions.BadRequestException;
import model.Fattura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import payloads.NewFatturaDTO;
import payloads.NewFatturaResponseDTO;
import service.FatturaService;

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

}
