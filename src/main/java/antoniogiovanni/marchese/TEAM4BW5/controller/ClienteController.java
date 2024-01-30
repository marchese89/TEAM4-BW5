package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.exceptions.BadRequestException;
import antoniogiovanni.marchese.TEAM4BW5.model.Cliente;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ClienteDTO;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ClienteResponseDTO;
import antoniogiovanni.marchese.TEAM4BW5.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO saveCliente(@RequestBody @Validated ClienteDTO body, BindingResult validation) throws Exception {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList().toString());
        }
        Cliente nuovoCliente = clienteService.creaNuovoCliente(body);
        return new ClienteResponseDTO(nuovoCliente.getId());
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClienti(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return clienteService.getClienti(page, size, sortBy);
    }

    @GetMapping("/{clienteId}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Cliente findById(@PathVariable long clienteId) {
        return clienteService.findById(clienteId);
    }

    @PutMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ClienteResponseDTO getClienteByIdAndUpdate(@PathVariable long clienteId, @RequestBody @Validated ClienteDTO modifiedClientePayload, BindingResult validation) throws Exception {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList().toString());
        }

        Cliente cliente = clienteService.findByIdAndUpdate(clienteId, modifiedClientePayload);
        return new ClienteResponseDTO(cliente.getId());
    }

    @DeleteMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getClienteByIdAndDelete(@PathVariable long clienteId) {
        Cliente found = clienteService.findById(clienteId);
        clienteService.findByIdAndDelete(clienteId);
    }

    //----------------------------------------------- ORDER BY NOMECONTATTO ASC
    @GetMapping("/sorted-name-asc")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClienteSortedByNomeContattoAsc(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "nomeContatto") String sortBy) {
        return clienteService.getClienteSortedByNomeContattoAsc(page, size, sortBy);
    }

//    @GetMapping("/sorted-name")
//    public List<Cliente> getClienteSortedByNomeContatto() {
//        return clienteService.getClienteSortedByNomeContatto();
//    }

    //----------------------------------------------- ORDER BY NOMECONTATTO DESC

    @GetMapping("/sorted-name-desc")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClienteSortedByNomeContattoDesc(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "nomeContatto") String sortBy) {
        return clienteService.getClienteSortedByNomeContattoDesc(page, size, sortBy);
    }

    //----------------------------------------------- ORDER BY FATTURATOANNUALE ASC

    @GetMapping("/sorted-fatt-asc")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClienteSortedByFatturatoAnnualeAsc(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "fatturatoAnnuale") String sortBy) {
        return clienteService.getClienteSortedByFatturatoAnnualeAsc(page, size, sortBy);
    }
    //----------------------------------------------- ORDER BY FATTURATOANNUALE DESC

    @GetMapping("/sorted-fatt-desc")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClienteSortedByFatturatoAnnualeDesc(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "fatturatoAnnuale") String sortBy) {
        return clienteService.getClienteSortedByFatturatoAnnualeDesc(page, size, sortBy);
    }

    //----------------------------------------------- ORDER BY DATAINSERIMENTO ASC

    @GetMapping("/sorted-data-ins-asc")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClienteSortedByDataInserimentoAsc(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "dataInserimento") String sortBy) {
        return clienteService.getClienteSortedByDataInserimentoAsc(page, size, sortBy);
    }

    //----------------------------------------------- ORDER BY DATAINSERIMENTO DESC

    @GetMapping("/sorted-data-ins-desc")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClienteSortedByDataInserimentoDesc(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "dataInserimento") String sortBy) {
        return clienteService.getClienteSortedByDataInserimentoDesc(page, size, sortBy);
    }

    //----------------------------------------------- ORDER BY DATAULTIMOCONTATTO ASC

    @GetMapping("/sorted-data-cont-asc")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClienteSortedByDataUltimoContattoAsc(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "dataUltimoContatto") String sortBy) {
        return clienteService.getClienteSortedByDataUltimoContattoAsc(page, size, sortBy);
    }

    //----------------------------------------------- ORDER BY DATAULTIMOCONTATTO DESC

    @GetMapping("/sorted-data-cont-desc")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClienteSortedByDataUltimoContattoDesc(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "dataUltimoContatto") String sortBy) {
        return clienteService.getClienteSortedByDataUltimoContattoDesc(page, size, sortBy);
    }
//-----------------------FILTER
    //----------------------------------------------- FILTER BY FATTURATO MAGGIORE

    @GetMapping("/filter-fatt-great")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> findByFatturatoAnnualeGreaterThanEqual(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "fatturatoAnnuale") String sortBy, @RequestParam double fatturato) {
        return clienteService.findByFatturatoAnnualeGreaterThanEqual(fatturato, page, size, sortBy);
    }

    //----------------------------------------------- FILTER BY FATTURATO MINORE

    @GetMapping("/filter-fatt-less")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> findByFatturatoAnnualeLessThanEqual(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "fatturatoAnnuale") String sortBy, @RequestParam double fatturato) {
        return clienteService.findByFatturatoAnnualeLessThanEqual(fatturato, page, size, sortBy);
    }

}
