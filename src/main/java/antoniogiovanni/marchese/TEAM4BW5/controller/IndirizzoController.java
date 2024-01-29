package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.exceptions.BadRequestException;
import antoniogiovanni.marchese.TEAM4BW5.model.Indirizzo;
import antoniogiovanni.marchese.TEAM4BW5.payloads.NewIndirizzoDTO;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ResponseDTO;
import antoniogiovanni.marchese.TEAM4BW5.service.IndirizzoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {

    @Autowired
    private IndirizzoService indirizzoService;
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Indirizzo> getEventi(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String orderBy) {
        return indirizzoService.getIndirizzi(page, size, orderBy);
    }
    @GetMapping("/{idIndirizzo}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Indirizzo getEventoById(@PathVariable long idIndirizzo){
        return indirizzoService.findById(idIndirizzo);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO createIndirizzo(@RequestBody @Validated NewIndirizzoDTO indirizzoDTO, BindingResult validation){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList().toString());
        }
        Indirizzo indirizzo = indirizzoService.save(indirizzoDTO);
        return new ResponseDTO(indirizzo.getId());
    }
    @PutMapping("/{idIndirizzo}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseDTO modifyIndirizzo( @RequestBody @Validated NewIndirizzoDTO eventoDTO,@PathVariable long idIndirizzo ,BindingResult validation){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList().toString());
        }
        Indirizzo found = indirizzoService.findById(idIndirizzo);

        Indirizzo indirizzo = indirizzoService.findByIdAndUpdate(idIndirizzo,eventoDTO);
        return new ResponseDTO(indirizzo.getId());
    }
    @DeleteMapping("/{idIndirizzo}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable long idIndirizzo){
        Indirizzo found = indirizzoService.findById(idIndirizzo);
        indirizzoService.findByIdAndDelete(idIndirizzo);
    }


}
