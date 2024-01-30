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

import java.time.LocalDate;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
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

    //-----------------------SORTED
    @GetMapping("/sorted")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClienteSorted(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "nomeContatto") String sortedBy,
                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        return clienteService.getClienteSorted(page, size, sortedBy, sortOrder);
    }

    @GetMapping("/provincia-sede-legale")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClientiByProvinciaSedeLegale(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String provincia
    ) {
        return clienteService.findByProvinciaSedeLegale(provincia, page, size);
    }

    @GetMapping("/sorted-by-provincia")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClientiSortedByProvincia(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return clienteService.findAllOrderByProvincia(page, size);
    }

    //-----------------------FILTER
    @GetMapping("/filtered")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Cliente> getClienteFiltered(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) Double fatturatoAnnuale,
            @RequestParam(required = false) LocalDate dataInserimentoAfter,
            @RequestParam(required = false) LocalDate dataUltimoContattoAfter,
            @RequestParam(required = false) String parteDelNome,
            @RequestParam(required = false) String greaterOrLess
    ) {
        return clienteService.getClienteFiltered(page, size, sort, fatturatoAnnuale, dataInserimentoAfter, dataUltimoContattoAfter, greaterOrLess, parteDelNome);
    }


}
