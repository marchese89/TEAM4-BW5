package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.exceptions.BadRequestException;
import antoniogiovanni.marchese.TEAM4BW5.exceptions.UnauthorizedException;
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

import java.util.List;

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
        //dobbiamo verificare che non ci siano altri indirizzi dello stesso tipo per lo stesso cliente
        int numIndirizziStessoTipo = indirizzoService.countByUserIdAndType(indirizzoDTO.idCliente(), indirizzoDTO.tipoIndirizzo());
        if(numIndirizziStessoTipo == 0) {
            Indirizzo indirizzo = indirizzoService.save(indirizzoDTO);
            return new ResponseDTO(indirizzo.getId());
        }else{
            throw new UnauthorizedException("esiste già un indirizzo dello stesso tipo per questo utente e non puoi crearne altri");
        }
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

    @GetMapping("/getByIdCliente/{idCliente}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<Indirizzo> getByIdCliente(@PathVariable long idCliente){
        return indirizzoService.getByIdCliente(idCliente);
    }


}
